import { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
  TouchableOpacity,
  Alert,
  ScrollView,
} from 'react-native';
import DateTimePicker from '@react-native-community/datetimepicker';
import { MaterialIcons } from '@expo/vector-icons';
import API from '../api';

const ScoreEdit = ({
  navigation: { navigate, replace },
  route: {
    params: { type, user, data, time },
  },
}) => {
  // data, time이 null로 오는 케이스 존재함
  const [demandCharge, setDemandCharge] = useState(
    data?.demandCharge ? String(data.demandCharge) : '0'
  );
  const [energyCharge, setEnergyCharge] = useState(
    data?.energyCharge ? String(data.energyCharge) : '0'
  );
  const [environmentCharge, setEnvironmentCharge] = useState(
    data?.environmentCharge ? String(data.environmentCharge) : '0'
  );
  const [fuelAdjustmentRate, setFuelAdjustmentRate] = useState(
    data?.fuelAdjustmentRate ? String(data.fuelAdjustmentRate) : '0'
  );
  const [elecChargeSum, setElecChargeSum] = useState(
    data?.elecChargeSum ? String(data.elecChargeSum) : '0'
  );
  const [vat, setVat] = useState(data?.vat ? String(data.vat) : '0');
  const [elecFund, setElecFund] = useState(
    data?.elecFund ? String(data.elecFund) : '0'
  );
  const [roundDown, setRoundDown] = useState(
    data?.roundDown ? String(data.roundDown) : '0'
  );
  const [totalbyCurrMonth, setTotalbyCurrMonth] = useState(
    data?.totalbyCurrMonth ? String(data.totalbyCurrMonth) : '0'
  );
  const [tvSubscriptionFee, setTvSubscriptionFee] = useState(
    data?.tvSubscriptionFee ? String(data.tvSubscriptionFee) : '0'
  );
  const [currMonthUsage, setCurrMonthUsage] = useState(
    data?.currMonthUsage ? String(data.currMonthUsage) : '0'
  );
  const [preMonthUsage, setPreMonthUsage] = useState(
    data?.preMonthUsage ? String(data.preMonthUsage) : '0'
  );
  const [lastYearUsage, setLastYearUsage] = useState(
    data?.lastYearUsage ? String(data.lastYearUsage) : '0'
  );
  // 달력
  const [showDate, setShowDate] = useState(false);

  useEffect(() => {
    console.log(demandCharge);
  }, []);

  const onPressBtn = () => {
    if (Object.entries(data).length === 0) {
      setShowDate(true);
    } else {
      requsetAPIPlus();
    }
  };

  // null일 경우
  const requsetAPI = async (e) => {
    setShowDate(false);

    if (e?.type === 'dismissed') {
      return;
    }

    const timestamp = e.nativeEvent.timestamp;
    const jsDate = new Date(timestamp);
    const year = jsDate.getFullYear();
    const month = jsDate.getMonth() + 1;

    const userJson = JSON.parse(user);
    const email = userJson.email;

    const numbers = {
      demandCharge,
      energyCharge,
      environmentCharge,
      fuelAdjustmentRate,
      elecChargeSum,
      vat,
      elecFund,
      roundDown,
      totalbyCurrMonth,
      tvSubscriptionFee,
      currMonthUsage,
      preMonthUsage,
      lastYearUsage,
    };

    if (
      Object.entries(numbers).filter((key, value) => value == '').length > 0
    ) {
      Alert.alert('빈 값이 있습니다.');
      return;
    }

    const { success, message } = await API.sendNumber(
      email,
      year,
      month,
      numbers
    );

    if (success) {
      navigate('Tabs', {
        screen: 'graph',
      });
    } else {
      Alert.alert(message);
    }
  };

  // null이 아닐 경우
  const requsetAPIPlus = async () => {
    const userJson = JSON.parse(user);
    const {
      user: { email },
    } = userJson;

    const year = time?.year;
    const month = time?.month;

    const numbers = {
      demandCharge,
      energyCharge,
      environmentCharge,
      fuelAdjustmentRate,
      elecChargeSum,
      vat,
      elecFund,
      roundDown,
      totalbyCurrMonth,
      tvSubscriptionFee,
      currMonthUsage,
      preMonthUsage,
      lastYearUsage,
    };
    const { success, message } = await API.sendNumber(
      email,
      year,
      month,
      numbers
    );

    if (success) {
      navigate('Tabs', {
        screen: 'graph',
      });
    } else {
      Alert.alert(message);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>{type} 고지서</Text>
        <Text style={styles.headerContent}>
          입력하신 내용이 맞는지 <Text style={styles.green}>확인</Text>해주세요
        </Text>
        {showDate && (
          <DateTimePicker
            mode="date"
            positiveButtonLabel="고지서 날짜 입력"
            negativeButtonLabel="닫기"
            value={new Date()}
            onChange={requsetAPI}
          />
        )}
      </View>
      <View style={styles.middle}>
        <ScrollView style={styles.scrollView}>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>기본요금</Text>
            <TextInput
              style={styles.elementText}
              value={demandCharge}
              autoFocus={true}
              keyboardType="numeric"
              onChangeText={setDemandCharge}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>전력량요금</Text>
            <TextInput
              style={styles.elementText}
              value={energyCharge}
              keyboardType="numeric"
              onChangeText={setEnergyCharge}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>기후환경요금</Text>
            <TextInput
              style={styles.elementText}
              value={environmentCharge}
              keyboardType="numeric"
              onChangeText={setEnvironmentCharge}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>연료비조정액</Text>
            <TextInput
              style={styles.elementText}
              value={fuelAdjustmentRate}
              keyboardType="numeric"
              onChangeText={setFuelAdjustmentRate}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>전기요금계</Text>
            <TextInput
              style={styles.elementText}
              value={elecChargeSum}
              keyboardType="numeric"
              onChangeText={setElecChargeSum}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>부가가치세</Text>
            <TextInput
              style={styles.elementText}
              value={vat}
              keyboardType="numeric"
              onChangeText={setVat}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>전력 기금</Text>
            <TextInput
              style={styles.elementText}
              value={elecFund}
              keyboardType="numeric"
              onChangeText={setElecFund}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>월단위 절사</Text>
            <TextInput
              style={styles.elementText}
              value={roundDown}
              keyboardType="numeric"
              onChangeText={setRoundDown}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>당월요금계</Text>
            <TextInput
              style={styles.elementText}
              value={totalbyCurrMonth}
              keyboardType="numeric"
              onChangeText={setTotalbyCurrMonth}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>TV수신료</Text>
            <TextInput
              style={styles.elementText}
              value={tvSubscriptionFee}
              keyboardType="numeric"
              onChangeText={setTvSubscriptionFee}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>당월 사용량</Text>
            <TextInput
              style={styles.elementText}
              value={currMonthUsage}
              keyboardType="numeric"
              onChangeText={setCurrMonthUsage}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>전월 사용량</Text>
            <TextInput
              style={styles.elementText}
              value={preMonthUsage}
              keyboardType="numeric"
              onChangeText={setPreMonthUsage}
            />
          </View>
          <View style={styles.element}>
            <Text style={styles.elementLabel}>전년동월 사용량</Text>
            <TextInput
              style={styles.elementText}
              value={lastYearUsage}
              keyboardType="numeric"
              onChangeText={setLastYearUsage}
            />
          </View>
        </ScrollView>
      </View>
      <View style={styles.footer}>
        <TouchableOpacity onPress={onPressBtn}>
          <Text style={styles.confirmBtn}>입력완료</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

export default ScoreEdit;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  header: {
    flex: 1.5,
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
  green: {
    color: 'green',
  },
  middle: {
    flex: 4,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 10,
  },
  scrollView: {
    width: '100%',
    paddingLeft: 20,
    paddingRight: 20,
  },
  element: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 20,
    justifyContent: 'space-between',
  },
  elementLabel: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'grey',
    textAlign: 'right',
  },
  elementText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'grey',
    textAlign: 'center',
    width: 100,
    border: 1,
    borderRadius: 5,
    borderWidth: 1,
  },
  footer: {
    flex: 1,
    paddingTop: 10,
    alignItems: 'center',
  },
  confirmBtn: {
    backgroundColor: 'rgb(52, 152, 219)',
    color: 'white',
    borderRadius: 5,
    padding: 10,
    width: 150,
    textAlign: 'center',
  },
});