package com.proyecto.teleisma.servicios.api;

import com.proyecto.teleisma.entidades.Usuario;
import java.util.List;

public interface IUsuarioService {
    void register(Usuario usuario) throws Exception;
    Usuario login(String nombre, String password) throws Exception;  // antes login(email, pwd)
    List<Usuario> getAllUsuarios() throws Exception;
    void deleteUsuario(int id) throws Exception;
}

