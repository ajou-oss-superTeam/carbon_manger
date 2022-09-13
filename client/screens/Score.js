import { useState, useEffect } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';

const Score = ({
  navigation: { navigate, replace },
  route: {
    params: { type, user, data, time },
  },
}) => {
  const goToLink = (btnType) => {
    if (btnType === 'edit') {
      navigate('Stack', {
        screen: 'scoreedit',
        params: { type, user, data, time },
      });
    } else {
      navigate('Tabs', {
        screen: 'graph',
      });
    }
  };

  return (
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
            {data?.demandCharge ? data.demandCharge : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전력량요금</Text>
          <Text style={styles.elementText}>
            {data?.energyCharge ? data.energyCharge : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>기후환경요금</Text>
          <Text style={styles.elementText}>
            {data?.environmentCharge ? data.environmentCharge : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>연료비조정액</Text>
          <Text style={styles.elementText}>
            {data?.fuelAdjustmentRate ? data.fuelAdjustmentRate : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전기요금계</Text>
          <Text style={styles.elementText}>
            {data?.elecChargeSum ? data.elecChargeSum : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>부가가치세</Text>
          <Text style={styles.elementText}>
            {data?.vat ? data.vat : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전력 기금</Text>
          <Text style={styles.elementText}>
            {data?.elecFund ? data.elecFund : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>월단위 절사</Text>
          <Text style={styles.elementText}>
            {data?.roundDown ? data.roundDown : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>당월요금계</Text>
          <Text style={styles.elementText}>
            {data?.totalbyCurrMonth ? data.totalbyCurrMonth : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>TV수신료</Text>
          <Text style={styles.elementText}>
            {data?.tvSubscriptionFee ? data.tvSubscriptionFee : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>당월 사용량</Text>
          <Text style={styles.elementText}>
            {data?.currMonthUsage ? data.currMonthUsage : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전월 사용량</Text>
          <Text style={styles.elementText}>
            {data?.preMonthUsage ? data.preMonthUsage : '인식 안됨'}
          </Text>
        </View>
        <View style={styles.element}>
          <Text style={styles.elementText}>전년동월 사용량</Text>
          <Text style={styles.elementText}>
            {data?.lastYearUsage ? data.lastYearUsage : '인식 안됨'}
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
