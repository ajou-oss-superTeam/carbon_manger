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
