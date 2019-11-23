package com.example.recycledviewpoolexample.dominio.repositorios;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.recycledviewpoolexample.activitys.AuthTrueActivity;
import com.example.recycledviewpoolexample.activitys.MainActivity;
import com.example.recycledviewpoolexample.dominio.dao.EntidadesRoomDatabase;
import com.example.recycledviewpoolexample.dominio.dao.UsuariosDao;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;
import com.example.recycledviewpoolexample.dominio.models.UsuariosViewModel;

import java.util.List;

public class UsuarioRepositorio {

    private UsuariosDao mDao;
    private String mEmail = "";

    public UsuarioRepositorio(Application aplication) {
        EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(aplication);
        Log.i("NELORE", "mDao EXISTE");
        mDao = db.userDao();
    }

    public Boolean getUser(String email, String senha) {

        Log.i("NELORE", "CHEGOU NO REPOSITORIO");
        Log.i("NELORE", "EMAIL :::: "+email);
        Log.i("NELORE", "SENHA :::: "+senha);
        //new getUserAsyncTask(mDao).execute(email,senha);
        return true;
    }
    public void inserir_usuario(Usuario user){
        new insertAsyncTask(mDao).execute(user);
    }

    private static class getUserAsyncTask extends AsyncTask<String, Void, Boolean >{
        private UsuariosDao dao;

        private getUserAsyncTask(UsuariosDao dao) {
            this.dao = dao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            Log.i("NELORE", "doInBack :: email :: "+strings[0]);
            Log.i("NELORE", "doInBack :: senha :: "+strings[1]);

            Usuario usuario = new Usuario();
            Boolean validar = false;
            try{
                 usuario = dao.get_user(strings[0]).get(0);
                 if(usuario.getSenha().equals(strings[1])){
                     validar = true;
                 }
            }catch (Exception e){
                Log.i("NELORE","Usuario nao cadastrado");

            }

            return validar;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result){
               Log.i("NELORE","ENTRAR");
            }
            else Log.i("NELORE", "Usuario nao cadastrado");
        }
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

}
