import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Login from '../screens/Login';
import SignUp from '../screens/SignUp';
import NotLogin from '../screens/NotLogin';

const NativeStack = createNativeStackNavigator();

// NotLogin, Login, SignUp
const Stack = ({ changePage }) => (
  <NativeStack.Navigator
    screenOptions={{
      headerShown: false,
    }}
  >
    <NativeStack.Screen
      name="notlogin"
      component={NotLogin}
      options={{ headerShown: false }}
    />
    <NativeStack.Screen
      name="login"
      component={Login}
      initialParams={{ changePage }}
    />
    <NativeStack.Screen
      name="signup"
      component={SignUp}
      initialParams={{ changePage }}
    />
  </NativeStack.Navigator>
);

export default Stack;
