package com.example.recycledviewpoolexample.activitys;

import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;
import com.example.recycledviewpoolexample.dominio.models.DiciplinasViewModel;
import com.example.recycledviewpoolexample.dominio.repositorios.DiciplinaRepositorio;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.List;

public class CadastrarDiciplinaActivity extends AppCompatActivity {


    public DiciplinasViewModel dvm;
    public LinearLayout ll;

    public static final String TAG = "MEU CADASTRO_DIC";

    public static String LOCAL_FOTOS = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES + File.separator + "laziness";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_diciplina);
        ll = findViewById(R.id.linear_vertical_cadastro);

        dvm = new ViewModelProvider(this).get(DiciplinasViewModel.class);
    }

    public void criarDiciplina(View view) {
        EditText et_nome_dic = findViewById(R.id.et_nome_diciplina_cadastro_diciplina);
        EditText et_professor = findViewById(R.id.et_nome_professor_cadastro_diciplina);
        EditText et_periodo = findViewById(R.id.et_periodo_letivo_cadastro_diciplina);


        if (et_nome_dic.getText().toString().equals("")) {
            Snackbar.make(ll, "INSIRA O NOME DA DICIPLINA", Snackbar.LENGTH_LONG).show();
            et_nome_dic.requestFocus();
        } else if (et_professor.getText().toString().equals("")) {
            Snackbar.make(ll, "INSIRA O NOME DO PROFESSOR", Snackbar.LENGTH_LONG).show();
            et_professor.requestFocus();
        } else if (et_periodo.getText().toString().equals("")) {
            Snackbar.make(ll, "INSIRA O PERIODO", Snackbar.LENGTH_LONG).show();
            et_periodo.requestFocus();
        } else {

            String nome_dic = et_nome_dic.getText().toString();
            String professor = et_professor.getText().toString();
            int periodo = Integer.parseInt(et_periodo.getText().toString());

            StringBuilder dic = new StringBuilder();
            dic.append(nome_dic);
            dic.append(professor);
            dic.append(periodo);

            inserir_banco(nome_dic, professor, periodo);
            //criar_diretorio(dic.toString());
            criar_diretorio(nome_dic);
        }


    }

    private void inserir_banco(String nome_dic, String professor, int periodo) {
        Diciplina dic = new Diciplina();
        dic.diciplina = nome_dic;
        dic.professor = professor;
        dic.periodo = periodo;

        dvm.insert_dic(dic);
    }

    private void criar_diretorio(String nome_folder) {

        nome_folder = nome_folder.replaceAll("\\s", "");

        File folder = new
                File(LOCAL_FOTOS
                + File.separator
                + nome_folder);


        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            Log.i(TAG, "PASTA CRIADA");
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        } else {
            Log.i(TAG, "NOME JA CADASTRADO");
            Toast.makeText(getApplicationContext(), "DICIPLINA JÁ CADASTRADA ", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent i = new Intent(getApplicationContext(), CadastrarDiciplinaActivity.class);
            startActivity(i);
        }
    }
}