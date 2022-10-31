import React, { useState, useEffect, useCallback } from 'react';
import { View, Image } from 'react-native';
import * as SplashScreen from 'expo-splash-screen';
import * as Font from 'expo-font';
import { Ionicons } from '@expo/vector-icons';
import { Asset, useAssets } from 'expo-asset';
import { NavigationContainer } from '@react-navigation/native';
import Root from './navigation/Root';

SplashScreen.preventAutoHideAsync();

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

  useEffect(() => {
    const startLoading = async () => {
      try {
        const fonts = loadFonts([Ionicons.font]);
        const images = loadImages([
          require('./assets/icon/logo.png'),
          require('./assets/images/facebook.jpeg'),
          require('./assets/images/google.png'),
          require('./assets/images/naver.png'),
          require('./assets/images/gas.jpg'),
          require('./assets/images/elect.jpg'),
          require('./assets/images/water.jpg'),
        ]);
        await Promise.all([...fonts, ...images]);
      } catch (err) {
        console.error(err);
      } finally {
        setReady(true);
      }
    };

    startLoading();
  }, []);

  const onLayoutRootView = useCallback(async () => {
    if (ready) {
      await SplashScreen.hideAsync();
    }
  }, [ready]);

  if (!ready) {
    return null;
  } else {
    return (
      <NavigationContainer onReady={onLayoutRootView}>
        <Root />
      </NavigationContainer>
    );
  }
}
