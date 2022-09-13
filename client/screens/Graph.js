import { useEffect, useState } from 'react';
import { View, Text, StyleSheet, Dimensions, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { LineChart } from 'react-native-chart-kit';
import API from '../api/index';

const Graph = () => {
  const [user, setUser] = useState(null);
  const [graphData, setGraphData] = useState(null);

  useEffect(() => {
    checkUser();
    drawGraph();
  }, []);

  const checkUser = async () => {
    const user = await AsyncStorage.getItem('@user');
    if (user) {
      setUser(JSON.parse(user));
    }
  };

  const drawGraph = async () => {
    setGraphData([
      {
        data: [
          Math.random() * 100,
          Math.random() * 100,
          Math.random() * 100,
          Math.random() * 100,
          Math.random() * 100,
          Math.random() * 100,
          Math.random() * 100,
        ],
        color: (opacity = 1) => 'rgba(58, 143, 255, 1)',
      },
      {
        data: [
          Math.random() * 100,
          Math.random() * 100,
          Math.random() * 100,
          Math.random() * 100,
          Math.random() * 100,
          Math.random() * 100,
          Math.random() * 100,
        ],
        color: (opacity = 1) => 'rgba(0, 255, 255, 1)',
      },
      {
        data: [0], // min
        withwithDots: false,
      },
    ]);
    // const { data, message, success } = await API.getGraph(user?.email);
    // if (success) {
    //   // setGraphData(data);
    //   setGraphData(
    //     {
    //       data: [
    //         Math.random() * 100,
    //         Math.random() * 100,
    //         Math.random() * 100,
    //         Math.random() * 100,
    //         Math.random() * 100,
    //         Math.random() * 100,
    //         Math.random() * 100,
    //       ],
    //     },
    //     {
    //       data: [
    //         Math.random() * 100,
    //         Math.random() * 100,
    //         Math.random() * 100,
    //         Math.random() * 100,
    //         Math.random() * 100,
    //         Math.random() * 100,
    //         Math.random() * 100,
    //       ],
    //     }
    //   );
    // } else {
    //   Alert.alert(message);
    // }
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerText}>
          {user?.nickname || '닉네임'}님의 월별 사용량
        </Text>
      </View>
      <View style={styles.middle}>
        {graphData && (
          <LineChart
            data={{
              labels: [
                '22/01',
                '22/02',
                '22/03',
                '22/04',
                '22/05',
                '22/06',
                '22/07',
              ],
              datasets: graphData,
            }}
            width={Dimensions.get('window').width - 20} // from react-native
            height={400}
            chartConfig={{
              backgroundColor: '#1cc910',
              backgroundGradientFrom: '#eff3ff',
              backgroundGradientTo: '#efefef',
              decimalPlaces: 2, // optional, defaults to 2dp
              color: (opacity = 1) => `black`,
              style: {
                borderRadius: 16,
              },
            }}
            bezier
            style={{
              borderRadius: 15,
            }}
          />
        )}
      </View>
    </View>
  );
};

export default Graph;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  header: {
    flex: 1,
    alignItems: 'flex-start',
    justifyContent: 'flex-end',
    paddingLeft: 20,
    paddingRight: 20,
  },
  headerText: {
    color: 'black',
    fontSize: 30,
    fontWeight: 'bold',
  },
  middle: {
    flex: 4,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'white',
  },
});
