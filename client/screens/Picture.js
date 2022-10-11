import { useEffect } from 'react';
import { View, Text, StyleSheet, Image, TouchableOpacity } from 'react-native';

const Picture = ({
  navigation: { navigate, replace },
  route: {
    params: { type, user },
  },
}) => {
  const goToLink = () => {
    navigate('Stack', {
      screen: 'camera',
      params: { type: type ? type : '전기', user },
    });
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>
          {type ? type : '전기'} 고지서 촬영
        </Text>
        <Text style={styles.headerContent}>
          사각형 범위 내부에 <Text style={styles.red}>청구내역</Text>이
        </Text>
        <Text style={styles.headerContent}>들어가도록 맞추어 주세요</Text>
      </View>
      <View style={styles.middle}>
        <View style={styles.imgCover}>
          {type && type === '전기' ? (
            <Image
              style={styles.exampleImg}
              source={require('../assets/images/example.jpeg')}
            />
          ) : (
            <Image
              style={styles.exampleImg}
              source={require('../assets/images/example_gas.png')}
            />
          )}
        </View>
      </View>
      <View style={styles.footer}>
        <Text style={styles.footerText}>
          <Text style={styles.bold}>어두운 배경</Text>에서
          <Text style={styles.bold}>빛반사</Text>에 주의하며
        </Text>
        <Text style={styles.footerText}>
          테두리 안에 청구내역을 맞춰서 촬영해주세요.
        </Text>
        <TouchableOpacity style={styles.footerBtn} onPress={() => goToLink()}>
          <Text style={styles.footerBtnText}>촬영페이지로</Text>
        </TouchableOpacity>
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
    flex: 2,
    alignItems: 'center',
    justifyContent: 'center',
  },
  headerTitle: {
    fontSize: 40,
    marginBottom: 10,
  },
  headerContent: {
    fontSize: 18,
  },
  red: {
    color: 'red',
  },
  middle: {
    flex: 2,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 10,
  },
  imgCover: {
    flex: 1,
    borderWidth: 3,
    width: '100%',
    backgroundColor: 'lightgrey',
    alignItems: 'center',
  },
  exampleImg: {
    resizeMode: 'contain',
    flex: 1,
    width: '100%',
  },
  footer: {
    flex: 1.5,
    paddingTop: 10,
    alignItems: 'center',
  },
  bold: {
    fontWeight: 'bold',
  },
  footerText: {
    fontSize: 18,
  },
  footerBtn: {
    marginTop: 10,
    backgroundColor: 'rgb(94, 94, 94)',
    color: 'white',
    width: 200,
    height: 50,
    borderRadius: 20,
    justifyContent: 'center',
    alignItems: 'center',
  },
  footerBtnText: {
    color: 'white',
    fontSize: 20,
    fontWeight: 'bold',
  },
});
