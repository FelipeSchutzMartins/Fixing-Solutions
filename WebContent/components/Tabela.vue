<template>
  <div>
    <table>
      <tbody>
      <tr>
        <td v-for="chave in keys" :key="chave">{{ chave }}</td>
        <td></td>
      </tr>
      <tr v-for="dado in dados" :key="dado.id">
        <td v-for="(value , key) in dado" :key="key">{{ value }}</td>
        <button type="button" @click="editar(dado)">Editar</button>
      </tr>
      </tbody>
    </table>
  </div>
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