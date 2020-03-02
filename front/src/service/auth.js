import cookie from 'vue-cookie'
import axios from 'axios'

export default {

  isAuth () {
    return cookie.get('auth') === 'true'
  },
  login (username, password) {
    return axios
      .post('/api/login', new URLSearchParams([['username', username], ['password', password]]))
      .then(res => {
        cookie.set('auth', 'true')
      })
  },
  logout () {
    return axios
      .post('/api/logout')
      .then(() => {
        cookie.delete('auth')
      })
  },
  register (username, password) {
    return axios
      .post('/api/users', { username, password })
  }
}
