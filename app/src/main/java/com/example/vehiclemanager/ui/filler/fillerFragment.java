package com.example.vehiclemanager.ui.filler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vehiclemanager.FillerDataEntry;
import com.example.vehiclemanager.R;
import com.example.vehiclemanager.filler_graph;
import com.example.vehiclemanager.filler_history;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class fillerFragment extends Fragment {
    private HomeViewModel homeViewModel;
    final Fragment fill=new FillerDataEntry();
    final Fragment graph=new filler_graph();
    final Fragment history=new filler_history();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_filler, container, false);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) root.findViewById(R.id.fillerbottom);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,new FillerDataEntry()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment=null;
                switch (item.getItemId()) {
                    case R.id.filler_entry:
                        selectedFragment=fill;
                        break;
                    case R.id.history:
                        selectedFragment=history;
                        break;
                    case R.id.Graph:
                        selectedFragment=graph;
                        break;
                }
                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });
        return root;
    }
}