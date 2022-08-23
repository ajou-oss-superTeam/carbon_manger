import { View, Text } from 'react-native';

const Home = ({ navigation: { navigate }, user }) => {
  return user ? (
    <View>
      <Text>Home</Text>
    </View>
  ) : (
    navigate('Stack', 'notlogin')
  );
};

export default Home;
