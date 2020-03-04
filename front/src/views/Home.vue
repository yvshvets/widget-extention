<template>
  <div class="home">
    <nav-bar />
    <el-button @click="exit">Logout</el-button>
    <img alt="Vue logo" src="../assets/logo.png">
  </div>
</template>

<script>
import { services } from '../service'
import NavBar from '../components/NavBar'

export default {
  name: 'Home',
  components: { NavBar },
  methods: {
    exit () {
      services.auth.logout()
        .then(() => {
          this.$router.push('/login')
        })
    }
  }
}
</script>
