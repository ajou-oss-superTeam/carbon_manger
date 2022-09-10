import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Login from '../screens/Login';
import SignUp from '../screens/SignUp';
import NotLogin from '../screens/NotLogin';
import Camera from '../screens/Camera';
import Score from '../screens/Score';
import ScoreEdit from '../screens/ScoreEdit';

const NativeStack = createNativeStackNavigator();

// NotLogin, Login, SignUp, Camera
const Stack = () => {
  return (
    <NativeStack.Navigator
      screenOptions={{
        headerShown: false,
      }}
    >
      {/* 비로그인 이용 페이지 */}
      <NativeStack.Screen name="notlogin" component={NotLogin} />
      <NativeStack.Screen name="login" component={Login} />
      <NativeStack.Screen name="signup" component={SignUp} />
      {/* 카메리 추가 페이지 */}
      <NativeStack.Screen name="camera" component={Camera} />
      <NativeStack.Screen name="score" component={Score} />
      <NativeStack.Screen name="scoreedit" component={ScoreEdit} />
    </NativeStack.Navigator>
  );
};

export default Stack;
