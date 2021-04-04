<template>
  <div class="align-self-center">
    <button @click="showModal('criarOrcamento')" style="height: 45px;" class="btn-default btn-success rounded">Criar Orçamento</button>

    <div style="height: auto;width: auto;display: block;margin-top: 15px;text-align: center;" class="rounded">
      <div style="background-color: white;height: 86%;width: 100%;display: block;" class="align-self-center rounded">
        <Tabela :url="'http://localhost:8080/buscarOrcamentos'" @abrirPopupAprovar="abrirPopupAprovar($event)" :exibir-menu-orcamento="true" ref="tabelaAjax"></Tabela>
      </div>
      <b-modal ref="criarOrcamento" @hide="reload()" hide-footer>

        <template #modal-header>
          <div class="mx-auto">
            <h5>Criar Orçamento</h5>
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

          <div class="card-body">
            <button @click="adicionarServico" class="btn-default btn-success rounded" type="button" style="height: 40px;"><font-awesome-icon :icon="['fa', 'plus-circle']" style="margin-right: 5px;"/>Adicionar Serviço</button>
          </div>

          <div class="card-body" v-for="servico in servicos" :key="servico">
              <label style="display: block;">Serviço #{{ servico.position }}</label>
              <input v-model="servico.descricao" class="form-control" style="width: 150px; display: inline;" placeholder="Descrição"><input v-model="servico.valor" @change="atualizarValor()" class="form-control" style="margin-left:15px;width: 60px;display: inline;" placeholder="valor">
          </div>

          <div class="card-body">
            <label>Valor</label>
            <input v-model="valor" class="form-control" style="width: 60px;" disabled>
          </div>
          <div class="card-body">
            <button @click="hideModal('criarOrcamento')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="criarOrcamento()" type="button" class="btn btn-success float-right">Criar</button>
          </div>
        </form>
      </b-modal>
      <b-modal ref="editar" hide-footer onclose="reload()">

        <template #modal-header>
          <div class="mx-auto">
            <h5>Editar Orçamento</h5>
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
          <div class="card-body">
            <button @click="hideModal('editar')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="editar()" type="button" class="btn btn-success float-right">Salvar</button>
          </div>
        </form>
      </b-modal>
      <b-modal ref="servicos" hide-footer onclose="reload()">

        <template #modal-header>
          <div class="mx-auto">
            <h5>Editar Servicos</h5>
          </div>
        </template>

        <form class="col-12">

          <div class="card-body">
            <button @click="adicionarServico" class="btn-default btn-success rounded" type="button" style="height: 40px;"><font-awesome-icon :icon="['fa', 'plus-circle']" style="margin-right: 5px;"/>Adicionar Serviço</button>
          </div>

          <div class="card-body" v-for="servico in servicos" :key="servico">
            <label style="display: block;">Serviço #{{ servico.id }}</label>
            <input v-model="servico.descricao" class="form-control" style="width: 150px; display: inline;" placeholder="Descrição"><input v-model="servico.valor" @change="atualizarValor()" class="form-control" style="margin-left:15px;width: 60px;display: inline;" placeholder="valor">
            <button @click="excluirServico(servico.id)" style="margin-left: 15px;height: 38px;" class="btn-danger rounded" type="button">Excluir</button>
          </div>

          <div class="card-body">
            <button @click="hideModal('servicos')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="salvarServicos()" type="button" class="btn btn-success float-right">Salvar</button>
          </div>
        </form>
      </b-modal>

      <b-modal ref="aprovar" hide-footer>

        <template #modal-header>
          <div class="mx-auto">
            <h5>Confirmar</h5>
          </div>
        </template>

        <form class="col-12">

          <div class="card-body">
            Tem certeza que deseja aprovar o orçamento?
          </div>

          <div class="card-body">
            <button @click="hideModal('aprovar')" type="button" class="btn btn-secondary float-left">Fechar</button><button @click="aprovar()" type="button" class="btn btn-success float-right">Confirmar</button>
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
      valor:0,
      clientes:[],
      horasPrevistas:null,
      responsaveis:[],
      servicos:[],
      responsavel:null,
      cliente:null,
      id:null
    }
  },
  watch:{
    servicos:function (newVal,old){

      var ref = this

      ref.atualizarValor();

    }
  },
  methods: {
    criarOrcamento: function () {
      var ref = this

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/criarOrcamento",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
          horasPrevistas: ref.horasPrevistas, cliente: ref.cliente?.id,
          responsavel: ref.responsavel?.id, servicos: ref.servicos, valor: ref.valor.toString()
        }),
        success: function (result) {

          alert("Orçamento criado com sucesso!")
          ref.hideModal('criarOrcamento')
          ref.reload();

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    },
    editar: function () {

      var ref = this

      window.$.ajax({
        method: "PUT",
        url: "http://localhost:8080/editarOrcamento",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
          horasPrevistas: ref.horasPrevistas, cliente: ref.cliente.id,
          responsavel: ref.responsavel.id, id: ref.id
        }),
        success: function (result) {

          alert("Orcamento atualizado com sucesso!")
          ref.hideModal('editar')
          ref.reload();

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    },
    excluir: function (id) {

      var ref = this

      window.$.ajax({
        method: "DELETE",
        url: "http://localhost:8080/deletarOrcamento",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id: id}),
        success: function (result) {

          alert("Orçamento excluido com sucesso!")
          ref.reload();

        },
        error: function (result) {

          alert(result.responseText)

        }
      });
    },
    showModal: function (modaiId, acao) {

      var ref = this
      if (acao == 'reload') {
        ref.reload();
      }
      ref.carregarDados();
      ref.$refs[modaiId].show()

    },
    hideModal: function (modaiId, acao) {

      var ref = this
      if (acao == 'reload') {
        ref.reload();
      }
      ref.$refs[modaiId].hide()

    },
    abrirPopupEditar: function (orcamento) {

      var ref = this

      ref.id = orcamento.id
      ref.horasPrevistas = orcamento.horasPrevistas
      ref.valor = orcamento.valor
      ref.responsavel = orcamento.funcionario
      ref.cliente = orcamento.cliente

      ref.carregarDados()
      ref.showModal('editar');

    },
    reload: function () {

      var ref = this;
      ref.$refs.tabelaAjax.request();
      ref.id = null
      ref.horasPrevistas = null
      ref.responsavel = null
      ref.cliente = null
      ref.servicos = []
      ref.valor = 0

    },

    carregarDados() {

      var ref = this

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

    },

    adicionarServico() {

      var ref = this

      ref.servicos.push({descricao: null, valor: 0, position: ref.servicos.length + 1})

    },

    atualizarValor() {

      var ref = this

      ref.valor = 0

      for (var i = 0; i < ref.servicos.length; i++) {

        ref.valor = Number(ref.valor) + Number(ref.servicos[i].valor)

      }

    },

    abrirPopupServicos: function (value) {

      var ref = this;

      ref.id = value.id

      ref.carregarServicos();
      ref.showModal('servicos');

    },
    carregarServicos: function () {

      var ref = this;

      window.$.ajax({
        method: "PUT",
        url: "http://localhost:8080/buscarServicos",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id: ref.id}),
        success: function (result) {

          ref.servicos = result.result

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    },

    excluirServico: function (id) {
      var ref = this;

      window.$.ajax({
        method: "DELETE",
        url: "http://localhost:8080/excluirServico",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({idServico: id, idOrcamento: ref.id}),
        success: function (result) {

          ref.carregarServicos()
          ref.hideModal('servicos', 'reload')
          alert("Excluido com sucesso bro!")

        },
        error: function (result) {

          alert(result.responseText)

        }
      });


    },

    salvarServicos: function () {

      var ref = this;

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/salvarServicos",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({servicos: ref.servicos, id: ref.id}),
        success: function (result) {

          alert("Salvo com sucesso bro")
          ref.hideModal('servicos', 'reload')

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    },

    abrirPopupAprovar(id){

      var ref = this
      ref.id = id
      ref.showModal("aprovar")

    },

    aprovar(){

      var ref = this

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/aprovar",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({id: ref.id}),
        success: function (result) {

          alert("Aprovado com sucesso!")
          ref.hideModal('aprovar', 'reload')

        },
        error: function (result) {

          alert(result.responseText)

        }
      });

    }
  }
}
</script>
