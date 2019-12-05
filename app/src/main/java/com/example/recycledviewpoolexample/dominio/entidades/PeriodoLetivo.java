package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

@Entity (tableName = Constantes.nome_tabela_horario)
public class PeriodoLetivo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name=Constantes.id_tabela_periodo)
    public int id;
    @ColumnInfo(name = Constantes.periodo_tabela_periodo)
    public int periodo;
    @ColumnInfo(name = Constantes.quantidade_tabela_periodo)
    public int quantidade;

}
