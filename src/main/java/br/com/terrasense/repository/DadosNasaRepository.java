package br.com.terrasense.repository;

import br.com.terrasense.model.DadosNasa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DadosNasaRepository extends JpaRepository<DadosNasa, Long> {

    List<DadosNasa> findByPlantacaoIdPlantacao(Long idPlantacao);

}