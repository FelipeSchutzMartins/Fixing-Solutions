<template>
  <div class="d-flex justify-content-center">
    <transition name="slide-fade">
      <div style="background-color: white;height: 44%;width: 50%;display: block;" class="align-self-center rounded">
        <form class="col-12">

          <div class="card-body">
            <select v-model="tipo" class="form-control">
              <option v-for="item in itens" :value="item" :key="item">
                {{ item }}
              </option>
            </select>
          </div>

          <div v-if="tipo">

            <div v-if="tipo=='Funcionario'">

              <div class="card-body">
                <label>Email</label>
                <input v-model="email" placeholder="seuemail@emailless.com" class="form-control">
              </div>

              <div class="card-body">
                <label>Nome</label>
                <input v-model="nome" placeholder="seuemail@emailless.com" class="form-control">
              </div>

              <div class="card-body">
                <label>Senha</label>
                <input v-model="senha" :type="'password'" class="form-control">
              </div>

              <div class="card-body">
                <label>Cargo</label>
                <select v-model="cargoSelecionado" class="form-control">
                  <option v-for="cargo in cargos" :value="cargo" :key="cargo.id">
                    {{ cargo.descricao }}
                  </option>
                </select>
              </div>

            </div>

            <div v-if="tipo=='Cliente'">

              <div class="card-body">
                <label>Nome</label>
                <input v-model="nome" class="form-control">
              </div>
              <div class="card-body">
                <label>Email</label>
                <input v-model="email" placeholder="seuemail@emailless.com" class="form-control">
              </div>
              <div class="card-body">
                <label>CPF</label>
                <input v-model="cpf" v-mask="'###.###.###-##'" class="form-control">
              </div>
              <div class="card-body">
                <label>Telefone</label>
                <input v-model="telefone" v-mask="'(##) #####-####'" class="form-control">
              </div>

            </div>

            <div v-if="tipo=='Orçamentos'">

              <div class="card-body">
                <label>Horas Previstas</label>
                <input v-model="horasPrevistas" class="form-control">
              </div>
              <div class="card-body">
                <label>Responsável</label>
                <select v-model="responsavel" class="form-control">
                  <option v-for="responsavel in responsaveis" :value="responsavel" :key="responsavel.id">
                    {{ responsavel.email }}
                  </option>
                </select>
              </div>
              <div class="card-body">
                <label>Cliente</label>
                <select v-model="cliente" class="form-control">
                  <option v-for="cliente in clientes" :value="cliente" :key="cliente.id">
                    {{ cliente.email }}
                  </option>
                </select>
              </div>

              <div class="card-body">
                <label>Valor</label>
                <input v-model="valor" class="form-control" style="width: 60px;" disabled>
              </div>

              <div class="card-body">
                <label>Data de criação</label>
                <input v-model="valor" class="form-control" style="width: 60px;" disabled>
              </div>

            </div>

            <div v-if="tipo=='Ordens de serviço'">



            </div>

          </div>

          <div class="card-body">
            <button @click="gerarRelatorio" type="button" class="btn btn-success float-right">Gerar relatorio BIRRRL!!!</button>
          </div>
        </form>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: "Relatorio",
  data: function(){
    return {
      itens:["Funcionario","Cliente","Orçamentos","Ordens de serviço"],
      tipo:null,
      cargos:[]
    }
  },
  methods: {
    gerarRelatorio(){

    },
    buscarCargos(){
      var ref = this

      window.$.ajax({
        method: "GET",
        url: "http://localhost:8080/buscarCargos",
        contentType: "application/json",
        success: function (result) {

          ref.cargos = result.result

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
.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-enter, .slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
</style>