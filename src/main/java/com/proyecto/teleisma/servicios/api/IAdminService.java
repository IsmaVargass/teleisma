package com.proyecto.teleisma.servicios.api;

import com.proyecto.teleisma.entidades.Pizza;
import com.proyecto.teleisma.entidades.Oferta;
import com.proyecto.teleisma.entidades.Usuario;
import java.util.List;

public interface IAdminService extends IUsuarioService {
    void addPizza(Pizza pizza) throws Exception;
    void addOferta(Oferta oferta) throws Exception;
    void removePizza(int id) throws Exception;
    void removeOferta(int id) throws Exception;
}

