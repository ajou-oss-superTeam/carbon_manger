from math import floor
import easyocr
import cv2

import numpy as np
from matplotlib import pyplot as plt

import torch



import argparse

def parse():
    parser = argparse.ArgumentParser(prog="OCR", description='')
    parser.add_argument("-img_path", type=str, dest="img_path", action="store", default="null")
    parser.add_argument("-output_path", type=str, dest="output_path", action="store", default="null")

    args = parser.parse_args()
    return args

gap = 400

def is_horizontal(a, b):
    return True if (abs(a-b)<100) else False

def is_vertical_near(a, b):
    return True if (abs(a-b)<gap) else False

def is_within(start, height, check):
    return True if ((start<=check) and (check<=(start+height))) else False




args = parse()

file_path = 'receipt1.jpg'
if(args.img_path != 'null'):
    file_path = args.img_path 
    print('new path : '+ file_path)

output_file_path = 'out.txt'
if(args.output_path != 'null'):
    output_file_path = args.output_path 
    print('new output path : '+ output_file_path)


img = cv2.imread(file_path, cv2.IMREAD_GRAYSCALE) 

ret, thresh1 = cv2.threshold(img,100,255, cv2.THRESH_BINARY)

filter = np.ones((5,5),np.float32)/25 

blur_img = cv2.filter2D(thresh1, -1 , filter)

max_height, max_width = blur_img.shape

print(max_width, max_height)


cropped = blur_img[800:2200,0:1500]

resize_width = 4200
resize_height = 4500
cropped= cv2.resize(cropped, dsize=(resize_width,resize_height), interpolation=cv2.INTER_CUBIC)

reader = easyocr.Reader(['ko'])
result = reader.readtext(cropped)

print(cropped.shape)

cv2.namedWindow('Binary',cv2.WINDOW_NORMAL)
cv2.resizeWindow('Binary',500,400)
cv2.moveWindow('Binary',0,0)

numeric_texts = []
combined_text = []
numeric_text_sectored = [ [],[],[]]

count = 0
for (bbox, text, prob) in result:

    (tl, tr, br, bl) = bbox
    tl = (int(tl[0]), int(tl[1]))
    tr = (int(tr[0]), int(tr[1]))
    br = (int(br[0]), int(br[1]))
    bl = (int(bl[0]), int(bl[1]))

    right_end_top = tr[0]

    middle_horizon = (tr[1] + br[1])/2

    text = text.replace(",","")
    
    print("[INFO] {:.4f} {:.4f} {:.4f}: {}?".format(right_end_top, middle_horizon, prob, text))

    cv2.rectangle(cropped, tl, br, (0, 255, 0), 2)

    if(text.isnumeric()):
        count += 1
        numeric_texts.append([bbox, text, prob])

cv2.imshow('Binary',cropped)
cv2.waitKey(0)

numeric_texts = np.array(numeric_texts, dtype=object)

print(numeric_texts.shape)
print(count)

minimum_y_gap = 100000

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
temp_group = []

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
print(numeric_group)

init_x = 0

file = open(output_file_path,'w')
file.write('{')

for group in numeric_group:
    if len(group) >=5:
        init_x = group[0][0]

        print(len(group)-1)
        total_height = group[len(group)-1][0] + (group[len(group)-1][2]/2) - (group[0][0] - (group[0][2]/2))

        total_item_count = floor(total_height/group[0][2])
        height_unit = total_height/total_item_count
        print('height unit / count')
        print(height_unit)
        print(total_item_count)
        for item in group:
            for count in range(0, total_item_count):
                if(is_within(init_x + (height_unit*count),height_unit, item[0])):

                    print('found : ' + str(count))
                    if(count == 0):
                        file.write('"base_fee":"'+item[len(item)-1]+'",')
                    elif(count == 1):
                        file.write('"pure_eletric_fee":"'+item[len(item)-1]+'",')
                    elif(count == 2):
                        file.write('"environment_fee":"'+item[len(item)-1]+'",')
                    elif(count == 3):
                        file.write('"fuel_fee":"'+item[len(item)-1]+'",')
                    elif(count == 4):
                        file.write('"eletric_fee":"'+item[len(item)-1]+'",')
                    elif(count == 5):
                        file.write('"VATS_fee":"'+item[len(item)-1]+'",')
                    elif(count == 6):
                        file.write('"unknown_fee":"'+item[len(item)-1]+'",')
                    elif(count == 7):
                        file.write('"cutoff_fee":"'+item[len(item)-1]+'",')
                    elif(count == 8):
                        file.write('"total_month_fee":"'+item[len(item)-1]+'",')
                    elif(count == 9):
                        file.write('"TV_fee":"'+item[len(item)-1]+'",')
file.write('"done":true')
file.write('}')
file.close()
