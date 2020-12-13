<template>
    <table class="table table-bordered">
      <thead class="thead-dark">
        <tr>
          <th v-for="chave in keys" v-show="chave!='id'" :key="chave" scope="col">{{ chave }}</th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="dado in dados" :key="dado.id" v-show="!dadosFiltrados.length>0&&(filtro == null || filtro == '')">
          <td scope="row" v-show="key!='id'" v-for="(value , key) in dado" :key="key">{{ value != null ? value.descricao == undefined ? value : value.descricao : value }}</td>
          <td><button type="button" class="btn-sm btn-primary btn-rounded" @click="editar(dado)">Editar</button></td>
          <td><button @click="excluir(dado.id)" class="btn-sm btn-danger float-left" type="button">Excluir</button></td>
        </tr>
        <tr v-for="dado in dadosFiltrados" :key="dado.id" v-show="dadosFiltrados.length>0||(filtro != null || filtro != '')">
          <td scope="row" v-show="key!='id'" v-for="(value , key) in dado" :key="key">{{ value != null ? value.descricao == undefined ? value : value.descricao : value }}</td>
          <td><button type="button" class="btn-sm btn-primary btn-rounded" @click="editar(dado)">Editar</button></td>
          <td><button @click="excluir(dado.id)" class="btn-sm btn-danger float-left" type="button">Excluir</button></td>
        </tr>
      </tbody>
      <tfoot>
        <tr>
          <td colspan="111">
            <input type="text" v-model="filtro" placeholder="filtro" class="float-right">
          </td>
        </tr>
      </tfoot>
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
      dadosFiltrados:[],
      keys:null,
      filtro:null
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
    excluir: function (id){

      this.$parent.excluir(id);

    },
    editar: function(dado) {

      this.$parent.abrirPopupEditar(dado);

    },
    tabelaFiltradaContemValor: function (valor){

      var ref = this

      for(var i=0;i<ref.dadosFiltrados.length;i++){

        if(ref.dadosFiltrados[i] == valor){
          return true
        }

      }

      return false

    }
  },
  watch:{
    filtro: function (newV,old){
      var ref = this

      ref.dadosFiltrados = []

      if(newV != null || newV != ""){

        for(var i in ref.dados){

          for(var j in ref.dados[i]){

            if(ref.tabelaFiltradaContemValor(ref.dados[i])){
              break
            }

            if(ref.dados[i][j].descricao != undefined){

              if(ref.dados[i][j].descricao.toString().toLowerCase().includes(newV.toLowerCase())) {

                ref.dadosFiltrados.push(ref.dados[i])

                break

              }

            }else if(ref.dados[i][j].toString().toLowerCase().includes(newV.toLowerCase())){

              ref.dadosFiltrados.push(ref.dados[i])

              break

            }

          }

        }

      }
    }
  },
  mounted() {
    this.request()
  }
}
</script>

<style scoped>

</style>