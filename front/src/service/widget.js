import axios from 'axios'

export default {
  all () {
    return axios.get('/api/widget')
  },
  activate (name) {
    return axios.post('/api/widget/activate', {}, { params: { id: name } })
  },
  deActivate (name) {
    return axios.post('/api/widget/deactivate', {}, { params: { id: name } })
  },
  myWidgets () {
    return axios.get('/api/widget/my')
  },
  widgetContent (id) {
    return axios.get(`/api/widget/${id}/content`)
  }
}
