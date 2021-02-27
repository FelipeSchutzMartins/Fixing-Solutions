<template>
  <div class="align-self-center">
    <button @click="showModal('criarOrcamento','reload')" style="height: 45px;" class="btn-default btn-success rounded">Criar Orçamento</button>

    <div style="height: auto;width: auto;display: block;margin-top: 15px;text-align: center;" class="rounded">
      <div style="background-color: white;height: 86%;width: 100%;display: block;" class="align-self-center rounded">
        <Tabela :url="'http://localhost:8080/buscarOrcamentos'" ref="tabelaAjax"></Tabela>
      </div>
      <b-modal ref="criarOrcamento" hide-footer title="Criar Orçamento">
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

          <a @click="adicionarServico"><i></i>Adicionar Serviço</a>

          <div class="card-body" v-for="servico in servicos" :key="servico.descricao">
            <input v-model="servico.descricao" class="form-control" style="width: 60px;" placeholder="Descrição"><input v-model="servico.valor" @change="atualizarValor()" class="form-control" style="width: 60px;" placeholder="valor">
          </div>

          <div class="card-body">
            <label>Valor</label>
            <input v-model="valor" class="form-control" style="width: 60px;" disabled>
          </div>
          <div class="card-body">
            <button @click="criarOrcamento()" type="button" class="btn btn-success float-right">Criar</button>
          </div>
        </form>
      </b-modal>
      <b-modal ref="editar" hide-footer onclose="reload()" title="Editar Orçamento">
<!--        <form class="col-12">-->
<!--          <div class="card-body">-->
<!--            <label></label>-->
<!--            <input v-model="" class="form-control">-->
<!--          </div>-->
<!--          <div class="card-body">-->
<!--            <label></label>-->
<!--            <input v-model="" placeholder="" class="form-control">-->
<!--          </div>-->
<!--          <div class="card-body">-->
<!--            <label>Senha</label>-->
<!--            <input v-model="" class="form-control">-->
<!--          </div>-->
<!--          <div class="card-body">-->

<!--          </div>-->
<!--          <div class="card-body">-->
<!--            <button @click="editar()" type="button" class="btn btn-success float-right">Salvar</button>-->
<!--          </div>-->
<!--        </form>-->
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
      cliente:null
    }
  },
  watch:{
    servicos:function (newVal,old){

      var ref = this

      ref.atualizarValor();

    }
  },
  methods:{
    criarOrcamento: function () {
      var ref = this

      window.$.ajax({
        method: "POST",
        url: "http://localhost:8080/criarOrcamento",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({horasPrevistas:ref.horasPrevistas,cliente:ref.cliente.id,
          responsavel:ref.responsavel.id,servicos:ref.servicos,valor:ref.valor}),
        success: function (result) {

          alert("Orçamento criado com sucesso!")
          ref.hideModal('criarOrcamento')
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
        url: "http://localhost:8080/editarOrcamento",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({}),
        success: function (result) {

          alert("Orcamento atualizado com sucesso!")
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
        url: "http://localhost:8080/deletarOrcamento",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({}),
        success: function (result) {

          alert("Orçamento excluido com sucesso!")
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
      ref.carregarDadosCriacao();
      ref.$refs[modaiId].show()

    },
    hideModal: function(modaiId) {

      this.$refs[modaiId].hide()

    },
    abrirPopupEditar: function(Orcamento){

      var ref = this

      ref.showModal('editar');

    },
    reload: function (){

      var ref = this;
      ref.$refs.tabelaAjax.request();


    },

    carregarDadosCriacao(){

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

    adicionarServico(){

      var ref = this

      ref.servicos.push({descricao:null,valor:0})

    },

    atualizarValor(){

      var ref = this

      ref.valor = 0

      for(var i=0;i<ref.servicos.length;i++){

        ref.valor = Number(ref.valor) + Number(ref.servicos[i].valor)

      }

    }

  }
}
</script>
