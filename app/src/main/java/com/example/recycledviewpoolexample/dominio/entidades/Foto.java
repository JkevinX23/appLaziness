package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

import java.util.Date;

@Entity(tableName = Constantes.nome_tabela_fotos)
public class Foto {

    @PrimaryKey
    public int id;
    @ColumnInfo (name = Constantes.data_tabela_fotos)
    public Date data;
    @ColumnInfo (name = Constantes.periodo_tabela_fotos)
    public String periodo;
    @ColumnInfo (name = Constantes.diciplina_tabela_fotos)
    public String diciplina;
    @ColumnInfo (name = Constantes.hora_tabela_fotos)
    public String hora;
}
