package com.example.recycledviewpoolexample.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.recycledviewpoolexample.Constantes;
import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;
import com.example.recycledviewpoolexample.dominio.models.DiciplinasViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class CadastrarDiciplinaActivity extends AppCompatActivity {
    public final String TAG = Constantes.TAG+"_CAD_DIS_ACT";

    private String mEmail;

    public DiciplinasViewModel dvm;
    public LinearLayout ll;

    public static String LOCAL_FOTOS = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES + File.separator + "laziness";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_diciplina);
        ll = findViewById(R.id.linear_vertical_cadastro);
        dvm = new ViewModelProvider(this).get(DiciplinasViewModel.class);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            mEmail = b.getString("email");
            Log.i(TAG, "EMAIL USUARIO :: " + mEmail);
        }


        Toolbar tb = findViewById(R.id.tb_cadastro_diciplinas);
        setSupportActionBar(tb);


    }

    private void setSupportActionBar(Toolbar tb) {
        tb.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    private void limparCampos() {
        EditText et_nome_dic = findViewById(R.id.et_nome_diciplina_cadastro_diciplina);
        EditText et_professor = findViewById(R.id.et_nome_professor_cadastro_diciplina);
        EditText et_periodo = findViewById(R.id.et_periodo_letivo_cadastro_diciplina);

        et_nome_dic.getText().clear();
        et_professor.getText().clear();
        et_periodo.getText().clear();
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

            nome_dic = nome_dic.replaceAll("\\s+", "");
            inserirBanco(nome_dic, professor, periodo);
            criarDiretorio(nome_dic);
            limparCampos();

        }


    }

    private void inserirBanco(String nome_dic, String professor, int periodo) {
        Diciplina dic = new Diciplina();
        dic.diciplina = nome_dic;
        dic.professor = professor;
        dic.periodo = periodo;
        dic.caminho = LOCAL_FOTOS + File.separator + nome_dic;
        dic.userId = mEmail;
        dvm.insertDic(dic);
    }

    private void criarDiretorio(String nome_folder) {

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
            limparCampos();
        }
    }
}