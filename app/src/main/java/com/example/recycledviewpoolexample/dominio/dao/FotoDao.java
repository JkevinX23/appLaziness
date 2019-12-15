package com.example.recycledviewpoolexample.dominio.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recycledviewpoolexample.dominio.entidades.Foto;

import java.util.List;

@Dao
public interface FotoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert_foto(Foto foto);

    @Query("SELECT * FROM foto")
    List<Foto> getAllFotos();

    @Query("SELECT * FROM foto WHERE id_diciplina=:id_diciplina")
    List<Foto> getFotosPorDiciplina(String id_diciplina);

    @Query("SELECT * FROM foto WHERE id_diciplina=:id_diciplina")
    LiveData<List<Foto>> getFotosPorDiciplinaLiveData(String id_diciplina);


    @Query("SELECT * FROM foto WHERE data=:data")
    List<Foto> getFotosPorData(String data);

    @Delete
    void remove_foto(Foto foto);
}
