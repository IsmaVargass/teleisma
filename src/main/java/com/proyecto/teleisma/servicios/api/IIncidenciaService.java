package com.proyecto.teleisma.servicios.api;

import com.proyecto.teleisma.entidades.Incidencia;

import java.util.List;

public interface IIncidenciaService {
    List<Incidencia> getAllIncidencias() throws Exception;
    void addIncidencia(Incidencia i) throws Exception;
    void deleteIncidencia(int id) throws Exception;
}
