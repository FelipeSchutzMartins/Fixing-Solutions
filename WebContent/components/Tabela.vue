<template>
    <table class="table table-bordered">
      <thead class="thead-dark">
        <tr>
          <th v-for="chave in keys" :key="chave" scope="col">{{ chave }}</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
      <tr v-for="dado in dados" :key="dado.id">
        <td scope="row" v-for="(value , key) in dado" :key="key">{{ value }}</td>
        <td><button type="button" class="btn-sm btn-primary btn-rounded" @click="editar(dado)">Editar</button></td>
      </tr>
      </tbody>
    </table>
</template>

<script>
export default {
  name: "tabela",
  props: {
    url:null
  },
  data: function (){
    return {
      dados:[],
      keys:null
    }
  },
  methods:{
    request: function(){

      var ref = this;

      window.$.ajax({
        method: "GET",
        url: ref.url,
        dataType: 'json',
        success: function (result) {

          ref.dados = result.result
          ref.keys = Object.keys(result.result[0])

        }
      });

    },
    editar: function(dado) {

      this.$parent.abrirPopupEditar(dado);

    }
  },
  mounted() {
    this.request()
  }
}
</script>

<style scoped>

</style>