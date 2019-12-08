package com.example.recycledviewpoolexample.activitys;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycledviewpoolexample.Constantes;
import com.example.recycledviewpoolexample.Item;
import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.SubItem;
import com.example.recycledviewpoolexample.adapters.ItemAdapter;
import com.example.recycledviewpoolexample.dominio.dao.EntidadesRoomDatabase;
import com.example.recycledviewpoolexample.dominio.dao.FotoDao;
import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;
import com.example.recycledviewpoolexample.dominio.entidades.Foto;
import com.example.recycledviewpoolexample.dominio.models.DiciplinasViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mFolders;
    private List<Diciplina> mDiciplinas;
    private List<Foto> mFotos = null;
    private FotoDao fotoDao;

    public static String EMAIL_USER;

    public int service = 0;

    private static final String TAG = "NELORE LOCAIS :::::";

    public static final String MY_ROOT = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES + File.separator + "laziness";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(getApplication());
        fotoDao = db.fotoDao();

        setContentView(R.layout.activity_main);
        verificarPermissoes();
        criar_root();
        Toolbar mToolbar = findViewById(R.id.tb_main);
        setSupportActionBar(mToolbar);

        if (carregar_mFolders()) {
            carregar_diciplinas();
        }
    }

    private void carregar_diciplinas() {
        Log.i(TAG, "carregarDic()");
        DiciplinasViewModel viewModel = new ViewModelProvider(this).get(DiciplinasViewModel.class);
        final List<Diciplina> listDic = new ArrayList<>();
        mDiciplinas = new ArrayList<>();
        viewModel.getlDisciplinasByUSer(EMAIL_USER).observe(this, new Observer<List<Diciplina>>() {
            @Override
            public void onChanged(List<Diciplina> diciplinas) {
                nenhumaDiciplinaTextView();
                RecyclerView rvItem = findViewById(R.id.rv_item);
                for (int i = 0; i < diciplinas.size(); i++) {
                    TextView tv = findViewById(R.id.tv_nenhuma_diciplina_main);
                    rvItem.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.GONE);

                    if (!(listDic.contains((diciplinas.get(i))))) {
                        Log.i(TAG, "NELOREEEE ::: " + diciplinas.get(i).diciplina);
                        listDic.add(diciplinas.get(i));
                        mDiciplinas.add(listDic.get(i));
                    }
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(), buildItemList());
                rvItem.setAdapter(itemAdapter);
                rvItem.setLayoutManager(layoutManager);
            }
        });
    }

    private boolean carregar_mFolders() {
        File toList = new File(MY_ROOT);
        Log.i(TAG, "LOCAL: " + MY_ROOT);
        File[] files = toList.listFiles();
        if (files != null) {
            mFolders = new ArrayList();
            int size = files.length;
            Log.i(TAG, "EXISTEM " + size + " FILES NO DIRETORIO");
            if(size==0)
                nenhumaDiciplinaTextView();
            else
            for (int i = 0; i < size; i++) {
                Log.i(TAG, "FILE: " + files[i].getName());
                if (files[i].isDirectory()) {
                    mFolders.add(files[i].getName());
                    Log.i(TAG, "DIRETORIO: " + files[i].getName());
                }
            }
            return true;

        } else{
            Log.i(TAG, "NENHUMA PASTA ENCONTRADA");
            nenhumaDiciplinaTextView();
        }

        return false;
    }

    private void nenhumaDiciplinaTextView() {
        TextView tv = findViewById(R.id.tv_nenhuma_diciplina_main);
        RecyclerView rvItem = findViewById(R.id.rv_item);
        rvItem.setVisibility(View.GONE);
        tv.setVisibility(View.VISIBLE);
    }

    private boolean verificarPermissoes() {
        final Boolean[] sucess = {false};
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        {

                            if (report.areAllPermissionsGranted()) {
                                Log.i(TAG, "PERMISSOES CONCEDIDAS");
                                sucess[0] = true;
                            }
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                Log.i(TAG, "PERMISSAO NEGADA");
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    Toast.makeText(getApplicationContext(), "VOCE DEVE PERMITIR O ACESSO A CAMERA", Toast.LENGTH_LONG);
                                    Log.i(TAG, "USUARIO SENDO FAZENDO M*** :: PERMISSAO NEGADA FOREVER");
                                } else {
                                    Log.i(TAG, "PERMISSAO NEGADA, MAS NAO FOREVER..REQUISITANDO MAIS UMA VEZ NOVAMETE");
                                    verificarPermissoes();
                                }
                            }
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).onSameThread().check();
        return sucess[0];
    }

    private void criar_root() {
        File folder = new File(MY_ROOT);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success)
            Log.i(Constantes.TAG, "ROOOT CRIADO");
        else Log.i(Constantes.TAG, "ROOT JA EXISTE");
    }

    private void setSupportActionBar(Toolbar mToolbar) {
        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (R.id.menu_item_cadastrar_dic == item.getItemId()) {
                    Intent i = new Intent(getApplicationContext(), CadastrarDiciplinaActivity.class);
                    i.putExtra("email",EMAIL_USER);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    private List<Item> buildItemList() {
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < mDiciplinas.size(); i++) {
            Log.i(TAG, "ADICIONANDO DICIPLINA A RV :: " + mDiciplinas.get(i));
            Item item = new Item(mDiciplinas.get(i), buildSubItemList(mDiciplinas.get(i)));
            itemList.add(item);
        }
        return itemList;
    }

    private List<SubItem> buildSubItemList(Diciplina dic) {
        List<SubItem> subItemList = new ArrayList<>();
        getSubItem(dic);
        Log.i(TAG, "is null?");
        if (mFotos != null) {
            Log.i(TAG, "PASSOU NO FILTRO NULL");
            for (int i = 0; i < mFotos.size(); i++) {
                Log.i(TAG, "EXISTEM FOTOS AQUI EM");
                SubItem subItem = new SubItem(dic, mFotos.get(i));
                subItemList.add(subItem);
            }
            mFotos = null;
            service = 0;
            Log.i(TAG, "SAIU");
        }
        return subItemList;
    }

    private void getSubItem(Diciplina dic) {
        new getFotosAsyncTask(fotoDao).execute(dic);
        while (service == 0) {
            Log.i(TAG, "RODANDO O LOOOOPP");
        }
        Log.i(TAG, "SAAAAIIUUUUUUU");
    }

    public class getFotosAsyncTask extends AsyncTask<Diciplina, Void, Void> {
        FotoDao mDao;
        List<Foto> mFotos;

        getFotosAsyncTask(FotoDao fotoDao) {
            mDao = fotoDao;
        }

        @Override
        protected Void doInBackground(Diciplina... diciplinas) {
            Log.i(TAG, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXx");
            Log.i(TAG, diciplinas[0].diciplina);
            paraLoop( mDao.getFotosPorDiciplina(diciplinas[0].caminho));
            return null;
        }
    }

    private void paraLoop(List<Foto> fotos) {
        Log.i(TAG, "SAINDO DO LOOP3");
        mFotos = fotos;
        service = 1;
    }
}
