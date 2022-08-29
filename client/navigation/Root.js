import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import Tabs from './Tabs';
import Stack from './Stack';

const Nav = createNativeStackNavigator();

const Root = () => (
  <Nav.Navigator>
    <Nav.Screen name="Tabs" component={Tabs} user={user} />
    <Nav.Screen name="Stack" component={Stack} user={user} />
  </Nav.Navigator>
);

export default Root;
