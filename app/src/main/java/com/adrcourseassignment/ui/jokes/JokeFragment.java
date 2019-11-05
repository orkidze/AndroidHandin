package com.adrcourseassignment.ui.jokes;

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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrcourseassignment.R;
import com.adrcourseassignment.services.jokes.JokeAdapter;

import java.util.List;

public class JokeFragment extends Fragment implements JokeAdapter.OnListItemClickListener{

    private JokeViewModel jokeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myJokeAdapter;
    private Fragment getMe(){
        return this;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        jokeViewModel =
                ViewModelProviders.of(this).get(JokeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_jokes, container, false);

        recyclerView = root.findViewById(R.id.rv);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        myJokeAdapter = new JokeAdapter(jokeViewModel, this);
        recyclerView.setAdapter(myJokeAdapter);

        jokeViewModel.getJokes().observe(this, new Observer<List<MutableLiveData<String>>>() {
            @Override
            public void onChanged(List<MutableLiveData<String>> strings) {
                for(MutableLiveData<String> mutString : strings){
                    if(!mutString.hasActiveObservers())
                    mutString.observe(getMe(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    myJokeAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        myJokeAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        return root;
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        final String content = jokeViewModel.getJokes().getValue().get(clickedItemIndex).getValue();
        AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();
        alertDialog.setTitle("Actions");
        alertDialog.setMessage(content);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Add to favorite",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        jokeViewModel.addJokeToFavorite(content);
                        Toast.makeText(getMe().getContext(),"Added to favorite",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Share",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        handleShare(content);
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

    private void handleShare(String content){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "A Joke");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(sharingIntent, "Share a joke"));
    }
}