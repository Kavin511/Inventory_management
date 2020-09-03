package com.example.vehiclemanager.ui.equipment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vehiclemanager.DataEntry;
import com.example.vehiclemanager.R;
import com.example.vehiclemanager.graph;
import com.example.vehiclemanager.history;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class equipmentFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_equipment, container, false);
        Fragment entry=new DataEntry();
                Fragment history=new history();
                        Fragment graph =new graph();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) root.findViewById(R.id.EquipmentBottom);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,new DataEntry()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment=null;
                switch (item.getItemId()) {
                    case R.id.bot_entry:
                        selectedFragment=entry;
                        break;
                    case R.id.bot_history:
                        selectedFragment=history;
                        break;
                    case R.id.bot_Graph:
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