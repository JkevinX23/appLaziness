package com.example.recycledviewpoolexample.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.dominio.dao.DiciplinasDao;
import com.example.recycledviewpoolexample.dominio.dao.EntidadesRoomDatabase;
import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;
import com.google.android.material.snackbar.Snackbar;

public class EditarDisciplinaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_diciplina);

        Intent i = getIntent();
        final Diciplina diciplina = (Diciplina) i.getSerializableExtra("disciplina");

        TextView tv_bv = findViewById(R.id.tv_bem_vindoCadastrarDic);
        final EditText et_nome_dic = findViewById(R.id.et_nome_diciplina_cadastro_diciplina);
        final EditText et_professor = findViewById(R.id.et_nome_professor_cadastro_diciplina);
        final EditText et_periodo = findViewById(R.id.et_periodo_letivo_cadastro_diciplina);

        tv_bv.setText("Bem vindo\nEdite os campos que desejar");
        et_nome_dic.setText(diciplina.diciplina);
        et_professor.setText(diciplina.professor);
        et_periodo.setText(String.valueOf(diciplina.periodo));

        Button confirmar = findViewById(R.id.bt_confirmar_cadastro_disciplina);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_nome_dic = findViewById(R.id.et_nome_diciplina_cadastro_diciplina);
                EditText et_professor = findViewById(R.id.et_nome_professor_cadastro_diciplina);
                EditText et_periodo = findViewById(R.id.et_periodo_letivo_cadastro_diciplina);

                if (et_nome_dic.getText().toString().equals("")) {
                    Snackbar.make(v, "INSIRA O NOME DA DICIPLINA", Snackbar.LENGTH_LONG).show();
                    et_nome_dic.requestFocus();
                } else if (et_professor.getText().toString().equals("")) {
                    Snackbar.make(v, "INSIRA O NOME DO PROFESSOR", Snackbar.LENGTH_LONG).show();
                    et_professor.requestFocus();
                } else if (et_periodo.getText().toString().equals("")) {
                    Snackbar.make(v, "INSIRA O PERIODO", Snackbar.LENGTH_LONG).show();
                    et_periodo.requestFocus();
                } else {

                    String nome_dic = et_nome_dic.getText().toString();
                    String professor = et_professor.getText().toString();
                    int periodo = Integer.parseInt(et_periodo.getText().toString());

                    diciplina.diciplina = nome_dic;
                    diciplina.professor = professor;
                    diciplina.periodo = periodo;

                    updateDisciplinaBanco();

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }

            private void updateDisciplinaBanco() {
                EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(getApplication());
                DiciplinasDao dao = db.dicDao();
                new UpdateDisciplinaBancoAsyncTask(dao).execute(diciplina);
            }
        });
    }

    private static class UpdateDisciplinaBancoAsyncTask extends AsyncTask<Diciplina, Void, Void> {
        DiciplinasDao dao;

        UpdateDisciplinaBancoAsyncTask(DiciplinasDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Diciplina... diciplinas) {
            dao.update_diciplina(diciplinas[0].diciplina, diciplinas[0].professor,diciplinas[0].periodo, diciplinas[0].caminho);
            return null;
        }
    }
}