import axios from 'axios';

const host = 'http://34.171.248.55:8080';

const API = {
  // 로그인
  async getLogin({ email, password }) {
    try {
      const { data } = await axios.post(`${host}/api/user/login`, {
        email,
        password,
      });

      if (data.success) {
        return { user: data.data, success: data.success };
      } else {
        return { message: data.message, success: data.success };
      }
    } catch (err) {
      console.error(err);
    }
  },

  // 회원가입
  async getSignup({ email, nickname, password, province, city }) {
    try {
      const { data } = await axios.post(`${host}/api/user/signup`, {
        email,
        nickname,
        password,
        province,
        city,
      });

      if (data.success) {
        return { user: data.data, success: data.success };
      } else {
        return { message: data.message, success: data.success };
      }
    } catch (err) {
      console.error(err);
    }
  },

  // 이미지 전송
  async sendImg(email, uri, year, month) {
    // return {
    //   success: true,
    //   data: {
    //     demandCharge: 1,
    //     energyCharge: 2,
    //     environmentCharge: 3,
    //     fuelAdjustmentRate: 4,
    //     elecChargeSum: 5,
    //     vat: 6,
    //     elecFund: 7,
    //     roundDown: 8,
    //     totalbyCurrMonth: 9,
    //     tvSubscriptionFee: 10,
    //     currMonthUsage: 11,
    //     preMonthUsage: 12,
    //     lastYearUsage: 13,
    //   },
    // };
    try {
      const formData = new FormData();

      formData.append('email', email);
      formData.append('year', year);
      formData.append('month', month);
      formData.append('image', uri);

      const { data } = await axios.post(
        `${host}/api/image/electrocity`,
        formData,
        {
          header: { 'Content-Type': 'multipart/form-data' },
        }
      );

      if (data.success) {
        return { data: data.data, success: data.success };
      } else {
        return { message: data.message, success: data.success };
      }
    } catch (err) {
      console.error(err);
    }
  },

  // 숫자 전송
  async sendNumber(email, year, month, numbers) {
    try {
      console.log({
        email,
        year,
        month,
        ...numbers,
      });

      const { data } = await axios.post(`${host}/api/image-edit/electricity`, {
        email,
        year,
        month,
        ...numbers,
      });

      if (data.success) {
        return { data: data.data, success: data.success };
      } else {
        return { message: data.message, success: data.success };
      }
    } catch (err) {
      console.error(err);
    }
  },
};

export default API;
