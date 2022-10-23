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

import {
  Text,
  View,
  TouchableOpacity,
  StyleSheet,
  TextInput,
  ScrollView,
  Alert,
} from 'react-native';
import { useEffect, useState } from 'react';
import Checkbox from 'expo-checkbox';
import { Ionicons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Picker } from '@react-native-picker/picker';
import { address } from '../assets/variables/address';
import API from '../api/index';

const SignUp = ({ navigation: { navigate, replace } }) => {
  const [email, onChangeEmail] = useState('');
  const [nickname, onChangeNickname] = useState('');
  const [password, onChangePassword] = useState('');
  const [passwordConfirm, onChangePasswordConfirm] = useState('');
  const [province, setProvince] = useState('강원도');
  const [cityArray, setCityArray] = useState(address.city.강원도);
  const [city, setCity] = useState('영월군');
  const [oldCheck, setOldCheck] = useState(false);
  const [serviceCheck, setServiceCheck] = useState(false);
  const [infoCheck, setInfoCheck] = useState(false);

  const signupOnPress = async () => {
    if (password !== passwordConfirm) {
      Alert.alert('비밀번호가 다릅니다.');
      return;
    }

    if (!oldCheck || !serviceCheck || !infoCheck) {
      Alert.alert('필수 체크 항목이 누락되었습니다.');
      return;
    }

    const { success, message } = await API.getSignup({
      email,
      nickname,
      password,
      province,
      city,
    });

    if (success) {
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
        <Text style={styles.h1}>이메일로 회원가입</Text>
      </View>
      <View style={styles.middle}>
        <ScrollView style={styles.scrollView}>
          <Text style={styles.label}>이메일 주소</Text>
          <TextInput
            style={styles.input}
            value={email}
            onChangeText={onChangeEmail}
            placeholder="내용을 입력해주세요"
            autoComplete="email"
          />
          <Text style={styles.label}>닉네임</Text>
          <TextInput
            style={styles.input}
            value={nickname}
            onChangeText={onChangeNickname}
            placeholder="내용을 입력해주세요"
            autoComplete="name"
          />
          <Text style={styles.label}>비밀번호</Text>
          <TextInput
            style={styles.input}
            value={password}
            onChangeText={onChangePassword}
            placeholder="내용을 입력해주세요"
            autoComplete="password"
            secureTextEntry={true}
          />
          <Text style={styles.label}>비밀번호 확인</Text>
          <TextInput
            style={styles.input}
            value={passwordConfirm}
            onChangeText={onChangePasswordConfirm}
            placeholder="내용을 입력해주세요"
            autoComplete="password"
            secureTextEntry={true}
          />
          <Text style={styles.label}>시도</Text>
          <View style={styles.picker}>
            <Picker
              selectedValue={province}
              onValueChange={(itemValue, itemIndex) => {
                setProvince(itemValue);
                setCityArray(address.city[itemValue]);
              }}
            >
              {address.province.map((item) => {
                return <Picker.Item key={item} label={item} value={item} />;
              })}
            </Picker>
          </View>
          <Text style={styles.label}>구</Text>
          <View style={styles.picker}>
            <Picker
              selectedValue={city}
              onValueChange={(itemValue, itemIndex) => setCity(itemValue)}
            >
              {cityArray.map((item) => {
                return <Picker.Item key={item} label={item} value={item} />;
              })}
            </Picker>
          </View>
          <View style={styles.check}>
            <Text>만 14세 이상입니다(필수)</Text>
            <Checkbox
              style={styles.checkbox}
              value={oldCheck}
              onValueChange={setOldCheck}
              color={oldCheck ? '#4630EB' : undefined}
            />
          </View>
          <View style={styles.check}>
            <Text>서비스 이용 약관(필수)</Text>
            <Checkbox
              style={styles.checkbox}
              value={serviceCheck}
              onValueChange={setServiceCheck}
              color={serviceCheck ? '#4630EB' : undefined}
            />
          </View>
          <View style={styles.check}>
            <Text>개인정보 수집 및 이용 동의(필수)</Text>
            <Checkbox
              style={styles.checkbox}
              value={infoCheck}
              onValueChange={setInfoCheck}
              color={infoCheck ? '#4630EB' : undefined}
            />
          </View>
        </ScrollView>
      </View>
      <View style={styles.footer}>
        <View style={styles.buttons}>
          <TouchableOpacity style={styles.buttonLogin} onPress={signupOnPress}>
            <Text style={styles.login}>확인</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};

export default SignUp;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    paddingLeft: 20,
    paddingRight: 20,
  },
  header: {
    flex: 1.5,
    justifyContent: 'center',
    alignItems: 'flex-start',
  },
  h1: {
    fontSize: 22,
    fontWeight: 'bold',
    marginTop: 30,
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
  picker: {
    marginBottom: 30,
    border: 1,
    borderWidth: 1,
    borderRadius: 10,
  },
  middle: { flex: 3 },
  scrollView: {},
  check: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  checkbox: {
    marginBottom: 10,
    fontSize: 40,
  },
  footer: { flex: 1 },
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
});
