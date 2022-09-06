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
import { MaterialIcons } from '@expo/vector-icons';
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
  // 이미지
  const [imageUrl, setImageUri] = useState(null);
  // 권한
  const [permission, requestPermission] = useState(false);

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
      const data = await cameraObj.takePictureAsync(null);
      setImageUri(data.uri);
      setCamera(false);
    }
  };

  const backToPage = () => {
    setCamera(false);
  };

  const submitImg = () => {
    if (!imageUrl) {
      Alert.alert('이미지를 입력해주시기 바랍니다.');
      return;
    }
    API.sendImg(imageUrl);
  };

  const goToLink = () => {
    navigate('Stack', {
      screen: 'camera',
      params: { type: type ? type : '전기' },
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
      </View>
      <View style={styles.middle}>
        <View style={styles.imgCover}>
          {imageUrl && <Image source={{ uri: imageUrl }} style={{ flex: 1 }} />}
        </View>
      </View>
      <View style={styles.footer}>
        <TouchableOpacity onPress={openCamera}>
          <MaterialIcons name="camera" size={50} color="black" />
        </TouchableOpacity>
        <View style={styles.footerBtn}>
          <TouchableOpacity onPress={submitImg}>
            <Text style={styles.footerBtnText}>사진 제출하기</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={goToLink}>
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
    flex: 1,
    paddingTop: 10,
    alignItems: 'center',
  },
  footerBtn: {
    flexDirection: 'row',
    justifyContent: 'space-around',
  },
  footerBtnText: {
    marginTop: 10,
    padding: 10,
    backgroundColor: 'rgb(52, 152, 219)',
    color: 'white',
    fontWeight: 'bold',
    borderRadius: 10,
  },
});
