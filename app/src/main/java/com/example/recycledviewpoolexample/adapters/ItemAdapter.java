package com.example.recycledviewpoolexample.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycledviewpoolexample.Constantes;
import com.example.recycledviewpoolexample.Item;
import com.example.recycledviewpoolexample.R;
import com.example.recycledviewpoolexample.activitys.EditarDisciplinaActivity;
import com.example.recycledviewpoolexample.activitys.TakePhotoActivity;
import com.example.recycledviewpoolexample.dominio.entidades.Diciplina;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Item> itemList;
    private Context mContext;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.itemList = itemList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int i) {
        final Item item = itemList.get(i);
        final Diciplina diciplina = item.getItemDic();

        itemViewHolder.tvItemTitle.setText(diciplina.diciplina);


        LinearLayoutManager layoutManager = new LinearLayoutManager(
                itemViewHolder.rvSubItem.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(item.getSubItemList().size());

        SubItemAdapter subItemAdapter = new SubItemAdapter(item.getSubItemList());

        itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
        itemViewHolder.rvSubItem.setAdapter(subItemAdapter);
        itemViewHolder.rvSubItem.setRecycledViewPool(viewPool);

        itemViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Constantes.TAG, "CLICKK :: " + itemViewHolder.getAdapterPosition());
                Intent i = new Intent(itemViewHolder.imageButton.getContext(),TakePhotoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("caminho",diciplina.caminho);
                i.putExtras(bundle);
                itemViewHolder.imageButton.getContext().startActivity(i);

            }
        });

        itemViewHolder.editDic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemViewHolder.editDic.getContext(), EditarDisciplinaActivity.class);
                intent.putExtra("disciplina",diciplina);

                itemViewHolder.editDic.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemTitle;
        private RecyclerView rvSubItem;
        private ImageButton imageButton;
        private ImageButton editDic;


        ItemViewHolder(View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.image_button_item);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            rvSubItem = itemView.findViewById(R.id.rv_sub_item);
            editDic = itemView.findViewById(R.id.image_edit_item);
        }
    }
}
