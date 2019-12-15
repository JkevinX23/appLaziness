package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

import java.io.Serializable;

@Entity(tableName = Constantes.nome_tabela_usuario, indices = {@Index(value = Constantes.email_tabela_usuario, unique = true)})
public class Usuario
        implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Constantes.email_tabela_usuario)
    private String email;

    @NonNull
    @ColumnInfo(name = Constantes.senha_usuario_tabela_usuario)
    private String senha;



    @ForeignKey(entity = Aluno.class,
            onUpdate = ForeignKey.CASCADE,
            parentColumns = Constantes.id_tabela_aluno,
            childColumns = "id_aluno")
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
