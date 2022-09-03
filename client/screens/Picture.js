import { useEffect } from 'react';
import { View, Text, StyleSheet } from 'react-native';

const Picture = ({
  navigation: { navigate, replace },
  route: {
    params: { type },
  },
}) => {
  return (
    <View style={styles.container}>
      <View>
        <Text style={styles.header}>{type ? type : '전기'} 고지서 촬영</Text>
        <Text>
          사각형 범위 내부에 <Text>청구내역</Text>이 들어가도록 맞추어 주세요
        </Text>
      </View>
      <View></View>
      <View>
        <Text></Text>
        <Text></Text>
      </View>
    </View>
  );
};

export default Picture;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  header: {
    flex: 1,
    backgroundColor: 'rgb(10, 89, 73)',
    flexDirection: 'row',
    alignItems: 'center',
  },
  headerText: {
    color: 'rgb(236, 242, 224)',
    fontSize: 37,
    fontWeight: 'bold',
  },
  logo: {
    width: 130,
    height: 130,
  },
  middle: {
    flex: 4,
    paddingLeft: 30,
    paddingRight: 30,
    justifyContent: 'center',
  },
});
