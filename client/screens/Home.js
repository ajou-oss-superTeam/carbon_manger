import { useEffect, useState } from 'react';
import { View, Text } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

const Home = ({ navigation: { replace } }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    checkUser();
  }, []);

  const checkUser = async () => {
    try {
      const user = await AsyncStorage.getItem('@user');
      if (user) {
        setUser(user);
      } else {
        replace('Stack', 'notlogin');
      }
    } catch (err) {
      console.error(err);
    }
  };

  const changePage = (user) => {
    setUser(user);
  };

  return (
    <View>
      <Text>Home</Text>
    </View>
  );
};

export default Home;
