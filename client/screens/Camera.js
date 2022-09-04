import { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableOpacity,
  Alert,
} from 'react-native';
import { Camera } from 'expo-camera';
import { MaterialIcons } from '@expo/vector-icons';

const Picture = ({
  navigation: { navigate, replace },
  route: {
    params: { type },
  },
}) => {
  // 카메라 페이지 여부
  const [camera, setCamera] = useState(false);
  // 카메라 객체 여부
  const [cameraObj, setCameraObj] = useState(null);
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
      const data = await camera.takePictureAsync(null);
      console.log(data);
      setImageUri(data);
    }
  };

  const backToPage = () => {
    setCamera(false);
  };

  const goToLink = () => {
    navigate('Stack', {
      screen: 'camera',
      params: { type: type ? type : '전기' },
    });
  };

  return camera ? (
    <View>
      <View>
        <Camera ref={(ref) => setCameraObj(ref)} ratio={'1:1'} />
      </View>
      <View>
        <TouchableOpacity onPress={takePicture}>
          <Text>사진 찍기</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={backToPage}>
          <Text>돌아가기</Text>
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
          {/* <Image
            style={styles.exampleImg}
            source={require('../assets/images/example.jpeg')}
          /> */}
        </View>
      </View>
      <View style={styles.footer}>
        <TouchableOpacity onPress={openCamera}>
          <MaterialIcons name="camera" size={50} color="black" />
        </TouchableOpacity>
        <TouchableOpacity style={styles.footerBtn}>
          <Text style={styles.footerBtnText}>직접 입력하기</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

export default Picture;

const styles = StyleSheet.create({
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
  footerBtnText: {
    marginTop: 10,
    padding: 10,
    backgroundColor: 'rgb(52, 152, 219)',
    color: 'white',
    fontWeight: 'bold',
    borderRadius: 10,
  },
});
