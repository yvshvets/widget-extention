import cookie from 'vue-cookie'
import axios from 'axios'

export function isAuth () {
  return cookie.get('auth') === 'true'
}

export function login (username, password) {
  return axios
    .post('/api/login', new URLSearchParams([['username', username], ['password', password]]))
    .then(res => {
      cookie.set('auth', 'true')
    })
}

export function logout () {
  return axios
    .post('/api/logout')
    .then(() => {
      cookie.delete('auth')
    })
}

export function register (username, password) {
  return axios
    .post('/api/users', { username, password })
}
