package com.proyecto.teleisma.servicios.api;

import com.proyecto.teleisma.entidades.ContactoCliente;
import java.util.List;

public interface IAtencionService {
    void enviarMensaje(ContactoCliente msg) throws Exception;
    List<ContactoCliente> getHistorial(int usuarioId) throws Exception;
    void deleteMensaje(int id) throws Exception;   // permitir borrar un mensaje/incidencia
}
