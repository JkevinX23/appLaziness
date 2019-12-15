package com.example.recycledviewpoolexample.dominio.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.recycledviewpoolexample.dominio.entidades.Aluno;
import com.example.recycledviewpoolexample.dominio.repositorios.AlunoRepositorio;

public class AlunoViewModel extends AndroidViewModel {

    private AlunoRepositorio mRepositorio;

    public AlunoViewModel(@NonNull Application application) {
        super(application);

        mRepositorio = new AlunoRepositorio(application);
    }

    public void insert_aluno(Aluno aluno){
        mRepositorio.inserir_aluno(aluno);
    }

    public void atualizar_aluno(Aluno aluno1) {
        mRepositorio.atualizar_aluno(aluno1);
    }
}
