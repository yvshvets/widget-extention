<template>
  <div class="social-button">
    <el-icon name="google" />
    <span>Log in with {{type}}</span>
  </div>
</template>

<script>
export default {
  name: 'SocialButton',
  props: {
    type: {
      type: String,
      default: ''
    }
  }
}
</script>
<style>
  .social-button {
    margin: 5px;
    font-size: small;
  }
</style>
