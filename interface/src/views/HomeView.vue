<template>
  <div class="d-flex flex-column container mt-5">
    <div class="d-flex justify-content-center">
      <router-link type="button" class="btn btn-primary w-25 fs-5" to="/movimentacao/formulario">Registrar Entrada
      </router-link>
    </div>
    <table class="mt-5 table w-25 align-self-center">
      <thead class="table-secondary">
        <tr>
          <th class="text-center col-md-1">Carro</th>
          <th class="text-center col-md-2">Moto</th>
          <th class="text-center col-md-2">Van</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="text-center align-middle col-md-1">{{ carro }}</td>
          <td class="text-center align-middle col-md-2">{{ moto }}</td>
          <td class="text-center align-middle col-md-2">{{ van }}</td>
        </tr>
      </tbody>
    </table>
    <div class="row mt-3">
      <div class="col-md-10 text-start">
        <p class="fs-5">Movimentações Abertas</p>
      </div>
    </div>
    <table class="table">
      <thead class="table-dark">
        <tr>
          <th class="text-center col-md-1">ID</th>
          <th class="text-center col-md-2">Vaga</th>
          <th class="text-center col-md-2">Placa</th>
          <th class="text-center col-md-3">Condutor</th>
          <th class="text-center col-md-2">Data Entrada</th>
          <th class="text-center col-md-3">Opções</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in movimentacoesList" :key="item.id">
          <th class="align-middle text-center col-md-1">{{ item.id }}</th>
          <th class="align-middle text-center col-md-1">{{ item.veiculo.tipo }}</th>
          <th class="align-middle col-md-2 text-center">{{ item.veiculo.placa }} <span v-if="!item.veiculo.ativo"
              class="badge text-bg-danger">Desativado</span>
          </th>
          <th class="align-middle col-md-3 text-center">{{ item.condutor.nome }} <span v-if="!item.condutor.ativo"
              class="badge text-bg-danger">Desativado</span>
          </th>
          <th class="align-middle col-md-2 text-center">
            {{ formatDate(item.dataEntrada) }}
          </th>
          <th class="align-middle text-center col-md-3">
            <BotoesAcoes confirmarRoute="movimentacao.form.confirmar" listarRoute="movimentacao.listar" :id="item.id">
            </BotoesAcoes>
          </th>
        </tr>
      </tbody>
    </table>
    <div v-if="movimentacoesList.length == 0" class="alert alert-secondary" role="alert">
      Nenhuma vaga ocupada no momento
    </div>
  </div>
</template>

<script lang="ts">

import BotoesAcoes from '@/components/BotoesAcoes.vue'
import { defineComponent } from 'vue'
import { Movimentacao } from '@/model/Movimentacao'
import { MovimentacaoClient } from '@/service/Movimentacao.client'
import { Tipo } from '@/model/Tipo'
import { ConfiguracaoClient } from '@/service/Configuracao.client'
import { Configuracao } from '@/model/Configuracao'

export default defineComponent({
  name: 'HomeView',
  data() {
    return {
      movimentacoesList: new Array<Movimentacao>(),
      carro: 0 as number,
      moto: 0 as number,
      van: 0 as number,
      configs: new Configuracao
    }
  },
  mounted() {
    this.findAll()
    this.getVagas()
  },
  components: {
    BotoesAcoes
  },
  methods: {
    getVagas() {
      const configClient = new ConfiguracaoClient()
      const movsClient = new MovimentacaoClient()
      configClient.getConfiguracao().then(sucess => {
        this.carro = sucess.qntdCarro
        this.moto = sucess.qntdMoto
        this.van = sucess.qntdVan

        movsClient.getVagas(Tipo.CARRO).then(sucTipo => {
          this.carro -= sucTipo.length
        })
        movsClient.getVagas(Tipo.MOTO).then(sucTipo => {
          this.moto -= sucTipo.length
        })
        movsClient.getVagas(Tipo.VAN).then(sucTipo => {
          this.van -= sucTipo.length
        })
      }).catch(error => {
        this.carro = 0
        this.van = 0
        this.moto = 0
      })
    },
    findAll() {
      const movimentacaoClient = new MovimentacaoClient()
      movimentacaoClient
        .listarAbertas()
        .then(sucess => {
          this.movimentacoesList = sucess
        })
        .catch(error => {
          console.error("Erro ao listar movimentações:", error);
        });
    },
    formatDate(dateString: string | number | Date) {
      const dateTime = new Date(dateString)
      const formattedDate = dateTime.toLocaleDateString()
      const formattedTime = dateTime.toLocaleTimeString([], {
        hour: '2-digit',
        minute: '2-digit'
      })
      return `${formattedDate} ${formattedTime}`
    }
  }
})
</script>

<style lang="scss">
$theme-colors: (
  'dark': #111111,
  'primary': #2b2727,
  'secondary': #5c5757,
  'info': #a4a4a4,
  'success': #198754,
  'warning': #ffc107,
  'danger': #dc3545
);
@import '~bootstrap/scss/bootstrap.scss';
</style>