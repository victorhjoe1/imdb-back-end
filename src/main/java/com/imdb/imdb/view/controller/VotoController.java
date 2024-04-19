package com.imdb.imdb.view.controller;

import com.imdb.imdb.services.VotoService;
import com.imdb.imdb.shared.VotoDTO;
import com.imdb.imdb.view.model.VotoRequest;
import com.imdb.imdb.view.model.VotoResponse;
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
@RequestMapping("/api/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @GetMapping
    public ResponseEntity<List<VotoResponse>> obterTodos() {
        List<VotoDTO> votos = votoService.obterTodos();
        ModelMapper mapper = new ModelMapper();

        List<VotoResponse> resposta = votos.stream()
                .map(voto -> mapper.map(voto, VotoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotoResponse> obterPorId(@PathVariable Integer id) {
        Optional<VotoDTO> votoDTO = votoService.obterPorId(id);

        if (votoDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ModelMapper().map(votoDTO.get(), VotoResponse.class), HttpStatus.OK);
    }

    @GetMapping("/user-movie")
    public ResponseEntity<VotoResponse> obterPorUserEFilmeId(@RequestParam Integer idUsuario, @RequestParam Integer idFilme) {

        Optional<VotoDTO> votoDTO = votoService.obterPorUserEfilmeId(idUsuario, idFilme);

        if (votoDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ModelMapper().map(votoDTO.get(), VotoResponse.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VotoResponse> adicionar(@RequestBody VotoRequest votoReq) {
        ModelMapper mapper = new ModelMapper();

        VotoDTO dto = mapper.map(votoReq, VotoDTO.class);
        dto = votoService.adicionar(dto);

        return new ResponseEntity<>(mapper.map(dto, VotoResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        votoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VotoResponse> atualizar(@RequestBody VotoRequest votoReq, @PathVariable Integer id) {
        ModelMapper mapper = new ModelMapper();

        VotoDTO dto = mapper.map(votoReq, VotoDTO.class);
        dto = votoService.atualizar(dto, id);

        return new ResponseEntity<>(mapper.map(dto, VotoResponse.class), HttpStatus.OK);
    }
}
