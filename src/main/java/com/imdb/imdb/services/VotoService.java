package com.imdb.imdb.services;

import com.imdb.imdb.model.Filme.Voto;
import com.imdb.imdb.model.exception.ResourceNotFoundException;
import com.imdb.imdb.repository.VotoRepository;
import com.imdb.imdb.shared.VotoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    public List<VotoDTO> obterTodos() {
        List<Voto> votos = votoRepository.findAll();

        return votos.stream()
                .map(voto -> new ModelMapper().map(voto, VotoDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<VotoDTO> obterPorId(Integer id) {
        Optional<Voto> voto = votoRepository.findById(id);

        if (voto.isEmpty()) {
            throw new ResourceNotFoundException("O voto com o id " + id + " não foi encontrado. O voto não existe.");
        }

        VotoDTO votoDTO = new ModelMapper().map(voto.get(), VotoDTO.class);

        return Optional.of(votoDTO);
    }

    public Optional<VotoDTO> obterPorUserEfilmeId(Integer idUsuario, Integer idFilme) {
        Optional<Voto> voto = votoRepository.findByIdUsuarioAndIdFilme(idUsuario, idFilme);

        if (voto.isEmpty()) {
            throw new ResourceNotFoundException("O voto com o usuario de id " + idUsuario + " ou com o filme de id " + idFilme + " não foi encontrado. O voto não existe.");
        }

        VotoDTO votoDTO = new ModelMapper().map(voto.get(), VotoDTO.class);

        return Optional.of(votoDTO);
    }

    public VotoDTO adicionar(VotoDTO votoDTO) {
        ModelMapper mapper = new ModelMapper();

        Voto voto = mapper.map(votoDTO, Voto.class);

        votoRepository.save(voto);
        votoDTO.setId(voto.getId());

        return votoDTO;
    }

    public void deletar(Integer id) {
        Optional<Voto> voto = votoRepository.findById(id);

        if (voto.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível deletar o voto com o id " + id + ". Voto não encontrado.");
        }

        votoRepository.deleteById(id);
    }

    public VotoDTO atualizar(VotoDTO votoDTO, Integer id) {
        Optional<Voto> votoEncontrado = votoRepository.findById(id);

        if (votoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível atualizar o voto com o id " + id + ". Id não encontrado.");
        }

        votoDTO.setId(id);

        ModelMapper mapper = new ModelMapper();

        Voto voto = mapper.map(votoDTO, Voto.class);

        votoRepository.save(voto);

        return votoDTO;
    }
}
