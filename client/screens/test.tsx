function Camera({navigation, route}: SignUpScreenProps) {
    const leftMargin = Platform.OS === 'android' ? 100 : 150;
    const topMargin = 60;
    const frameWidth = 200;
    const frameHeight = 250;

const chartHeight = Dimensions.get('window').height;
const chartWidth = Dimensions.get('window').width;

if (Platform.OS === 'android') {
  chartAndroidWidth = chartWidth;
  chartAndroidHeigth = chartHeight;
} else if (Platform.OS === 'ios') {
  chartIosWidth = chartWidth;
  chartIosHeigth = chartHeight;
}

const ref = React.createRef();

const [image, setImage] = useState<{
  uri: string;
  name: string;
  type: string;
}>();
const [preview, setPreview] = useState<{uri: string}>();

const takePicture = async function () {
  const options = {quality: 0.5, base64: true};
  const data = await ref.current.takePictureAsync(options);
  //  eslint-disable-next-line
  onResponse(data);
};

var wineNo = '';
const onResponse = useCallback(async (response: any) => {
  console.log('response : ' + response);
  console.log(response.width, response.height, response.exif);
  var body = new FormData();
  var imgRatio = 0.0;
  var convHeight = 0;
  var convWidth = 0;
  if (response.width > response.height) {
    imgRatio = response.width / 1024;
  } else {
    imgRatio = response.height / 1024;
  }

  convHeight = Number(response.height) / imgRatio;
  convWidth = Number(response.width) / imgRatio;

  return ImageResizer.createResizedImage(
    response.uri,
    parseInt(String(convWidth)),
    parseInt(String(convHeight)),
    response.uri.includes('jpeg') ? 'JPEG' : 'JPEG',
    100,
    0,
  ).then(r => {
    console.log('uri : ' + r.uri);
    console.log('r.name : ' + r.name);

    setImage({
      uri: r.uri,
      name: r.name,
      type: 'multipart/form-data',
    });

    var photoType = Platform.OS === 'android' ? 'image/jpeg' : 'image/jpg';
    var photo = {
      uri: r.uri,
      name: r.name,
      type: photoType,
    };
    body.append('file', photo);
    body.append('recog_text', 'porto white');

    var fileDateUrl = '';
    axios
      .get(서버 uri)
      .then(function (data) {
        console.log('tensorUrl2 : ' + data.data.tensorUrl);
        fileDateUrl = data.data.tensorUrl;

        axios
          .post(fileDateUrl, body, {
            headers: {'content-type': 'multipart/form-data'},
          })
          .then(function (data2) {
            wNo = data2.data.wNo;
            console.log('데이터 유알 wNo2 : ' + wNo);
            
          })
          .catch(function (error2) {
            console.log('데이터 에러 : ' + error2);
          });
      })
      .catch(function (error) {
        console.log(error);
      });
  });
}, []);

return (
  <View>
    <RNCamera
      ref={ref}
      style={{
        width: Platform.OS === 'android' ? chartAndroidWidth : chartIosWidth,
        height:
          Platform.OS === 'android' ? chartAndroidHeigth : chartIosHeigth,
      }}
      captureAudio={false}
      type={RNCamera.Constants.Type.back}
      androidCameraPermissionOptions={{
        title: 'Permission to use camera',
        message: 'We need your permission to use your camera',
        buttonPositive: 'Ok',
        buttonNegative: 'Cancel',
      }}
      androidRecordAudioPermissionOptions={{
        title: 'Permission to use audio recording',
        message: 'We need your permission to use your audio',
        buttonPositive: 'Ok',
        buttonNegative: 'Cancel',
      }}
    />

    <View
      style={{
        position: 'absolute',
        top: leftMargin,
        right: topMargin,
        width: frameHeight,
        height: frameWidth,
        borderWidth: 2,
        borderColor: 'white',
        opacity: 0.5,
      }}>
      <Text
        style={{
          fontSize: 20,
          color: 'white',
          fontWeight: 'bold',
          alignItems: 'center',
        }}>
        라벨을 촬영해주세요
      </Text>
    </View>

    <View style={[styles.overlay, styles.bottomOverlay]}>
      <TouchableOpacity
        onPress={() => takePicture().then(console.log('gdgd'))}>
        <Image
          source={require('../images/camera.png')}
          style={{width: 50, height: 50}}></Image>
      </TouchableOpacity>
    </View>
  </View>
);
}