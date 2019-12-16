package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Constantes.NOME_TABELA_FOTOS,

        foreignKeys = @ForeignKey(entity = Diciplina.class,
                parentColumns = Constantes.CAMINHO_PASTA,
                childColumns = Constantes.ID_DICIPLINA_TABELA_FOTOS,
                onDelete = CASCADE,onUpdate = CASCADE))

public class Foto {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nome_foto")
    public String nome_foto;
    @ColumnInfo(name = Constantes.DATA_TABELA_FOTOS)
    public String data;
    @ColumnInfo(name = Constantes.ID_DICIPLINA_TABELA_FOTOS)
    public String id_diciplina;
    @ColumnInfo(name = Constantes.HORA_TABELA_FOTOS)
    public String hora;
}
