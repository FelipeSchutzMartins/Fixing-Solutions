<template>
  <div>
    <input v-model="nome">
    <br><br>
    <input v-model="email" placeholder="seuemail@emailless.com">
    <br><br>
    <input v-model="senha" :type="'password'">
    <br><br>
    <select v-model="cargoSelecionado">
      <option v-for="cargo in cargos" :value="cargo" :key="cargo.id">
        {{ cargo.descricao }}
      </option>
    </select>
    <button @click="$router.push({ name:'login'})">Cancelar</button><button @click="cadastrar()">Cadastrar</button>
  </div>
</template>

<script>
    import jQuery from "jquery";
    const $ = jQuery;
    export default {
        name: "criarConta",
        data () {
          return {
            email: '',
            senha: '',
            nome: '',
            cargoSelecionado:null,
            cargos: []
          }
        },
        methods: {
          cadastrar: function () {
            var ref = this
            $.ajax({
              method: "POST",
              url: "http://localhost:8080/criarConta",
              contentType: 'application/json; charset=utf-8',
              dataType: 'json',
              data:JSON.stringify({email:ref.email,senha:ref.senha,nome:ref.nome,cargo:ref.cargoSelecionado.id}),
              success: function (result) {
                console.log(result)
              }
            });
          },
          buscarCargos: function (){
            var ref = this

            $.ajax({
              method: "GET",
              url: "http://localhost:8080/buscarCargos",
              contentType: "application/json",
              success: function (result) {

                ref.cargos = result.result
                
              }
            });
          }
        },

        mounted() {
          this.buscarCargos();
        }
    }
</script>

<style scoped>

</style>
