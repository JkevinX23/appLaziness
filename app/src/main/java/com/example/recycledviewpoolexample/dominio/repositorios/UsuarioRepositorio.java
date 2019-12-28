package com.example.recycledviewpoolexample.dominio.repositorios;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.recycledviewpoolexample.dominio.dao.EntidadesRoomDatabase;
import com.example.recycledviewpoolexample.dominio.dao.UsuariosDao;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;

public class UsuarioRepositorio {

    private UsuariosDao mDao;
    private String mEmail = "";

    public UsuarioRepositorio(Application aplication) {
        EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(aplication);
        Log.i("NELORE", "mDao EXISTE");
        mDao = db.userDao();
    }

    public void inserir_usuario(Usuario user) {
        new insertAsyncTask(mDao).execute(user);
    }

    public void atualizar_user(Usuario user) {
        new atualizarAsyncTask(mDao).execute(user);
    }

    public void delete_user(Usuario user) {
        new deleteAsyncTask(mDao).execute(user);
    }


    private static class insertAsyncTask extends AsyncTask<Usuario, Void, Void> {

        private UsuariosDao mAsyncTaskDao;

        insertAsyncTask(UsuariosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Usuario... params) {
            mAsyncTaskDao.inserir_usuario(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("NELORE", "INSERIU ");
        }
    }

    private static class atualizarAsyncTask extends AsyncTask<Usuario, Void, Void> {
        UsuariosDao mDao;

        atualizarAsyncTask(UsuariosDao mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Usuario... usuarios) {
            mDao.updateUser(usuarios[0]);
            return null;
        }
    }


    private static class deleteAsyncTask extends AsyncTask<Usuario, Void, Void> {
        UsuariosDao dao;
        deleteAsyncTask(UsuariosDao mDao) {
        dao = mDao;
        }

        @Override
        protected Void doInBackground(Usuario... usuarios) {
            dao.deleteUser(usuarios[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
