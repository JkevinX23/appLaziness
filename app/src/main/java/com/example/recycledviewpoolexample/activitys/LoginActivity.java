package com.example.recycledviewpoolexample.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.recycledviewpoolexample.AuthAsyncIntentService;
import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;
import com.example.recycledviewpoolexample.dominio.models.UsuariosViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    public View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tv_cadastre = findViewById(R.id.tv_cadastrar_login_activity);
        tv_cadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrarUsuarioActivity.class));
            }
        });

        Button bt_confirmar = findViewById(R.id.bt_confirmar_login_activity);
        bt_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView = v;
                EditText et_email = findViewById(R.id.et_email_login_activity);
                EditText et_senha = findViewById(R.id.et_senha_login_activity);

                if (et_email.getText().toString().equals("")) {
                    Snackbar.make(v, "Insira seu email", Snackbar.LENGTH_LONG).show();
                    et_email.requestFocus();
                } else if (et_senha.getText().toString().equals("")) {
                    Snackbar.make(v, "Insira sua senha", Snackbar.LENGTH_LONG).show();
                    et_senha.requestFocus();
                } else {
                    String email = et_email.getText().toString();
                    String senha = et_senha.getText().toString();

                    validar(email, senha);
                }
            }
        });
    }

    private void validar(String email, String senha) {

        AsyncAuthResultReciver resultReciver = new AsyncAuthResultReciver(new Handler());
        Intent intent = new Intent(this, AuthAsyncIntentService.class);
        intent.putExtra("gambiarra", resultReciver);
        intent.putExtra("email", email);
        intent.putExtra("senha", senha);
        startService(intent);

        /*

        UsuariosViewModel uvm = new UsuariosViewModel(getApplication());
        if(uvm.getUsuario(email,senha)){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        Log.i("NELORE", " NOME DO USUARIO ::: ");*/
    }

    private class AsyncAuthResultReciver extends ResultReceiver {
        public AsyncAuthResultReciver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);


            //se result == 1 Email cadastrado.
            //se result == 0 Email nao cadastrado .
            //se result == 1 && bundle false -> Senha errada.
            //se result == 1 && bundler true -> Senha correta.

            if (resultCode == 1) {
                if (resultData != null)
                    if (resultData.getBoolean("auth")) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else Snackbar.make(mView, "Senha incorreta", Snackbar.LENGTH_LONG).show();
            } else Snackbar.make(mView, "Email nao cadastrado", Snackbar.LENGTH_LONG).show();
        }
    }
}
