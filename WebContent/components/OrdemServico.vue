<template>
  <div class="align-self-center">
    <button @click="showModal('criarOs','reload')" style="height: 45px;" class="btn-default btn-success rounded">Criar ordem de serviço</button>
    <div style="height: auto;width: auto;display: block;margin-top: 15px;text-align: center;" class="rounded">
      <div style="background-color: white;height: 86%;width: 100%;display: block;" class="align-self-center rounded">
        <Tabela :url="'http://localhost:8080/buscarOs'" ref="tabelaAjax"></Tabela>
      </div>
      <b-modal ref="criarOs" hide-footer title="Criar ordem de serviço">
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
            <button @click="criarOs()" type="button" class="btn btn-success float-right">Criar</button>
          </div>
        </form>
      </b-modal>
      <b-modal ref="editar" hide-footer onclose="reload()" title="">
        <form class="col-12">

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
      orcamento:null,
      orcamentos:null,
      titulo:null
    }
  },
  methods:{

    showModal: function(modaiId,acao) {
      var ref = this
      if(acao=='reload'){
        ref.reload();
      }
      ref.carregarDados();
      ref.$refs[modaiId].show()

    },
    hideModal: function(modaiId) {

      this.$refs[modaiId].hide()

    },

    reload: function (){

      var ref = this;
      ref.$refs.tabelaAjax.request();


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

    }

  }
}
</script>

<style scoped>

</style>