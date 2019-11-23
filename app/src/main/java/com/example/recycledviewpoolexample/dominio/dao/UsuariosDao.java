package com.example.recycledviewpoolexample.dominio.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recycledviewpoolexample.Constantes;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;

import java.util.List;

@Dao
public interface UsuariosDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void inserir_usuario(Usuario usuario);

    @Query("SELECT * FROM USUARIO WHERE EMAIL =:email ")
    List<Usuario> get_user(String email);
}
