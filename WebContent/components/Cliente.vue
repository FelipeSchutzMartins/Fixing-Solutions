<template>
  <div class="align-self-center">
    <button @click="showModal('criarCliente')" style="height: 45px;" class="btn-default btn-primary rounded">Criar Cliente</button>
    <div style="height: auto;width: auto;display: block;margin-top: 15px;text-align: center;" class="rounded">
      <div style="background-color: white;height: 86%;width: 100%;display: block;" class="align-self-center rounded">
        <Tabela :url="'http://localhost:8080/buscarClientes'" ref="tabelaAjax"></Tabela>
      </div>
      <b-modal ref="criarCliente" @hide="reload()" hide-footer>

        <template #modal-header>
          <div class="mx-auto">
            <h5>Criar Cliente</h5>
          </div>
        </template>

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
              <label>CPF</label>
              <input v-model="cpf" v-mask="'###.###.###-##'" class="form-control">
            </div>
            <div class="card-body">
              <label>Telefone</label>
              <input v-model="telefone" v-mask="'(##) #####-####'" class="form-control">
            </div>
            <div class="card-body">
              <button @click="hideModal('criarCliente')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="criarCliente()" type="button" class="btn btn-success float-right">Criar</button>
            </div>
          </form>
      </b-modal>
      <b-modal ref="editar" hide-footer onclose="reload()" title="Editar Cliente">

        <template #modal-header>
          <div class="mx-auto">
            <h5>Editar Cliente</h5>
          </div>
        </template>

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
            <label>CPF</label>
            <input v-model="cpf" v-mask="'###.###.###-##'" class="form-control">
          </div>
          <div class="card-body">
            <label>Telefone</label>
            <input v-model="telefone" v-mask="'(##) #####-####'" class="form-control">
          </div>
          <div class="card-body">
            <button @click="hideModal('editar')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="editar()" type="button" class="btn btn-success float-right">Salvar</button>
          </div>
        </form>
      </b-modal>
    </div>

    <b-modal ref="confirmacaoExclusaoCliente" hide-footer>

      <template #modal-header>
        <div class="mx-auto">
          <h5>Confirmar</h5>
        </div>
      </template>

      <form class="col-12">

        <div class="card-body">
          Tem certeza que deseja excluir o cliente?
        </div>

        <div class="card-body">
          <button @click="hideModal('confirmacaoExclusaoCliente')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="excluirCliente()" type="button" class="btn btn-success float-right">Confirmar</button>
        </div>

      </form>
    </b-modal>

  </div>
</template>

<script>
import Tabela from "./Tabela";
export default {
  name: "Cliente",
  components: {Tabela},
  data: function(){
    return {
      telefone:null,
      cpf:null,
      email:null,
      nome:null,
      id:null
    }
  },
  methods:{
    criarCliente: function () {
      var ref = this

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/criarCliente",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({email:ref.email,nome:ref.nome,cpf:ref.cpf,telefone:ref.telefone}),
        success: function (result) {

          alert("Cliente criado com sucesso!")
          ref.hideModal('criarCliente')
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
        url: "http://localhost:8080/editarCliente",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id:ref.id,email:ref.email,nome:ref.nome,cpf:ref.cpf,telefone:ref.telefone}),
        success: function (result) {

          alert("Cliente atualizado com sucesso!")
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
      ref.id = id
      ref.showModal('confirmacaoExclusaoCliente')

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
    abrirPopupEditar: function(cliente){

      var ref = this
      ref.id = cliente.id
      ref.telefone = cliente.telefone
      ref.nome = cliente.nome
      ref.cpf = cliente.cpf
      ref.email = cliente.email
      ref.showModal('editar');

    },
    reload: function (){

      var ref = this;
      ref.$refs.tabelaAjax.request();
      ref.id = null
      ref.telefone = null
      ref.nome = null
      ref.cpf = null
      ref.email = null

    },

    excluirCliente: function () {

      var ref = this

      window.$.ajax({
        method: "DELETE",
        url: "http://localhost:8080/deletarCliente",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id: ref.id}),
        success: function (result) {

          alert("Cliente excluido com sucesso!")
          ref.hideModal('editar')
          ref.hideModal('confirmacaoExclusaoCliente')
          ref.reload();

        },
        error: function (result) {

          alert(result.responseText)

        }
      });
    },

  }
}
</script>

<style scoped>

</style>