import axios from 'axios';
import { Platform } from 'react-native';
import Constants from 'expo-constants';

const host = Constants.expoConfig.extra.apiUrl;
const API = {
  // 로그인
  async getLogin({ email, password }) {
    try {
      const { data, headers } = await axios.post(`${host}/api/user/login`, {
        email,
        password,
      });

      if (data.success) {
        return {
          user: data.data,
          success: data.success,
          token: headers.authorization,
        };
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
      const { data, headers } = await axios.post(`${host}/api/user/signup`, {
        email,
        nickname,
        password,
        province,
        city,
      });

      if (data.success) {
        return {
          user: data.data,
          success: data.success,
          token: headers.authorization,
        };
      } else {
        return { message: data.message, success: data.success };
      }
    } catch (err) {
      console.error(err.response);
    }
  },

  // 탄소 그래프
  async getCarbonGraph(email, token) {
    try {
      const { data } = await axios.post(
        `${host}/api/graph/all/carbon`,
        {
          email,
        },
        {
          headers: {
            Authorization: token.token,
          },
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

  // =======================================
  // 전기 이미지 전송
  async sendImg(email, uri, base, year, month, token) {
    try {
      const { data } = await axios.post(
        `${host}/api/image/electricity`,
        {
          email,
          year,
          month,
          image: base,
          uri,
        },
        {
          headers: {
            Authorization: token.token,
          },
        }
      );

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

  // 전기 사진 수정
  async editImgInfo(id, numbers, token) {
    try {
      const { data } = await axios.put(
        `${host}/api/image/electricity/${id}/edit`,
        {
          id,
          ...numbers,
        },
        {
          headers: {
            Authorization: token.token,
          },
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

  // 전기 숫자 전송
  async sendNumber(email, year, month, numbers, token) {
    try {
      const { data } = await axios.post(
        `${host}/api/image/electricity/input`,
        {
          email,
          year,
          month,
          ...numbers,
        },
        {
          headers: {
            Authorization: token.token,
          },
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

  // 전기 그래프
  async getGraph(email, token) {
    try {
      const { data } = await axios.post(
        `${host}/api/graph/electricity/fee`,
        { email },
        {
          headers: {
            Authorization: token.token,
          },
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

  // =======================================
  // 가스 이미지 전송
  async sendGasImg(email, uri, base, year, month, token) {
    try {
      const { data } = await axios.post(
        `${host}/api/image/gas`,
        {
          email,
          year,
          month,
          image: base,
          uri,
        },
        {
          headers: {
            Authorization: token.token,
          },
        }
      );

      if (data.success) {
        return { data: data.data, success: data.success };
      } else {
        return { message: data.message, success: data.success };
      }
    } catch (err) {
      console.error(err.response);
      return { message: '/api/image/gas 에러', success: false };
    }
  },

  // 가스 사진 수정
  async editGasImgInfo(id, numbers, token) {
    try {
      const { data } = await axios.put(
        `${host}/api/image/gas/${id}/edit`,
        {
          id,
          ...numbers,
        },
        {
          headers: {
            Authorization: token.token,
          },
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

  // 가스 숫자 전송
  async sendGasNumber(email, year, month, numbers, token) {
    try {
      const { data } = await axios.post(
        `${host}/api/image/gas/input`,
        {
          email,
          year,
          month,
          ...numbers,
        },
        {
          headers: {
            Authorization: token.token,
          },
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
};

export default API;
