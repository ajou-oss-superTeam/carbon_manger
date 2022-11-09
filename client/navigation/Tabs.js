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
        initialParams={{ hashValue: 1 }}
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
