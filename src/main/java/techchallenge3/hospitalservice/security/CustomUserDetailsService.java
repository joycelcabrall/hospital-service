package techchallenge3.hospitalservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import techchallenge3.hospitalservice.entity.Usuario;
import techchallenge3.hospitalservice.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado"));

        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + usuario.getPerfil().name()
                        )
                )
        );
    }
}