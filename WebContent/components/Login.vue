<template>
  <div>
    <input v-model="email" placeholder="seuemail@emailless.com">
    <br><br>
    <input v-model="senha" :type="'password'">
    <br><br>
    <button @click="$router.push({ name:'criarConta'})">Criar Conta</button><button @click="fazerLogin()">Login</button>
  </div>
</template>

<script>
    import jQuery from "jquery";
    const $ = jQuery;
    export default {
      name: "Login",
      data() {
        return {
          email: '',
          senha: ''
        }
      },
      methods: {
        fazerLogin: function () {

          var ref = this

          $.ajax({
            method: "POST",
            url: "http://localhost:8080/login",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({email:ref.email,senha:ref.senha}),
            success: function (result) {
              console.log(result)
            }
          });
        }
      }
    }
</script>

<style scoped>

</style>
