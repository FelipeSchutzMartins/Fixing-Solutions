<template>
  <div>
    <Header></Header>
    <div style="background: dodgerblue;height: 100vh;" class="d-flex justify-content-center">
      <div style="background-color: white;height: 43%;width: 50%;display: block;" class="align-self-center rounded">
        <div style="margin-left: 5%;margin-top: 5%;display: block;">
          <button style="width: 30%;height:90px;">OS</button>
          <button style="width: 30%;height:90px;margin-left: 2%;">Clientes</button>
          <button style="width: 30%;height:90px;margin-left: 2%;">Orçamento</button>
        </div>
        <div style="margin-left: 5%;margin-top: 5%;display: block;">
          <button style="width: 30%;height:90px;">Funcionários</button>
          <button style="width: 30%;margin-left: 2%;height:90px;">Relatórios</button>
        </div>
      </div>
    </div>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from "./Header";
import Footer from "./Footer";

export default {
  name: "Home",
  components:{
    Header,
    Footer
  },
  props: {
    user:null
  },
  mounted() {
    // alert(this.user)
  }
}
</script>
