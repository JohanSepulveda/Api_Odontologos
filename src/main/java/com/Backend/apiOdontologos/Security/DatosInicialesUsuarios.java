package com.Backend.apiOdontologos.Security;


import com.Backend.apiOdontologos.Model.Usuario;
import com.Backend.apiOdontologos.Model.UsuarioRole;
import com.Backend.apiOdontologos.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosInicialesUsuarios implements ApplicationRunner {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //crear un usuario como si fuera real
        //guardarlo en la base
        BCryptPasswordEncoder cifrador= new BCryptPasswordEncoder();
        String passSinCifrar="contrasena";
        String passCifrada=cifrador.encode(passSinCifrar);
        Usuario usuarioAInsertar=new Usuario("Johan",
                "Johansp",
                "perezjohan551@gmail.com",passCifrada, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuarioAInsertar);
        //crear un usuario tipo admin
        String passCifrada2=cifrador.encode("password");
        usuarioAInsertar= new Usuario("Sepulveda","Johansp","jsepulv28@hotmail.com",
                passCifrada2,UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuarioAInsertar);

    }
}
