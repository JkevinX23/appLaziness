package com.example.recycledviewpoolexample.dominio.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recycledviewpoolexample.Constantes;
import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;

import java.util.List;

@Dao
public interface DiciplinasDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void inserir_diciplina(Diciplina diciplina);

    @Delete
    void delete_disciplina(Diciplina diciplina);

    @Query("DELETE FROM "+ Constantes.NOME_TABELA_DICIPLINAS)
    void delete_diciplina_all();

    @Query("UPDATE diciplinas SET nome=:disciplina, professor = :professor, periodo=:periodo WHERE caminho_pasta=:id")
    void update_diciplina(String disciplina, String professor, int periodo,String id);

    @Query("SELECT  * FROM "+ Constantes.NOME_TABELA_DICIPLINAS)
    LiveData<List<Diciplina>> getAllDiciplinas();

    @Query("SELECT * FROM diciplinas WHERE user_id=:user")
    LiveData<List<Diciplina>> getDisciplinaFromUser(String user);

}
