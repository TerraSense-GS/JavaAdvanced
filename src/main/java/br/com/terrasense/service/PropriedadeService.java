package br.com.terrasense.service;

import br.com.terrasense.dto.propriedade.EnderecoDTO;
import br.com.terrasense.dto.propriedade.PropriedadeRequestDTO;
import br.com.terrasense.dto.propriedade.PropriedadeResponseDTO;
import br.com.terrasense.exception.ResourceNotFoundException;
import br.com.terrasense.model.Endereco;
import br.com.terrasense.model.Propriedade;
import br.com.terrasense.model.Usuario;
import br.com.terrasense.repository.PropriedadeRepository;
import br.com.terrasense.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;
    private final UsuarioRepository usuarioRepository;

    public List<PropriedadeResponseDTO> listarTodos() {
        return propriedadeRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public PropriedadeResponseDTO buscarPorId(Long id) {

        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Propriedade não encontrada com ID: " + id));

        return toResponseDTO(propriedade);
    }

    public PropriedadeResponseDTO cadastrar(PropriedadeRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado com ID: "
                                        + dto.idUsuario()));

        Propriedade propriedade = new Propriedade();

        propriedade.setNomePropriedade(dto.nomePropriedade());
        propriedade.setTipoPropriedade(dto.tipoPropriedade());
        propriedade.setAreaTotal(dto.areaTotal());
        propriedade.setEndereco(toEndereco(dto.endereco()));
        propriedade.setUsuario(usuario);

        propriedade = propriedadeRepository.save(propriedade);

        return toResponseDTO(propriedade);
    }

    public PropriedadeResponseDTO atualizar(Long id, PropriedadeRequestDTO dto) {

        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Propriedade não encontrada com ID: " + id));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado com ID: "
                                        + dto.idUsuario()));

        propriedade.setNomePropriedade(dto.nomePropriedade());
        propriedade.setTipoPropriedade(dto.tipoPropriedade());
        propriedade.setAreaTotal(dto.areaTotal());
        propriedade.setEndereco(toEndereco(dto.endereco()));
        propriedade.setUsuario(usuario);

        propriedade = propriedadeRepository.save(propriedade);

        return toResponseDTO(propriedade);
    }

    public void deletar(Long id) {

        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Propriedade não encontrada com ID: " + id));

        propriedadeRepository.delete(propriedade);
    }

    private PropriedadeResponseDTO toResponseDTO(Propriedade propriedade) {

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                propriedade.getEndereco().getCidade(),
                propriedade.getEndereco().getEstado(),
                propriedade.getEndereco().getCep()
        );

        return new PropriedadeResponseDTO(
                propriedade.getIdPropriedade(),
                propriedade.getNomePropriedade(),
                propriedade.getTipoPropriedade(),
                propriedade.getAreaTotal(),
                enderecoDTO,
                propriedade.getUsuario().getIdUsuario()
        );
    }

    private Endereco toEndereco(EnderecoDTO dto) {
        return new Endereco(
                dto.cidade(),
                dto.estado(),
                dto.cep()
        );
    }
}