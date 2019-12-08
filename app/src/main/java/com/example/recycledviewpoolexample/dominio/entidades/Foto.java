package com.example.recycledviewpoolexample.dominio.entidades;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.recycledviewpoolexample.Constantes;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Constantes.nome_tabela_fotos)
@ForeignKey(entity = Diciplina.class,
        parentColumns = Constantes.CAMINHO_PASTA,
        childColumns = Constantes.id_diciplina_tabela_fotos,
        onDelete = CASCADE)

public class Foto {

    @PrimaryKey@NonNull
    public String nome_foto;
    @ColumnInfo (name = Constantes.data_tabela_fotos)
    public String data;
    @ColumnInfo (name = Constantes.id_diciplina_tabela_fotos)
    public String id_diciplina;
    @ColumnInfo (name = Constantes.hora_tabela_fotos)
    public String hora;
}
