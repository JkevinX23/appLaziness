package com.example.recycledviewpoolexample.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;
import com.example.recycledviewpoolexample.dominio.models.UsuariosViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    public UsuariosViewModel uvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        uvm = new ViewModelProvider(this).get(UsuariosViewModel.class);

        TextView tv_sign_in = findViewById(R.id.tv_login_cadastrar_usuario_activity);
        tv_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        Button bt_confirmar = findViewById(R.id.bt_confirmar_cadastro_usuario_activity);
        bt_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_nome = findViewById(R.id.et_nome_cadastro_usuario_activity);
                EditText et_email = findViewById(R.id.et_email_cadastro_usuario_activity);
                EditText et_senha = findViewById(R.id.et_senha_cadastro_usuario_activity);

                if(et_nome.getText().toString().equals("")){
                    Snackbar.make(v,"Por favor, insira seu nome",Snackbar.LENGTH_LONG).show();
                    et_nome.requestFocus();
                }else if(et_email.getText().toString().equals("")){
                    Snackbar.make(v,"Por favor, insira seu email",Snackbar.LENGTH_LONG).show();
                    et_email.requestFocus();
                }else if(et_senha.getText().toString().equals("")){
                    Snackbar.make(v,"Por favor, crie uma senha",Snackbar.LENGTH_LONG).show();
                    et_senha.requestFocus();
                }else{
                    String nome = et_nome.getText().toString();
                    String email = et_email.getText().toString();
                    String senha = et_senha.getText().toString();
                    inserir_banco (nome,email,senha);
                }
            }
        });
    }

    private void inserir_banco(String nome, String email, String senha) {
        Usuario user = new Usuario();
        user.setEmail(email);
        user.setNome(nome);
        user.setSenha(senha);
        uvm.insert_user(user);
    }
}
