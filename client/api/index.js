import axios from 'axios';

const host = 'https://34.171.248.55';

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
};

export default API;
