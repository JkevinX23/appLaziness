package com.example.recycledviewpoolexample.dominio.repositorios;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.recycledviewpoolexample.dominio.dao.DiciplinasDao;
import com.example.recycledviewpoolexample.dominio.dao.EntidadesRoomDatabase;
import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;

import java.util.List;

public class DiciplinaRepositorio {
    private DiciplinasDao mDao;
    private LiveData<List<Diciplina>> mAllDic;

    public DiciplinaRepositorio(Application application) {
        EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(application);
        mDao = db.dicDao();
        mAllDic = mDao.getAllDiciplinas();
    }

    public LiveData<List<Diciplina>> getAlldic() {
        return mAllDic;
    }

    public void insert_dic(Diciplina dic) {
        new insertAsyncTask(mDao).execute(dic);
    }

    private static class insertAsyncTask extends AsyncTask<Diciplina, Void, Void> {

        private DiciplinasDao mAsyncTaskDao;

        insertAsyncTask(DiciplinasDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Diciplina... params) {
            Log.i("NELORE", "INSERIU ");
            mAsyncTaskDao.inserir_diciplina(params[0]);
            return null;
        }
    }
}
