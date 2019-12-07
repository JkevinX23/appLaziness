package com.example.recycledviewpoolexample.dominio.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.recycledviewpoolexample.dominio.entidades.Aluno;
import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;


@Database(entities = {
        Diciplina.class,
        Usuario.class,
        Aluno.class},
        version = 1,
        exportSchema = false)
public abstract class EntidadesRoomDatabase extends RoomDatabase {
    public abstract DiciplinasDao dicDao();
    public abstract UsuariosDao userDao();
    public abstract AlunosDao alunosDao();

    private static volatile EntidadesRoomDatabase INSTANCE;
    public static EntidadesRoomDatabase getDatabase (final Context context){
        if(INSTANCE == null){
            synchronized (EntidadesRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),EntidadesRoomDatabase.class,"laziness_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
