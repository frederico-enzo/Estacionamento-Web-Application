<template>
  <div class="container w-50">
    <div class="row mt-5">
      <div class="col-md-12 text-start">
        <p class="fs-5">Detalhes do Veiculo</p>
      </div>
    </div>
    <div class="row w-100 d-flex justify-content-center m-0">
      <div class="mt-3 w-100 text-start">
        <p class="fs-6 m-0"><span class="dado">Placa: </span> {{ veiculo.placa }} </p>
      </div>
    </div>
    <div class="row w-100 d-flex justify-content-center m-0">
      <div class="mt-3 w-100 text-start">
        <p class="fs-6 m-0"><span class="dado">Modelo: </span> {{ modelo.nome }} - {{ marca.nome }} </p>
      </div>
    </div>
    <div class="row w-100 d-flex justify-content-center m-0">
      <div class="mt-3 w-100 text-start">
        <p class="fs-6 m-0"><span class="dado">Tipo: </span> {{ veiculo.tipo }}</p>
      </div>
    </div>
    <div class="row w-100 d-flex justify-content-center m-0">
      <div class="mt-3 w-100 text-start">
        <p class="fs-6"><span class="dado">Cor: </span> {{ veiculo.cor }}</p>
      </div>
    </div>
    <div class="row w-100 d-flex justify-content-center m-0">
      <div class="w-100 text-start">
        <p class="fs-6"><span class="dado"> Status: </span>
          <span v-if="veiculo.ativo" class="badge text-bg-success">
            Ativo
          </span>
          <span v-if="!veiculo.ativo" class="badge text-bg-danger">
            Desativado
          </span>
        </p>
      </div>
    </div>
    <hr>
    <div class="row mt-5">
      <div class="col-md-12 text-start">
        <p class="fs-5">Histórico</p>
      </div>
    </div>
    <div class="row w-100 d-flex justify-content-center m-0">
      <div class="w-100 text-start">
        <p class="fs-6"><span class="dado">Data do Cadastro: </span>{{ formatDate(veiculo.cadastro) }}</p>
      </div>
    </div>
    <div class="row w-100 d-flex justify-content-center m-0">
      <div class="w-100 text-start">
        <p class="fs-6"><span class="dado">Última edição: </span> {{ formatDate(veiculo.edicao) }}</p>
      </div>
    </div>

    <div class="row d-flex justify-content-center mt-3">
      <div class="col-md-3">
        <div class="d-grid gap-2">
          <router-link type="button" class="btn btn-secondary" to="/veiculos">Voltar
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { VeiculoClient } from '@/service/Veiculo.client'
import { Marca } from '@/model/Marca'
import { Modelo } from '@/model/Modelo'
import { Veiculo } from '@/model/Veiculo'
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'VeiculoInfo',
  data(): any {
    return {
      marca: new Marca(),
      modelo: new Modelo(),
      veiculo: new Veiculo()
    }
  },
  computed: {
    id(): any {
      return this.$route.query.id
    }
  },
  mounted() {
    if (this.id !== undefined) {
      this.findById(Number(this.id))
    }
  },
  methods: {
    formatDate(dateString: string | number | Date) {
      if (dateString != null) {
        const dateTime = new Date(dateString)
        const formattedDate = dateTime.toLocaleDateString()
        const formattedTime = dateTime.toLocaleTimeString([], {
          hour: '2-digit',
          minute: '2-digit'
        })
        return `${formattedDate} ${formattedTime}`
      } else {
        return '---'
      }
    },
    findById(id: number) {
      const veiculoClient = new VeiculoClient()
      veiculoClient
        .findById(id)
        .then(sucess => {
          this.veiculo = sucess
          this.modelo = sucess.modelo
          this.marca = sucess.modelo.marca
        })
        .catch(error => {
          this.mensagem.mensagem = error.response.data
          this.mensagem.status = false
          this.mensagem.ativo = true
        })
    }
  }
})
</script>

<style scoped lang="scss">
$theme-colors: (
  'dark': #111111,
  'primary': #515151,
  'secondary': #c8c8c8,
  'info': #a4a4a4,
  'success': #198754,
  'warning': #ffc107,
  'danger': #dc3545
);
@import '~bootstrap/scss/bootstrap.scss';
</style>
