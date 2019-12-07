package com.example.recycledviewpoolexample.activitys;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;
import com.example.recycledviewpoolexample.dominio.models.DiciplinasViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mFolders;
    private List<String> mDiciplinas;

    public static String EMAIL_USER;

    private static final String TAG = "NELORE LOCAIS :::::";

    public static final String MY_ROOT = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES + File.separator + "laziness";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        DiciplinasViewModel viewModel = new ViewModelProvider(this).get(DiciplinasViewModel.class);
        final List<Diciplina> listDic = new ArrayList<>();
        mDiciplinas = new ArrayList<>();
        viewModel.getmAllDics().observe(this, new Observer<List<Diciplina>>() {
            @Override
            public void onChanged(List<Diciplina> diciplinas) {
                Log.i(TAG, "DIC SIZE :: " + diciplinas.size());
                for (int i = 0; i < diciplinas.size(); i++) {
                    if(!(listDic.contains((diciplinas.get(i)).diciplina))){
                        Log.i(TAG, "NELOREEEE ::: " + diciplinas.get(i).diciplina);
                        listDic.add(diciplinas.get(i));
                        mDiciplinas.add(listDic.get(i).diciplina);
                    }

                }
                RecyclerView rvItem = findViewById(R.id.rv_item);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(),buildItemList());
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
            for (int i = 0; i < size; i++) {
                Log.i(TAG, "FILE: " + files[i].getName());
                if (files[i].isDirectory()) {
                    mFolders.add(files[i].getName());
                    Log.i(TAG, "DIRETORIO: " + files[i].getName());
                }
            }
            return true;

        } else
            Log.i(TAG, "NENHUMA PASTA ENCONTRADA");
        return false;
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
                    startActivity(i);
                }
                return true;
            }
        });
    }

    private List<Item> buildItemList() {
        List<Item> itemList = new ArrayList<>();
        Log.i(TAG, "nDic :: " + mDiciplinas.size());
        for (int i = 0; i < mDiciplinas.size(); i++) {
            Log.i(TAG, "ADICIONANDO DICIPLINA A RV :: " + mDiciplinas.get(i));
            Item item = new Item(mDiciplinas.get(i), buildSubItemList(mDiciplinas.get(i)));
            itemList.add(item);
        }
        return itemList;
    }

    private List<SubItem> buildSubItemList(String dic) {
        List<SubItem> subItemList = new ArrayList<>();
        List<String> fotos = new ArrayList<>();
        fotos = getSubItem(dic);
        for (int i = 0; i < fotos.size(); i++) {
            SubItem subItem = new SubItem(dic, fotos.get(i));
            subItemList.add(subItem);
        }
        return subItemList;
    }

    private List<String> getSubItem(String dic) {
        File file = new File(MY_ROOT + File.separator + dic);
        List<String> n = new ArrayList<String>();
        n = Arrays.asList(file.list());
        return n;
    }

}
