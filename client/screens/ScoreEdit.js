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
import AsyncStorage from '@react-native-async-storage/async-storage';
import { MaterialIcons } from '@expo/vector-icons';
import API from '../api';

const ScoreEdit = ({
  navigation: { navigate, replace, reset },
  route: {
    params: { type, data, time },
  },
}) => {
  // 달력
  const [showDate, setShowDate] = useState(false);
  // 전기 ==========
  const [demandCharge, setDemandCharge] = useState(
    data?.electricityInfoList?.demandCharge
      ? String(data?.electricityInfoList?.demandCharge)
      : '0'
  );
  const [energyCharge, setEnergyCharge] = useState(
    data?.electricityInfoList?.energyCharge
      ? String(data?.electricityInfoList?.energyCharge)
      : '0'
  );
  const [environmentCharge, setEnvironmentCharge] = useState(
    data?.electricityInfoList?.environmentCharge
      ? String(data?.electricityInfoList?.environmentCharge)
      : '0'
  );
  const [fuelAdjustmentRate, setFuelAdjustmentRate] = useState(
    data?.electricityInfoList?.fuelAdjustmentRate
      ? String(data?.electricityInfoList?.fuelAdjustmentRate)
      : '0'
  );
  const [elecChargeSum, setElecChargeSum] = useState(
    data?.electricityInfoList?.elecChargeSum
      ? String(data?.electricityInfoList?.elecChargeSum)
      : '0'
  );
  const [vat, setVat] = useState(
    data?.electricityInfoList?.vat
      ? String(data?.electricityInfoList?.vat)
      : '0'
  );
  const [elecFund, setElecFund] = useState(
    data?.electricityInfoList?.elecFund
      ? String(data?.electricityInfoList?.elecFund)
      : '0'
  );
  const [roundDown, setRoundDown] = useState(
    data?.electricityInfoList?.roundDown
      ? String(data?.electricityInfoList?.roundDown)
      : '0'
  );
  const [totalbyCurrMonth, setTotalbyCurrMonth] = useState(
    data?.electricityInfoList?.totalbyCurrMonth
      ? String(data?.electricityInfoList?.totalbyCurrMonth)
      : '0'
  );
  const [tvSubscriptionFee, setTvSubscriptionFee] = useState(
    data?.electricityInfoList?.tvSubscriptionFee
      ? String(data?.electricityInfoList?.tvSubscriptionFee)
      : '0'
  );
  const [currMonthUsage, setCurrMonthUsage] = useState(
    data?.electricityInfoList?.currMonthUsage
      ? String(data?.electricityInfoList?.currMonthUsage)
      : '0'
  );
  const [preMonthUsage, setPreMonthUsage] = useState(
    data?.electricityInfoList?.preMonthUsage
      ? String(data?.electricityInfoList?.preMonthUsage)
      : '0'
  );
  const [lastYearUsage, setLastYearUsage] = useState(
    data?.electricityInfoList?.lastYearUsage
      ? String(data?.electricityInfoList?.lastYearUsage)
      : '0'
  );
  // 가스 ===========
  const [accumulatedMonthUsage, setAccumulatedMonthUsage] = useState(
    data?.gasInfoList?.accumulatedMonthUsage
      ? String(data?.gasInfoList?.accumulatedMonthUsage)
      : '0'
  );
  const [previousMonthUsage, setPreviousMonthUsage] = useState(
    data?.gasInfoList?.previousMonthUsage
      ? String(data?.gasInfoList?.previousMonthUsage)
      : '0'
  );
  const [checkedUsage, setCheckedUsage] = useState(
    data?.gasInfoList?.checkedUsage
      ? String(data?.gasInfoList.checkedUsage)
      : '0'
  );
  const [currentMonthUsage, setCurrentMonthUsage] = useState(
    data?.gasInfoList?.currentMonthUsage
      ? String(data?.gasInfoList.currentMonthUsage)
      : '0'
  );
  const [unitEnergy, setUnitEnergy] = useState(
    data?.gasInfoList?.unitEnergy ? String(data?.gasInfoList.unitEnergy) : '0'
  );
  const [usedEnergy, setUsedEnergy] = useState(
    data?.gasInfoList?.usedEnergy ? String(data?.gasInfoList.usedEnergy) : '0'
  );

  const onPressBtn = () => {
    if (Object.entries(data).length === 0) {
      setShowDate(true);
    } else {
      requsetAPIPlus();
    }
  };

  // 바로 직접수정 - null일 경우
  const requsetAPI = async (e) => {
    setShowDate(false);

    if (e?.type === 'dismissed') {
      return;
    }

    const timestamp = e.nativeEvent.timestamp;
    const jsDate = new Date(timestamp);
    const year = jsDate.getFullYear();
    const month = jsDate.getMonth() + 1;

    const user = await AsyncStorage.getItem('@user');
    const token = await AsyncStorage.getItem('@token');

    const parseUser = JSON.parse(user);
    const parseToken = JSON.parse(token);
    const email = parseUser.user.email;

    if (type === '전기') {
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
        Object.entries(numbers).filter(
          ([key, value], index) => value == '0' || Number(value) < 0
        ).length > 0
      ) {
        Alert.alert('빈 값 혹은 유효하지 않은 값이 있습니다.');
        return;
      }

      const { success, message } = await API.sendNumber(
        email,
        year,
        month,
        numbers,
        parseToken
      );

      if (success) {
        reset({
          routes: [
            {
              name: 'Tabs',
              state: {
                routes: [
                  {
                    name: 'graph',
                  },
                ],
              },
            },
          ],
        });
      } else {
        Alert.alert(message);
      }
    } else {
      const gasNumbers = {
        accumulatedMonthUsage,
        previousMonthUsage,
        checkedUsage,
        currentMonthUsage,
        unitEnergy,
        usedEnergy,
      };

      if (
        Object.entries(gasNumbers).filter(
          ([key, value], index) => value == '0' || Number(value) < 0
        ).length > 0
      ) {
        Alert.alert('빈 값 혹은 유효하지 않은 값이 있습니다.');
        return;
      }

      const { success, message } = await API.sendGasNumber(
        email,
        year,
        month,
        gasNumbers,
        parseToken
      );

      if (success) {
        reset({
          routes: [
            {
              name: 'Tabs',
              state: {
                routes: [
                  {
                    name: 'graph',
                  },
                ],
              },
            },
          ],
        });
      } else {
        Alert.alert(message);
      }
    }
  };

  // 사진 후 수정 - null이 아닐 경우
  const requsetAPIPlus = async () => {
    const token = await AsyncStorage.getItem('@token');
    const parseToken = JSON.parse(token);

    if (type === '전기') {
      const id = data.electricityInfoList.id;
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
        Object.entries(numbers).filter(
          ([key, value], index) => value == '0' || Number(value) < 0
        ).length > 0
      ) {
        Alert.alert('빈 값 혹은 유효하지 않은 값이 있습니다.');
        return;
      }

      const { success, message } = await API.editImgInfo(
        id,
        numbers,
        parseToken
      );

      if (success) {
        reset({
          routes: [
            {
              name: 'Tabs',
              state: {
                routes: [
                  {
                    name: 'graph',
                  },
                ],
              },
            },
          ],
        });
      } else {
        Alert.alert(message);
      }
    } else {
      const id = data.gasInfoList.id;
      const gasNumbers = {
        accumulatedMonthUsage,
        previousMonthUsage,
        checkedUsage,
        currentMonthUsage,
        unitEnergy,
        usedEnergy,
      };

      if (
        Object.entries(gasNumbers).filter(
          ([key, value], index) => value == '0' || Number(value) < 0
        ).length > 0
      ) {
        Alert.alert('빈 값 혹은 유효하지 않은 값이 있습니다.');
        return;
      }

      const { success, message } = await API.editGasImgInfo(
        id,
        gasNumbers,
        parseToken
      );

      if (success) {
        reset({
          routes: [
            {
              name: 'Tabs',
              state: {
                routes: [
                  {
                    name: 'graph',
                  },
                ],
              },
            },
          ],
        });
      } else {
        Alert.alert(message);
      }
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
        {type === '전기' ? (
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
        ) : (
          <ScrollView style={styles.scrollView}>
            <View style={styles.element}>
              <Text style={styles.elementLabel}>당월지침</Text>
              <TextInput
                style={styles.elementText}
                value={accumulatedMonthUsage}
                autoFocus={true}
                keyboardType="numeric"
                onChangeText={setAccumulatedMonthUsage}
              />
            </View>
            <View style={styles.element}>
              <Text style={styles.elementLabel}>전월지침</Text>
              <TextInput
                style={styles.elementText}
                value={previousMonthUsage}
                keyboardType="numeric"
                onChangeText={setPreviousMonthUsage}
              />
            </View>
            <View style={styles.element}>
              <Text style={styles.elementLabel}>검침량</Text>
              <TextInput
                style={styles.elementText}
                value={checkedUsage}
                keyboardType="numeric"
                onChangeText={setCheckedUsage}
              />
            </View>
            <View style={styles.element}>
              <Text style={styles.elementLabel}>당월 사용량</Text>
              <TextInput
                style={styles.elementText}
                value={currentMonthUsage}
                keyboardType="numeric"
                onChangeText={setCurrentMonthUsage}
              />
            </View>
            <View style={styles.element}>
              <Text style={styles.elementLabel}>단위 열량</Text>
              <TextInput
                style={styles.elementText}
                value={unitEnergy}
                keyboardType="numeric"
                onChangeText={setUnitEnergy}
              />
            </View>
            <View style={styles.element}>
              <Text style={styles.elementLabel}>사용 열량</Text>
              <TextInput
                style={styles.elementText}
                value={usedEnergy}
                keyboardType="numeric"
                onChangeText={setUsedEnergy}
              />
            </View>
          </ScrollView>
        )}
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
