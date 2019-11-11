package com.adrcourseassignment.services.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.adrcourseassignment.R;
import com.adrcourseassignment.dataModels.FavoriteJoke;
import com.adrcourseassignment.ui.gallery.FavoriteJokesViewModel;

import java.util.List;

public class FavoriteJokeAdapter extends RecyclerView.Adapter<FavoriteJokeAdapter.ViewHolder> {

    private FavoriteJokesViewModel viewModel;
    private LiveData<List<FavoriteJoke>> favoriteJokesCache;
    private OnListItemClickListener listener;

    public FavoriteJokeAdapter(FavoriteJokesViewModel viewModel, OnListItemClickListener listener){
        this.viewModel = viewModel;
        this.favoriteJokesCache = viewModel.getJokes();
        this.listener = listener;
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
        String jokeValue = favoriteJokesCache.getValue().get(position).getContent();
        holder.content.setText(jokeValue);
    }

    @Override
    public int getItemCount() {
        List<FavoriteJoke> list = favoriteJokesCache.getValue();
        if(list == null)
            return 0;
        else return list.size();
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
            FavoriteJoke joke = favoriteJokesCache.getValue().get(getAdapterPosition());
            listener.onListItemClick(joke);
        }
    }

    public interface OnListItemClickListener {
        void onListItemClick(FavoriteJoke joke);
    }
}
