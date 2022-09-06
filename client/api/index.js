import axios from 'axios';

const host = 'http://34.171.248.55:8080';

const API = {
  // 로그인
  async getLogin({ email, password }) {
    try {
      const res = await axios.post(`${host}/api/user/login`, {
        email,
        password,
      });

      if (res.data.success) {
        return { user: res.data, success: res.data.success };
      } else {
        return { message: res.data.message, success: res.data.success };
      }
    } catch (err) {
      console.error(err);
    }
  },

  // 회원가입
  async getSignup({ email, nickname, password, province, city }) {
    try {
      const res = await axios.post(`${host}/api/user/signup`, {
        email,
        nickname,
        password,
        province,
        city,
      });

      if (res.data.success) {
        return { user: res.data, success: res.data.success };
      } else {
        return { message: res.data.message, success: res.data.success };
      }
    } catch (err) {
      console.error(err);
    }
  },

  // 이미지 전송
  async sendImg(uri) {
    try {
      const index = uri.indexOf('Camera/');
      const name = uri.slice(index + 7);
      const dotIndex = name.indexOf('.');
      const type = name.slice(dotIndex + 1);
      const formData = new FormData();

      // return;
      formData.append('image', {
        uri,
        name,
        type: `image/${type}`,
      });

      const res = await axios.post(
        `${host}/api/image/electrocity?type=image`,
        formData,
        { header: { 'Content-Type': 'multipart/form-data' } }
      );

      if (res.data.success) {
        return res.data;
      } else {
        return res.data.message;
      }
    } catch (err) {
      console.error(err);
    }
  },
};

export default API;
