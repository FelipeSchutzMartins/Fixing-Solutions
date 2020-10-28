<template>
  <div>
    <input v-model="email" placeholder="seuemail@emailless.com">
    <input v-model="senha" :type="'password'">
    <button @click="cadastrar()">Cadastrar</button>
  </div>
</template>

<script>
    export default {
        name: "CadastroUsuario",
        data () {
          return {
            email: '',
            senha: ''
          }
        },
        methods: {
          cadastrar: function () {

          }
        }
    }
</script>

<style scoped>

</style>
