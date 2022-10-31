import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  Image,
  Alert,
} from 'react-native';

const NotLogin = ({ navigation: { navigate }, route }) => {
  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Image
          style={styles.logo}
          source={require('../assets/icon/logo.png')}
        />
        <Text style={styles.h1}>지금 탄소비서와</Text>
        <Text style={styles.h1}>지구 지키기를 실천하세요!</Text>
        <Text style={styles.h4}>나의 고지서부터 환경 점수 관리까지</Text>
      </View>
      <View style={styles.middle}></View>
      <View style={styles.footer}>
        <View style={styles.images}>
          <TouchableOpacity
            onPress={() =>
              Alert.alert(
                '기능명세서 범위에 해당되지 않는 기능입니다. 추후 발전시킬 예정입니다.'
              )
            }
          >
            <Image
              style={styles.image}
              source={require('../assets/images/facebook.jpeg')}
            />
          </TouchableOpacity>
          <TouchableOpacity
            onPress={() =>
              Alert.alert(
                '기능명세서 범위에 해당되지 않는 기능입니다. 추후 발전시킬 예정입니다.'
              )
            }
          >
            <Image
              style={styles.image}
              source={require('../assets/images/google.png')}
            />
          </TouchableOpacity>
          <TouchableOpacity
            onPress={() =>
              Alert.alert(
                '기능명세서 범위에 해당되지 않는 기능입니다. 추후 발전시킬 예정입니다.'
              )
            }
          >
            <Image
              style={styles.image}
              source={require('../assets/images/naver.png')}
            />
          </TouchableOpacity>
        </View>
        <View style={styles.buttons}>
          <TouchableOpacity onPress={() => navigate('login')}>
            <Text style={styles.emailLogin}>이메일로 로그인</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={() => navigate('signup')}>
            <Text style={styles.emailSignup}>이메일로 회원가입</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};

export default NotLogin;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  header: {
    flex: 4,
    justifyContent: 'center',
    alignItems: 'center',
  },
  logo: {
    width: 200,
    height: 200,
  },
  h1: {
    fontSize: 22,
    fontWeight: 'bold',
    marginBottom: 5,
  },
  h4: {
    fontSize: 15,
  },
  middle: { flex: 1 },
  footer: { flex: 3 },
  images: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'space-around',
    paddingLeft: 30,
    paddingRight: 30,
  },
  image: {
    width: 50,
    height: 50,
  },
  buttons: {
    flex: 2,
    alignItems: 'center',
    justifyContent: 'space-around',
  },
  emailLogin: {
    backgroundColor: 'rgb(211, 211, 211)',
    width: 200,
    height: 30,
    borderRadius: 10,
    paddingTop: 5,
    textAlign: 'center',
  },
  emailSignup: {
    width: 200,
    height: 30,
    borderRadius: 10,
    textAlign: 'center',
    textAlignVertical: 'center',
  },
});
