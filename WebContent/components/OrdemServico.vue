<template>
  <div class="align-self-center">
    <button @click="showModal('','reload')" style="height: 45px;" class="btn-default btn-success rounded">Criar Cliente</button>
    <div style="height: auto;width: auto;display: block;margin-top: 15px;text-align: center;" class="rounded">
      <div style="background-color: white;height: 86%;width: 100%;display: block;" class="align-self-center rounded">
        <Tabela :url="'http://localhost:8080/'" ref="tabelaAjax"></Tabela>
      </div>
      <b-modal ref="" hide-footer title="">
        <form class="col-12">

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

    }
  },
  methods:{

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

    reload: function (){

      var ref = this;
      ref.$refs.tabelaAjax.request();


    }
  }
}
</script>

<style scoped>

</style>