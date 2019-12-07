package com.example.recycledviewpoolexample;

import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;
import com.example.recycledviewpoolexample.dominio.entidades.Foto;

public class SubItem {

    private Foto foto;
    private Diciplina mDiciplina;

    public SubItem(Diciplina diciplina, Foto foto) {
        this.mDiciplina = diciplina;
        this.foto = foto;
    }

    public Foto getSubItemImage() {
        return foto;
    }

    public Diciplina getPasta () {
        return mDiciplina;
    }

}
