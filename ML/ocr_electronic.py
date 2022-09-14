# -*- coding: utf-8 -*-
from math import floor
import easyocr
import cv2

import numpy as np

import argparse

def parse():
    parser = argparse.ArgumentParser(prog="OCR", description='')
    parser.add_argument("-img_path", type=str, dest="img_path", action="store", default="null")
    parser.add_argument("-output_path", type=str, dest="output_path", action="store", default="null")

    args = parser.parse_args()
    return args

def is_horizontal(a, b):
    return True if (abs(a-b)<100) else False

def is_vertical_near(a, b):
    return True if (abs(a-b)<gap) else False

def is_within(start, height, check):
    return True if ((start<=check) and (check<=(start+height))) else False

def distance_between(x1, y1, x2, y2):
    return (((x2-x1)**2)+((y2-y1)**2))**(1/2)

gap = 400

def read_fee(blur_img):

    cropped = blur_img[800:2200,0:1500]

    resize_width = 4200
    resize_height = 4500
    cropped= cv2.resize(cropped, dsize=(resize_width,resize_height), interpolation=cv2.INTER_CUBIC)

    reader = easyocr.Reader(['ko'])
    result = reader.readtext(cropped)

    numeric_texts = []
    numeric_text_sectored = [ [],[],[]]

    count = 0
    for (bbox, text, prob) in result:

        (tl, tr, br, bl) = bbox
        tl = (int(tl[0]), int(tl[1]))
        tr = (int(tr[0]), int(tr[1]))
        br = (int(br[0]), int(br[1]))
        bl = (int(bl[0]), int(bl[1]))

        text = text.replace(",","")

        if(text.isnumeric()):
            count += 1
            numeric_texts.append([bbox, text, prob])

    numeric_texts = np.array(numeric_texts, dtype=object)

    vertical_sections = [0,0,0]
    section_size = resize_width/3
    for (bbox1, text1, prob1) in numeric_texts:
        (tl, tr, br, bl) = bbox1
        tl = (int(tl[0]), int(tl[1]))
        tr = (int(tr[0]), int(tr[1]))
        br = (int(br[0]), int(br[1]))
        bl = (int(bl[0]), int(bl[1]))

        center_x = (tr[1] + br[1])/2
        height = (br[1] - tr[1])
        right_x = tr[0]

        vertical_sections[min(floor(tr[0]/section_size),2)] += 1

        numeric_text_sectored[min(floor(tr[0]/section_size),2)].append([center_x, right_x, height,text1])

    for sector in numeric_text_sectored:
        sector = sorted(sector)

    re_sectors = []
    for sector in numeric_text_sectored:
        re_sector = []

        for (center_x,right_x, height, text) in sector:
            if(re_sector == []):
                re_sector.append([center_x,right_x,height,text])
            elif(is_horizontal(re_sector[len(re_sector)-1][0], center_x) == True):
                re_sector[len(re_sector)-1][3] = re_sector[len(re_sector)-1][3] + text
            else:
                re_sector.append([center_x,right_x,height,text])

        re_sectors.append(re_sector)

    numeric_text_sectored = re_sectors
    numeric_group = []

    for sector in numeric_text_sectored:
        temp_group = []

        for (middle_horizon_x,right_x,height, text) in sector:
            if(temp_group == []):
                temp_group.append([middle_horizon_x,right_x,height,text])
            elif(is_vertical_near(temp_group[len(temp_group)-1][0], middle_horizon_x) == True):
                temp_group.append([middle_horizon_x,right_x,height,text])
            else:
                numeric_group.append(temp_group)
                temp_group = []
                temp_group.append([middle_horizon_x,right_x,height,text])

        numeric_group.append(temp_group)

    numeric_group = np.array(numeric_group)

    output_string = ''
    for group in numeric_group:
        if len(group) >=5:
            init_x = group[0][0]
            total_height = group[len(group)-1][0] + (group[len(group)-1][2]/2) - (group[0][0] - (group[0][2]/2))

            total_item_count = floor(total_height/group[0][2])
            height_unit = total_height/total_item_count

            for item in group:
                for count in range(0, total_item_count):
                    if(is_within(init_x + (height_unit*count),height_unit, item[0])):

                        if(count == 0):
                            output_string += '"base_fee":"'+item[len(item)-1]+'",'
                        elif(count == 1):
                            output_string += '"pure_eletric_fee":"'+item[len(item)-1]+'",'
                        elif(count == 2):
                            output_string += '"environment_fee":"'+item[len(item)-1]+'",'
                        elif(count == 3):
                            output_string += '"fuel_fee":"'+item[len(item)-1]+'",'
                        elif(count == 4):
                            output_string += '"eletric_fee":"'+item[len(item)-1]+'",'
                        elif(count == 5):
                            output_string += '"VATS_fee":"'+item[len(item)-1]+'",'
                        elif(count == 6):
                            output_string += '"unknown_fee":"'+item[len(item)-1]+'",'
                        elif(count == 7):
                            output_string += '"cutoff_fee":"'+item[len(item)-1]+'",'
                        elif(count == 8):
                            output_string += '"total_month_fee":"'+item[len(item)-1]+'",'
                        elif(count == 9):
                            output_string += '"TV_fee":"'+item[len(item)-1]+'",'

    return output_string

def read_usage(blur_img):

    cropped = blur_img[800:3000,2000:]
    resize_width = 4200
    resize_height = 4500
    cropped= cv2.resize(cropped, dsize=(resize_width,resize_height), interpolation=cv2.INTER_CUBIC)

    reader = easyocr.Reader(['ko'])
    result = reader.readtext(cropped)

    numeric_texts = []
    check_loc_text = []

    check_center_x = -1
    check_center_y = -1

    count = 0
    # 숫자 텍스트만 넣기
    for (bbox, text, prob) in result:

        (tl, tr, br, bl) = bbox
        tl = (int(tl[0]), int(tl[1]))
        tr = (int(tr[0]), int(tr[1]))
        br = (int(br[0]), int(br[1]))
        bl = (int(bl[0]), int(bl[1]))

        text = text.replace(",","")
        if(text.isnumeric()):
            count += 1
            numeric_texts.append([bbox, text, prob])

        if("센터" in text):
            check_loc_text.append([bbox, text, prob])
            check_center_x = (tr[1] + br[1])/2
            check_center_y = (tr[0] + tl[0])/2

    numeric_closest = []
    for (bbox1, text1, prob1) in numeric_texts:
        (tl, tr, br, bl) = bbox1
        tl = (int(tl[0]), int(tl[1]))
        tr = (int(tr[0]), int(tr[1]))
        br = (int(br[0]), int(br[1]))
        bl = (int(bl[0]), int(bl[1]))

        center_x = (tr[1] + br[1])/2
        center_y = (tr[0] + tl[0])/2

        dist = distance_between(check_center_x, check_center_y, center_x, center_y)

        numeric_closest.append([dist,text1])

    numeric_closest = sorted(numeric_closest)
    numeric_closest = np.array(numeric_closest, dtype=object)

    output_string=''
    output_string += '"last_year":"'+numeric_closest[0][1]+'",'
    output_string += '"previous_month":"'+numeric_closest[1][1]+'",'
    output_string += '"current_month":"'+numeric_closest[2][1]+'",'

    return output_string


args = parse()

file_path = 'receipt1.jpg'
if(args.img_path != 'null'):
    file_path = args.img_path 

output_file_path = 'out.json'
if(args.output_path != 'null'):
    output_file_path = args.output_path 

img = cv2.imread(file_path, cv2.IMREAD_GRAYSCALE) 

ret, thresh1 = cv2.threshold(img,100,255, cv2.THRESH_BINARY)

filter = np.ones((5,5),np.float32)/25 

blur_img = cv2.filter2D(thresh1, -1 , filter)
blur_img = cv2.resize(blur_img, dsize=(3600,2900), interpolation=cv2.INTER_CUBIC)

max_height, max_width = blur_img.shape

json_result = '{'

json_fee = read_fee(blur_img)
json_result += json_fee

json_usage = read_usage(blur_img)
json_result += json_usage

json_result += '"done":true}'

file = open(output_file_path,'w')
file.write(json_result)

file.close()