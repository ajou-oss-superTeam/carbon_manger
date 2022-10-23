/**
 *  Copyright 2022 Carbon_Developers
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableOpacity,
  Alert,
  Button,
} from 'react-native';
import { Camera } from 'expo-camera';
import DateTimePicker from '@react-native-community/datetimepicker';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { MaterialIcons } from '@expo/vector-icons';
import Spinner from 'react-native-loading-spinner-overlay';
import API from '../api';

const CameraScreen = ({
  navigation: { navigate, replace },
  route: {
    params: { type },
  },
}) => {
  // 카메라 페이지 여부
  const [camera, setCamera] = useState(false);
  // 카메라 객체 여부
  const [cameraObj, setCameraObj] = useState(null);
  // 이미지, base64
  const [imageUrl, setImageUri] = useState(null);
  const [base, setBase] = useState(null);
  // 권한
  const [permission, requestPermission] = useState(false);
  // 날짜
  const [showDate, setShowDate] = useState(false);
  // 로딩
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    (async () => {
      const cameraStatus = await Camera.requestCameraPermissionsAsync();
      requestPermission(cameraStatus.status === 'granted');
    })();
  }, []);

  const openCamera = async () => {
    if (permission) {
      setCamera(true);
    } else {
      Alert.alert('카메라 권한이 필요합니다.');
    }
  };

  const takePicture = async () => {
    if (cameraObj) {
      const data = await cameraObj.takePictureAsync({
        quality: 1,
        base64: true,
      });
      setImageUri(data.uri);
      setBase(data.base64);
      setCamera(false);
    }
  };

  const backToPage = () => {
    setCamera(false);
  };

  const submitImg = async () => {
    if (!imageUrl) {
      Alert.alert('이미지를 입력해주시기 바랍니다.');
      return;
    }

    setShowDate(true);
  };

  const requsetAPI = async (e) => {
    setShowDate(false);

    if (e.type === 'dismissed') {
      return;
    }

    const timestamp = e.nativeEvent.timestamp;
    const jsDate = new Date(timestamp);
    const year = jsDate.getFullYear();
    const month = jsDate.getMonth() + 1;

    const user = await AsyncStorage.getItem('@user');
    const token = await AsyncStorage.getItem('@token');

    const parseUser = JSON.parse(user);
    const parseToken = JSON.parse(token);
    const email = parseUser?.user?.email;

    // loading
    setLoading(true);

    if (type === '전기') {
      const { data, success, message } = await API.sendImg(
        email,
        imageUrl,
        base,
        year,
        month,
        parseToken
      );

      if (success) {
        navigate('Stack', {
          screen: 'score',
          params: { type, data, time: { year, month } },
        });
      } else {
        Alert.alert(message);
      }
    } else {
      const { data, success, message } = await API.sendGasImg(
        email,
        imageUrl,
        base,
        year,
        month,
        parseToken
      );

      if (success) {
        navigate('Stack', {
          screen: 'score',
          params: { type, data, time: { year, month } },
        });
      } else {
        Alert.alert(message);
      }
    }

    // finished
    setLoading(false);
  };

  const goToLink = () => {
    navigate('Stack', {
      screen: 'scoreedit',
      params: { type, data: {} },
    });
  };

  return camera ? (
    <View style={{ flex: 1 }}>
      <View style={styles.cameraContainer}>
        <Camera
          style={styles.fixedRatio}
          ref={(ref) => setCameraObj(ref)}
          ratio={'1:1'}
        />
      </View>
      <View style={styles.btns}>
        <TouchableOpacity
          onPress={takePicture}
          style={styles.catch}
          hitSlop={{ top: 100, bottom: 100, left: 100, right: 100 }}
        >
          <Button title="사진 찍기" />
        </TouchableOpacity>
        <TouchableOpacity
          onPress={backToPage}
          style={styles.backBtn}
          hitSlop={{ top: 100, bottom: 100, left: 100, right: 100 }}
        >
          <Button title="돌아가기" />
        </TouchableOpacity>
      </View>
    </View>
  ) : (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>
          {type ? type : '전기'} 고지서 촬영
        </Text>
        <Text style={styles.headerContent}>
          사각형 범위 내부에 <Text style={styles.red}>청구내역</Text>이
        </Text>
        <Text style={styles.headerContent}>들어가도록 맞추어 주세요</Text>
        {showDate && (
          <DateTimePicker
            mode="date"
            positiveButtonLabel="고지서 날짜 입력"
            negativeButtonLabel="닫기"
            value={new Date()}
            onChange={requsetAPI}
          />
        )}
      </View>
      <View style={styles.middle}>
        <Spinner
          visible={loading}
          textContent={'Loading...'}
          textStyle={styles.spinnerTextStyle}
        />
        <View style={styles.imgCover}>
          {imageUrl && <Image source={{ uri: imageUrl }} style={{ flex: 1 }} />}
        </View>
      </View>
      <View style={styles.footer}>
        <TouchableOpacity onPress={openCamera}>
          <MaterialIcons name="camera" size={50} color="black" />
        </TouchableOpacity>
        <View style={styles.footerBtns}>
          <TouchableOpacity onPress={submitImg} style={styles.footerBtn}>
            <Text style={styles.footerBtnText}>사진 제출하기</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={goToLink} style={styles.footerBtn}>
            <Text style={styles.footerBtnText}>직접 입력하기</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};

export default CameraScreen;

const styles = StyleSheet.create({
  // 카메라
  cameraContainer: {
    flex: 1,
    flexDirection: 'row',
  },
  fixedRatio: {
    flex: 1,
    aspectRatio: 1,
  },
  btns: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  catch: {
    width: 100,
    height: 100,
    marginBottom: 30,
  },
  backBtn: {
    width: 100,
    height: 100,
    zIndex: 1,
  },
  // 화면
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  header: {
    flex: 2,
    paddingTop: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
  headerTitle: {
    fontSize: 40,
    marginBottom: 10,
  },
  headerContent: {
    fontSize: 18,
  },
  red: {
    color: 'red',
  },
  middle: {
    flex: 3,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 10,
  },
  spinnerTextStyle: {
    color: '#FFF',
  },
  imgCover: {
    flex: 1,
    borderWidth: 3,
    width: '100%',
    backgroundColor: 'lightgrey',
  },
  exampleImg: {
    resizeMode: 'contain',
    flex: 1,
    aspectRatio: 1, // Your aspect ratio
  },
  footer: {
    flex: 2,
    paddingTop: 10,
    alignItems: 'center',
  },
  footerBtns: {
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-around',
    color: 'white',
    marginTop: 30,
  },
  footerBtn: {
    padding: 10,
    backgroundColor: 'rgb(52, 152, 219)',
    color: 'white',
    fontWeight: 'bold',
    borderRadius: 10,
  },
  footerBtnText: {
    color: 'white',
  },
});
