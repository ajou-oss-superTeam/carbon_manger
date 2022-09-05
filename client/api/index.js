import axios from 'axios';

const host = 'http://34.171.248.55:8080';

const API = {
  async getLogin({ email, passowrd }) {
    try {
      const res = await axios.post(`${host}/api/user/login`, {
        email,
        passowrd,
      });

      if (res.data.success) {
        return res.data;
      } else {
        return res.data.message;
      }
    } catch (err) {
      console.error(err);
    }
  },

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
