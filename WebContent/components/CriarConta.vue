<template>
  <div style="background: dodgerblue;height: 100vh;" class="d-flex justify-content-center">
        <div class="card align-self-center rounded" style="width: 400px;">
          <form class="col-12">
            <div class="card-body">
              <label>Nome</label>
              <input v-model="nome" class="form-control">
            </div>
            <div class="card-body">
              <label>Email</label>
              <input v-model="email" placeholder="seuemail@emailless.com" class="form-control">
            </div>
            <div class="card-body">
              <label>Senha</label>
              <input v-model="senha" :type="'password'" class="form-control">
            </div>
            <div class="card-body">
              <label>Cargo</label>
              <select v-model="cargoSelecionado" class="form-control">
                <option v-for="cargo in cargos" :value="cargo" :key="cargo.id">
                  {{ cargo.descricao }}
                </option>
              </select>
            </div>
            <div class="card-body">
              <button @click="$router.push({ name:'login'})" class="btn btn-danger">Cancelar</button><button @click="cadastrar()" class="btn btn-success float-right">Cadastrar</button>
            </div>
          </form>
        </div>
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
              contentType: 'application/json',
              dataType: 'json',
              data: JSON.stringify({email:ref.email,senha:ref.senha,nome:ref.nome,cargo:ref.cargoSelecionado?.id}),
              success: function (result) {

                alert("Cadastro feito com sucesso!")
                ref.email = ''
                ref.senha = ''
                ref.nome  = ''
                ref.cargoSelecionado = null

              },
              error: function (result){

                alert(result.responseText)

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
                
              },
              error: function (result){

                alert(result.responseText)

              }
            });
          }
        },

        mounted() {
          this.buscarCargos();
        }
    }
</script>
