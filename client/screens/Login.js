import { Text, View, TouchableOpacity, StyleSheet, Image } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useEffect } from 'react';

const Login = ({
  navigation: { navigate },
  route: {
    params: { changePage },
  },
}) => {
  useEffect(() => {
    // changePage();
  }, []);

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Ionicons
          name="arrow-back"
          size={35}
          color="black"
          onPress={() => navigate('notlogin')}
        />
        <Text style={styles.h1}>이메일로 로그인</Text>
      </View>
      <View style={styles.middle}></View>
      <View style={styles.footer}>
        <View style={styles.images}>
          <Image
            style={styles.image}
            source={require('../assets/images/facebook.jpeg')}
          />
          <Image
            style={styles.image}
            source={require('../assets/images/google.png')}
          />
          <Image
            style={styles.image}
            source={require('../assets/images/naver.png')}
          />
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

export default Login;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    paddingLeft: 20,
    paddingRight: 10,
  },
  header: {
    flex: 4,
    justifyContent: 'center',
    alignItems: 'flex-start',
  },
  h1: {
    fontSize: 22,
    fontWeight: 'bold',
    marginTop: 30,
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
