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
import API from '../api/index';
import {
  NAVI_BG,
  NAVI_ITEM_DEFAULT,
  NAVI_ITEM_CLICK,
} from '../assets/variables/color';

const MyPage = ({ navigation: { navigate, replace } }) => {
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [city, setCity] = useState('');
  const [province, setProvince] = useState('');
  const [totalCount, setTotalCount] = useState('');

  useEffect(() => {
    checkUser();
  }, []);

  const checkUser = async () => {
    const user = await AsyncStorage.getItem('@user');
    const token = await AsyncStorage.getItem('@token');

    const parseUser = JSON.parse(user);
    const parseToken = JSON.parse(token);

    // 마이페이지
    if (parseUser?.user?.email) {
      const { data, message, success } = await API.getMypage(
        parseUser?.user?.email,
        parseToken
      );

      if (success) {
        setNickname(data?.nickName);
        setEmail(data?.email);
        setCity(data?.city);
        setProvince(data?.province);
        setTotalCount(data?.totalCount);
      } else {
        Alert.alert(message);
      }
    }
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
          주소: {province} {city}
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
