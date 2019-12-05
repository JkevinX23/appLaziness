package com.example.recycledviewpoolexample.dominio.entidades;


import androidx.core.net.ConnectivityManagerCompat;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

@Entity(tableName = Constantes.nome_tabela_diciplinas)
public class Diciplina {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constantes.ID_tabela_diciplinas)
    public int id;

    @ColumnInfo(name = Constantes.diciplina_tabela_diciplinas)
    public String diciplina;

    @ColumnInfo(name = Constantes.professor_tabela_diciplinas)
    public String professor;

    @ColumnInfo(name = Constantes.periodo_tabela_diciplinas)
    public int periodo;

    @ColumnInfo
    public int horario;

}
