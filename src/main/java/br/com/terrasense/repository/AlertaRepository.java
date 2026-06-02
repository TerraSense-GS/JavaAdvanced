package br.com.terrasense.repository;

import br.com.terrasense.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByPlantacaoIdPlantacao(Long idPlantacao);

    List<Alerta> findByStatusAlertaIgnoreCase(String statusAlerta);

    List<Alerta> findByNivelAlertaIgnoreCase(String nivelAlerta);

}