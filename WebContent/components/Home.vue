<template>
  <div>
    <Header></Header>
    <div style="background: dodgerblue;height: 100vh;" class="d-flex justify-content-center">
      <div v-if="!clientes"  style="background-color: white;height: 43%;width: 50%;display: block;" class="align-self-center rounded">
        <div style="margin-left: 5%;margin-top: 5%;display: block;">
          <button style="width: 30%;height:90px;">OS</button>
          <button style="width: 30%;height:90px;margin-left: 2%;" @click="clientes=true">Clientes</button>
          <button style="width: 30%;height:90px;margin-left: 2%;">Orçamento</button>
        </div>
        <div style="margin-left: 5%;margin-top: 5%;display: block;">
          <button style="width: 30%;height:90px;">Funcionários</button>
          <button style="width: 30%;margin-left: 2%;height:90px;">Relatórios</button>
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
  }
}
</script>
