import { useEffect, useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableOpacity,
  Alert,
} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

const Home = ({ navigation: { navigate, replace } }) => {
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
        replace('Stack', {
          screen: 'notlogin',
        });
      }
    } catch (err) {
      console.error(err);
    }
  };

  const goToLink = (type) => {
    switch (type) {
      case '전기':
        navigate('Tabs', {
          screen: 'picture',
          params: { type, user },
        });
        break;
      case '가스':
        Alert.alert('개발 중입니다.');
        break;
      case '수도':
        Alert.alert('개발 중입니다.');
        break;
      default:
        break;
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Image
          style={styles.logo}
          source={require('../assets/icon/logo.png')}
        />
        <Text style={styles.headerText}>당신의 탄소 비서</Text>
      </View>
      <View style={styles.middle}>
        <TouchableOpacity style={styles.item1} onPress={() => goToLink('전기')}>
          <Image
            style={styles.itemLogo}
            source={require('../assets/images/elect.jpg')}
          />
          <Text style={styles.itemText}>전력 사용량 입력하기</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.item2} onPress={() => goToLink('가스')}>
          <Image
            style={styles.itemLogo}
            source={require('../assets/images/gas.jpg')}
          />
          <Text style={styles.itemText}>가스 사용량 입력하기</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.item3} onPress={() => goToLink('수도')}>
          <Image
            style={styles.itemLogo}
            source={require('../assets/images/water.jpg')}
          />
          <Text style={styles.itemText}>수도 사용량 입력하기</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

export default Home;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  header: {
    flex: 1.5,
    paddingTop: 20,
    backgroundColor: 'rgb(10, 89, 73)',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  headerText: {
    color: 'rgb(236, 242, 224)',
    fontSize: 27,
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
  item1: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'rgb(130, 190, 163)',
    marginBottom: 30,
    padding: 15,
  },
  item2: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'rgb(177, 216, 194)',
    marginBottom: 30,
    padding: 10,
  },
  item3: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'rgb(236, 242, 224)',
    padding: 10,
  },
  itemLogo: {
    borderRadius: 100,
    marginRight: 30,
    width: 70,
    height: 70,
  },
  itemText: {
    fontSize: 20,
    color: 'rgb(51, 51, 51)',
    fontWeight: 'bold',
  },
});
