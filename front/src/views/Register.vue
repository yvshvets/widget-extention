<template>
  <div class="register">
    <div class="form">
      <el-card>
        <header>
          Register to continue
        </header>
        <el-form :model="form">
          <el-form-item label="User name">
            <el-input v-model="form.username" />
          </el-form-item>

          <el-form-item label="Password">
            <el-input type="password" v-model="form.password" />
          </el-form-item>

          <el-form-item label="Confirm password">
            <el-input type="password" v-model="form.confirm" />
          </el-form-item>
        </el-form>

        <el-footer>
          <el-button type="submit" @click="registerHandle" >Register</el-button>
        </el-footer>

        <div class="sign-up-button" @click="$router.push('/login')">
          Already signed up?
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import { register } from '../components/auth'

export default {
  name: 'Register',
  data () {
    return {
      form: {
        username: '',
        password: '',
        confirm: ''
      }
    }
  },
  methods: {
    registerHandle () {
      register(this.form.username, this.form.password)
        .then(() => {
          this.$router.push('/')
        })
    }
  }
}
</script>
<style>
  .register {
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
