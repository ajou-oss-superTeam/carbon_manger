import { Text, View, TouchableOpacity } from 'react-native';

const Login = ({ navigation: { navigate } }) => (
  <TouchableOpacity onPress={() => navigate('Two')}>
    <Text>go to two</Text>
  </TouchableOpacity>
);

export default Login;
