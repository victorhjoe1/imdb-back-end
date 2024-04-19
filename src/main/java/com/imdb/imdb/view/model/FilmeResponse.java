package com.imdb.imdb.view.model;

import com.imdb.imdb.model.Filme.Voto;

import java.util.List;

public class FilmeResponse {

    public Integer id;

    public String nome;

    public String descricao;

    public List<Voto> votos;

    public Integer mediaVotos;

    public String diretor;

    public String  genero;

    public String atores;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    public Integer getMediaVotos() {
        return calcularMediaVotos();
    }

    private Integer calcularMediaVotos() {
        if (votos == null || votos.isEmpty()) {
            return 0;
        }

        double soma = 0.0;
        for (Voto voto : votos) {
            soma += voto.getValor();
        }
        double media = soma / votos.size();
        return (int) Math.round(media);
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    
}
