<template>
  <div class="wrapper">
    <div class="logo"></div>
    <div class="menu">
      <i v-for="item in items" :key="item.path" class="menu-item">
        <router-link :to="item.path"><div>{{item.name}}</div></router-link>
      </i>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'

export default {
  name: 'NavBar',
  computed: {
    ...mapGetters(['items'])
  },
  methods: {
    ...mapMutations(['addItem'])
  }
}
</script>
<style lang="scss">
  .wrapper {
    height: 50px;
    background-color: #2c3e50;
    display: flex;
    flex-direction: row;

    .logo {
      height: 50px;
      width: 150px;
    }

    .menu {
      display: flex;
      align-items: center;
      justify-content: flex-end;
      width: 100%;

      .menu-item {
        margin: 5px;
        padding: 10px;

        a {
          text-decoration: none;
          color: aliceblue;
        }
      }
    }
  }
</style>
