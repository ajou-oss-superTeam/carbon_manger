import { useEffect, useState } from 'react';
import { View, Text, StyleSheet, Dimensions, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { LineChart } from 'react-native-chart-kit';
import API from '../api/index';

const Graph = () => {
  const [nickname, setNickname] = useState('');
  const [graphData, setGraphData] = useState(null);

  useEffect(() => {
    drawGraph();
  }, []);

  const drawGraph = async () => {
    const user = await AsyncStorage.getItem('@user');
    const parseUser = JSON.parse(user);
    if (parseUser?.user?.email) {
      const { data, message, success } = await API.getGraph(
        parseUser?.user?.email
      );

      if (success) {
        setGraphData(data);
      } else {
        Alert.alert(message);
      }

      setNickname(user?.user?.nickname);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerText}>
          {nickname || '닉네임'}님의 월별 사용량
        </Text>
      </View>
      <View style={styles.middle}>
        {graphData && (
          <LineChart
            data={{
              labels: data.labels,
              datasets: [
                {
                  data: data.datasets.userData,
                  color: (opacity = 1) => 'rgba(58, 143, 255, 1)',
                },
                {
                  data: data.datasets.averageData,
                  color: (opacity = 1) => 'rgba(0, 255, 255, 1)',
                },
                { data: [0], withwithDots: false },
              ],
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
