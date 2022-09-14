import { View, Text, StyleSheet } from 'react-native';

const MyPage = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.prepare}>이 페이지는 개발 중 입니다.</Text>
    </View>
  );
};

export default MyPage;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  prepare: {
    textAlign: 'center',
    fontSize: 20,
  },
});
