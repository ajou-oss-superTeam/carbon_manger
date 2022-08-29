import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Text, View, useColorScheme } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import Graph from '../screens/Graph';
import Home from '../screens/Home';
import MyPage from '../screens/MyPage';
import Picture from '../screens/Picture';
import { NAVI_BG, NAVI_ITEM_DEFAULT, NAVI_ITEM_CLICK } from '../assets/color';

const Tab = createBottomTabNavigator();

// Home, Picture, Graph, MyPage
function Tabs({ user }) {
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
      }}
    >
      <Tab.Screen
        name="홈"
        component={Home}
        options={{
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name="home"
              size={24}
              color={focused ? NAVI_ITEM_CLICK : NAVI_ITEM_DEFAULT}
            />
          ),
        }}
      />
      <Tab.Screen
        name="고지서 등록하기"
        component={Picture}
        options={{
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name="camera"
              size={24}
              color={focused ? NAVI_ITEM_CLICK : NAVI_ITEM_DEFAULT}
            />
          ),
        }}
      />
      <Tab.Screen
        name="점수 보기"
        component={Graph}
        options={{
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name="bar-chart"
              size={24}
              color={focused ? NAVI_ITEM_CLICK : NAVI_ITEM_DEFAULT}
            />
          ),
        }}
      />
      <Tab.Screen
        name="마이페이지"
        component={MyPage}
        options={{
          tabBarIcon: ({ focused, color, size }) => (
            <Ionicons
              name="person"
              size={24}
              color={focused ? NAVI_ITEM_CLICK : NAVI_ITEM_DEFAULT}
            />
          ),
        }}
      />
    </Tab.Navigator>
  );
}

export default Tabs;
