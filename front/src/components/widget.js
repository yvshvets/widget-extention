import axios from 'axios'

export function allWidget () {
  return axios
    .get('/api/widget')
}

export function add (file) {
  const form = new FormData()
  form.append('files', file)
  return axios
    .post('/api/widget', form, {
      headers: {
        'content-type': `multipart/form-data; boundary=${form._boundary}`
      }
    })
}
