package com.adrcourseassignment.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class FavoriteJokesFragment extends Fragment implements FavoriteJokeAdapter.OnListItemClickListener {

    private FavoriteJokesViewModel favoriteJokesViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter favoriteJokeAdapter;

    private FavoriteJokesFragment getMe(){
        return this;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteJokesViewModel =
                ViewModelProviders.of(this).get(FavoriteJokesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorite_jokes, container, false);

        recyclerView = root.findViewById(R.id.rv_favorite
        );
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        favoriteJokeAdapter = new FavoriteJokeAdapter(favoriteJokesViewModel,this);
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

    @Override
    public void onListItemClick(FavoriteJoke joke) {
        ShowDialogBoxForJoke(joke);
    }

    private void ShowDialogBoxForJoke(final FavoriteJoke joke){
        AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();
        alertDialog.setTitle("Actions");
        alertDialog.setMessage(joke.getContent());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Not funny anymore (Remove)",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        handleRemove(joke.getId());
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Share",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        handleShare(joke.getContent());
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void handleRemove(int id){
        favoriteJokesViewModel.removeJoke(id);
        Toast.makeText(getMe().getContext(),"Removed",Toast.LENGTH_SHORT).show();
    }

    private void handleShare(String content){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "A Joke");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(sharingIntent, "Share a joke"));
    }
}
