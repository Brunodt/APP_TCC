package com.example.logincadastro.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaDeUsuarios implements Serializable {

    public ArrayList<Usuario> listaDeUsuarios;
    public static final String KEY = "listaDeUsuarios";

    public ListaDeUsuarios(ArrayList<Usuario> listaDeUsuarios){
        this.listaDeUsuarios = listaDeUsuarios;
    }
}
