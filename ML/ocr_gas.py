# -*- coding: utf-8 -*-
from math import floor
import easyocr
import cv2

import numpy as np

import argparse

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
            
def read_usage(blur_img):

    cropped = blur_img[0:2800,0:1500*2]

    resize_width = 4200
    resize_height = 4800 # 4500
    cropped= cv2.resize(blur_img, dsize=(resize_width,resize_height), interpolation=cv2.INTER_CUBIC)

    reader = easyocr.Reader(['ko', 'en'])
    result = reader.readtext(cropped)

    meter_unit_texts = []
    joule_unit_texts = []

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

        text = text.replace(" ","")

        if(check_rule_numeric_meteric(text)):
            meter_unit_texts.append([tl[1],bbox, text, prob])

        if(check_rule_numeric_m_joule(text)):
            joule_unit_texts.append([tl[1],bbox, text, prob])

    if(display_):
        cv2.resizeWindow('Binary',500,400)
        cv2.moveWindow('Binary',0,0)
        cv2.imshow('Binary',cropped)
        cv2.waitKey(0)

    meter_unit_texts = sorted(meter_unit_texts)
    joule_unit_texts = sorted(joule_unit_texts)
    meter_unit_texts = np.array(meter_unit_texts, dtype=object)
    joule_unit_texts = np.array(joule_unit_texts, dtype=object)


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

    return output_string

args = parse()

file_path = 'gas_example.jpg'
if(args.img_path != 'null'):
    file_path = args.img_path 

output_file_path = 'out.json'
if(args.output_path != 'null'):
    output_file_path = args.output_path 

img = cv2.imread(file_path, cv2.IMREAD_GRAYSCALE) 

ret, thresh1 = cv2.threshold(img,100,255, cv2.THRESH_BINARY)

filter = np.ones((5,5),np.float32)/25 

blur_img = cv2.filter2D(thresh1, -1 , filter)
blur_img = cv2.resize(blur_img, dsize=(3600,2900*2), interpolation=cv2.INTER_CUBIC)

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