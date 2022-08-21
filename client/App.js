import AppLoading from 'expo-app-loading';
import React, { useState } from 'react';
import { Text, Image, useColorScheme } from 'react-native';
import * as Font from 'expo-font';
import { Ionicons } from '@expo/vector-icons';
import { Asset, useAssets } from 'expo-asset';
import { NavigationContainer } from '@react-navigation/native';
import Tabs from './navigation/Tabs';

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
  // const [assets] = useAssets([require('./test.jpg')]);
  // const [loaded] = Font.useFonts(Ionicons.font);
  const [ready, setReady] = useState(false);
  const onFinish = () => setReady(true);

  const startLoading = async () => {
    const fonts = loadFonts([Ionicons.font]);
    const images = loadImages([
      require('./test.jpg'),
      // 'https://reactnavigation.org/img/spiro.svg',
    ]);
    await Promise.all([...fonts, ...images]);
  };
  // const isDark = useColorScheme() === 'dark';

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
      <Tabs />
    </NavigationContainer>
  );
}
