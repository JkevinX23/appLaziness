package com.example.recycledviewpoolexample.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.dominio.dao.AlunosDao;
import com.example.recycledviewpoolexample.dominio.dao.DiciplinasDao;
import com.example.recycledviewpoolexample.dominio.dao.EntidadesRoomDatabase;
import com.example.recycledviewpoolexample.dominio.dao.FotoDao;
import com.example.recycledviewpoolexample.dominio.dao.UsuariosDao;
import com.example.recycledviewpoolexample.dominio.entidades.Aluno;
import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;
import com.example.recycledviewpoolexample.dominio.entidades.Foto;
import com.example.recycledviewpoolexample.dominio.entidades.Usuario;
import com.example.recycledviewpoolexample.dominio.models.AlunoViewModel;
import com.example.recycledviewpoolexample.dominio.models.DiciplinasViewModel;
import com.example.recycledviewpoolexample.dominio.models.UsuariosViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.List;

public class PerfilUsuarioActivity extends AppCompatActivity {

    List<Diciplina> mDisciplinas;
    Usuario mUsuario;
    Aluno mAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        Intent i = getIntent();
        String email = i.getStringExtra("email");
        //mDisciplinas = (List<Diciplina>) i.getSerializableExtra("dicList");
        getAluno(email);
    }

    private void preencherCampos(Aluno aluno) {
        TextView tv_nome = findViewById(R.id.tv_nome_perfil);
        TextView tv_idade = findViewById(R.id.tv_idade_perfil);
        TextView tv_sexo = findViewById(R.id.tv_sexo_perfil);
        TextView tv_email = findViewById(R.id.tv_email_perfil);
        TextView tv_curso = findViewById(R.id.tv_curso_perfil);
        TextView tv_disciplinas = findViewById(R.id.tv_disciplinas_perfil);

        tv_nome.setText(aluno.nome);
        tv_curso.setText(aluno.curso);
        tv_email.setText(aluno.usuario);
        tv_idade.setText(String.valueOf(aluno.idade));
        tv_sexo.setText(aluno.sexo);
        tv_disciplinas.setText("Exibir Disciplinas");

    }

    private void getAluno(String email) {
        EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(getApplication());
        AlunosDao dao = db.alunosDao();
        new getAlunoAsyncTask(dao).execute(email);
    }

    private class getAlunoAsyncTask extends AsyncTask<String, Void, Aluno> {
        AlunosDao dao;

        getAlunoAsyncTask(AlunosDao dao) {
            this.dao = dao;
        }

        @Override
        protected Aluno doInBackground(String... strings) {
            List<Aluno> alunos = dao.getAlunoFromIdUser(strings[0]);
            return alunos.get(0);
        }

        @Override
        protected void onPostExecute(Aluno aluno) {
            super.onPostExecute(aluno);
            setUser(aluno);
        }
    }

    private void setUser(Aluno aluno) {
        preencherCampos(aluno);
        getUsuario(aluno);
        getDisciplinaWithAluno(aluno);
    }

    private void getUsuario(Aluno aluno) {
        EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(getApplication());
        UsuariosDao dao = db.userDao();
        mAluno = aluno;
        new getUsuarioAsyncTask(dao).execute(aluno);
    }

    private void getDisciplinaWithAluno(Aluno aluno) {
        DiciplinasViewModel viewModel = new DiciplinasViewModel(getApplication());
        viewModel.getlDisciplinasByUSer(aluno.usuario).observe(this, new Observer<List<Diciplina>>() {
            @Override
            public void onChanged(List<Diciplina> diciplinas) {
                mDisciplinas = diciplinas;
                exibirDisciplinas();
            }
        });
    }

    private void exibirDisciplinas() {
        TextView tv_disciplinas = findViewById(R.id.tv_disciplinas_perfil);
        tv_disciplinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("NELORE", "INICIANDO ALERT DIALOG");
                AlertDialog.Builder builder = new AlertDialog.Builder(PerfilUsuarioActivity.this);
                builder.setTitle("Lista de Disciplinas");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        PerfilUsuarioActivity.this,
                        android.R.layout.select_dialog_item);
                if (!mDisciplinas.isEmpty()) {
                    Log.i("NELORE", "CONSEGUI CAPTURAR DISCIPLINAS");
                    for (int i = 0; i < mDisciplinas.size(); i++)
                        arrayAdapter.add(mDisciplinas.get(i).diciplina);

                    Log.i("NELORE", "LISTA PREENCHIDA");
                    builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    Log.i("NELORE", "EXIBINDO ALERT DIALOG");
                    builder.show();
                }
            }
        });
    }

    public void editPerfil(final View view) {
        if (mUsuario == null)
            return;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PerfilUsuarioActivity.this);
        alertDialog.setTitle("Editar Usuário");
        alertDialog.setMessage("Olá, para sua segurança, insira sua senha novamente");


        final EditText input = new EditText(PerfilUsuarioActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        //alertDialog.setIcon(R.drawable.key);

        final String[] senha = new String[1];
        alertDialog.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        senha[0] = input.getText().toString();

                        if (senha[0].equals(mUsuario.getSenha())) {
                            Log.i("NELORE", "senha correta");
                            Intent i = new Intent(getApplicationContext(),EditarPerfilUsuarioActivity.class);
                            i.putExtra("usuario", (Serializable) mUsuario);
                            i.putExtra("aluno", (Serializable) mAluno);
                            startActivity(i);
                        } else {
                            Log.i("NELORE", "senha errada");
                            Snackbar.make(view,"SENHA INCORRETA", Snackbar.LENGTH_LONG).show();
                        }

                    }
                });
        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private class getUsuarioAsyncTask extends AsyncTask<Aluno, Void, Usuario> {
        UsuariosDao mDao;

        public getUsuarioAsyncTask(UsuariosDao dao) {
            mDao = dao;
        }

        @Override
        protected Usuario doInBackground(Aluno... alunos) {

            return mDao.get_user(alunos[0].usuario).get(0);
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            super.onPostExecute(usuario);
            mUsuario = usuario;
        }
    }
}