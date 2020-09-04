package com.example.vehiclemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import net.skoumal.fragmentback.BackFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link forgot_password#newInstance} factory method to
 * create an instance of this fragment.
 */
public class forgot_password extends Fragment implements BackFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public boolean onBackPressed() {

        // -- your code --

        // return true if you want to consume back-pressed event
        getChildFragmentManager().beginTransaction().replace(R.id.for_g,new Login()).commit();
        return true;
    }

    public int getBackPriority() {
        // use apropriate priority here
        return NORMAL_BACK_PRIORITY;
    }
    public forgot_password() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment forgot_password.
     */
    // TODO: Rename and change types and number of parameters
    public static forgot_password newInstance(String param1, String param2) {
        forgot_password fragment = new forgot_password();
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
        View v=inflater.inflate(R.layout.fragment_forgot_password, container, false);
        if(container!=null)
            container.removeAllViews();
        EditText mail;
        Button send;
        mail=v.findViewById(R.id.email_address);
        send=v.findViewById(R.id.forgot_password);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(mail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(),"Password reset mail sent",Toast.LENGTH_LONG).show();
                                    getChildFragmentManager().beginTransaction().replace(R.id.for_g,new Login()).commit();
                                }
                            }
                        });
            }
        });

        return v;
    }


}