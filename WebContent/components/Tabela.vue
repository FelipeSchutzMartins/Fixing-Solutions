<template>
  <transition name="slide-fade">
    <table class="table table-bordered">
      <thead class="thead-dark">
        <tr>
          <th v-for="chave in keys" v-show="chave!='Id'" :key="chave" scope="col">{{ chave }}</th>
          <th></th>
          <th></th>
          <th v-if="exibirMenu"></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="dado in dados" :key="dado.id" v-show="!dadosFiltrados.length>0&&(filtro == null || filtro == '')">
          <td scope="row" v-show="key!='id'&&key!='servicos'&&key!='orcamento'" v-for="(value , key) in dado" :key="key">{{ valorExibido(value) }}</td>
          <td v-show="dado.servicos!=undefined"><button type="button" class="btn-sm btn-primary btn-rounded" @click="verServico(dado)">Ver serviços</button></td>
          <td v-show="dado.orcamento!=undefined"><button type="button" class="btn-sm btn-primary btn-rounded" @click="verDetalhes(dado)">Ver detalhes</button></td>
          <td><button type="button" class="btn-sm btn-primary btn-rounded" @click="editar(dado)">Editar</button></td>
          <td><button @click="excluir(dado.id)" class="btn-sm btn-danger float-left" type="button">Excluir</button></td>
          <td v-if="exibirMenu">
            <div>
              <b-dropdown>

                <template #button-content>
                  <font-awesome-icon :icon="['fa', 'cogs']"/>
                </template>

                <b-dropdown-item>An item</b-dropdown-item>
                <b-dropdown-item>Another item</b-dropdown-item>

              </b-dropdown>
            </div>
          </td>
        </tr>
        <tr v-for="dado in dadosFiltrados" :key="dado.id" v-show="dadosFiltrados.length>0||(filtro != null || filtro != '')">
          <td scope="row" v-show="key!='id'&&key!='servicos'&&key!='orcamento'" v-for="(value , key) in dado" :key="key">{{ valorExibido(value) }}</td>
          <td v-show="dado.servicos!=undefined"><button type="button" class="btn-sm btn-primary btn-rounded" @click="verServico(dado)">Ver serviços</button></td>
          <td v-show="dado.orcamento!=undefined"><button type="button" class="btn-sm btn-primary btn-rounded" @click="verDetalhes(dado)">Ver detalhes</button></td>
          <td><button type="button" class="btn-sm btn-primary btn-rounded" @click="editar(dado)">Editar</button></td>
          <td><button @click="excluir(dado.id)" class="btn-sm btn-danger float-left" type="button">Excluir</button></td>
          <td v-if="exibirMenu">

            <b-dropdown>

              <template #button-content>
                <font-awesome-icon :icon="['fa', 'cogs']"/>
              </template>

              <b-dropdown-item>An item</b-dropdown-item>
              <b-dropdown-item>Another item</b-dropdown-item>

            </b-dropdown>

          </td>
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
  </transition>
</template>

<script>
export default {
  name: "tabela",
  props: {
    url:null,
    exibirMenu:{
      type: Boolean,
      default: false
    }
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

          var keys = Object.keys(result.result[0])
          for(var i=0;i<keys.length;i++){

            var key = keys[i]

            var keyCorrigida = key[0].toUpperCase()
            for(var j = 1;j<key.length;j++){

              keyCorrigida += key[j]

            }

            keys[i] = keyCorrigida

          }

          ref.keys = keys

        },
        error: function (result){

          alert(result.responseText)

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

    },
    valorExibido: function(value) {

      var valor;
      if (value?.email != undefined) {
        valor = value.email
      } else if (value?.descricao != undefined) {
        valor = value.descricao
      }else{
        valor = value
      }

      return valor;

    },
    verServico(value){

      this.$parent.abrirPopupServicos(value);

    },
    verDetalhes(value){

      this.$parent.abrirPopupDetalhes(value.orcamento);

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

<style>
.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-enter, .slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
</style>
