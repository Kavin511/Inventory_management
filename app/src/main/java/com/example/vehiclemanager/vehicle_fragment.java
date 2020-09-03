package com.example.vehiclemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vehiclemanager.ui.account.accountFragment;
import com.example.vehiclemanager.ui.account.vehicle_list;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link vehicle_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class vehicle_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public vehicle_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment vehicle_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static vehicle_fragment newInstance(String param1, String param2) {
        vehicle_fragment fragment = new vehicle_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_vehicle_fragment, container, false);
        EditText machine_name,model_num,serial_num,year,reg_num;
        machine_name=root.findViewById(R.id.machine_name);
        model_num=root.findViewById(R.id.model_num);
        serial_num=root.findViewById(R.id.serial_num);
        year=root.findViewById(R.id.year);
        reg_num=root.findViewById(R.id.reg_num);


        return root;
    }
}