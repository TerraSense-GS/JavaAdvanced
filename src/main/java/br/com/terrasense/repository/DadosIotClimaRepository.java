package br.com.terrasense.repository;

import br.com.terrasense.model.DadosIotClima;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DadosIotClimaRepository extends JpaRepository<DadosIotClima, Long> {

    List<DadosIotClima> findByPlantacaoIdPlantacao(Long idPlantacao);

}