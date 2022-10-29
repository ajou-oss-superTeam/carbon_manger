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
