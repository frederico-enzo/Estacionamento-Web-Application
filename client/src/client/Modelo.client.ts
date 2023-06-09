import { Modelo } from "@/Model/Modelo";
import axios, { AxiosInstance } from "axios";

export class ModeloClient{
    private axiosClient: AxiosInstance;

    constructor() {
        this.axiosClient = axios.create({
            baseURL: 'http://localhost:8080/api/modelo',
            headers: {'Content-type' : 'application/json'}
        });
    }

    public async findById(id: Number) : Promise<Modelo>{
        try{
            return(await this.axiosClient.get<Modelo>(`${id}`)).data
        } catch (error:any){
            return Promise.reject(error.response);
        }
    }

    public async findAll(): Promise<Modelo[]>{
        try{
            return( await this.axiosClient.get<Modelo[]>('/lista')).data;
        } catch (error:any){
        return Promise.reject(error.response);
        }
    }   

    public async ativo(): Promise<Modelo[]>{
        try{
            return( await this.axiosClient.get<Modelo[]>('/ativos')).data;
        } catch (error:any){
            return Promise.reject(error.response);
        }
    }

    public async newModelo(modelo : Modelo): Promise<void>{
        try{
            return(await this.axiosClient.post('', modelo));
        } catch (error: any){
            return Promise.reject(error.response);
        }
    }

    public async upDate(id: Number, modelo: Modelo) : Promise<void>{
        try{
            return (await this.axiosClient.put(`/${modelo.id}`, modelo)).data;
        } catch (error:any){
            return Promise.reject(error.response);
        }
    }

    public async excluir(id: number): Promise<string> {
        try {
            return (await this.axiosClient.delete<string>(`/${id}`)).data
        } catch (error:any) {
            return Promise.reject(error.response)
        }
    }

}