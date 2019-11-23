package com.example.recycledviewpoolexample;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.recycledviewpoolexample.dominio.dao.EntidadesRoomDatabase;
import com.example.recycledviewpoolexample.dominio.dao.UsuariosDao;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;

import java.util.List;

public class AuthAsyncIntentService extends IntentService {

    private ResultReceiver resultReceiver;

    public AuthAsyncIntentService() {
        super("name");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.hasExtra(Constantes.RESULT_RECIVER)) {
            resultReceiver = intent.getParcelableExtra(Constantes.RESULT_RECIVER);
            String email = intent.getStringExtra(Constantes.EMAIL);
            String senha = intent.getStringExtra(Constantes.SENHA);
            Log.i(Constantes.TAG, "a intent chegou krl ::: email :: " + email + " e a senha :: " + senha);

            if (resultReceiver != null) {

                /*AuthModuleTask moduleTask = new AuthModuleTask();
                moduleTask.start(this);*/

                EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(getApplication());
                UsuariosDao dao = db.userDao();
                List<Usuario> user = dao.get_user(email);
                Usuario usuario;

                Log.i(Constantes.TAG, "nao deu ruim, glória :::: Size :: " + user.size());

                if (user.size() > 0) {
                    usuario = user.get(0);
                    Bundle b = new Bundle();
                    if (email.equals(usuario.getEmail()) && senha.equals(usuario.getSenha())) {

                        b.putBoolean(Constantes.AUTH_KEY, true);
                        Log.i(Constantes.TAG, "onSucesso Auth");
                        resultReceiver.send(1, b);
                        stopSelf();
                    } else {
                        b.putBoolean(Constantes.AUTH_KEY, false);
                        Log.i(Constantes.TAG, "errou a senha");
                        resultReceiver.send(1, b);
                        stopSelf();
                    }
                } else {
                    Log.i(Constantes.TAG, "email inexistente");
                    resultReceiver.send(0, null);
                    stopSelf();
                }
            }
        }
    }
}
