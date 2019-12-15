package com.example.recycledviewpoolexample.dominio.repositorios;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.recycledviewpoolexample.dominio.dao.EntidadesRoomDatabase;
import com.example.recycledviewpoolexample.dominio.dao.FotoDao;
import com.example.recycledviewpoolexample.dominio.entidades.Foto;

import java.util.List;

public class FotoRepositorio {
    private FotoDao mDao;
    private List<Foto> mAllFotos;

    public FotoRepositorio(Application application) {
        EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(application);
        mDao = db.fotoDao();
    }


    public void insertFoto(Foto foto) {
        new insertFotoAsyncTask(mDao).execute(foto);
    }

    public void deleteFoto(Foto foto) {
        new deleteFotoAsyncTask(mDao).execute(foto);
    }

    private static class insertFotoAsyncTask extends AsyncTask<Foto, Void, Void> {
        private FotoDao mDao;

        insertFotoAsyncTask(FotoDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Foto... fotos) {
            mDao.insert_foto(fotos[0]);
            return null;
        }
    }

    private static class deleteFotoAsyncTask extends AsyncTask<Foto, Void, Void> {
        private FotoDao mDao;

        deleteFotoAsyncTask(FotoDao mDao) {
            this.mDao = mDao;
        }


        @Override
        protected Void doInBackground(Foto... fotos) {
            mDao.remove_foto(fotos[0]);
            return null;
        }
    }
}
