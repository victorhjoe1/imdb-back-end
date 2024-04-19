package com.imdb.imdb.shared;

import com.imdb.imdb.model.Filme.Voto;
import com.imdb.imdb.model.Usuario.CargoUsuario;

import java.util.List;

public record UsuarioDTO(String login, String senha, CargoUsuario cargo, List<Voto> votos) {
}
