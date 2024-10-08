import { Condutor } from '@/model/Condutor'
import axios, { AxiosInstance } from 'axios'
export class CondutorClient {
  private axiosClient: AxiosInstance

  constructor() {
    this.axiosClient = axios.create({
      baseURL: `http://localhost:9000/estacionamento/api`,
      headers: { 'Content-type': 'application/json' }
    })
  }

  public async findById(id: number): Promise<Condutor> {
    try {
      const response = await this.axiosClient.get<Condutor>(`/condutor?id=${id}`)
      return response.data
    } catch (error) {
      return Promise.reject(error)
    }
  }

  public async lista(): Promise<Condutor[]> {
    try {
      const response = await this.axiosClient.get<Condutor[]>('/condutor/lista')
      return response.data
    } catch (error) {
      console.error(error)
      return []
    }
  }
  public async findByNome(nome: string): Promise<Condutor[]> {
    try {
      const response = await this.axiosClient.get<Condutor[]>(`/condutor/nome?nome=${nome}`)
      return response.data
    } catch (error) {
      return Promise.reject(error)
    }
  }
  public async findByCpf(cpf: string): Promise<Condutor[]> {
    try {
      const response = await this.axiosClient.get<Condutor[]>(`/condutor/cpf?cpf=${cpf}`)
      return response.data
    } catch (error) {
      return Promise.reject(error)
    }
  }

  public async listaAtivos(): Promise<Condutor[]> {
    try {
      const response = await this.axiosClient.get<Condutor[]>('/condutor/lista/ativos')
      return response.data
    } catch (error) {
      console.error(error)
      return []
    }
  }

  public async cadastrarCondutor(condutor: Condutor): Promise<Condutor> {
    try {
      const response = await this.axiosClient.post<Condutor>('/condutor', condutor)
      return response.data
    } catch (error) {
      return Promise.reject(error)
    }
  }

  public async editarCondutor(condutor: Condutor): Promise<Condutor> {
    try {
      const response = await this.axiosClient.put<Condutor>(
        `/condutor?id=${condutor.id}`,
        condutor
      )
      return response.data
    } catch (error) {
      return Promise.reject(error)
    }
  }

  public async desativarCondutor(id: number): Promise<Condutor> {
    try {
      const response = await this.axiosClient.delete<Condutor>(
        `/condutor?id=${id}`
      )
      return response.data
    } catch (error) {
      return Promise.reject(error)
    }
  }
}
