package br.com.terrasense.service;

import br.com.terrasense.dto.dadosnasa.DadosNasaRequestDTO;
import br.com.terrasense.dto.dadosnasa.DadosNasaResponseDTO;
import br.com.terrasense.exception.ResourceNotFoundException;
import br.com.terrasense.model.DadosNasa;
import br.com.terrasense.model.Plantacao;
import br.com.terrasense.repository.DadosNasaRepository;
import br.com.terrasense.repository.PlantacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DadosNasaService {

    private final DadosNasaRepository dadosNasaRepository;
    private final PlantacaoRepository plantacaoRepository;

    public List<DadosNasaResponseDTO> listarTodos() {
        return dadosNasaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public DadosNasaResponseDTO buscarPorId(Long id) {

        DadosNasa dadosNasa = dadosNasaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Dados NASA não encontrados com ID: " + id));

        return toResponseDTO(dadosNasa);
    }

    public DadosNasaResponseDTO cadastrar(DadosNasaRequestDTO dto) {

        Plantacao plantacao = plantacaoRepository
                .findById(dto.idPlantacao())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plantação não encontrada com ID: "
                                        + dto.idPlantacao()));

        DadosNasa dadosNasa = new DadosNasa();

        dadosNasa.setDataReferencia(dto.dataReferencia());
        dadosNasa.setRadiacaoSolar(dto.radiacaoSolar());
        dadosNasa.setNdvi(dto.ndvi());
        dadosNasa.setTemperaturaSat(dto.temperaturaSat());
        dadosNasa.setUmidadeSat(dto.umidadeSat());
        dadosNasa.setPrecipSat(dto.precipSat());
        dadosNasa.setDataColeta(dto.dataColeta());
        dadosNasa.setPlantacao(plantacao);

        dadosNasa = dadosNasaRepository.save(dadosNasa);

        return toResponseDTO(dadosNasa);
    }

    public DadosNasaResponseDTO atualizar(Long id, DadosNasaRequestDTO dto) {

        DadosNasa dadosNasa = dadosNasaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Dados NASA não encontrados com ID: " + id));

        Plantacao plantacao = plantacaoRepository
                .findById(dto.idPlantacao())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plantação não encontrada com ID: "
                                        + dto.idPlantacao()));

        dadosNasa.setDataReferencia(dto.dataReferencia());
        dadosNasa.setRadiacaoSolar(dto.radiacaoSolar());
        dadosNasa.setNdvi(dto.ndvi());
        dadosNasa.setTemperaturaSat(dto.temperaturaSat());
        dadosNasa.setUmidadeSat(dto.umidadeSat());
        dadosNasa.setPrecipSat(dto.precipSat());
        dadosNasa.setDataColeta(dto.dataColeta());
        dadosNasa.setPlantacao(plantacao);

        dadosNasa = dadosNasaRepository.save(dadosNasa);

        return toResponseDTO(dadosNasa);
    }

    public void deletar(Long id) {

        DadosNasa dadosNasa = dadosNasaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Dados NASA não encontrados com ID: " + id));

        dadosNasaRepository.delete(dadosNasa);
    }

    private DadosNasaResponseDTO toResponseDTO(DadosNasa dadosNasa) {

        return new DadosNasaResponseDTO(
                dadosNasa.getIdDadoNasa(),
                dadosNasa.getDataReferencia(),
                dadosNasa.getRadiacaoSolar(),
                dadosNasa.getNdvi(),
                dadosNasa.getTemperaturaSat(),
                dadosNasa.getUmidadeSat(),
                dadosNasa.getPrecipSat(),
                dadosNasa.getDataColeta(),
                dadosNasa.getPlantacao().getIdPlantacao()
        );
    }
}