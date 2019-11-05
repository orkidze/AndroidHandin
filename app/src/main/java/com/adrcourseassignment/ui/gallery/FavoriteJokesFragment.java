package com.adrcourseassignment.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrcourseassignment.R;
import com.adrcourseassignment.dataModels.FavoriteJoke;
import com.adrcourseassignment.services.favorite.FavoriteJokeAdapter;

import java.util.List;

public class FavoriteJokesFragment extends Fragment {

    private FavoriteJokesViewModel favoriteJokesViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter favoriteJokeAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteJokesViewModel =
                ViewModelProviders.of(this).get(FavoriteJokesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorite_jokes, container, false);

        recyclerView = root.findViewById(R.id.rv_favorite
        );
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        favoriteJokeAdapter = new FavoriteJokeAdapter(favoriteJokesViewModel);
        recyclerView.setAdapter(favoriteJokeAdapter);

        favoriteJokesViewModel.getJokes().observe(this, new Observer<List<FavoriteJoke>>() {
            @Override
            public void onChanged(List<FavoriteJoke> favoriteJokes) {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        favoriteJokeAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        return root;
    }

}
