package com.example.recycledviewpoolexample.dominio.entidades;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

@Entity (tableName = Constantes.NOME_TABELA_HORARIO)
public class Horario {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name=Constantes.ID_TABELA_HORARIO)
    public int id;

    @ColumnInfo(name= Constantes.DIA_TABELA_HORARIO)
    public String dia;

    @ColumnInfo public int idDic;
}
