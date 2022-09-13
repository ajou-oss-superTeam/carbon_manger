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

  // 사진 수정
  async editImgInfo(id, numbers) {
    try {
      const { data } = await axios.post(
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
      console.error(err);
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
      console.error(err);
    }
  },

  // 숫자 전송
  async getGraph(email) {
    try {
      const { data } = await axios.post(`${host}/api/graph/electricity/fee`, {
        email,
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
