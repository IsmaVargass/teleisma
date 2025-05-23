package com.proyecto.teleisma.servicios.api;

import com.proyecto.teleisma.entidades.Pizza;
import com.proyecto.teleisma.entidades.Usuario;
import java.util.List;

public interface IGerenteService extends IUsuarioService {
    double calcularBeneficioTotal() throws Exception;
    List<Pizza> getPizzasVendidasPorFecha(String fecha) throws Exception;
    double calcularBeneficioDesdeVentas();
}
