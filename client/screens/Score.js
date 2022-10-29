/**
 *  Copyright 2022 Carbon_Developers
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import { useState, useEffect } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';

const Score = ({
  navigation: { navigate, replace, reset },
  route: {
    params: { type, data, time },
  },
}) => {
  const [electricityInfoList, setElectricityInfoList] = useState(null);
  const [gasInfoList, setGasInfoList] = useState(null);

  useEffect(() => {
    if (type === '전기') {
      setElectricityInfoList(data?.electricityInfoList);
    } else {
      setGasInfoList(data?.gasInfoList);
    }
  }, []);

  const goToLink = (btnType) => {
    if (btnType === 'edit') {
      navigate('Stack', {
        screen: 'scoreedit',
        params: { type, data, time },
      });
    } else {
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
    }
  };

  return type === '전기' ? (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>{type} 고지서</Text>
        <Text style={styles.headerContent}>
          입력하신 내용이 맞는지 <Text style={styles.green}>확인</Text>해주세요
        </Text>
        <Text style={styles.headerContent}>
          ({time.year}년 {time.month}월 고지서)
        </Text>
      </View>
      <View style={styles.middle}>
        <View style={styles.element}>
          <Text style={styles.elementText}>기본요금</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.demandCharge
              ? electricityInfoList.demandCharge
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전력량요금</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.energyCharge
              ? electricityInfoList.energyCharge
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>기후환경요금</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.environmentCharge
              ? electricityInfoList.environmentCharge
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>연료비조정액</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.fuelAdjustmentRate
              ? electricityInfoList.fuelAdjustmentRate
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전기요금계</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.elecChargeSum
              ? electricityInfoList.elecChargeSum
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>부가가치세</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.vat ? electricityInfoList.vat : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전력 기금</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.elecFund
              ? electricityInfoList.elecFund
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>월단위 절사</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.roundDown
              ? electricityInfoList.roundDown
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>당월요금계</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.totalbyCurrMonth
              ? electricityInfoList.totalbyCurrMonth
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>TV수신료</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.tvSubscriptionFee
              ? electricityInfoList.tvSubscriptionFee
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>당월 사용량</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.currMonthUsage
              ? electricityInfoList.currMonthUsage
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전월 사용량</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.preMonthUsage
              ? electricityInfoList.preMonthUsage
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전년동월 사용량</Text>
          <Text style={styles.elementText}>
            {electricityInfoList?.lastYearUsage
              ? electricityInfoList.lastYearUsage
              : '인식 안됨'}
          </Text>
        </View>
      </View>
      <View style={styles.footer}>
        <TouchableOpacity onPress={(e) => goToLink('edit')}>
          <Text style={styles.editBtn}>수정하기</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={(e) => goToLink('confirm')}>
          <Text style={styles.confirmBtn}>확인</Text>
        </TouchableOpacity>
      </View>
    </View>
  ) : (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>{type} 고지서</Text>
        <Text style={styles.headerContent}>
          입력하신 내용이 맞는지 <Text style={styles.green}>확인</Text>해주세요
        </Text>
        <Text style={styles.headerContent}>
          ({time.year}년 {time.month}월 고지서)
        </Text>
      </View>
      <View style={styles.middle}>
        <View style={styles.element}>
          <Text style={styles.elementText}>당월지침</Text>
          <Text style={styles.elementText}>
            {gasInfoList?.accumulatedMonthUsage
              ? gasInfoList?.accumulatedMonthUsage
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전월지침</Text>
          <Text style={styles.elementText}>
            {gasInfoList?.previousMonthUsage
              ? gasInfoList?.previousMonthUsage
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>검침량</Text>
          <Text style={styles.elementText}>
            {gasInfoList?.checkedUsage
              ? gasInfoList?.checkedUsage
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>당월 사용량</Text>
          <Text style={styles.elementText}>
            {gasInfoList?.currentMonthUsage
              ? gasInfoList?.currentMonthUsage
              : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>단위 열량</Text>
          <Text style={styles.elementText}>
            {gasInfoList?.unitEnergy ? gasInfoList?.unitEnergy : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>사용 열량</Text>
          <Text style={styles.elementText}>
            {gasInfoList?.usedEnergy ? gasInfoList?.usedEnergy : '인식 안됨'}
          </Text>
        </View>
      </View>
      <View style={styles.footer}>
        <TouchableOpacity onPress={(e) => goToLink('edit')}>
          <Text style={styles.editBtn}>수정하기</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={(e) => goToLink('confirm')}>
          <Text style={styles.confirmBtn}>확인</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

export default Score;

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
  element: {
    flex: 1,
    paddingLeft: 20,
    paddingRight: 20,
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  elementText: {
    fontSize: 22,
    fontWeight: 'bold',
    color: 'grey',
  },
  footer: {
    flex: 1,
    paddingTop: 10,
    alignItems: 'center',
  },
  editBtn: {
    backgroundColor: 'rgb(52, 152, 219)',
    color: 'white',
    borderRadius: 5,
    padding: 5,
    marginBottom: 10,
    fontSize: 10,
  },
  confirmBtn: {
    backgroundColor: 'rgb(94, 94, 94)',
    color: 'white',
    borderRadius: 5,
    padding: 10,
    width: 150,
    textAlign: 'center',
  },
});
