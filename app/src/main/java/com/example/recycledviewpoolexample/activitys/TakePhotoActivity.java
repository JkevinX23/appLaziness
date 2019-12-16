package com.example.recycledviewpoolexample.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.recycledviewpoolexample.Constantes;
import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.dominio.dao.EntidadesRoomDatabase;
import com.example.recycledviewpoolexample.dominio.dao.FotoDao;
import com.example.recycledviewpoolexample.dominio.entidades.Foto;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TakePhotoActivity extends AppCompatActivity {


    private static final String TAG = Constantes.TAG+"_TAKE_PHOTO";

    public String nomeFoto;
    public String diciplinaFoto;
    public String horaFoto;
    public String dataFoto;
    private FotoDao fotoDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        EntidadesRoomDatabase db = EntidadesRoomDatabase.getDatabase(getApplication());
        fotoDao = db.fotoDao();

        Intent i = getIntent();
        Bundle b = i.getExtras();

        assert b != null;
        String caminho = b.getString("caminho");
        assert caminho != null;
        caminho = caminho.replaceAll("\\s+", "");
        Log.i(TAG, "take_photo " + caminho);

        getFotoCamera(caminho);
    }

    private void getFotoCamera(String caminho) {

        Intent takefotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takefotoIntent.resolveActivity(getPackageManager()) != null) {
            File photo_file = null;
            try {
                photo_file = createFotoFile(caminho);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "DEEEU RUUIM :: CRIAR PHOTO FILE");
            }

            if (photo_file != null) {
                Log.i(TAG, photo_file.toString());
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.recycledviewpoolexample.fileprovider", photo_file);
                Log.i(TAG, "URL :: " + photoURI.toString());
                takefotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takefotoIntent, 1);
            }
        }

    }

    private void salvarNoBanco(Intent i) {
        Foto foto = new Foto();
        foto.nome_foto = nomeFoto;
    }

    private File createFotoFile(String caminho) throws IOException {

        String data_foto = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Log.i(TAG, "DATA :: " + data_foto);
        String hora_foto = new SimpleDateFormat("HHmmss").format(new Date());
        Log.i(TAG, "HORA :: " + hora_foto);
        Log.i(TAG, caminho);
        String file_name = "JPEG_" + data_foto + "_" + hora_foto + "_";
        File storage = new File(caminho);
        File foto = File.createTempFile(file_name, ".jpg", storage);
        Log.i(TAG, "NOME DA FOTO GERADO :: " + foto.getName() + "\nSALVO EM ::w " + storage.getName());

        diciplinaFoto = caminho;
        nomeFoto = foto.getName();
        horaFoto = hora_foto;
        dataFoto = data_foto;

        Foto newFoto = new Foto();
        newFoto.nome_foto = nomeFoto;
        newFoto.data = data_foto;
        newFoto.hora = hora_foto;
        newFoto.id_diciplina = diciplinaFoto;

        new InserirNoBancoAsyncTask(fotoDao).execute(newFoto);


        return foto;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            salvarNoBanco(data);
        }
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }

    private static class InserirNoBancoAsyncTask extends AsyncTask<Foto, Void, Void> {
        private FotoDao mDao;

        InserirNoBancoAsyncTask(FotoDao fotoDao) {
            mDao = fotoDao;
        }

        @Override
        protected Void doInBackground(Foto... fotos) {
            mDao.insert_foto(fotos[0]);
            return null;
        }
    }
}

