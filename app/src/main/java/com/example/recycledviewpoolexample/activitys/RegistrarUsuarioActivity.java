package com.example.recycledviewpoolexample.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.dominio.entidades.Aluno;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;
import com.example.recycledviewpoolexample.dominio.models.AlunoViewModel;
import com.example.recycledviewpoolexample.dominio.models.UsuariosViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    public UsuariosViewModel usuariosViewModel;
    public AlunoViewModel alunoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        usuariosViewModel = new ViewModelProvider(this).get(UsuariosViewModel.class);
        alunoViewModel = new ViewModelProvider(this).get(AlunoViewModel.class);

        TextView tv_sign_in = findViewById(R.id.tv_login_cadastrar_usuario_activity);
        tv_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        TextView tv_sexo = findViewById(R.id.tv_sexo_cadastro_usuario_activity);
        tv_sexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSexoAlertDialog(v);
            }
        });


        Button bt_confirmar = findViewById(R.id.bt_confirmar_cadastro_usuario_activity);
        bt_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_nome = findViewById(R.id.et_nome_cadastro_usuario_activity);
                EditText et_email = findViewById(R.id.et_email_cadastro_usuario_activity);
                EditText et_senha = findViewById(R.id.et_senha_cadastro_usuario_activity);
                EditText et_curso = findViewById(R.id.et_curso_cadastro_usuario_activity);
                EditText et_idade = findViewById(R.id.et_idade_cadastro_usuario_activity);
                TextView tv_sexo = findViewById(R.id.tv_sexo_cadastro_usuario_activity);

                if (et_nome.getText().toString().equals("")) {
                    Snackbar.make(v, "Por favor, insira seu nome", Snackbar.LENGTH_LONG).show();
                    et_nome.requestFocus();
                } else if (et_email.getText().toString().equals("")) {
                    Snackbar.make(v, "Por favor, insira seu email", Snackbar.LENGTH_LONG).show();
                    et_email.requestFocus();
                } else if (et_senha.getText().toString().equals("")) {
                    Snackbar.make(v, "Por favor, crie uma senha", Snackbar.LENGTH_LONG).show();
                    et_senha.requestFocus();
                } else if (et_curso.getText().toString().equals("")) {
                    Snackbar.make(v, "Por favor, insira seu curso", Snackbar.LENGTH_LONG).show();
                    et_curso.requestFocus();
                } else if (et_idade.getText().toString().equals("")) {
                    Snackbar.make(v, "Por favor, insira sua idade", Snackbar.LENGTH_LONG).show();
                    et_idade.requestFocus();
                } else if (tv_sexo.getText().toString().equals("sexo")) {
                    Snackbar.make(v, "Por favor, selecione seu sexo", Snackbar.LENGTH_LONG).show();
                    tv_sexo.requestFocus();
                } else {
                    String nome = et_nome.getText().toString();
                    String email = et_email.getText().toString();
                    String senha = et_senha.getText().toString();
                    String idade = et_idade.getText().toString();
                    String curso = et_curso.getText().toString();
                    String sexo = tv_sexo.getText().toString();

                    inserir_banco(nome, email, senha,idade,curso,sexo);
                }
            }
        });
    }

    private void getSexoAlertDialog(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarUsuarioActivity.this);
        builder.setTitle("Selecione seu sexo");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                RegistrarUsuarioActivity.this,
                android.R.layout.select_dialog_singlechoice);

        arrayAdapter.add("Masculino");
        arrayAdapter.add("Feminino");
        arrayAdapter.add("Outro");
        final int[] select = {0};
        builder.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("nelore", arrayAdapter.getItem(which));
                        select[0] = which;
                        dialog.dismiss();

                    }
                });
        builder.show();

        TextView tv = v.findViewById(R.id.tv_sexo_cadastro_usuario_activity);
        switch (select[0]) {
            case 0:
                tv.setText("Masculino");
                break;
            case 1:
                tv.setText("Feminino");
               break;
            case 2:
                tv.setText("Outro");
               break;
        }
    }

    private void inserir_banco(String nome, String email, String senha,String idade,String curso,String sexo) {
        Usuario user = new Usuario();
        Aluno aluno = new Aluno();

        user.setEmail(email);
        user.setSenha(senha);
        aluno.usuario = email;
        aluno.curso = curso;
        aluno.idade = Integer.parseInt(idade);
        aluno.nome = nome;
        aluno.sexo = sexo;

        usuariosViewModel.insert_user(user);
        alunoViewModel.insert_aluno(aluno);

        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}