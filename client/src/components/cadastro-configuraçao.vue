<template>
  <div>
    <button class="btn b" @click="exibirPopup"></button>
    <div class="overlay" v-if="exibir">
      <div id="popup" v ow="exibir" class="popup-container">
        <div class="close"><button @click="fecharPopup">x</button></div>
        <h3>Configurações</h3>
        <div class="form-conteiner">
            <form class="form">
                <input class="form-control" type="Number" placeholder="Valor Hora">
                <input class="form-control" type="Number" placeholder=" Valor Multa por Minuto">
                <div class="flex">
                    <label for="">Fim Expediente</label>
                    <input class="form-control" type="Time" placeholder="Inicio Expediente">
                </div>
                <div class="flex">
                    <label for="">Fim Expediente</label>
                     <input class="form-control" type="Time" placeholder="Fim Expediente">
                </div>
            
                <input class="form-control" type="Number" placeholder="Tempo Para Desconto">
                <input class="form-control" type="Number" placeholder="Vagos Van">
                <input class="form-control" type="Number" placeholder="Vagas Moto">
                <input class="form-control" type="Number" placeholder="Vagas Carro">
                <button type="button" class="btn btn-outline-success">Salvar</button>
            </form>
        </div>
     </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      exibir: false,
    };
  },

  methods: {
    exibirPopup() {
      this.exibir = true;
    },
    fecharPopup() {
      this.exibir = false;
    },
  },
};
</script>

<style>
.flex{
    display: flex;
    padding: 15px;
}
.flex input{
    width: 25%;
}
.close{
    padding: 5px;
    display: flex;
    justify-content: flex-end;
}
.close :nth-child(1){
    display: flex;
    height: 25px;
    justify-content: center;
    align-items: center;
    border-color:red;
    color: red;
    border-radius: 100px;
    padding-bottom:5px ;
}
.close :nth-child(1):hover{
    display: flex;
    width: 23px;
    height: 25px;
    justify-content: center;
    align-items: center;
    border: none;
    background: red;
    color: white;
    border-radius: 100px;
    padding-bottom:5px ;
}
h3{
    display: flex;
    justify-content: center;
}
.form{
    gap: 5px;
    padding: 15px;
    display: flex;
    flex-direction: column;
}
.overlay {
  position: fixed;
  top: 0;
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
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  height: 560px;
  background-color: #ffffff;
  border: 1px solid #8e8e8e;
  border-radius: 10px;
}

</style>