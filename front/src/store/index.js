import Vuex from 'vuex'
import Vue from 'vue'
import navstore from './modules/navbar'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    navstore
  }
})
