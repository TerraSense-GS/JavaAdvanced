package br.com.terrasense.service;

import br.com.terrasense.dto.usuario.UsuarioRequestDTO;
import br.com.terrasense.dto.usuario.UsuarioResponseDTO;
import br.com.terrasense.exception.DuplicateResourceException;
import br.com.terrasense.exception.ResourceNotFoundException;
import br.com.terrasense.model.Usuario;
import br.com.terrasense.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado com ID: " + id));

        return toResponseDTO(usuario);
    }

    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException(
                    "Já existe um usuário cadastrado com este e-mail.");
        }

        Usuario usuario = new Usuario();

        usuario.setNomeCompleto(dto.nomeCompleto());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setPerfilCargo(dto.perfilCargo());

        usuario = usuarioRepository.save(usuario);

        return toResponseDTO(usuario);
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado com ID: " + id));

        if (usuarioRepository.existsByEmailAndIdUsuarioNot(dto.email(), id)) {
            throw new DuplicateResourceException(
                    "Já existe um usuário cadastrado com este e-mail."
            );
        }
        usuario.setNomeCompleto(dto.nomeCompleto());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setPerfilCargo(dto.perfilCargo());

        usuario = usuarioRepository.save(usuario);

        return toResponseDTO(usuario);
    }

    public void deletar(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado com ID: " + id));

        usuarioRepository.delete(usuario);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNomeCompleto(),
                usuario.getTelefone(),
                usuario.getEmail(),
                usuario.getPerfilCargo()
        );
    }
}