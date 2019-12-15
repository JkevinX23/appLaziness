package com.example.recycledviewpoolexample.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.SubItem;
import com.example.recycledviewpoolexample.activitys.MainActivity;
import com.example.recycledviewpoolexample.activitys.RegistrarUsuarioActivity;
import com.example.recycledviewpoolexample.dominio.entidades.Foto;
import com.example.recycledviewpoolexample.dominio.repositorios.FotoRepositorio;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {
    private List<SubItem> subItemList;

    SubItemAdapter(Context context, List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sub_item, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubItemViewHolder subItemViewHolder, int i) {
        SubItem subItem = subItemList.get(i);
        final Foto fotoObject = subItem.getSubItemImage();

        File file = new File(subItem.getPasta().caminho + File.separator + fotoObject.nome_foto);
        if (file.exists() && file.canRead()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
                Bitmap foto = BitmapFactory.decodeStream(bufferedInputStream);
                Bitmap resized = Bitmap.createScaledBitmap(foto, 100, 100, false);
                subItemViewHolder.iv.setImageBitmap(resized);
                subItemViewHolder.data.setText(fotoObject.data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        subItemViewHolder.iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {

                Log.i("NELROE", "POSICAO SUBITEM ::" + subItemViewHolder.getAdapterPosition());

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Deseja excluir a foto?");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        v.getContext(),
                        android.R.layout.select_dialog_singlechoice);

                builder.setAdapter(arrayAdapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apagarImagem(v, fotoObject, subItemViewHolder.getAdapterPosition());
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

                return false;
            }
        });
    }

    private void apagarImagem(View v, Foto foto, int pos) {
        Activity activity = (Activity) v.getContext();
        FotoRepositorio frep = new FotoRepositorio(activity.getApplication());
        frep.deleteFoto(foto);
        subItemList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, subItemList.size());

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
