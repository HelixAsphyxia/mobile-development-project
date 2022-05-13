package com.example.finalproject.recyclers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.EditDeck;
import com.example.finalproject.R;
import com.example.finalproject.domain.Deck;

import java.util.List;

public class DeckRecyclerAdapter extends RecyclerView.Adapter<DeckRecyclerAdapter.MyViewHolder> {

    private List<Deck> decksList;
    private Context context;

    private Class target;

    public DeckRecyclerAdapter(List<Deck> decksList, Context context, Class target) {
        this.decksList = decksList;
        this.context = context;
        this.target = target;
    }

    @NonNull
    @Override
    public DeckRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckRecyclerAdapter.MyViewHolder holder, int position) {
        String name = decksList.get(position).getName();
//        String descriptiton = decksList.get(position).getDescription();

        holder.primary.setText(name);
        holder.itemView.setOnClickListener((View view) -> {
            Intent to = new Intent(context, target);

            to.putExtra("deckId", decksList.get(position).getId());

            context.startActivity(to);
        });
    }

    @Override
    public int getItemCount() {
        return decksList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView primary;

        public MyViewHolder(final View view) {
            super(view);

            primary = view.findViewById(R.id.txt_primary);
        }
    }

}
