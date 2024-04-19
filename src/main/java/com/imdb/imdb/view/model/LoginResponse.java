package com.imdb.imdb.view.model;
import com.imdb.imdb.model.Usuario.CargoUsuario;

public record LoginResponse(Integer id, String token, CargoUsuario cargo) {
}
