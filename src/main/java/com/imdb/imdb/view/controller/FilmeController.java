package com.imdb.imdb.view.controller;

import com.imdb.imdb.services.FilmesService;
import com.imdb.imdb.shared.FilmeDTO;
import com.imdb.imdb.view.model.FilmeRequest;
import com.imdb.imdb.view.model.FilmeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

    @Autowired
    private FilmesService filmesService;

    @GetMapping
    public ResponseEntity<List<FilmeResponse>> obterTodos(){

        List<FilmeDTO> filmes = filmesService.obterTodos();
        ModelMapper mapper = new ModelMapper();

        List<FilmeResponse> resposta = filmes.stream()
                .map(filme -> mapper.map(filme, FilmeResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<FilmeResponse>> obterPorId(@PathVariable Integer id){
        Optional<FilmeDTO> filmeDTO = filmesService.obterPorId(id);

        FilmeResponse filme = new ModelMapper().map(filmeDTO.get(), FilmeResponse.class);

        return new ResponseEntity<>(Optional.of(filme), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FilmeResponse>>  search(@RequestParam String search) {
        List<FilmeDTO> filmes = filmesService.obterPorPesquisa(search);
        ModelMapper mapper = new ModelMapper();

        List<FilmeResponse> resposta = filmes.stream()
                .map(filme -> mapper.map(filme, FilmeResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FilmeResponse> adicionar(@RequestBody FilmeRequest ingredienteReq){
        ModelMapper mapper = new ModelMapper();

        FilmeDTO dto = mapper.map(ingredienteReq, FilmeDTO.class);

        dto = filmesService.adicionar(dto);

        return new ResponseEntity<>(mapper.map(dto, FilmeResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id){
        filmesService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeResponse> atualizar(@RequestBody FilmeRequest filmeReq, @PathVariable Integer id){
        ModelMapper mapper = new ModelMapper();

        FilmeDTO dto = mapper.map(filmeReq, FilmeDTO.class);



        dto = filmesService.atualizar(dto, id);

        return new ResponseEntity<>(mapper.map(dto, FilmeResponse.class), HttpStatus.OK);
    }
}