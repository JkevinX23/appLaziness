package com.example.recycledviewpoolexample.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;
import com.example.recycledviewpoolexample.dominio.models.UsuariosViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tv_cadastre = findViewById(R.id.tv_cadastrar_login_activity);
        tv_cadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrarUsuarioActivity.class));
            }
        });

        Button bt_confirmar = findViewById(R.id.bt_confirmar_login_activity);
        bt_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_email = findViewById(R.id.et_email_login_activity);
                EditText et_senha = findViewById(R.id.et_senha_login_activity);

                if(et_email.getText().toString().equals("")){
                    Snackbar.make(v,"Insira seu email",Snackbar.LENGTH_LONG).show();
                    et_email.requestFocus();
                }else if(et_senha.getText().toString().equals("")){
                    Snackbar.make(v,"Insira sua senha",Snackbar.LENGTH_LONG).show();
                    et_senha.requestFocus();
                }
                else{
                    String email = et_email.getText().toString();
                    String senha = et_senha.getText().toString();

                    validar(email,senha);
                }
            }
        });
    }

    private void validar(String email, String senha){


        UsuariosViewModel uvm = new UsuariosViewModel(getApplication());
        if(uvm.getUsuario(email,senha)){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        Log.i("NELORE", " NOME DO USUARIO ::: ");
    }
}
