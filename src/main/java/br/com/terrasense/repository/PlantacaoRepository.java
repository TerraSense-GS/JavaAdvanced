package br.com.terrasense.repository;

import br.com.terrasense.model.Plantacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlantacaoRepository extends JpaRepository<Plantacao, Long> {

    List<Plantacao> findByPropriedadeIdPropriedade(Long idPropriedade);

    List<Plantacao> findByStatusPlantacaoIgnoreCase(String statusPlantacao);

    List<Plantacao> findByTipoPlantacaoContainingIgnoreCase(String tipoPlantacao);

}