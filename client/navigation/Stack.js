import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Login from '../screens/Login';
import SignUp from '../screens/SignUp';

const NativeStack = createNativeStackNavigator();

// Login, SignUp
const Stack = () => (
  <NativeStack.Navigator>
    <NativeStack.Screen name="login" component={Login} />
    <NativeStack.Screen name="signup" component={SignUp} />
  </NativeStack.Navigator>
);

export default Stack;
