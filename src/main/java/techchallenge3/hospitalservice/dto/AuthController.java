package techchallenge3.hospitalservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import techchallenge3.hospitalservice.dto.LoginRequest;
import techchallenge3.hospitalservice.dto.LoginResponse;
import techchallenge3.hospitalservice.entity.Usuario;
import techchallenge3.hospitalservice.repository.UsuarioRepository;
import techchallenge3.hospitalservice.security.JwtService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(request.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.gerarToken(usuario.getEmail());

        return new LoginResponse(token);
    }
}