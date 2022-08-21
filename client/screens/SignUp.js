import { Text, View, TouchableOpacity } from 'react-native';

const SignUp = ({ navigation: { navigate } }) => (
  <TouchableOpacity onPress={() => navigate('Two')}>
    <Text>go to two</Text>
  </TouchableOpacity>
);

export default SignUp;
