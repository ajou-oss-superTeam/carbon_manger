# -*- coding: utf-8 -*-
from math import floor
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

display_ = False

def parse():
    parser = argparse.ArgumentParser(prog="OCR", description='')
    parser.add_argument("-img_path", type=str, dest="img_path", action="store", default="null")
    parser.add_argument("-output_path", type=str, dest="output_path", action="store", default="null")

    args = parser.parse_args()
    return args

def check_rule_numeric_meteric(string):
    numeric_front_only = False
    metric_back = False
    for char in string:
        if(char.isdigit() or (char in ['.',','])):
            numeric_front_only = True
            if(metric_back):
                return False
        elif(char in ['m','M']):
            metric_back = True
        else:
            if(not (numeric_front_only and metric_back)):
                return False    
    return True if (numeric_front_only and metric_back) else False


def check_rule_numeric_m_joule(string):
    numeric_front_only = False
    mj_stirng_front = False
    mj_stirng_back = False
    for char in string:
        if(char.isdigit() or (char in ['.',',','O','o'])):
            numeric_front_only = True
            if(mj_stirng_front or mj_stirng_back):
                return False
        elif(char in ['m','M']):
            mj_stirng_front = True
        elif(mj_stirng_front and (char in ['j','J'])):
            mj_stirng_back = True
        else:
            if(not (numeric_front_only and mj_stirng_front and mj_stirng_back)):
                return False    
    return True if (numeric_front_only and mj_stirng_front and mj_stirng_back) else False

def check_rule_numeric_won(string):
    won_last = False
    numeric_front = False
    pure_numeric = ''
    if(string[len(string)-1] == "원"):
        won_last = True
    if(string[0] in ['~','-']):
        if(check_pure_numeric(string[1:len(string)-2])):
            numeric_front = True
    else:
        pure_numeric = get_pure_numeric(string)
        if(check_pure_numeric(string[0:len(string)-2])):
            numeric_front = True
            
    return True if(won_last and numeric_front) else False

def get_pure_numeric(string):
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

def check_pure_numeric(string):
    for ch in string:
        if(ch.isdigit()):
            continue
        elif(ch in ['o','O']):
            continue
        elif(ch in [',','.']):
            continue
        else: 
            return False
    return True

def distance_between_point(a, b):
    return (((int(b[0])-int(a[0]))**2)+((int(b[1])-int(a[1]))**2))**(1/2)

def min_distance_between_box(a, b):
    min_dist = 99999
    for a_point in a:
        for b_point in b:
            dist =  distance_between_point(a_point,b_point)
            if(min_dist > dist):
                min_dist = dist
    return min_dist


def read_usage(blur_img):

    cropped = blur_img[0:2800,0:1500*2]

    resize_width = 4200
    resize_height = 4800 # 4500
    cropped= cv2.resize(blur_img, dsize=(resize_width,resize_height), interpolation=cv2.INTER_CUBIC)

    reader = easyocr.Reader(['ko', 'en'])
    result = reader.readtext(cropped)

    meter_unit_texts = []
    joule_unit_texts = []
    won_unit_texts = []

    if(display_):
        cv2.namedWindow('Binary',cv2.WINDOW_NORMAL)

    for (bbox, text, prob) in result:

        (tl, tr, br, bl) = bbox
        tl = (int(tl[0]), int(tl[1]))
        tr = (int(tr[0]), int(tr[1]))
        br = (int(br[0]), int(br[1]))
        bl = (int(bl[0]), int(bl[1]))
        if(display_):
            cv2.rectangle(cropped, tl, br, (0, 255, 0), 2)
            print(text)

        text = text.replace(" ","")

        if(check_rule_numeric_meteric(text)):
            meter_unit_texts.append([tl[1],bbox, text, prob])

        if(check_rule_numeric_m_joule(text)):
            joule_unit_texts.append([tl[1],bbox, text, prob])
        
        if(check_rule_numeric_won(text)):
            won_unit_texts.append([tl[1],bbox, text, prob])

    if(display_):
        cv2.resizeWindow('Binary',500,400)
        cv2.moveWindow('Binary',0,0)
        cv2.imshow('Binary',cropped)
        cv2.waitKey(0)

    meter_unit_texts = sorted(meter_unit_texts)
    joule_unit_texts = sorted(joule_unit_texts)
    won_unit_texts = sorted(won_unit_texts)
    meter_unit_texts = np.array(meter_unit_texts, dtype=object)
    joule_unit_texts = np.array(joule_unit_texts, dtype=object)
    #won_unit_texts = np.array(won_unit_texts, dtype=object)

    output_string = ''
    meter_unit_count = 0
    for (loc, bbox, text, prob) in meter_unit_texts:
        if(meter_unit_count==0):
            output_string += '"accumulated_month_usage":"'+str(get_pure_numeric(text))+'",'
        if(meter_unit_count==1):
            output_string += '"previous_month_usage":"'+str(get_pure_numeric(text))+'",'
        if(meter_unit_count==2):
            output_string += '"checked_usage":"'+str(get_pure_numeric(text))+'",'
        if(meter_unit_count==3):
            output_string += '"current_month_usage":"'+str(float(get_pure_numeric(text))/10000)+'",'
        
        meter_unit_count += 1

    joule_unit_count = 0
    for (loc, bbox, text, prob) in joule_unit_texts:
        if(joule_unit_count==0):
            output_string += '"unit_energy":"'+str(float(get_pure_numeric(text))/10000)+'",'
        if(joule_unit_count==1):
            output_string += '"used_energy":"'+str(float(get_pure_numeric(text))/10000)+'",'     
        joule_unit_count += 1


    numeric_won_group = []
    temp_group = []
    for idx in range(0,len(won_unit_texts)):
        if(idx == 0):
            temp_group.append(won_unit_texts[idx])
            continue
        else:
            found = False
            for gidx in range(0,len(numeric_won_group)):
                for item in numeric_won_group[gidx]:
                    if(min_distance_between_box(item[1],won_unit_texts[idx][1]) <= 300):
                        numeric_won_group[gidx].append(won_unit_texts[idx]) 
                        found = True
                        break
                if(found):
                    break
            if(not found):
                for item in temp_group:
                    if(min_distance_between_box(item[1],won_unit_texts[idx][1]) <= 300):
                        temp_group.append(won_unit_texts[idx]) 
                        found = True
                        break
            if(not found):
                numeric_won_group.append(temp_group)
                temp_group = []
                temp_group.append(won_unit_texts[idx])

    if(len(numeric_won_group) == 0):
        numeric_won_group.append(temp_group)

    target_group = []
    for group in numeric_won_group:
        if((len(group)>=4)and(len(group)<6)):
            target_group = group
    
    won_unit_count = 0
    for (loc, bbox, text, prob) in target_group:
        if(won_unit_count==0):
            output_string += '"demandCharge":"'+str(get_pure_numeric(text))+'",'
        if(won_unit_count==1):
            output_string += '"totalbyCurrMonth":"'+str(get_pure_numeric(text))+'",'
        if(won_unit_count==2):
            output_string += '"vat":"'+str(get_pure_numeric(text))+'",'
        if(won_unit_count==3):
            output_string += '"roundDown":"-'+str(get_pure_numeric(text[1:]))+'",'
        if(won_unit_count==4):
            output_string += '"totalPrice":"'+str(get_pure_numeric(text))+'",'
        won_unit_count += 1

    return output_string

def read_gas_recipt(args):
    file_path = 'gas_example.jpg'
    if(args.img_path != 'null'):
        file_path = args.img_path 

    output_file_path = 'out.json'
    if(args.output_path != 'null'):
        output_file_path = args.output_path 

    try:
        img = cv2.imread(file_path, cv2.IMREAD_GRAYSCALE) 

        ret, thresh1 = cv2.threshold(img,100,255, cv2.THRESH_BINARY)

        filter = np.ones((5,5),np.float32)/25 

        blur_img = cv2.filter2D(thresh1, -1 , filter)
        blur_img = cv2.resize(blur_img, dsize=(3600,2900*2), interpolation=cv2.INTER_CUBIC)

    except:
        file = open(output_file_path,'w')
        file.write('{"done":false}')

        file.close()
        return 

    max_height, max_width = blur_img.shape

    json_result = '{'

    if(display_):
        cv2.namedWindow('Binary',cv2.WINDOW_NORMAL)
        cv2.resizeWindow('Binary',500,400)
        cv2.moveWindow('Binary',0,0)
        cv2.imshow('Binary',blur_img)
        cv2.waitKey(0)


    json_fee = read_usage(blur_img)
    json_result += json_fee

    json_result += '"done":true}'

    file = open(output_file_path,'w')
    file.write(json_result)

    file.close()


if __name__=="__main__":
    args = parse()
    read_gas_recipt(args)