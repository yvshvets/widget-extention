<template>
  <div class="home">
    <el-button @click="exit">Logout</el-button>
    <img alt="Vue logo" src="../assets/logo.png">
  </div>
</template>

<script>
import { services } from '../service'

export default {
  name: 'Home',
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
