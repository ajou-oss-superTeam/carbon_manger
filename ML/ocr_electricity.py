# -*- coding: utf-8 -*-
from math import floor
from math import atan
import easyocr
import cv2

import numpy as np
import argparse

'''
Copyright 2022 Carbon_Developers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
'''

gap = 100
temp_multiply = 3

def parse():
    parser = argparse.ArgumentParser(prog="OCR", description='')
    parser.add_argument("-img_path", type=str, dest="img_path", action="store", default="null")
    parser.add_argument("-output_path", type=str, dest="output_path", action="store", default="null")
    parser.add_argument("-gpu_use", type=str, dest="gpu_use", action="store", default="True")

    args = parser.parse_args()
    return args

def is_horizontal(a, b):
    return True if (abs(a-b)<500) else False

def is_horizontal_v2(a_tr_x, a_tr_y, b_tl_x, b_tl_y, direction_x=1, direction_y=1):
    dist = ((a_tr_x-b_tl_x)**2 + (a_tr_x-a_tr_y)**2)**(1/2)
    direction_base = atan((a_tr_x-b_tl_x)/(a_tr_x-a_tr_y))
    direction_check = atan(direction_x/direction_y)

    return True if (dist<50) else False

def is_vertical_near(a, b):
    return True if (abs(a-b)<gap) else False

def is_within(start, height, check):
    return True if ((start<=check) and (check<=(start+height))) else False

def is_within_v2(value, check, interval):
    return True if ((value<=check+interval) and (value>=check-interval)) else False

def distance_between_point(a, b):
    return (((int(b[0])-int(a[0]))**2)+((int(b[1])-int(a[1]))**2))**(1/2)

def get_front_numeric(string):
    output_string = ''
    for ch in string:
        if(ch.isdigit()):
            output_string += ch
        elif(ch in ['o','O']):
            output_string += '0'
        elif(ch in [',','.']):
            continue
        else: 
            return output_string
    return output_string


def min_distance_between_box(a, b):
    min_dist = 99999
    for a_point in a:
        for b_point in b:
            dist =  distance_between_point(a_point,b_point)
            if(min_dist > dist):
                min_dist = dist
    return min_dist

def max_distance_between_box(a, b):
    max_dist = -1
    for a_point in a:
        for b_point in b:
            dist =  distance_between_point(a_point,b_point)
            if(max_dist < dist):
                max_dist = dist
    return max_dist

def distance_between(x1, y1, x2, y2):
    return (((x2-x1)**2)+((y2-y1)**2))**(1/2)

def rule_current_month_string(string):
    checked = False
    for check in ["월요금", "월요"]:
        if(check in string):
            checked = True
    return checked

def rule_electricity_usage_string(string):
    checked = False
    for check in ["력량"]:
        if(check in string):
            checked = True
    return checked

def rule_electricity_fee_string(string):
    checked = False
    for check in ["기요"]:
        if(check in string):
            checked = True
    return checked

def rule_environment_fee_string(string):
    checked = False
    for check in ["환경요","후환경"]:
        if(check in string):
            checked = True
    return checked

def rule_center_123_string(string):
    checked = False
    for check in ["고객센","고객샌","고객터","센터","샌터"]:
        if(check in string):
            checked = True
    return checked

def rule_usage_compare_string(string):
    usage_checked = False
    for check in ["사용","용량"]:
        if(check in string):
            usage_checked = True
    compare_checked = False
    for check in ["비교"]:
        if(check in string):
            compare_checked = True
    return True if (usage_checked and compare_checked) else False

def read_fee(blur_img, using_gpu):
    resize_width = (int)(blur_img.shape[0]*1.5*1.5)
    resize_height = (int)(blur_img.shape[1]*2*1.5)

    cropped= cv2.resize(blur_img, dsize=(resize_width,resize_height), interpolation=cv2.INTER_CUBIC)

    filter = np.ones((3,3),np.float32)/9
    cropped = cv2.filter2D(cropped, -1 , filter)

    reader = easyocr.Reader(['ko'],gpu=using_gpu)
    result = reader.readtext(cropped)

    numeric_texts = []
    numeric_texts_sorted = []

    item_texts = []

    count = 0
    for (bbox, text, prob) in result:

        (tl, tr, br, bl) = bbox
        tl = (int(tl[0]), int(tl[1]))
        tr = (int(tr[0]), int(tr[1]))
        br = (int(br[0]), int(br[1]))
        bl = (int(bl[0]), int(bl[1]))

        for erase_char in [","," ","'","."]:
            text = text.replace(erase_char,"")

        if(text.isnumeric()):
            count += 1
            numeric_texts.append([bbox, text, prob])
            numeric_texts_sorted.append([tl[1], bbox, text, prob])

        if(get_front_numeric(text) != ''):
            count += 1
            numeric_texts.append([bbox, get_front_numeric(text), prob])
            numeric_texts_sorted.append([tl[1], bbox, get_front_numeric(text), prob])

        else:
            if(len(text)>=3):
                item_texts.append([bbox, text.replace(' ',''), prob])

    numeric_texts_sorted = sorted(numeric_texts_sorted)
    numeric_texts = np.array(numeric_texts, dtype=object)

    for idx in range(0,len(numeric_texts_sorted)):
        if (idx>0):
            current_check_box = numeric_texts_sorted[idx-1][1]
            (a_tl, a_tr, a_br, a_bl) = current_check_box
            current_box = numeric_texts_sorted[idx][1]
            (b_tl, b_tr, b_br, b_bl) = current_box
            if(is_horizontal_v2(current_check_box[1][0],current_check_box[1][1],
                current_box[0][0],current_box[0][1])):
                numeric_texts_sorted[idx-1][2] += numeric_texts_sorted[idx][2]
                numeric_texts_sorted[idx-1][1][1] = numeric_texts_sorted[idx][1][1]
                numeric_texts_sorted[idx-1][1][2] = numeric_texts_sorted[idx][1][2]
                numeric_texts_sorted.remove(idx)
        else:
            continue

    numeric_group = []
    temp_group = []
    found = False
    for idx in range(0,len(numeric_texts_sorted)):
        if(idx == 0):
            temp_group.append(numeric_texts_sorted[idx])
            continue
        else:
            found = False
            for gidx in range(0,len(numeric_group)):
                for item in numeric_group[gidx]:
                    if(min_distance_between_box(item[1],numeric_texts_sorted[idx][1]) <= 1000*temp_multiply):
                        numeric_group[gidx].append(numeric_texts_sorted[idx]) 
                        found = True
                        break
                if(found):
                    break
            if(not found):
                for item in temp_group:
                    if(min_distance_between_box(item[1],numeric_texts_sorted[idx][1]) <= 1000*temp_multiply):
                        temp_group.append(numeric_texts_sorted[idx]) 
                        found = True
                        break
            if(not found):
                numeric_group.append(temp_group)
                temp_group = []
                temp_group.append(numeric_texts_sorted[idx])
                
    if(found):
        numeric_group.append(temp_group)

    numeric_group = np.array(numeric_group, dtype=object)

    output_string = ''
    center_123_box = []
    center_123_box_found = False
    usage_compare_box = []
    usage_compare_box_found = False

    for (bbox, text, prob) in item_texts:
        temp_string = ''
        if(rule_current_month_string(text)):
            temp_string += '"total_month_fee":"'
        elif(rule_electricity_fee_string(text)):
            temp_string += '"eletric_fee":"'
        elif(rule_electricity_usage_string(text)):
            temp_string += '"pure_eletric_fee":"'
        elif(rule_environment_fee_string(text)):
            temp_string += '"environment_fee":"'

        min_dist = 99999
        closest_text = ''
        if(temp_string != ''):
            for (numeric_bbox, numeric_text, numeric_prob) in numeric_texts:
                temp_dist = max_distance_between_box(numeric_bbox,bbox)
                if(temp_dist < min_dist):
                    min_dist = temp_dist
                    closest_text = numeric_text
            if(min_dist <= 3000*1.5*temp_multiply):
                temp_string += closest_text+'",'
                output_string += temp_string

        if(rule_center_123_string(text)):
            center_123_box = bbox
            center_123_box_found = True
        if(rule_usage_compare_string(text)):
            usage_compare_box = bbox
            usage_compare_box_found = True

    if(center_123_box_found):
        temp_close_numerics = []
        for (numeric_bbox, numeric_text, numeric_prob) in numeric_texts:
            temp_dist = min_distance_between_box(numeric_bbox,center_123_box)
            if((temp_dist < 2000*temp_multiply) and (len(numeric_text) > 1)):
                temp_close_numerics.append([temp_dist, numeric_text])
        temp_close_numerics = sorted(temp_close_numerics)

        for i in range(0,min(3,len(temp_close_numerics))):
            if(i==0):
                output_string += ('"last_year":"' + temp_close_numerics[i][1]+'",')
            if(i==1):
                output_string += ('"previous_month":"' + temp_close_numerics[i][1]+'",')
            if(i==2):
                output_string += ('"current_month":"' + temp_close_numerics[i][1]+'",')

    elif(usage_compare_box_found):
        temp_close_numerics = []
        for (numeric_bbox, numeric_text, numeric_prob) in numeric_texts:
            temp_dist = min_distance_between_box(numeric_bbox,usage_compare_box)
            if((temp_dist < 10000*temp_multiply) and (len(numeric_text) > 1) and (numeric_bbox[0][1] > usage_compare_box[0][1])):
                temp_close_numerics.append([temp_dist, numeric_text])
        temp_close_numerics = sorted(temp_close_numerics)

        for i in range(0,min(3,len(temp_close_numerics))):
            if(i==0):
                output_string += ('"current_month":"' + temp_close_numerics[i][1]+'",')
            if(i==1):
                output_string += ('"previous_month":"' + temp_close_numerics[i][1]+'",')
            if(i==2):
                output_string += ('"last_year":"' + temp_close_numerics[i][1]+'",')

    return output_string

def convert_gray_electric_img(img):
    r = img[:, :, 2]
    g = img[:, :, 1]
    b = img[:, :, 0]

    result = ((0.2 * r) + (0.3 * g) + ((0.5) * b))
    return result.astype(np.uint8)


def read_electric_bill(args):
    file_path = 'test3.jpg'
    if(args.img_path != 'null'):
        file_path = args.img_path 

    output_file_path = 'out.json'
    if(args.output_path != 'null'):
        output_file_path = args.output_path 

    using_gpu = False
    if(args.gpu_use == "True"):
        using_gpu = True

    try:
        img_raw = cv2.imread(file_path) 

        img = convert_gray_electric_img(img_raw)
        img = cv2.resize(img, dsize=(3600,2900*2), interpolation=cv2.INTER_CUBIC)

        ret, thresh1 = cv2.threshold(img,120,255, cv2.THRESH_BINARY_INV)
        kernel = np.ones((5, 5), np.uint8)
        thresh1 = cv2.dilate(thresh1,kernel,5)
        thresh1 = cv2.erode(thresh1,kernel,2)

        ret, thresh1 = cv2.threshold(thresh1,50,255, cv2.THRESH_BINARY_INV)

        filter = np.ones((1,1),np.float32)/1
        blur_img = cv2.filter2D(thresh1, -1 , filter)

    except:
        file = open(output_file_path,'w')
        file.write('{"done":false}')

        file.close()
        return 

    max_height, max_width = blur_img.shape

    json_result = '{'

    json_fee = read_fee(blur_img, using_gpu)
    json_result += json_fee

    json_result += '"done":true}'

    file = open(output_file_path,'w')
    file.write(json_result)

    file.close()


if __name__=="__main__":
    args = parse()
    read_electric_bill(args)