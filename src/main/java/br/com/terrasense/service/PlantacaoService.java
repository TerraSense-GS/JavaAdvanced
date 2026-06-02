package br.com.terrasense.service;

import br.com.terrasense.dto.plantacao.PlantacaoRequestDTO;
import br.com.terrasense.dto.plantacao.PlantacaoResponseDTO;
import br.com.terrasense.exception.ResourceNotFoundException;
import br.com.terrasense.model.Plantacao;
import br.com.terrasense.model.Propriedade;
import br.com.terrasense.repository.PlantacaoRepository;
import br.com.terrasense.repository.PropriedadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantacaoService {

    private final PlantacaoRepository plantacaoRepository;
    private final PropriedadeRepository propriedadeRepository;

    public List<PlantacaoResponseDTO> listarTodos() {
        return plantacaoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public PlantacaoResponseDTO buscarPorId(Long id) {

        Plantacao plantacao = plantacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plantação não encontrada com ID: " + id));

        return toResponseDTO(plantacao);
    }

    public PlantacaoResponseDTO cadastrar(
            PlantacaoRequestDTO dto
    ) {

        Propriedade propriedade = propriedadeRepository
                .findById(dto.idPropriedade())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Propriedade não encontrada com ID: "
                                        + dto.idPropriedade()));

        Plantacao plantacao = new Plantacao();

        plantacao.setNomePlantacao(dto.nomePlantacao());
        plantacao.setTipoPlantacao(dto.tipoPlantacao());
        plantacao.setAreaHectares(dto.areaHectares());
        plantacao.setDataPlantio(dto.dataPlantio());
        plantacao.setStatusPlantacao(dto.statusPlantacao());
        plantacao.setPropriedade(propriedade);

        plantacao = plantacaoRepository.save(plantacao);

        return toResponseDTO(plantacao);
    }

    public PlantacaoResponseDTO atualizar(
            Long id,
            PlantacaoRequestDTO dto
    ) {

        Plantacao plantacao = plantacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plantação não encontrada com ID: " + id));

        Propriedade propriedade = propriedadeRepository
                .findById(dto.idPropriedade())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Propriedade não encontrada com ID: "
                                        + dto.idPropriedade()));

        plantacao.setNomePlantacao(dto.nomePlantacao());
        plantacao.setTipoPlantacao(dto.tipoPlantacao());
        plantacao.setAreaHectares(dto.areaHectares());
        plantacao.setDataPlantio(dto.dataPlantio());
        plantacao.setStatusPlantacao(dto.statusPlantacao());
        plantacao.setPropriedade(propriedade);

        plantacao = plantacaoRepository.save(plantacao);

        return toResponseDTO(plantacao);
    }

    public void deletar(Long id) {

        Plantacao plantacao = plantacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plantação não encontrada com ID: " + id));

        plantacaoRepository.delete(plantacao);
    }

    private PlantacaoResponseDTO toResponseDTO(
            Plantacao plantacao
    ) {

        return new PlantacaoResponseDTO(
                plantacao.getIdPlantacao(),
                plantacao.getNomePlantacao(),
                plantacao.getTipoPlantacao(),
                plantacao.getAreaHectares(),
                plantacao.getDataPlantio(),
                plantacao.getStatusPlantacao(),
                plantacao.getPropriedade().getIdPropriedade()
        );
    }
}