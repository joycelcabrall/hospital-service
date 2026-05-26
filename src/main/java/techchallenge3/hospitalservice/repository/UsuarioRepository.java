package techchallenge3.hospitalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techchallenge3.hospitalservice.entity.Usuario;
import techchallenge3.hospitalservice.enums.PerfilUsuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByPerfil(PerfilUsuario perfil);
}