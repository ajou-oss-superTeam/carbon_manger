import {
  Text,
  View,
  TouchableOpacity,
  StyleSheet,
  TextInput,
  Alert,
} from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useEffect, useState } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import API from '../api/index';

const Login = ({ navigation: { navigate, replace }, route: { params } }) => {
  const [email, onChangeEmail] = useState('');
  const [password, onChangePassword] = useState('');

  const loginOnPress = async () => {
    const { user, success, message, token } = await API.getLogin({
      email,
      password,
    });

    if (success) {
      await AsyncStorage.setItem('@user', JSON.stringify({ user }));
      await AsyncStorage.setItem('@token', JSON.stringify({ token }));
      replace('Tabs', {
        screen: 'Home',
      });
    } else {
      Alert.alert(message);
    }
  };

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
      <View style={styles.middle}>
        <Text style={styles.label}>이메일 주소</Text>
        <TextInput
          style={styles.input}
          value={email}
          onChangeText={onChangeEmail}
          placeholder="내용을 입력해주세요"
          autoComplete="email"
        />
        <Text style={styles.label}>비밀번호</Text>
        <TextInput
          style={styles.input}
          value={password}
          onChangeText={onChangePassword}
          placeholder="내용을 입력해주세요"
          autoComplete="password"
          visible-password={true}
          secureTextEntry={true}
        />
      </View>
      <View style={styles.footer}>
        <View style={styles.buttons}>
          <TouchableOpacity style={styles.buttonLogin} onPress={loginOnPress}>
            <Text style={styles.login}>로그인</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={styles.buttonfindPassord}
            onPress={() =>
              Alert.alert(
                '기능명세서 범위에 해당되지 않는 기능입니다. 추후 발전시킬 예정입니다.'
              )
            }
          >
            <Text style={styles.findPassword}>비밀번호를 잊으셨나요?</Text>
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
    paddingRight: 20,
  },
  header: {
    flex: 2,
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
  label: {
    fontSize: 18,
    marginBottom: 15,
  },
  input: {
    marginBottom: 30,
    border: 1,
    borderWidth: 1,
    borderRadius: 10,
    padding: 10,
  },
  middle: { flex: 3 },
  footer: { flex: 2 },
  buttons: {
    alignItems: 'center',
  },
  buttonLogin: {
    backgroundColor: 'rgb(26, 188, 156)',
    width: '100%',
    height: 40,
    borderRadius: 10,
    justifyContent: 'center',
    marginBottom: 20,
  },
  login: {
    alignSelf: 'center',
    color: 'white',
    fontSize: 20,
    fontWeight: 'bold',
  },
  buttonfindPassord: {
    justifyContent: 'center',
    height: 40,
  },
  findPassword: {
    color: 'rgb(26, 188, 156)',
    fontSize: 18,
    textDecorationLine: 'underline',
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
