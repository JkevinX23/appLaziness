package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

@Entity (tableName = Constantes.NOME_TABELA_HORARIO)
public class PeriodoLetivo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name=Constantes.ID_TABELA_PERIODO)
    public int id;
    @ColumnInfo(name = Constantes.PERIODO_TABELA_PERIODO)
    public int periodo;
    @ColumnInfo(name = Constantes.QUANTIDADE_TABELA_PERIODO)
    public int quantidade;

}
