<template>
  <div>
    <Header></Header>
    <div style="height: 100vh;" :style="{ backgroundImage: 'url(' + require('../assets/backgroundImage.jpg') + ')' }"  class="d-flex justify-content-center">
<!--      style="background: dodgerblue;height: 100vh;"-->
      <div v-if="!clientes"  style="background-color: white;height: 44%;width: 50%;display: block;" class="align-self-center rounded">
        <div style="margin-left: 5%;margin-top: 5%;display: block;">
          <button style="width: 30%;height:90px;" class="btn-default bg-info rounded btn-sm">OS</button>
          <button style="width: 30%;height:90px;margin-left: 2%;" class="btn-default bg-info rounded btn-sm" @click="abrirCliente()">Clientes</button>
          <button style="width: 30%;height:90px;margin-left: 2%;"  class="btn-default bg-info rounded btn-sm">Orçamento</button>
        </div>
        <div style="margin-left: 5%;margin-top: 5%;display: block;">
          <button style="width: 30%;height:90px;" class="btn-default bg-info rounded btn-sm">Funcionários</button>
          <button style="width: 30%;margin-left: 2%;height:90px;" class="btn-default bg-info rounded btn-sm">Relatórios</button>
        </div>
      </div>
      <Cliente v-if="clientes"></Cliente>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "./Header";
import Footer from "./Footer";
import Cliente from "./Cliente";

export default {
  name: "Home",
  components:{
    Cliente,
    Header,
    Footer
  },
  data () {
    return {
      clientes:false
    }
  },
  props: {
    user:null
  },
  mounted() {
    // alert(this.user)
  },
  methods:{
    abrirCliente: function (){

      var ref = this;

      ref.clientes = true

    },
    abrirHome: function (){

      var ref = this
      ref.clientes = false

    }
  }
}
</script>
