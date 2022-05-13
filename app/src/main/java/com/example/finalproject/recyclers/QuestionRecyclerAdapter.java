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
import com.example.finalproject.EditQuestion;
import com.example.finalproject.R;
import com.example.finalproject.domain.Question;

import java.util.List;

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.MyViewHolder> {

    private List<Question> questionsList;
    private Context context;

    public QuestionRecyclerAdapter(List<Question> questionsList, Context context) {
        this.questionsList = questionsList;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionRecyclerAdapter.MyViewHolder holder, int position) {
        String question = questionsList.get(position).getQuestion();
//        String descriptiton = decksList.get(position).getDescription();

        holder.primary.setText(question);
        holder.itemView.setOnClickListener((View view) -> {
            Intent to = new Intent(context, EditQuestion.class);

            to.putExtra("deckId", questionsList.get(position).getDeck());
            to.putExtra("questionId", questionsList.get(position).getId());

            context.startActivity(to);
        });
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView primary;

        public MyViewHolder(final View view) {
            super(view);

            primary = view.findViewById(R.id.txt_primary);
        }
    }

}
