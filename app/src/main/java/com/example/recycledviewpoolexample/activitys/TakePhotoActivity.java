package com.example.recycledviewpoolexample.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.recycledviewpoolexample.BuildConfig;
import com.example.recycledviewpoolexample.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.recycledviewpoolexample.activitys.MainActivity.MY_ROOT;


public class TakePhotoActivity extends AppCompatActivity {


    private static final String TAG = "NELORE" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String diciplina = b.getString("diciplina");
        Log.i(TAG, "take_photo " + diciplina);

        get_foto_camera(diciplina);
    }

    private void get_foto_camera(String diciplina) {

        Intent takefotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takefotoIntent.resolveActivity(getPackageManager()) != null) {
            File photo_file = null;
            try {
                photo_file = create_foto_file(diciplina);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "DEEEU RUUIM :: CRIAR PHOTO FILE");
            }

            if (photo_file!= null){
                Log.i(TAG,  photo_file.toString());
                Uri photoURI = FileProvider.getUriForFile(this,"com.example.recycledviewpoolexample.fileprovider", photo_file);
                Log.i(TAG, "URL :: " + photoURI.toString());
                takefotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takefotoIntent, 1);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }

    private File create_foto_file(String diciplina) throws IOException {

        diciplina = diciplina.replaceAll("\\s", "");
        String hora_foto = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String file_name = "JPEG_" + hora_foto + "_";
        File storage = new File(MY_ROOT + File.separator + diciplina);
        File foto = File.createTempFile(file_name, ".jpg", storage);
        Log.i("NELORE", "NOME DA FOTO GERADO :: " + file_name + "\nSALVO EM :: " + storage.getName());
        return foto;
    }

}

