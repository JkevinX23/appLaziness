package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

import java.io.Serializable;

@Entity(tableName = "aluno",
        foreignKeys =

        @ForeignKey(entity = Usuario.class,
                parentColumns = Constantes.EMAIL_TABELA_USUARIO,
                childColumns = "id_usuario",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE))

public class Aluno implements Serializable {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_usuario")
    public String usuario; // email


    @ColumnInfo(name = Constantes.NOME_ALUNO_TABELA_ALUNO)
    public String nome;
    @ColumnInfo(name = Constantes.IDADE_TABELA_ALUNO)
    public int idade;
    @ColumnInfo(name = Constantes.CURSO_TABELA_ALUNO)
    public String curso;
    @ColumnInfo(name = Constantes.SEXO_TABELA_ALUNO)
    public String sexo;
}
