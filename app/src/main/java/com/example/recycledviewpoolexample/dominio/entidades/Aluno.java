package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.recycledviewpoolexample.Constantes;

@Entity(tableName = "aluno")
public class Aluno {


    @ForeignKey(entity = Usuario.class,
            parentColumns = Constantes.email_tabela_usuario,
            childColumns = "id_usuario",
            onUpdate = ForeignKey.CASCADE)
    @ColumnInfo(name = "id_usuario")
    public String usuario;


    @ColumnInfo(name= Constantes.nome_aluno_tabela_aluno)
    public String nome;
    @ColumnInfo(name= Constantes.idade_tabela_aluno)
    public int idade;
    @ColumnInfo(name= Constantes.curso_tabela_aluno)
    public String curso;
    @ColumnInfo(name= Constantes.sexo_tabela_aluno)
    public int sexo;
}
