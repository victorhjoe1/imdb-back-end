package com.imdb.imdb.shared;

import com.imdb.imdb.model.Usuario.CargoUsuario;

public record RegisterDTO(String login, String senha, CargoUsuario cargo) {
}
