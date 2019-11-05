package com.adrcourseassignment.services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.adrcourseassignment.R;
import com.adrcourseassignment.ui.jokes.JokeViewModel;

import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {

    private JokeViewModel jokeViewModel;
    private List<MutableLiveData<String>> jokesCache;
    private JokeAdapter.OnListItemClickListener onClickListener;

    public JokeAdapter(JokeViewModel jokeViewModel, JokeAdapter.OnListItemClickListener onClickListener){
        this.onClickListener = onClickListener;
        this.jokeViewModel = jokeViewModel;
        this.jokesCache = jokeViewModel.getJokes().getValue();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_joke, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position > jokesCache.size() - 2){
            jokeViewModel.addMoreJokes();
        }
        MutableLiveData<String> mutableString = jokesCache.get(position);
        holder.content.setText(mutableString.getValue());

    }

    @Override
    public int getItemCount() {
        return jokesCache.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView content;

        ViewHolder(View itemView){
            super(itemView);
            content = itemView.findViewById(R.id.joke_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onListItemClick(getAdapterPosition());
        }
    }

    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}
