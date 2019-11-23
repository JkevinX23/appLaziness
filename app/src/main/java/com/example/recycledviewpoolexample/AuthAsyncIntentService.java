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

public class AuthAsyncIntentService extends IntentService{

    private ResultReceiver resultReceiver;

    public AuthAsyncIntentService() {
        super("name");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent.hasExtra("gambiarra")){
            resultReceiver = intent.getParcelableExtra("gambiarra");
            String email = intent.getStringExtra("email");
            String senha = intent.getStringExtra("senha");
            Log.i("NELORE", "a intent chegou krl ::: email :: "+email+" e a senha :: "+senha);

            if(resultReceiver!=null){
                /*AuthModuleTask moduleTask = new AuthModuleTask();
                moduleTask.start(this);*/
                EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(getApplication());
                UsuariosDao dao = db.userDao();
                List<Usuario> user = dao.get_user(email);
                Usuario usuario;
                Log.i("NELORE", "nao deu ruim, glória :::: Size :: "+user.size());
                if(user.size()>0){
                    usuario = user.get(0);
                    Bundle b = new Bundle();
                    if(email.equals(usuario.getEmail())&&senha.equals(usuario.getSenha())){

                        b.putBoolean("auth",true);
                        Log.i("NELORE","onSucesso Auth");
                        resultReceiver.send(1,b);
                        stopSelf();
                    }
                    else{
                        b.putBoolean("auth",false);
                        Log.i("NELORE","errou a senha");
                        resultReceiver.send(1,b);
                        stopSelf();
                    }
                }
                else {
                    Log.i("NELORE","email inexistente");
                    resultReceiver.send(0,null);
                    stopSelf();
                }
            }
        }
    }
}
