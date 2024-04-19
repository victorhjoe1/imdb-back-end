package com.imdb.imdb.services;

import com.imdb.imdb.model.Filme.Filme;
import com.imdb.imdb.model.exception.ResourceNotFoundException;
import com.imdb.imdb.repository.FilmeRepository;
import com.imdb.imdb.shared.FilmeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmesService {

    @Autowired
    private FilmeRepository filmeRepository;

    /**
     * Método para retornar uma lista de filmes.
     * @return Retorna uma lista de filmes.
     *
     */
    public List<FilmeDTO> obterTodos(){
        List<Filme> filmes = filmeRepository.findAll();

        return filmes.stream()
                .map(filme -> new ModelMapper().map(filme, FilmeDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método para retornar uma lista de filmes por  busca.
     * @return Retorna uma lista de filmes de acordo com uma busca.
     *
     */
    public List<FilmeDTO> obterPorPesquisa(String search){
        List<Filme> filmes = filmeRepository.findBySearch(search);

        return filmes.stream()
                .map(filme -> new ModelMapper().map(filme, FilmeDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Método que retorna o fime encontrado pelo seu id.
     * @param id do filme que será localizado.
     * @return Retorna um filme caso seja localizado.
     */
    public Optional<FilmeDTO> obterPorId(Integer id){

        Optional<Filme> filme = filmeRepository.findById(id);

        if(filme.isEmpty()){
            throw new ResourceNotFoundException("O filme com o id " + id + " não foi encontrado. O filme não existe.");
        }

        FilmeDTO filmeDTO = new ModelMapper().map(filme.get(), FilmeDTO.class);

        return Optional.of(filmeDTO);
    }

    /**
     * Método para adicionar um filme na lista.
     * @param filme que foi adicionado ns lista.
     * @return Retorna o filme que foi adicionado na lista.
     */
    public FilmeDTO adicionar(FilmeDTO filmeDTO){

        ModelMapper mapper = new ModelMapper();

        Filme filme = mapper.map(filmeDTO, Filme.class);

        filmeRepository.save(filme);
        filmeDTO.setId(filme.getId());

        return filmeDTO;
    }

    /**
     * Método para deletar o filme por id.
     * @param id do filme a ser deletado.
     */
    public void deletar(Integer id){
        Optional<Filme> filme = filmeRepository.findById(id);

        if(filme.isEmpty()){
            throw new ResourceNotFoundException("Não foi possível deletar o filme com o id" + id + ". Filme não encontrado.");
        }

        filmeRepository.deleteById(id);
    }

    /**
     * Método para atualizar o filme na lista.
     * @param filme que será atualizado.
     * @return Retorna o filme após atualizar a lista.
     */
    public FilmeDTO atualizar(FilmeDTO filmeDTO, Integer id){

        Optional<Filme> filmeEncontrado = filmeRepository.findById(id);

        if(filmeEncontrado.isEmpty()){
            throw new ResourceNotFoundException("Não foi possível atualizar o filme com o id" + id + ". Id não encontrado.");
        }

        filmeDTO.setId(id);

        ModelMapper mapper = new ModelMapper();

        Filme filme = mapper.map(filmeDTO, Filme.class);

        filmeRepository.save(filme);

        return filmeDTO;
    }
}
