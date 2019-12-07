package com.example.recycledviewpoolexample.dominio.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recycledviewpoolexample.dominio.entidades.Aluno;

import java.util.List;

@Dao
public interface AlunosDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert_aluno(Aluno aluno);

    @Delete
    void delete_aluno(Aluno aluno);

    @Query("DELETE FROM aluno")
    void deleteAll_aluno();

    @Query("SELECT * FROM aluno")
    List<Aluno> getAll_aluno();

    @Query("SELECT * FROM aluno WHERE id_usuario=:id_user")
    List<Aluno> getAlunoFromIdUser(String id_user);

    @Query("UPDATE aluno SET nome=:nome WHERE id_usuario=:id_user")
    void updateNomeAluno(String nome, String id_user);

    @Query("UPDATE aluno SET idade=:idade WHERE id_usuario=:id_user")
    void updateIdadeAluno(int idade, String id_user);

    @Query("UPDATE aluno SET curso=:curso WHERE id_usuario=:id_user")
    void updateCursoAluno(String curso, String id_user);

    @Query("UPDATE aluno SET sexo=:sexo WHERE id_usuario=:id_user")
    void updateSexoAluno(String sexo, String id_user);


}
