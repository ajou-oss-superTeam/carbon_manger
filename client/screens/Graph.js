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

const Graph = ({
  navigation: { navigate },
  route: {
    params: { hashValue },
  },
}) => {
  const [nickname, setNickname] = useState('');
  const [graphData, setGraphData] = useState(null);
  const [maxValue, setMaxValue] = useState(0);

  useEffect(() => {
    drawGraph();
  }, [hashValue]);

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
        const datasets = data.datasets;
        const value = datasets.map((item) => {
          const sum = item
            .filter((num) => num !== '')
            .reduce((sum, cur) => {
              return sum + cur;
            }, 0);
          return sum;
        });

        setMaxValue(Math.max(...value));
        setGraphData(data);
      } else {
        Alert.alert(message);
      }
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
        {graphData && maxValue !== 0 ? (
          <StackedBarChart
            data={{
              labels: [...graphData.labels],
              legend: graphData.legend,
              data: [...graphData.datasets, [maxValue]],
              barColors: ['yellow', 'red', 'blue'],
              withDots: true,
            }}
            hideLegend={true}
            width={Dimensions.get('window').width - 16}
            height={400}
            fromZero={true}
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
        ) : (
          <View style={styles.noData}>
            <Text style={styles.noDataText}>얼른 고지서를 제출하세요!</Text>
          </View>
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
  noData: {
    width: 300,
    height: 300,
    marginTop: 20,
    borderRadius: 20,
    padding: 30,
    backgroundColor: 'green',
    justifyContent: 'center',
    alignItems: 'center',
  },
  noDataText: {
    color: 'white',
  },
});
