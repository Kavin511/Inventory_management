package com.example.vehiclemanager;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.admin.v1beta1.Progress;
import com.google.firestore.v1beta1.WriteResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import java.util.Calendar;

public class FillerDataEntry extends Fragment {
    EditText hydraulicoil,engineoil,transmissionoil,gearoil,coolantoil,filter,partnumm,estimated_cost,ending_reading,starting_reading,model_num,filter2,partnum;
    Button add_filler;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_filler_data_entry, container, false);
        hydraulicoil=v.findViewById(R.id.hydraulicoil);
        engineoil=v.findViewById(R.id.engineoil);
        transmissionoil=v.findViewById(R.id.transmissionoil);
        gearoil=v.findViewById(R.id.gearoil);
        coolantoil=v.findViewById(R.id.coolantoil);
        filter=v.findViewById(R.id.filter);
        partnumm=v.findViewById(R.id.partnum);
        add_filler=v.findViewById(R.id.add_filler);
        starting_reading=v.findViewById(R.id.starting_reading);
        estimated_cost=v.findViewById(R.id.estimated_cost);
        ending_reading=v.findViewById(R.id.ending_reading);
        model_num=v.findViewById(R.id.model_num);
        filter2=v.findViewById(R.id.filter2);
        partnum=v.findViewById(R.id.partnum2);
        Map<String,Object> filler=new HashMap<>();
        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setContentView(R.layout.progressdialog);
        Date currentTime = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        add_filler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hydraulic=hydraulicoil.getText().toString().trim().length()>0?hydraulicoil.getText().toString().trim():"N/A";
                String engine=engineoil.getText().toString().trim().length()>0?engineoil.getText().toString().trim():"N/A";
                String transmission=transmissionoil.getText().toString().trim().length()>0?transmissionoil.getText().toString().trim():"N/A";
                String gear=gearoil.getText().toString().trim().length()>0?gearoil.getText().toString().trim():"N/A";
                String coolant=coolantoil.getText().toString().trim().length()>0?coolantoil.getText().toString().trim():"N/A";
                String filt=filter.getText().toString().trim().length()>0?filter.getText().toString().trim():"N/A";
                String part=partnumm.getText().toString().trim().length()>0?partnumm.getText().toString().trim():"N/A";
//                String add=add_filler.getText().toString().trim().length()>0?add_filler.getText().toString().trim():"N/A";
                String ending=ending_reading.getText().toString().trim().length()>0?ending_reading.getText().toString().trim():"N/A";
                String cost=estimated_cost.getText().toString().trim().length()>0?estimated_cost.getText().toString().trim():"0";
                String starting=starting_reading.getText().toString().trim().length()>0?starting_reading.getText().toString().trim():"0";
                String partnum2=partnum.getText().toString().trim();
                String model=model_num.getText().toString().trim();
                String filt2=filter2.getText().toString().trim();
                String date=simpleDateFormat.format(calendar.getTime());
                String time=currentTime.toString().trim();
                filler.put("hydraulic",hydraulic);
                filler.put("engine_oil",engine);
                filler.put("transmission",transmission);
                filler.put("gearoil",gear);
                filler.put("coolant_oil",coolant);
                filler.put("filter",filt);
                filler.put("partnumber",part);
                filler.put("date",date);
                filler.put("time",time);
                filler.put("cost",cost);
                filler.put("starting",starting);
                filler.put("ending",ending);
                filler.put("partnum2",partnum2);
                filler.put("model_num",model);
                filler.put("filter2",filt2);
//                timestamp:firebase.firestore.FieldValue.serverTimestamp()
                progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressDialog.show();
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                String userid=firebaseAuth.getCurrentUser().getUid();
                db.collection("users").document(userid).collection("filter_details").add(filler).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(),"Added Successfully",Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(),"Data added sucessfully !",Toast.LENGTH_LONG).show();
                        progressDialog.cancel();
                        progressDialog.dismiss();
                        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Unable to add try after some time !",Toast.LENGTH_LONG).show();
                        Log.w(TAG,"Error adding document", e);
                        progressDialog.cancel();
                        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                });
            }
        });
        return v;

    }
}