package com.example.recycledviewpoolexample;

public class SubItem {

    private String foto;
    private String pasta;

    public SubItem(String pasta, String foto) {
        this.pasta = pasta;
        this.foto = foto;
    }

    public String getSubItemImage() {
        return foto;
    }

    public String getPasta () {
        return pasta;
    }

}
