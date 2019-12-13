package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

@Entity(tableName = "aluno", foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = Constantes.email_tabela_usuario,
        childColumns = "id_usuario",
        onUpdate = ForeignKey.CASCADE))
public class Aluno {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_usuario")
    public String usuario; // email


    @ColumnInfo(name = Constantes.nome_aluno_tabela_aluno)
    public String nome;
    @ColumnInfo(name = Constantes.idade_tabela_aluno)
    public int idade;
    @ColumnInfo(name = Constantes.curso_tabela_aluno)
    public String curso;
    @ColumnInfo(name = Constantes.sexo_tabela_aluno)
    public String sexo;
}
