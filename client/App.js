import AppLoading from 'expo-app-loading';
import React, { useState } from 'react';
import { Text, Image, useColorScheme } from 'react-native';
import * as Font from 'expo-font';
import { Ionicons } from '@expo/vector-icons';
import { Asset, useAssets } from 'expo-asset';
import { NavigationContainer } from '@react-navigation/native';
import Root from './navigation/Root';

const loadFonts = (fonts) => fonts.map((font) => Font.loadAsync(font));
const loadImages = (images) =>
  images.map((image) => {
    if (typeof image === 'string') {
      return Image.prefetch(image);
    } else {
      return Asset.loadAsync(image);
    }
  });

export default function App() {
  // @1 loading
  const [ready, setReady] = useState(false);
  const onFinish = () => setReady(true);
  const startLoading = async () => {
    const fonts = loadFonts([Ionicons.font]);
    const images = loadImages([
      require('./assets/icon/logo.png'),
      require('./assets/images/facebook.jpeg'),
      require('./assets/images/google.png'),
      require('./assets/images/naver.png'),
    ]);
    await Promise.all([...fonts, ...images]);
  };
  // const isDark = useColorScheme() === 'dark';

  // check loading
  if (!ready) {
    return (
      <AppLoading
        startAsync={startLoading}
        onFinish={onFinish}
        onError={console.error}
      />
    );
  }

  return (
    <NavigationContainer>
      <Root />
    </NavigationContainer>
  );
}
