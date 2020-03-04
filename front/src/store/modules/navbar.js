const navstore = {
  state: {
    items: []
  },

  actions: {
    loadItems ({ commit }) {

    }
  },

  mutations: {
    addItem: (state, { name, path, action }) => {
      state.items.push({ name, path, action })
    },
    clear: state => {
      state.items = []
    }
  },

  getters: {
    items: state => state.items
  }
}

export default navstore
