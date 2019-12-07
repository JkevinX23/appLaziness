package com.example.recycledviewpoolexample.dominio.entidades;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

@Entity(tableName = Constantes.nome_tabela_diciplinas)
public class Diciplina {

    @PrimaryKey()
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

}
