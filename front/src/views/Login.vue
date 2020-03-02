<template>
  <div class="login">
    <div class="form">
      <el-card>
        <header>
          Login to continue
        </header>
        <el-form :model="form">
          <el-form-item label="User log in">
            <el-input v-model="form.username" />
          </el-form-item>

          <el-form-item label="Password">
            <el-input type="password" v-model="form.password" />
          </el-form-item>
        </el-form>

        <el-footer>
          <el-button type="submit" @click="loginHandle" >Log In</el-button>
        </el-footer>

        <social-button type="Google"/>
        <social-button type="Facebook"/>

        <div class="sign-up-button" @click="$router.push('/register')">
          Not signed up?
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>

import SocialButton from '../components/SocialButton'
import { services } from '../service'

export default {
  name: 'Login',
  data () {
    return {
      form: {
        username: '',
        password: ''
      }
    }
  },
  components: {
    SocialButton
  },
  methods: {
    loginHandle () {
      services.auth.login(this.form.username, this.form.password)
        .then(() => {
          this.$router.push('/')
        })
    }
  }
}
</script>

<style>
  .login {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .form {
    margin-top: 15vh;
    width: 450px;
  }
  .sign-up-button {
    font-size: small;
    float: right;
    margin: 25px;
    cursor: pointer;
  }
  .sign-up-button:hover {
    transform: scale(1.1);
  }
</style>
