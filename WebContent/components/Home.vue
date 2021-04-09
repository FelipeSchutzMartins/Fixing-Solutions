<template>
  <div>
    <Header :permissoes-admin="permissoesAdmin"></Header>
<!--    <div style="height: 100vh;" :style="{ backgroundImage: 'url(' + require('../assets/backgroundImage.jpg') + ')' }"  class="d-flex justify-content-center">-->
    <div style="background: white;height: 100vh;"  class="d-flex justify-content-center">

      <transition name="slide-fade">
        <div v-if="!cliente&&!funcionario&&!orcamento&&!ordemServico&&!relatorio" style="border:1px;border-style:inset;border-color:#ff0000;border-radius: 3px; height: 44%;width: 50%;display: block;" class="align-self-center">
          <div style="margin-left: 5%;margin-top: 5%;display: block;">
            <button style="width: 30%;height:90px;" class="btn-default bg-info rounded btn-sm" @click="abrirOrdemServico()">OS</button>
            <button style="width: 30%;height:90px;margin-left: 2%;" class="btn-default bg-info rounded btn-sm" @click="abrirCliente()">Clientes</button>
            <button style="width: 30%;height:90px;margin-left: 2%;"  class="btn-default bg-info rounded btn-sm" @click="abrirOrcamento()">Orçamento</button>
          </div>
          <div style="margin-left: 5%;margin-top: 5%;display: block;">
            <button style="width: 30%;height:90px;" class="btn-default bg-info rounded btn-sm" @click="abrirFuncionario()" :disabled="!permissoesAdmin">Funcionários</button>
            <button style="width: 30%;margin-left: 2%;height:90px;" class="btn-default bg-info rounded btn-sm" @click="abrirRelatorio()" :disabled="!permissoesAdmin">Relatórios</button>
          </div>
        </div>
      </transition>

      <transition name="slide-fade">
        <Cliente v-if="cliente"></Cliente>
      </transition>

      <transition name="slide-fade">
        <Funcionario v-if="funcionario"></Funcionario>
      </transition>

      <transition name="slide-fade">
        <Orcamento v-if="orcamento"></Orcamento>
      </transition>

      <transition name="slide-fade">
        <OrdemServico v-if="ordemServico"></OrdemServico>
      </transition>

      <transition name="slide-fade">
        <Relatorio v-if="relatorio"></Relatorio>
      </transition>

    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "./Header";
import Footer from "./Footer";
import Cliente from "./Cliente";
import Funcionario from "./Funcionario";
import Orcamento from "./Orcamento";
import OrdemServico from "./OrdemServico.vue";
import Relatorio from "./Relatorio";

export default {
  name: "Home",
  components:{
    Relatorio,
    Funcionario,
    Cliente,
    Orcamento,
    OrdemServico,
    Header,
    Footer
  },
  data () {
    return {
      cliente:false,
      funcionario:false,
      orcamento:false,
      ordemServico:false,
      relatorio:false,
      permissoesAdmin:false
    }
  },
  props: {
    user:null
  },
  mounted() {
    var ref = this
    window.$.ajax({
           method: "GET",
           url: "http://localhost:8080/verificarlogin",
           contentType: 'application/json',
           dataType: 'json',
           success: function (result) {
             console.log(result.result[0].cargo.descricao)
             if(result.result[0].cargo.descricao == "Administrador"){

               ref.permissoesAdmin = true

             }else{
               ref.permissoesAdmin = false
             }

           },
           error: function (result){

             alert(result.responseText)
             ref.$router.push({ name:'login'})

           }
         });

  },
  methods:{

    abrirCliente: function (){

      var ref = this;

      ref.cliente = true
      ref.funcionario = false
      ref.orcamento = false
      ref.ordemServico = false
      ref.relatorio = false

    },

    abrirFuncionario: function (){

      var ref = this

      ref.funcionario = true
      ref.cliente = false
      ref.orcamento = false
      ref.ordemServico = false
      ref.relatorio = false

    },

    abrirHome: function (){

      var ref = this
      ref.cliente = false
      ref.funcionario = false
      ref.orcamento = false
      ref.ordemServico = false
      ref.relatorio = false

    },

    abrirOrcamento: function(){

        var ref = this
        ref.cliente = false
        ref.funcionario = false
        ref.orcamento = true
        ref.ordemServico = false
        ref.relatorio = false

    },

    abrirOrdemServico: function (){

      var ref = this
      ref.cliente = false
      ref.funcionario = false
      ref.orcamento = false
      ref.ordemServico = true
      ref.relatorio = false

    },

    abrirRelatorio: function (){

      var ref = this
      ref.cliente = false
      ref.funcionario = false
      ref.orcamento = false
      ref.ordemServico = false
      ref.relatorio = true

    }


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
