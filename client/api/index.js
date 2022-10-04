import axios from 'axios';
import { Platform } from 'react-native';
import Constants from 'expo-constants';

const host = Constants.expoConfig.extra.apiUrl;
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
      console.error(err.response);
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
      console.error(err.response);
    }
  },

  // 이미지 전송
  async sendImg(email, uri, base, year, month) {
    try {
      const { data } = await axios.post(`${host}/api/image/electricity`, {
        email,
        year,
        month,
        image: base,
        uri,
      });

      if (data.success) {
        return { data: data.data, success: data.success };
      } else {
        return { message: data.message, success: data.success };
      }
    } catch (err) {
      console.error(err.response);
      return { message: '/api/image/electricity 에러', success: false };
    }
  },

  // 사진 수정
  async editImgInfo(id, numbers) {
    try {
      const { data } = await axios.put(
        `${host}/api/image/electricity/${id}/edit`,
        {
          id,
          ...numbers,
        }
      );

      if (data.success) {
        return { data: data.data, success: data.success };
      } else {
        return { message: data.message, success: data.success };
      }
    } catch (err) {
      console.error(err.response);
    }
  },

  // 숫자 전송
  async sendNumber(email, year, month, numbers) {
    try {
      const { data } = await axios.post(`${host}/api/image/electricity/input`, {
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
      console.error(err.response);
    }
  },

  // 숫자 전송
  async getGraph(email) {
    try {
      const { data } = await axios.post(
        `${host}/api/graph/electricity/fee?email=${email}`
      );

      if (data.success) {
        return { data: data.data, success: data.success };
      } else {
        return { message: data.message, success: data.success };
      }
    } catch (err) {
      console.error(err.response);
    }
  },
};

export default API;
