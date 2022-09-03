import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Login from '../screens/Login';
import SignUp from '../screens/SignUp';
import NotLogin from '../screens/NotLogin';

const NativeStack = createNativeStackNavigator();

// NotLogin, Login, SignUp
const Stack = ({ navigation: { navigate }, route }) => {
  return (
    <NativeStack.Navigator
      screenOptions={{
        headerShown: false,
      }}
    >
      <NativeStack.Screen name="notlogin" component={NotLogin} />
      <NativeStack.Screen name="login" component={Login} />
      <NativeStack.Screen name="signup" component={SignUp} />
    </NativeStack.Navigator>
  );
};

export default Stack;
