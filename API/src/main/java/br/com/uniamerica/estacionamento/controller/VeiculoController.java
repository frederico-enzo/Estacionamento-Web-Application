package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {
    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);
        return veiculo == null ? ResponseEntity.badRequest().body("Nenhum veiculo encontrado") : ResponseEntity.ok(veiculo);
    }
    @GetMapping("/placa")
    public ResponseEntity<?> findByPlaca(@RequestParam("placa") final String placa)     {
        final List<Veiculo> veiculos = this.veiculoRepository.findByPlaca(placa);
        return veiculos.isEmpty() ? ResponseEntity.badRequest().body("Nenhum veiculo encontrado") : ResponseEntity.ok(veiculos);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listarAll(){
        return ResponseEntity.ok(this.veiculoRepository.findAll());
    }
    @GetMapping("/lista/ativos")
    public ResponseEntity<?> listarAllAtivos(){
        final List<Veiculo> veiculos = this.veiculoRepository.findAllAtivo();
        return veiculos.isEmpty() ? ResponseEntity.badRequest().body("Nenhum veiculo encontrado") : ResponseEntity.ok(veiculos);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Validated final Veiculo veiculo){
        try {
            final Veiculo newVeiculo = this.veiculoService.cadastrar(veiculo);
            return ResponseEntity.ok(String.format("Veiulo placa [ %s ] cadastrado com sucesso!", newVeiculo.getPlaca()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> atualizar(
            @RequestParam("id") final Long id,
            @RequestBody @Validated
            final Veiculo veiculo
    ){
        try {
            final Veiculo newVeiculo = this.veiculoService.editar(id, veiculo);
            return ResponseEntity.ok(String.format("Veiulo placa [ %s ] editado com sucesso!", newVeiculo.getPlaca()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> desativar(
            @RequestParam("id") final Long id
    ){
        try{
            return this.veiculoService.desativar(id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}