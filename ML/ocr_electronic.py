
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

gap = 400

def is_horizontal(a, b):
    return True if (abs(a-b)<100) else False

def is_vertical_near(a, b):
    return True if (abs(a-b)<gap) else False

def is_within(start, height, check):
    return True if ((start<=check) and (check<=(start+height))) else False


def distance_between(x1, y1, x2, y2):
    return (((x2-x1)**2)+((y2-y1)**2))**(1/2)

# cropped = blur_img[800:max_height,max_width-1500:max_width]

def read_fee(blur_img):

    cropped = blur_img[800:2200,0:1500]
    # 해상도 변경
    resize_width = 4200
    resize_height = 4500
    cropped= cv2.resize(cropped, dsize=(resize_width,resize_height), interpolation=cv2.INTER_CUBIC)

    reader = easyocr.Reader(['ko'])
    result = reader.readtext(cropped)
    # print(result)

    print(cropped.shape)

    # 여긴 그냥 가시화 부분이라 자바로 옮길 때는 없앨부분
    # cv2.namedWindow('Binary',cv2.WINDOW_NORMAL)
    # cv2.resizeWindow('Binary',500,400)
    # cv2.moveWindow('Binary',0,0)

    # 컨테이너 초기화
    numeric_texts = []
    combined_text = []
    numeric_text_sectored = [ [],[],[]]

    count = 0
    # 숫자 텍스트만 넣기
    for (bbox, text, prob) in result:

        (tl, tr, br, bl) = bbox
        tl = (int(tl[0]), int(tl[1]))
        tr = (int(tr[0]), int(tr[1]))
        br = (int(br[0]), int(br[1]))
        bl = (int(bl[0]), int(bl[1]))

        right_end_top = tr[0]

        middle_horizon = (tr[1] + br[1])/2

        text = text.replace(",","")
        
        # print("[INFO] {:.4f} {:.4f} {:.4f}: {}?".format(right_end_top, middle_horizon, prob, text))

        cv2.rectangle(cropped, tl, br, (0, 255, 0), 2)

        if(text.isnumeric()):
            count += 1
            numeric_texts.append([bbox, text, prob])

    # 이것도 가시화 부분
    # cv2.imshow('Binary',cropped)
    # cv2.waitKey(0)

    numeric_texts = np.array(numeric_texts, dtype=object)

    print(numeric_texts.shape)
    print(count)

    minimum_y_gap = 100000

    # 구역별 그룹화 하기
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

    # 구역별 순서 정렬 (위 -> 아래 방향)
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
    # print(numeric_group)

    init_x = 0


    output_string = ''
    for group in numeric_group:
        if len(group) >=5:
            init_x = group[0][0]

            # 전체 높이랑 첫번째 아이템 높이를 기반으로 대충 몇개가 들어가 있을지 추정 (gap이 무관되긴하네)
            total_height = group[len(group)-1][0] + (group[len(group)-1][2]/2) - (group[0][0] - (group[0][2]/2))

            total_item_count = floor(total_height/group[0][2])
            # total_item_count=len(group)
            height_unit = total_height/total_item_count
            print('height unit / count')
            print(height_unit)
            print(total_item_count)
            for item in group:
                for count in range(0, total_item_count):
                    if(is_within(init_x + (height_unit*count),height_unit, item[0])):

                        print('found : ' + str(count))
                        if(count == 0):
                            #file.write('"base_fee":"'+item[len(item)-1]+'",')
                            output_string += '"base_fee":"'+item[len(item)-1]+'",'
                        elif(count == 1):
                            #file.write('"pure_eletric_fee":"'+item[len(item)-1]+'",')
                            output_string += '"pure_eletric_fee":"'+item[len(item)-1]+'",'
                        elif(count == 2):
                            #file.write('"environment_fee":"'+item[len(item)-1]+'",')
                            output_string += '"environment_fee":"'+item[len(item)-1]+'",'
                        elif(count == 3):
                            #file.write('"fuel_fee":"'+item[len(item)-1]+'",')
                            output_string += '"fuel_fee":"'+item[len(item)-1]+'",'
                        elif(count == 4):
                            #file.write('"eletric_fee":"'+item[len(item)-1]+'",')
                            output_string += '"eletric_fee":"'+item[len(item)-1]+'",'
                        elif(count == 5):
                            #file.write('"VATS_fee":"'+item[len(item)-1]+'",')
                            output_string += '"VATS_fee":"'+item[len(item)-1]+'",'
                        elif(count == 6):
                            #file.write('"unknown_fee":"'+item[len(item)-1]+'",')
                            output_string += '"unknown_fee":"'+item[len(item)-1]+'",'
                        elif(count == 7):
                            #file.write('"cutoff_fee":"'+item[len(item)-1]+'",')
                            output_string += '"cutoff_fee":"'+item[len(item)-1]+'",'
                        elif(count == 8):
                            #file.write('"total_month_fee":"'+item[len(item)-1]+'",')
                            output_string += '"total_month_fee":"'+item[len(item)-1]+'",'
                        elif(count == 9):
                            #file.write('"TV_fee":"'+item[len(item)-1]+'",')
                            output_string += '"TV_fee":"'+item[len(item)-1]+'",'

    return output_string



def read_usage(blur_img):

    cropped = blur_img[800:3000,2000:]
    # 해상도 변경
    resize_width = 4200
    resize_height = 4500
    cropped= cv2.resize(cropped, dsize=(resize_width,resize_height), interpolation=cv2.INTER_CUBIC)

    reader = easyocr.Reader(['ko'])
    result = reader.readtext(cropped)
    # print(result)

    print(cropped.shape)

    # 여긴 그냥 가시화 부분이라 자바로 옮길 때는 없앨부분
    # cv2.namedWindow('Binary',cv2.WINDOW_NORMAL)
    # cv2.resizeWindow('Binary',500,400)
    # cv2.moveWindow('Binary',0,0)

    # 컨테이너 초기화
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

        right_end_top = tr[0]

        middle_horizon = (tr[1] + br[1])/2

        text = text.replace(",","")
        
        # print("[INFO] {:.4f} {:.4f} {:.4f}: {}?".format(right_end_top, middle_horizon, prob, text))

        cv2.rectangle(cropped, tl, br, (0, 255, 0), 2)

        if(text.isnumeric()):
            count += 1
            numeric_texts.append([bbox, text, prob])

        if('센터' in text):
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



    # 이것도 가시화 부분
    # cv2.imshow('Binary',cropped)
    # cv2.waitKey(0)
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
    print('new path : '+ file_path)

output_file_path = 'out.json'
if(args.output_path != 'null'):
    output_file_path = args.output_path 
    print('new output path : '+ output_file_path)


img = cv2.imread(file_path, cv2.IMREAD_GRAYSCALE) 

ret, thresh1 = cv2.threshold(img,100,255, cv2.THRESH_BINARY)

# model.load_state_dict(torch.load("C:\\Users\\netro\\OneDrive\\바탕 화면\\work\\ocr\\korean_g2.pth", map_location=torch.device('cpu')))# 

# 이미지 전처리 관련 기법
# 1. 흑백이미지로 변경한다 (어차피 eacyocr 모델도 자체적으로 할거다 (뭐하러 3차원 데이터를 쓰나))
# 2. 흑백으로 바꾸면 글자만 인식하도록 thresholding을 한다.
# 3. 숫자 텍스트만 뽑아낸다 (위치, 텍스트 내용)
# 4. 구역을 나눠서 그룹화를 시도한다 
# 5. 그룹 중 실제 요금 데이터를 찾아낸다

filter = np.ones((5,5),np.float32)/25 

blur_img = cv2.filter2D(thresh1, -1 , filter)
blur_img = cv2.resize(blur_img, dsize=(3600,2900), interpolation=cv2.INTER_CUBIC)

max_height, max_width = blur_img.shape

print(max_width, max_height)

json_result = '{'

json_fee = read_fee(blur_img)
json_result += json_fee

json_usage = read_usage(blur_img)
json_result += json_usage

json_result += '"done":true}'

file = open(output_file_path,'w')
file.write(json_result)

file.close()