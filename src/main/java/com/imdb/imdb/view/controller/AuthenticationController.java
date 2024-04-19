package com.imdb.imdb.view.controller;

import com.imdb.imdb.model.Usuario.CargoUsuario;
import com.imdb.imdb.model.Usuario.Usuario;
import com.imdb.imdb.model.exception.BadRequestException;
import com.imdb.imdb.repository.UsuarioRepository;
import com.imdb.imdb.security.TokenService;
import com.imdb.imdb.shared.AuthenticationDTO;
import com.imdb.imdb.shared.LoginRequest;
import com.imdb.imdb.shared.RegisterDTO;
import com.imdb.imdb.shared.UsuarioDTO;
import com.imdb.imdb.view.model.LoginResponse;
import jakarta.validation.Valid;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        Usuario usuario = usuarioRepository.findByLogin(data.login());

        CargoUsuario cargo = usuario.getCargo();

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(usuario.getId(), token, cargo));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if(this.usuarioRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario(data.login(), encryptedPassword, data.cargo());

        this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid UsuarioDTO data, @PathVariable Integer id) {
        if(this.usuarioRepository.findById(id) == null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario usuarioAtualizado = new Usuario(data.login(), encryptedPassword, data.cargo());
        usuarioAtualizado.setId(id);


        this.usuarioRepository.save(usuarioAtualizado);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/activate")
    public ResponseEntity activateUser(@RequestBody LoginRequest login) {
        Usuario usuario = usuarioRepository.findByLogin(login.login());
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setAtivo(true);
        usuario.isEnabled();
        usuarioRepository.save(usuario);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/deactivate")
    public ResponseEntity deactivateUser(@RequestBody LoginRequest login) {
        Usuario usuario = usuarioRepository.findByLogin(login.login());
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setAtivo(false);
        usuario.isEnabled();
        usuarioRepository.save(usuario);

        return ResponseEntity.ok().build();
    }
}
