package com.example.recycledviewpoolexample.dominio.repositorios;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.recycledviewpoolexample.dominio.dao.AlunosDao;
import com.example.recycledviewpoolexample.dominio.dao.EntidadesRoomDatabase;
import com.example.recycledviewpoolexample.dominio.entidades.Aluno;

public class AlunoRepositorio {

    private AlunosDao mDao;

    public AlunoRepositorio(Application application){
        EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(application);
        Log.i("NELORE", "mDao EXISTE");
        mDao = db.alunosDao();
    }

    public void inserir_aluno(Aluno aluno){
       new inserirAlunoAsyncTask(mDao).execute(aluno);
    }

    private static class inserirAlunoAsyncTask extends AsyncTask<Aluno,Void,Void> {
       private AlunosDao alunosDao;

        inserirAlunoAsyncTask(AlunosDao mDao) {
            alunosDao = mDao;
        }

        @Override
        protected Void doInBackground(Aluno... alunos) {
            alunosDao.insert_aluno(alunos[0]);
            return null;
        }
    }
}
