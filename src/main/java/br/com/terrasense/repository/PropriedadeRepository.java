package br.com.terrasense.repository;

import br.com.terrasense.model.Propriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropriedadeRepository extends JpaRepository<Propriedade, Long> {

    List<Propriedade> findByUsuarioIdUsuario(Long idUsuario);

    List<Propriedade> findByTipoPropriedadeContainingIgnoreCase(String tipoPropriedade);

}