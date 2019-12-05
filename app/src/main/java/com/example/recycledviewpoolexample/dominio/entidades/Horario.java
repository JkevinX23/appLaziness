package com.example.recycledviewpoolexample.dominio.entidades;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

@Entity (tableName = Constantes.nome_tabela_horario)
public class Horario {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name=Constantes.id_tabela_horario)
    public int id;

    @ColumnInfo(name= Constantes.dia_tabela_horario)
    public String dia;

    @ColumnInfo public int idDic;
}
