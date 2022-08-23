import { useState } from 'react';
import { View, Text } from 'react-native';

const Home = ({ navigation: { navigate } }) => {
  const [user, setUser] = useState(null);
  return user ? (
    <View>
      <Text>Home</Text>
    </View>
  ) : (
    navigate('Stack', 'notlogin')
  );
};

export default Home;
