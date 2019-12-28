package com.example.recycledviewpoolexample;

import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;

import java.util.List;

public class Item {
    private Diciplina dic;
    private String itemTitle;
    private List<SubItem> subItemList;

    public Item(Diciplina diciplina, List<SubItem> subItemList) {
        this.dic = diciplina;
        this.subItemList = subItemList;
    }

    public Diciplina getItemDic() {
        return dic;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<SubItem> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }
}
