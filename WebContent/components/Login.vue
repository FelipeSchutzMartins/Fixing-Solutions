<template>
  <div>
    <input v-model="email" placeholder="seuemail@emailless.com">
    <input v-model="senha" :type="'password'">
    <button @click="fazerLogin()">Login</button>
  </div>
</template>

<script>
    export default {
        name: "Login",
        data () {
          return {
            email: '',
            senha: ''
          }
        },
       methods: {
          fazerLogin: function () {

          }
       }
    }
</script>

<style scoped>

</style>
