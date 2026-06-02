package br.com.terrasense.service;

import br.com.terrasense.dto.alerta.AlertaRequestDTO;
import br.com.terrasense.dto.alerta.AlertaResponseDTO;
import br.com.terrasense.exception.ResourceNotFoundException;
import br.com.terrasense.model.Alerta;
import br.com.terrasense.model.Plantacao;
import br.com.terrasense.repository.AlertaRepository;
import br.com.terrasense.repository.PlantacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final PlantacaoRepository plantacaoRepository;

    public List<AlertaResponseDTO> listarTodos() {
        return alertaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public AlertaResponseDTO buscarPorId(Long id) {

        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Alerta não encontrado com ID: " + id));

        return toResponseDTO(alerta);
    }

    public AlertaResponseDTO cadastrar(
            AlertaRequestDTO dto
    ) {

        Plantacao plantacao = plantacaoRepository.findById(dto.idPlantacao())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plantação não encontrada com ID: "
                                        + dto.idPlantacao()));

        Alerta alerta = new Alerta();

        alerta.setTipoAlerta(dto.tipoAlerta());
        alerta.setMensagem(dto.mensagem());
        alerta.setNivelAlerta(dto.nivelAlerta());
        alerta.setStatusAlerta(dto.statusAlerta());
        alerta.setDataAlerta(dto.dataAlerta());
        alerta.setPlantacao(plantacao);

        alerta = alertaRepository.save(alerta);

        return toResponseDTO(alerta);
    }

    public AlertaResponseDTO atualizar(
            Long id,
            AlertaRequestDTO dto
    ) {

        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Alerta não encontrado com ID: " + id));

        Plantacao plantacao = plantacaoRepository.findById(dto.idPlantacao())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plantação não encontrada com ID: "
                                        + dto.idPlantacao()));

        alerta.setTipoAlerta(dto.tipoAlerta());
        alerta.setMensagem(dto.mensagem());
        alerta.setNivelAlerta(dto.nivelAlerta());
        alerta.setStatusAlerta(dto.statusAlerta());
        alerta.setDataAlerta(dto.dataAlerta());
        alerta.setPlantacao(plantacao);

        alerta = alertaRepository.save(alerta);

        return toResponseDTO(alerta);
    }

    public void deletar(Long id) {

        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Alerta não encontrado com ID: " + id));

        alertaRepository.delete(alerta);
    }

    private AlertaResponseDTO toResponseDTO(
            Alerta alerta
    ) {

        return new AlertaResponseDTO(
                alerta.getIdAlerta(),
                alerta.getTipoAlerta(),
                alerta.getMensagem(),
                alerta.getNivelAlerta(),
                alerta.getStatusAlerta(),
                alerta.getDataAlerta(),
                alerta.getPlantacao().getIdPlantacao()
        );
    }
}