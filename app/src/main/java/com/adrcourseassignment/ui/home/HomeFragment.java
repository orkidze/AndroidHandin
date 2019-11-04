package com.adrcourseassignment.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrcourseassignment.R;
import com.adrcourseassignment.services.JokeAdapter;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myJokeAdapter;
    private Fragment getMe(){
        return this;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        recyclerView = root.findViewById(R.id.rv);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        myJokeAdapter = new JokeAdapter(homeViewModel);
        recyclerView.setAdapter(myJokeAdapter);

        homeViewModel.getJokes().observe(this, new Observer<List<MutableLiveData<String>>>() {
            @Override
            public void onChanged(List<MutableLiveData<String>> strings) {
                for(MutableLiveData<String> mutString : strings){
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


}