package com.adrcourseassignment.services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.adrcourseassignment.R;
import com.adrcourseassignment.ui.home.HomeViewModel;

import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {

    private HomeViewModel homeViewModel;
    private List<MutableLiveData<String>> jokesCache;

    public JokeAdapter(HomeViewModel homeViewModel){
        this.homeViewModel = homeViewModel;
        this.jokesCache = homeViewModel.getJokes().getValue();
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
            homeViewModel.addMoreJokes();
        }
        MutableLiveData<String> mutableString = jokesCache.get(position);
        holder.content.setText(mutableString.getValue());

    }

    @Override
    public int getItemCount() {
        return jokesCache.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView content;

        ViewHolder(View itemView){
            super(itemView);
            content = itemView.findViewById(R.id.joke_content);
        }
    }
}
