package com.example.recycledviewpoolexample.dominio.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;
import com.example.recycledviewpoolexample.dominio.repositorios.DiciplinaRepositorio;

import java.util.List;

public class DiciplinasViewModel extends AndroidViewModel {

    private DiciplinaRepositorio mRepositorio;
    private LiveData<List<Diciplina>> mAllDics;

    public DiciplinasViewModel(Application application) {
        super(application);

        mRepositorio = new DiciplinaRepositorio(application);
        mAllDics = mRepositorio.getAlldic();
    }

    public LiveData<List<Diciplina>>getmAllDics(){
        return mAllDics;
    }

    public void insert_dic(Diciplina diciplina){
        mRepositorio.insert_dic(diciplina);
    }
}
