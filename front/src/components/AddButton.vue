<template>
  <div class="add-button-wrapper">
    <el-button @click="handle">+</el-button>
  </div>
</template>
<script>
export default {
  name: 'AddButton',
  methods: {
    handle () {
      this.$emit('click')
    }
  }
}
</script>
<style>
  .add-button-wrapper {
    position: fixed;
    z-index: 1;
    top: 90vh;
    left: 90vw;
  }
  .add-button-wrapper .el-button {
    font-size: x-large;
    background-color: green;
    border-radius: 50%;
    color: aliceblue;
    padding: 15px;
    line-height: 1;
  }
</style>
