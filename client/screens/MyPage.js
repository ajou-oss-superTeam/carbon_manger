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

const MyPage = ({ navigation: { navigate, replace } }) => {
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
      <TouchableOpacity style={styles.logoutBtn} onPress={logout}>
        <Text>로그아웃</Text>
      </TouchableOpacity>
    </View>
  );
};

export default MyPage;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  logoutBtn: {
    textAlign: 'center',
    fontSize: 20,
  },
});
