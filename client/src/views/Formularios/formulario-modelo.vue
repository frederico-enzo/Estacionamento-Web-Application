<template>
  <NavBar />
  <div class="overlay">
    <div
      id="popup"
      class="popup-container"
      :style="{ height: mensagem.ativo ? '350px' : '250px' }"
    >
      <br />
      <h3>Modelo</h3>
      <div class="form-conteiner">
        <div v-if="mensagem.ativo" class="row">
          <div class="col-md-12 text-start">
            <div :class="mensagem.css" role="alert">
              <strong>{{ mensagem.titulo }}</strong> {{ mensagem.mensagem }}
              <button
                @click="redefinirTamanhoPopup"
                type="button"
                class="btn-close"
                data-bs-dismiss="alert"
                aria-label="Close"
              ></button>
            </div>
          </div>
        </div>
      </div>
      <form class="form">
        <input
          v-model="modelo.nome"
          class="form-control"
          type="text"
          placeholder="Nome"
        />
        <select v-model="modelo.marcaId" class="form-control">
          <option v-for="marca in marcasList" :key="marca.id" :value="marca">
            {{ marca.nome }}
          </option>
        </select>

        <button
          @click="onClickCadastrar()"
          type="button"
          class="btn btn-outline-success"
        >
          Cadastrar
        </button>
      </form>
    </div>
  </div>
</template>

  <script lang="ts">
import { MarcaClient } from "@/client/Marca.client";
import { Marca } from "@/Model/Marca";
import { Modelo } from "@/Model/Modelo";
import { ModeloClient } from "@/client/Modelo.client";
import NavBar from '@/components/NavBar.vue'
import { defineComponent } from "vue";

export default defineComponent({
  components: {
    NavBar,
  },
  data() {
    return {
      modelo: new Modelo(),
      mensagem: {
        ativo: false,
        titulo: "",
        mensagem: "",
        css: "",
      },
      exibir: false,
      marcasList: new Array<Marca>(),
    };
  },

  mounted() {
    this.carregarMarcas();
  },

  methods: {
    exibirPopup() {
      this.exibir = true;
    },
    fecharPopup() {
      this.exibir = false;
    },
    carregarMarcas() {
      const marcaClient = new MarcaClient();
      marcaClient
        .findAll()
        .then((sucess) => {
          this.marcasList = sucess;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    onClickCadastrar() {
      const modeloClient = new ModeloClient();
      modeloClient
        .newModelo(this.modelo)
        .then((success) => {
          this.modelo = new Modelo();
          this.mensagem.ativo = true;
          this.mensagem.mensagem = "Modelo cadastrada com sucesso";
          this.mensagem.titulo = "";
          this.mensagem.css = "alert alert-success alert-dismissible fade show";
        })
        .catch((error) => {
          this.mensagem.ativo = true;
          this.mensagem.mensagem = "";
          this.mensagem.titulo = "Error. ";
          this.mensagem.css = "alert alert-danger alert-dismissible fade show";
        });
    },
    redefinirTamanhoPopup() {
      this.mensagem.ativo = false;
      const popup = document.getElementById("popup");
      if (popup) {
        popup.style.height = "250px";
      }
    },
  },
});
</script>

<style scoped>
.flex {
  display: flex;
  padding: 15px;
}
.flex input {
  width: 25%;
}
.close {
  padding: 5px;
  display: flex;
  justify-content: flex-end;
}
.close :nth-child(1) {
  display: flex;
  height: 25px;
  justify-content: center;
  align-items: center;
  border-color: red;
  color: red;
  border-radius: 100px;
  padding-bottom: 5px;
}
.close :nth-child(1):hover {
  display: flex;
  width: 23px;
  height: 25px;
  justify-content: center;
  align-items: center;
  border: none;
  background: red;
  color: white;
  border-radius: 100px;
  padding-bottom: 5px;
}
h3 {
  display: flex;
  justify-content: center;
}

.form {
  padding: 15px;
  display: flex;
  flex-direction: column;
}
.overlay {
  position: fixed;
  top: 80px;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 100;
  display: flex;
  justify-content: center;
  align-items: center;
}

#popup {
  display: flex;
  flex-direction: column;
  padding: 10px;
  position: fixed;
  top: 30%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 600px;
  height: 300px;
  background-color: #ffffff;
  border: 1px solid #8e8e8e;
  border-radius: 10px;
}
</style>