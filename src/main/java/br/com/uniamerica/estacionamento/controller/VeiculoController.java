package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.servece.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdPath(@PathVariable("id")final Long id){
        final Veiculo veiculo = this.veiculoService.findById(id);

        return ResponseEntity.ok(veiculo);
    }

    @GetMapping("/list")
    public ResponseEntity<?> findall(){
        final List<Veiculo> veiculo = this.veiculoService.findAll();

        return ResponseEntity.ok(veiculo);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Veiculo>>findByAtivos(){
        List<Veiculo> veiculosAtivos = this.veiculoService.findByAtivo();
        return ResponseEntity.ok(veiculosAtivos);
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){
        try{
            this.veiculoService.cadastrar(veiculo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id, @RequestBody final  Veiculo veiculo){
        try{
            final Veiculo veiculoBanco = this.veiculoService.findById(id);

            if(veiculoBanco == null || !veiculoBanco.getId().equals(veiculo.getId()))
            {
                throw new RuntimeException("Não foi possível identificar o registro informado");
            }

            this.veiculoService.editar(veiculo);
            return ResponseEntity.ok("Registro editado com sucesso");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        try {
            this.veiculoService.delete(id);
            return ResponseEntity.ok("veiculo excluído com sucesso");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }
    }
}
