import { useEffect, useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableOpacity,
  Alert,
} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {
  NAVI_BG,
  NAVI_ITEM_DEFAULT,
  NAVI_ITEM_CLICK,
} from '../assets/variables/color';

const MyPage = ({ navigation: { navigate, replace } }) => {
  const [nickname, setNickname] = useState('user5');
  const [email, setEmail] = useState('test5@gmail.com');
  const [city, setCity] = useState('수원시');
  const [province, setProvince] = useState('경기도');
  const [totalCount, setTotalCount] = useState('10');

  useEffect(() => {
    checkUser();
  }, []);

  const checkUser = async () => {
    const user = await AsyncStorage.getItem('@user');
    const token = await AsyncStorage.getItem('@token');
  };

  const logout = async () => {
    await AsyncStorage.setItem('@user', '');
    replace('Stack', {
      screen: 'notlogin',
    });
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerText}>마이페이지</Text>
      </View>
      <View style={styles.body}>
        <Text style={styles.column}>닉네임: {nickname}</Text>
        <Text style={styles.column}>이메일: {email}</Text>
        <Text style={styles.column}>
          주소: {city} {province}
        </Text>
        <Text style={styles.column}>제출 횟수: {totalCount}</Text>
        <TouchableOpacity style={styles.logoutBtn} onPress={logout}>
          <Text style={styles.logoutBtnText}>로그아웃</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

export default MyPage;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  header: {
    flex: 1,
    alignItems: 'flex-start',
    justifyContent: 'flex-end',
    paddingLeft: 20,
    paddingRight: 20,
  },
  headerText: {
    color: 'black',
    fontSize: 30,
    fontWeight: 'bold',
  },
  body: {
    flex: 4,
    justifyContent: 'center',
    paddingTop: 20,
    paddingLeft: 20,
    paddingRight: 20,
    backgroundColor: 'white',
  },
  column: {
    fontSize: 20,
    marginBottom: 10,
    backgroundColor: 'green',
    borderRadius: 20,
    color: 'white',
    padding: 15,
  },
  logoutBtn: {
    alignItems: 'center',
    justifyContent: 'center',
    fontSize: 20,
    width: 100,
    height: 30,
    borderRadius: 30,
    backgroundColor: NAVI_ITEM_CLICK,
  },
  logoutBtnText: {
    color: 'white',
  },
});
