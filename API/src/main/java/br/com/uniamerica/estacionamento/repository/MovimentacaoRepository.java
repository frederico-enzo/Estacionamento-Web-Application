package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    @Query("from Movimentacao where condutor.id = :id")
    List<Movimentacao> findByCondutorId(@Param("id") final Long id);
    @Query("from Movimentacao where dataSaida = null and ativo = true")
    List<Movimentacao> findAllAbertas();
    @Query("from Movimentacao where veiculo.id = :id")
    List<Movimentacao> findByVeiculoId(@Param("id") final Long id);
    @Query("from Movimentacao where veiculo.id = :id and dataSaida = null and ativo = true")
    List<Movimentacao> getEstacionado(@Param("id") final Long id);
    @Query("from Movimentacao where veiculo.tipo = :tipo and dataSaida = null and ativo = true")
    List<Movimentacao> getVagas(@Param("tipo") final Tipo tipo);
}