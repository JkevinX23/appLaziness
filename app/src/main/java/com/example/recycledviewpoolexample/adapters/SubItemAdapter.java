package com.example.recycledviewpoolexample.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.SubItem;
import com.example.recycledviewpoolexample.dominio.entidades.Foto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static com.example.recycledviewpoolexample.activitys.MainActivity.MY_ROOT;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {

    private List<SubItem> subItemList;

    SubItemAdapter(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sub_item, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, int i) {
        SubItem subItem = subItemList.get(i);
        Foto fotoObject = subItem.getSubItemImage();

        File file = new File(subItem.getPasta().caminho+ File.separator + fotoObject.nome_foto);
        if (file.exists() && file.canRead()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
                Bitmap foto = BitmapFactory.decodeStream(bufferedInputStream);
                Bitmap resized = Bitmap.createScaledBitmap(foto, 60, 60, false);
                subItemViewHolder.iv.setImageBitmap(resized);
                subItemViewHolder.data.setText(fotoObject.data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        // SubItem subItem = subItemList.get(i);
        // subItemViewHolder.tvSubItemTitle.setText(subItem.getSubItemTitle());
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView data;
        ImageView iv;

        SubItemViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_sub_item);
            data = itemView.findViewById(R.id.tv_data);
        }
    }
}
