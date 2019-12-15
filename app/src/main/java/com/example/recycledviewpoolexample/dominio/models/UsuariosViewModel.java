package com.example.recycledviewpoolexample.dominio.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.recycledviewpoolexample.dominio.entidades.Usuario;
import com.example.recycledviewpoolexample.dominio.repositorios.UsuarioRepositorio;

public class UsuariosViewModel extends AndroidViewModel {
    private UsuarioRepositorio mRepositorio;

    public UsuariosViewModel(Application application) {
        super(application);
        mRepositorio = new UsuarioRepositorio(getApplication());
    }
/*
    public Boolean getUsuario(String email,String senha){
      return mRepositorio.getUser(email,senha);
    }*/

    public void insert_user(Usuario user){
        mRepositorio.inserir_usuario(user);
    }

    public void atualizar_user(Usuario user){mRepositorio.atualizar_user(user);}
}

