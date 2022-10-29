import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Text, View, useColorScheme } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import Graph from '../screens/Graph';
import Home from '../screens/Home';
import MyPage from '../screens/MyPage';
import Picture from '../screens/Picture';
import {
  NAVI_BG,
  NAVI_ITEM_DEFAULT,
  NAVI_ITEM_CLICK,
} from '../assets/variables/color';

const Tab = createBottomTabNavigator();

// Home, Picture, Graph, MyPage
function Tabs() {
  return (
    <Tab.Navigator
      screenOptions={{
        tabBarStyle: { backgroundColor: NAVI_BG },
        tabBarActiveTintColor: NAVI_ITEM_CLICK,
        tabBarInactiveTintColor: NAVI_ITEM_DEFAULT,
        tabBarLabelStyle: {
          marginTop: -5,
        },
        headerStyle: {
          backgroundColor: NAVI_BG,
        },
        headerTitleStyle: {
          color: NAVI_ITEM_CLICK,
        },
        headerShown: false,
      }}
    >
      <Tab.Screen
        name="home"
        component={Home}
        options={{
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name="home"
              size={24}
              color={focused ? NAVI_ITEM_CLICK : NAVI_ITEM_DEFAULT}
            />
          ),
          headerShown: false,
        }}
      />
      <Tab.Screen
        name="picture"
        component={Picture}
        options={{
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name="camera"
              size={24}
              color={focused ? NAVI_ITEM_CLICK : NAVI_ITEM_DEFAULT}
            />
          ),
          headerShown: false,
        }}
        initialParams={{ type: '전기' }}
      />
      <Tab.Screen
        name="graph"
        component={Graph}
        options={{
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name="bar-chart"
              size={24}
              color={focused ? NAVI_ITEM_CLICK : NAVI_ITEM_DEFAULT}
            />
          ),
          headerShown: false,
        }}
      />
      <Tab.Screen
        name="mypage"
        component={MyPage}
        options={{
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name="person"
              size={24}
              color={focused ? NAVI_ITEM_CLICK : NAVI_ITEM_DEFAULT}
            />
          ),
          headerShown: false,
        }}
      />
    </Tab.Navigator>
  );
}

export default Tabs;

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
