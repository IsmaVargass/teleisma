package com.proyecto.teleisma.servicios.impl;

import com.proyecto.teleisma.entidades.Pizza;
import com.proyecto.teleisma.entidades.Oferta;
import com.proyecto.teleisma.entidades.Usuario;
import com.proyecto.teleisma.servicios.api.IAdminService;

import java.util.List;

public class AdminServiceImpl implements IAdminService {
    private final UsuarioServiceImpl us = new UsuarioServiceImpl();
    private final PizzaServiceImpl ps   = new PizzaServiceImpl();
    private final OfertaServiceImpl os  = new OfertaServiceImpl();

    @Override public void register(Usuario u) throws Exception { us.register(u); }
    @Override public Usuario login(String e, String p) throws Exception { return us.login(e,p); }
    @Override public List<Usuario> getAllUsuarios() throws Exception { return us.getAllUsuarios(); }
    @Override public void deleteUsuario(int id) throws Exception { us.deleteUsuario(id); }

    @Override public void addPizza(Pizza pizza) throws Exception { ps.addPizza(pizza); }
    @Override public void addOferta(Oferta oferta) throws Exception { os.addOferta(oferta); }
    @Override public void removePizza(int id) throws Exception { ps.deletePizza(id); }
    @Override public void removeOferta(int id) throws Exception { os.deleteOferta(id); }
}
