<template>
  <div class="align-self-center">
    <button @click="showModal('criarFuncionario','reload')" style="height: 45px;" class="btn-default btn-success rounded">Criar Funcionario</button>

    <div style="height: auto;width: auto;display: block;margin-top: 15px;text-align: center;" class="rounded">
      <div style="background-color: white;height: 86%;width: 100%;display: block;" class="align-self-center rounded">
        <Tabela :url="'http://localhost:8080/buscarFuncionario'" ref="tabelaAjax"></Tabela>
      </div>
      <b-modal ref="criarFuncionario" hide-footer title="Criar Funcionario">
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
            <button @click="criarCliente()" type="button" class="btn btn-success float-right">Criar</button>
          </div>
        </form>
      </b-modal>
      <b-modal ref="editar" hide-footer onclose="reload()" title="Criar Funcionario">
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
            <button @click="editar()" type="button" class="btn btn-success float-right">Salvar</button>
          </div>
        </form>
      </b-modal>
    </div>
  </div>
</template>

<script>
import Tabela from "./Tabela";
export default {
  name: "Orcamento",
  components: {Tabela},
  data: function(){
    return {
      id:null,
      email: null,
      senha: null,
      nome: null,
      cargoSelecionado:null,
      cargos: []
    }
  },
  methods:{
    criarFuncionario: function () {
      var ref = this

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/criarConta",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({email:ref.email,senha:ref.senha,nome:ref.nome,cargo:ref.cargoSelecionado?.id}),
        success: function (result) {

          alert("Funcionario criado com sucesso!")
          ref.hideModal('criarFuncionario')
          ref.reload();

        },
        error: function (result){

          alert(result.responseText)

        }
      });

    },
    editar:function (){

      var ref = this

      window.$.ajax({
        method: "PUT",
        url: "http://localhost:8080/editarFuncionario",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id:ref.id,email:ref.email,senha:ref.senha,nome:ref.nome,cargo:ref.cargoSelecionado?.id}),
        success: function (result) {

          alert("Funcionario atualizado com sucesso!")
          ref.hideModal('editar')
          ref.reload();

        },
        error: function (result){

          alert(result.responseText)

        }
      });

    },
    excluir: function (id) {

      var ref = this

      window.$.ajax({
        method: "DELETE",
        url: "http://localhost:8080/deletarFuncionario",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id: id}),
        success: function (result) {

          alert("Funcionario excluido com sucesso!")
          ref.hideModal('editar')
          ref.reload();

        },
        error: function (result) {

          alert(result.responseText)

        }
      });
    },
    showModal: function(modaiId,acao) {
      var ref = this
      if(acao=='reload'){
        ref.reload();
      }
      ref.$refs[modaiId].show()

    },
    hideModal: function(modaiId) {

      this.$refs[modaiId].hide()

    },
    abrirPopupEditar: function(funcionario){

      var ref = this
      ref.id = funcionario.id
      ref.senha = funcionario.senha
      ref.nome = funcionario.nome
      ref.email = funcionario.email
      ref.cargoSelecionado = funcionario.cargo
      ref.showModal('editar');

    },
    reload: function (){

      var ref = this;
      ref.$refs.tabelaAjax.request();
      ref.id = null
      ref.senha = null
      ref.nome = null
      ref.email = null
      ref.cargoSelecionado = null

    },
    buscarCargos: function (){
      var ref = this

      window.$.ajax({
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
