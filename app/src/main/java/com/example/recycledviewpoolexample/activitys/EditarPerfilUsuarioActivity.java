package com.example.recycledviewpoolexample.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.dominio.entidades.Aluno;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;
import com.example.recycledviewpoolexample.dominio.models.AlunoViewModel;
import com.example.recycledviewpoolexample.dominio.models.UsuariosViewModel;
import com.google.android.material.snackbar.Snackbar;

public class EditarPerfilUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_usuario);

        Intent intent = getIntent();
        final Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        final Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
        assert aluno != null;
        Log.i("NELORE", "Aluno no editar okay nome :: " + aluno.nome);
        assert usuario != null;
        Log.i("NELORE", "Usuario no editar okay email :: " + usuario.getEmail());


        final EditText et_nome = findViewById(R.id.et_nome_perfil_editar);
        final EditText et_email = findViewById(R.id.et_email_perfil_editar);
        final EditText et_senha = findViewById(R.id.et_senha_perfil_editar);
        final EditText et_curso = findViewById(R.id.et_curso_perfil_editar);

        et_nome.setText(aluno.nome);
        et_email.setText(usuario.getEmail());
        et_curso.setText(aluno.curso);
        et_senha.setText(usuario.getSenha());
        et_email.setEnabled(false);

        Button btConfirmar = findViewById(R.id.bt_confirmar_edicao_perfil);
        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_nome.getText().toString().equals("")) {
                    Snackbar.make(v, "Nome nao pode ficar vazio", Snackbar.LENGTH_LONG).show();
                    et_nome.requestFocus();
                } else if (et_senha.getText().toString().equals("")) {
                    Snackbar.make(v, "Senha nao pode ficar vazia", Snackbar.LENGTH_LONG).show();
                    et_senha.requestFocus();
                } else if (et_curso.getText().toString().equals("")) {
                    Snackbar.make(v, "Curso nao pode ficar vazio", Snackbar.LENGTH_LONG).show();
                    et_curso.requestFocus();

                } else {
                    String nome = et_nome.getText().toString();
                    String email = et_email.getText().toString();
                    String senha = et_senha.getText().toString();
                    String curso = et_curso.getText().toString();

                    Aluno aluno1 = aluno;
                    aluno1.nome = nome;
                    aluno1.curso = curso;

                    atualizarAluno(aluno1);
                    atualizarUsuario(email, senha, usuario.getIdAluno());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });

    }

    private void atualizarAluno(Aluno aluno1) {
        AlunoViewModel viewModel = new AlunoViewModel(getApplication());
        viewModel.atualizar_aluno(aluno1);
    }

    private void atualizarUsuario(String email, String senha, String idAluno) {
        UsuariosViewModel viewModel = new UsuariosViewModel(getApplication());
        Usuario user = new Usuario();
        user.setEmail(email);
        user.setSenha(senha);
        user.setIdAluno(idAluno);
        viewModel.atualizar_user(user);
    }

}
