package com.proyecto.teleisma.servicios.api;

import com.proyecto.teleisma.entidades.Oferta;
import java.util.List;
import com.proyecto.teleisma.entidades.Pizza;

public interface IOfertaService {
    List<Oferta> getAllOfertas() throws Exception;
    void addOferta(Oferta oferta) throws Exception;
    Oferta getOfertaById(int id) throws Exception;
    void updateOferta(Oferta oferta) throws Exception;
    void deleteOferta(int id) throws Exception;
    void insertOferta(Oferta o);
    List<Pizza> getAllPizzas() throws Exception;

}
