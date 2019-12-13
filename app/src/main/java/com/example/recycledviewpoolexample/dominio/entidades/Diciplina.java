package com.example.recycledviewpoolexample.dominio.entidades;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Constantes.nome_tabela_diciplinas,
        foreignKeys = @ForeignKey(entity = Aluno.class,
                parentColumns = "id_usuario",
                childColumns = "user_id",onDelete = CASCADE),indices = @Index(value = "user_id",unique = false))

public class Diciplina implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = Constantes.CAMINHO_PASTA)
    public String caminho;

    @ColumnInfo(name = Constantes.diciplina_tabela_diciplinas)
    public String diciplina;

    @ColumnInfo(name = Constantes.professor_tabela_diciplinas)
    public String professor;

    @ColumnInfo(name = Constantes.periodo_tabela_diciplinas)
    public int periodo;

    @ColumnInfo
    public int horario;

    @ColumnInfo(name = "user_id")
    public String userId;


}
