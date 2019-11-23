package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

@Entity(tableName = Constantes.nome_tabela_usuario,indices = {@Index(value = Constantes.email_tabela_usuario,unique = true)})
public class Usuario {

    @PrimaryKey @ColumnInfo(name=Constantes.email_tabela_usuario)@NonNull private String email;
    @ColumnInfo (name=Constantes.nome_usuario_tabela_usuario) @NonNull private String nome;
    @ColumnInfo (name=Constantes.senha_usuario_tabela_usuario)@NonNull private String senha;

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    @NonNull
    public String getSenha() {
        return senha;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    public void setSenha(@NonNull String senha) {
        this.senha = senha;
    }
}
