package com.example.vehiclemanager;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class filler_history extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: Rename and change types of parameters

    public filler_history() {
        // Required empty public constructor
    }

//  TODO: Rename and change types and number of parameters
    public static filler_history newInstance(String param1, String param2) {
        filler_history fragment = new filler_history();
        Bundle args = new Bundle();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_filler_history, container, false);
        LinearLayout linearLayout=v.findViewById(R.id.filler_history);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String userid=firebaseAuth.getCurrentUser().getUid();

        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.show();
        db.collection("users").document(userid).collection("filler_details").orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int v=0;
                if (task.isSuccessful()){
                    progressDialog.cancel();
                    progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    for(QueryDocumentSnapshot document:task.getResult())
                    {
                        String k="\uD835\uDE3F\uD835\uDE56\uD835\uDE69\uD835\uDE5A : "+document.get("date").toString()+"\n\uD835\uDE4F\uD835\uDE5E\uD835\uDE62\uD835\uDE5A : "+document.get("time").toString()+"\n\uD835\uDE40\uD835\uDE63\uD835\uDE5C\uD835\uDE5E\uD835\uDE63\uD835\uDE5A \uD835\uDE64\uD835\uDE5E\uD835\uDE61 : "+"\n\uD835\uDE4E\uD835\uDE69\uD835\uDE56\uD835\uDE67\uD835\uDE69\uD835\uDE5E\uD835\uDE63\uD835\uDE5C \uD835\uDE4D\uD835\uDE5A\uD835\uDE56\uD835\uDE59\uD835\uDE5E\uD835\uDE63\uD835\uDE5C : "+document.get("starting")+"\n\uD835\uDE40\uD835\uDE63\uD835\uDE59\uD835\uDE5E\uD835\uDE63\uD835\uDE5C \uD835\uDE4D\uD835\uDE5A\uD835\uDE56\uD835\uDE59\uD835\uDE5E\uD835\uDE63\uD835\uDE5C : "+document.get("ending")+"\n\uD835\uDE3E\uD835\uDE64\uD835\uDE68\uD835\uDE69 : "+document.get("cost").toString()+document.get("engine_oil")+"\n\uD835\uDE41\uD835\uDE5E\uD835\uDE61\uD835\uDE69\uD835\uDE5A\uD835\uDE67 : "+document.get("filter")+"\n\uD835\uDE42\uD835\uDE5A\uD835\uDE56\uD835\uDE67 \uD835\uDE64\uD835\uDE5E\uD835\uDE61 : "+document.get("gearoil")+"\n\uD835\uDE4B\uD835\uDE56\uD835\uDE67\uD835\uDE69 \uD835\uDE49\uD835\uDE6A\uD835\uDE62\uD835\uDE57\uD835\uDE5A\uD835\uDE67 : "+document.get("partnumber")+"\n\uD835\uDE4F\uD835\uDE67\uD835\uDE56\uD835\uDE63\uD835\uDE68\uD835\uDE62\uD835\uDE5E\uD835\uDE68\uD835\uDE68\uD835\uDE5E\uD835\uDE64\uD835\uDE63 : "+document.get("transmission");
                        TextView textView = new TextView(getActivity());
                        int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
                        textView.setPadding(padding,padding,padding,padding);
                        textView.setText(k);
                        textView.setBackground(getResources().getDrawable(R.drawable.text_vehicle));
                        v++;
                        if(v%2==0)
                            textView.setBackground(getResources().getDrawable(R.drawable.oddtext));
                        linearLayout.addView(textView);
                    }
                }
                else {
                    progressDialog.cancel();
                    progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getActivity(),"Problem occurred try after some time",Toast.LENGTH_LONG).show();
                    Log.d("Prob","Errro",task.getException());
                }
            }
        });
//        EditText search=v.findViewById(R.id.search);
//        Button search_button=v.findViewById(R.id.button);
//        search_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new
//            }
//        });
        return v;
    }
}