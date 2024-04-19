package com.imdb.imdb.model.Usuario;

public enum CargoUsuario {
    ADMIN("admin"),
    USER("user");

    private String cargo;

    CargoUsuario(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
}
