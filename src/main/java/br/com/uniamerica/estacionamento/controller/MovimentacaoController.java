package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.Repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        return movimentacao == null
                ? ResponseEntity.badRequest().body("Modelo não encontrado")
                : ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/list")
    public ResponseEntity<?> findall(){
        final List<Movimentacao> movimentacaos = this.movimentacaoRepository.findAll();

        return ResponseEntity.ok(movimentacaos);
    }
        @GetMapping("/abertos")
    public ResponseEntity<List<Movimentacao>> findBySaida() {
        final List<Movimentacao> movimentacaos = this.movimentacaoRepository.findBySaidaIsNull();

        return ResponseEntity.ok(movimentacaos);
    }

    @PostMapping
    public ResponseEntity<?> editar(@RequestParam final Movimentacao movimentacaos){
        try{
            this.movimentacaoRepository.save(movimentacaos);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id, @RequestBody final Movimentacao movimentacaos){
        try{
            final Movimentacao movimentacaosBanco = this.movimentacaoRepository.findById(id).orElse(null);

            if(movimentacaosBanco == null || !movimentacaosBanco.getId().equals(movimentacaos.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.movimentacaoRepository.save(movimentacaos);
            return ResponseEntity.ok("Registro editado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable final Long id) {
        final Optional<Movimentacao> movimentacaoOptional = this.movimentacaoRepository.findById(id);

        if (movimentacaoOptional.isPresent()) {
            final Movimentacao movimentacao = movimentacaoOptional.get();

            if (!movimentacao.isAtivo()) {
                this.movimentacaoRepository.delete(movimentacao);
                return ResponseEntity.ok("Registro excluído com sucesso.");
            } else {
                return ResponseEntity.badRequest().body("Não é possível excluir uma movimentação ativa.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
