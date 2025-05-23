package com.proyecto.teleisma.servicios.api;

import com.proyecto.teleisma.entidades.Pizza;
import java.util.List;

public interface IPizzaService {
    List<Pizza> getAllPizzas() throws Exception;
    void addPizza(Pizza pizza) throws Exception;
    void updatePizza(Pizza pizza) throws Exception;
    void deletePizza(int id) throws Exception;
    Pizza getPizzaById(int id) throws Exception;
}

