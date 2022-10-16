import { useEffect, useState } from 'react';
import { View, Text, StyleSheet, Dimensions, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { LineChart, StackedBarChart } from 'react-native-chart-kit';
import API from '../api/index';
import {
  NAVI_BG,
  NAVI_ITEM_DEFAULT,
  NAVI_ITEM_CLICK,
} from '../assets/variables/color';

const Graph = () => {
  const [nickname, setNickname] = useState('');
  const [graphData, setGraphData] = useState(null);

  useEffect(() => {
    drawGraph();
  }, []);

  const drawGraph = async () => {
    const user = await AsyncStorage.getItem('@user');
    const token = await AsyncStorage.getItem('@token');

    const parseUser = JSON.parse(user);
    const parseToken = JSON.parse(token);

    // 닉네임
    setNickname(parseUser?.user?.nickname);

    // 그패프 데이터
    if (parseUser?.user?.email) {
      const { data, message, success } = await API.getCarbonGraph(
        parseUser?.user?.email,
        parseToken
      );

      if (success) {
        setGraphData(data);
        // console.log(data);
      } else {
        Alert.alert(message);
      }

      // setGraphData({
      //   labels: ['21/12', '22/01', '22/03'],
      //   legend: ['전기', '가스', '수도'],
      //   datasets: [
      //     [60, 20, ''],
      //     [10, 30, ''],
      //     ['', 50, ''],
      //   ],
      // });
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerText}>
          {nickname || '닉네임'}님의 탄소 배출량
        </Text>
      </View>
      <View style={styles.middle}>
        <View style={styles.label}>
          <View style={styles.item}>
            <View style={styles.yellowDot}></View>
            <Text>전기</Text>
          </View>
          <View style={styles.item}>
            <View style={styles.redDot}></View>
            <Text>가스</Text>
          </View>
        </View>
        {graphData && (
          // <LineChart
          //   data={{
          //     labels: graphData.labels,
          //     datasets: [
          //       {
          //         data: graphData.datasets.userData,
          //         color: (opacity = 1) => 'rgba(58, 143, 255, 1)',
          //       },
          //       {
          //         data: graphData.datasets.averageData,
          //         color: (opacity = 1) => 'rgba(0, 255, 255, 1)',
          //       },
          //       { data: [0], withwithDots: false },
          //     ],
          //   }}
          //   width={Dimensions.get('window').width - 20} // from react-native
          //   height={400}
          //   chartConfig={{
          //     backgroundColor: '#1cc910',
          //     backgroundGradientFrom: '#eff3ff',
          //     backgroundGradientTo: '#efefef',
          //     decimalPlaces: 2, // optional, defaults to 2dp
          //     color: (opacity = 1) => `black`,
          //     style: {
          //       borderRadius: 16,
          //     },
          //   }}
          //   bezier
          //   style={{
          //     borderRadius: 15,
          //   }}
          // />
          // <StackedBarChart
          //   data={graphData}
          //   width={Dimensions.get('window').width - 20} // from react-native
          //   height={400}
          //   chartConfig={{
          //     backgroundColor: '#1cc910',
          //     backgroundGradientFrom: '#eff3ff',
          //     backgroundGradientTo: '#efefef',
          //     decimalPlaces: 2, // optional, defaults to 2dp
          //     color: (opacity = 1) => `black`,
          //     style: {
          //       borderRadius: 16,
          //     },
          //   }}
          //   bezier
          //   style={{
          //     borderRadius: 15,
          //   }}
          // />
          <StackedBarChart
            data={{
              labels: graphData.labels,
              // legend: graphData.legend,
              data: graphData.datasets,
              barColors: ['yellow', 'red', 'blue'],
            }}
            width={Dimensions.get('window').width - 16}
            height={400}
            chartConfig={{
              backgroundColor: NAVI_BG,
              backgroundGradientFrom: NAVI_BG,
              backgroundGradientTo: NAVI_ITEM_CLICK,
              color: (opacity = 1) => `lightgrey`,
              barPercentage: 0.3,
              propsForLabels: {
                fill: 'none',
              },
              propsForHorizontalLabels: {
                fontSize: 8,
                dy: 15,
              },
              propsForVerticalLabels: {
                fontSize: 6,
                dy: -10,
                dx: -8,
              },
              style: {
                borderRadius: 16,
              },
            }}
            style={{
              marginVertical: 8,
              borderRadius: 16,
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
  label: {
    backgroundColor: NAVI_ITEM_CLICK,
    flexDirection: 'row',
  },
  item: { flexDirection: 'row', padding: 10 },
  yellowDot: {
    width: 20,
    height: 20,
    backgroundColor: 'yellow',
    marginRight: 5,
  },
  redDot: {
    width: 20,
    height: 20,
    backgroundColor: 'red',
    marginRight: 5,
  },
  middle: {
    flex: 4,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'white',
  },
});
