<template>
  <div class="home">
    <el-button @click="exit">Logout</el-button>
    <img alt="Vue logo" src="../assets/logo.png">
  </div>
</template>

<script>
import { logout } from '../components/auth'

export default {
  name: 'Home',
  methods: {
    exit () {
      logout()
        .then(() => {
          this.$router.push('/login')
        })
    }
  }
}
</script>
