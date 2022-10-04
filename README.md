# 탄소 비서 (Carbon Manager)

## What is it?

##### 탄소비서는 모바일, PC에서 웹으로 접근가능한 서비스로, 사용자의 요금 고지서(현재 전기요금서만 지원됩니다)를 읽어서 요금과 소모량을 기록합니다. 탄소비서는 이를 통해 탄소 소비량을 분석하고 평가를 해주어, 경각심을 유도하고 탄소 배출량을 줄이도록 하는 것을 목적으로 합니다.

## How it works?

##### 탄소비서는 [EasyOCR](https://github.com/JaidedAI/EasyOCR, 'easyocr link') 라이브러리를 활용하여 사용자의 요금 고지서를 읽을 수 있습니다. 카메라로 고지서를 탄소비서에서 알려주는 예시대로 촬영하면, 이미지 전처리 과정과, 읽어낸 텍스트의 재정리를 통해 각 요금과 사용량을 인식해냅니다.

## Structure of Carbon Manager

##### 탄소비서는 크게 Front-End client와 Back-end Server로 나눌 수 있습니다. 그 구조는 아래과 같습니다.

##### (이미지1.jpg)

## What's Next

##### 탄소비서는 앞으로 가스, 수도 등의 고지서도 읽을 예정입니다. 또한, 사용자의 소모량 추이 분석과 보다 더 많은 공공데이터를 활용하여, 사용자들에게 보다 다양하고 자세한 분석결과를 제공할 것입니다.

## Client 개발 환경 구축 방법

```
    // Expo cli 설치
    npm install -g expo@latest expo-cli@latest

    // Expo Login
    npx expo login -h

    // 클라이언트 이동
    cd client

    // 패키지 설치
    npm i

    // 패키시 시작
    npx expo start

    // 이후 터미널에 나오는 QR코드 Expo go 앱으로 촬영
```

## App version

    - 2022-10-05 1.0.0 (초기버전)
    - 2022-10-06 1.0.1 (로딩 추가)
