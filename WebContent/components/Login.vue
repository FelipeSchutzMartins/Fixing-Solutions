<template>
<!--  <div style="background: dodgerblue;height: 100vh;" class="d-flex justify-content-center">-->
    <div style="height: 100vh;" :style="{backgroundImage: 'url('+require('../assets/backgroundImage.jpg')+')'}" class="d-flex justify-content-center">
    <div class="card align-self-center rounded" style="width: 400px;">
      <form class="col-12">
        <div class="card-body">
          <label>Email</label>
          <input v-model="email" placeholder="seuemail@emailless.com" class="form-control">
        </div>
        <div class="card-body">
          <label>Senha</label>
          <input v-model="senha" :type="'password'" class="form-control">
        </div>
        <div class="card-body">
          <button @click="$router.push({ name:'criarConta'})" class="btn btn-primary">Criar Conta</button><button @click="fazerLogin()" type="button" class="btn btn-success float-right">Login</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
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

          var ref = this;

          window.$.ajax({
            method: "POST",
            url: "http://localhost:8080/login",
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({email:ref.email,senha:ref.senha}),
            success: function (result) {

              ref.$router.push({ name:'home',params: { user: 'someValue' }})

            },
            error: function (result){

              alert(result.responseText)

            }
          });
        }
      }
    }
</script>

<style scoped>

</style>
