package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

import java.io.Serializable;

@Entity(tableName = Constantes.NOME_TABELA_USUARIO, indices = {@Index(value = "id_aluno", unique = true)},
        foreignKeys = @ForeignKey(entity = Aluno.class,
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE,
        parentColumns = "id_usuario",
        childColumns = "id_aluno"))

public class Usuario
        implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Constantes.EMAIL_TABELA_USUARIO)
    private String email;

    @NonNull
    @ColumnInfo(name = Constantes.SENHA_USUARIO_TABELA_USUARIO)
    private String senha;


    @ColumnInfo(name = "id_aluno")
    private String idAluno;


    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getSenha() {
        return senha;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setSenha(@NonNull String senha) {
        this.senha = senha;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }


}
