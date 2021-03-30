<template>
  <div class="align-self-center">
    <button @click="showModal('criarOs')" style="height: 45px;" class="btn-default btn-success rounded">Criar ordem de serviço</button>
    <div style="height: auto;width: auto;display: block;margin-top: 15px;text-align: center;" class="rounded">
      <div style="background-color: white;height: 86%;width: 100%;display: block;" class="align-self-center rounded">
        <Tabela :url="'http://localhost:8080/buscarOs'" :exibir-menu="true" ref="tabelaAjax">

        </Tabela>
      </div>
      <b-modal ref="criarOs" @hide="reload()" hide-footer>

        <template #modal-header>
          <div class="mx-auto">
            <h5>Criar ordem de serviço</h5>
          </div>
        </template>

        <form class="col-12">
          <div class="card-body">
            <label>Titulo</label>
            <input v-model="titulo" class="form-control">
          </div>
          <div class="card-body">
            <label>Orçamento</label>
            <select v-model="orcamento" class="form-control">
              <option v-for="orcamento in orcamentos" :value="orcamento" :key="orcamento.id">
                {{ orcamento.id }}
              </option>
            </select>
          </div>
          <div class="card-body">
            <button @click="hideModal('criarOs')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="criarOs()" type="button" class="btn btn-success float-right">Criar</button>
          </div>
        </form>
      </b-modal>

      <b-modal ref="verDetalhesOrcamento" hide-footer onClose="reload">

        <template #modal-header>
          <div class="mx-auto">
            <h5>Detalhes</h5>
          </div>
        </template>

        <form class="col-12">
          <div class="card-body">
            <label>Horas Previstas</label>
            <input v-model="horasPrevistas" class="form-control">
          </div>
          <div class="card-body">
            <label>Responsável</label>
            <select v-model="responsavel" class="form-control">
              <option v-for="responsavel in responsaveis" :value="responsavel" :key="responsavel.id">
                {{ responsavel.email }}
              </option>
            </select>
          </div>
          <div class="card-body">
            <label>Cliente</label>
            <select v-model="cliente" class="form-control">
              <option v-for="cliente in clientes" :value="cliente" :key="cliente.id">
                {{ cliente.email }}
              </option>
            </select>
          </div>
        </form>

        <div class="card-body">
          <button @click="adicionarServico" class="btn-default btn-success rounded" type="button" style="height: 40px;"><font-awesome-icon :icon="['fa', 'plus-circle']" style="margin-right: 5px;"/>Adicionar Serviço</button>
        </div>

        <div class="card-body" v-for="servico in servicos" :key="servico">
          <label style="display: block;">Serviço #{{ servico.id }}</label>
          <input v-model="servico.descricao" class="form-control" style="width: 150px; display: inline;" placeholder="Descrição"><input v-model="servico.valor" @change="atualizarValor()" class="form-control" style="margin-left:15px;width: 60px;display: inline;" placeholder="valor">
          <button @click="excluirServico(servico.id)" style="margin-left: 15px;height: 38px;" class="btn-danger rounded" type="button">Excluir</button>
        </div>

        <div class="card-body">
          <button @click="hideModal('verDetalhesOrcamento')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="alterarOrcamento()" type="button" class="btn btn-success float-right">Salvar</button>
        </div>

      </b-modal>

      <b-modal ref="editar" hide-footer onClose="reload">

        <template #modal-header>
          <div class="mx-auto">
            <h5>Editar OS</h5>
          </div>
        </template>

        <form class="col-12">

          <div class="card-body">
            <label>titulo</label>
            <input v-model="titulo" class="form-control">
          </div>

          <div class="card-body">
            <button @click="hideModal('editar')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="editar()" type="button" class="btn btn-success float-right">Salvar</button>
          </div>

        </form>

      </b-modal>

    </div>
  </div>
</template>

<script>
import Tabela from "./Tabela";
export default {
  name: "OrdemServico",
  components: {Tabela},
  data: function(){
    return {
      id:null,
      orcamento:null,
      orcamentos:null,
      titulo:null,
      status:null,
      /*coisas do orcamento*/
      orcamentoId:null,
      clientes:[],
      horasPrevistas:null,
      responsaveis:[],
      servicos:[],
      responsavel:null,
      cliente:null,
    }
  },
  methods:{

    showModal: function(modaiId,acao) {
      var ref = this
      if(acao=='reload'){
        ref.reload();
      }

      ref.$refs.[modaiId].show()

    },
    hideModal: function(modaiId,acao) {

      var ref = this

      if(acao=='reload'){
        ref.reload();
      }

      ref.$refs.[modaiId].hide()

    },

    reload: function (){

      var ref = this;
      ref.carregarDados()
      ref.$refs.tabelaAjax.request();


    },

    editar(){

      var ref = this

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/editarOs",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id:ref.id,titulo:ref.titulo}),
        success: function (result) {

          alert("Orçamento editado com sucesso!")
          ref.reload();
          ref.hideModal('editar')

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    },

    excluir(id){

      var ref = this

      window.$.ajax({
        method: "DELETE",
        url: "http://localhost:8080/deletarOrdemServico",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id:id}),
        success: function (result) {

          alert("Orçamento excluido com sucesso!")
          ref.reload();

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    },

    carregarDados() {

      var ref = this

      window.$.ajax({
        method: "GET",
        url: "http://localhost:8080/buscarOrcamentos",
        contentType: 'application/json',
        dataType: 'json',
        success: function (result) {

          ref.orcamentos = result.result

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    },

    criarOs(){

      var ref = this

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/criarOs",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({titulo:ref.titulo,orcamento:ref.orcamento?.id}),
        success: function (result) {

          alert("Os criado com sucesso!")
          ref.hideModal('criarOs')
          ref.reload();

        },
        error: function (result){

          alert(result.responseText)

        }
      });

    },
    abrirPopupDetalhes(value){

      var ref = this
      ref.orcamentoId    = value.id;
      ref.responsavel    = value.funcionario;
      ref.cliente        = value.cliente;
      ref.horasPrevistas = value.horasPrevistas

      window.$.ajax({
        method: "GET",
        url: "http://localhost:8080/buscarCliente",
        contentType: 'application/json',
        dataType: 'json',
        success: function (result) {

          ref.clientes = result.result

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

      window.$.ajax({
        method: "GET",
        url: "http://localhost:8080/buscarResponsaveis",
        contentType: 'application/json',
        dataType: 'json',
        success: function (result) {

          ref.responsaveis = result.result

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

      window.$.ajax({
        method: "PUT",
        url: "http://localhost:8080/buscarServicos",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id:ref.orcamentoId}),
        success: function (result) {

          ref.servicos = result.result

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

      ref.showModal('verDetalhesOrcamento');

    },

    excluirServico:function (id) {
      var ref = this;

      window.$.ajax({
        method: "DELETE",
        url: "http://localhost:8080/excluirServico",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({idServico: id, idOrcamento: ref.orcamentoId}),
        success: function (result) {

          ref.reload();
          ref.hideModal('verDetalhesOrcamento', )
          alert("Excluido com sucesso bro!")

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    },

    alterarOrcamento: function(){

      var ref = this;

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/alterarOrcamento",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({servicos:ref.servicos,horasPrevistas:ref.horasPrevistas,cliente:ref.cliente.id,
          responsavel:ref.responsavel.id,valor:ref.valor,id:ref.orcamentoId}),
        success: function (result) {

          alert("Salvo com sucesso bro")
          ref.hideModal('verDetalhesOrcamento','reload')

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    },

    adicionarServico(){

      var ref = this

      ref.servicos.push({descricao:null,valor:0,position:ref.servicos.length+1})

    },

    abrirPopupEditar(ordemServico){

      var ref = this

      ref.titulo = ordemServico.titulo
      ref.status = ordemServico.status
      ref.id     = ordemServico.id

      ref.showModal('editar');

    }

  }
}
</script>

<style scoped>

</style>