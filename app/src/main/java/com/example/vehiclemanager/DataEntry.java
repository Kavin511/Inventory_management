package com.example.vehiclemanager;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataEntry extends Fragment {
    EditText vehiclename,model,inspectionReport,desc,partnum,quantity,cost,action,location,remark;
    Button Add;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_data_entry, container, false);
        vehiclename=v.findViewById(R.id.vehiclename);
        model=v.findViewById(R.id.model);
        inspectionReport=v.findViewById(R.id.inspectionReport);
        desc=v.findViewById(R.id.desc);
        partnum=v.findViewById(R.id.partnum);
        quantity=v.findViewById(R.id.quantity);
        cost=v.findViewById(R.id.cost);
        location=v.findViewById(R.id.location);
        remark=v.findViewById(R.id.remark);
        action=v.findViewById(R.id.action);
        Add=v.findViewById(R.id.Add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String S_vehiclename = vehiclename.getText().toString().trim().length()>0?(vehiclename.getText().toString()).trim():"N/A";
               String S_inspectionReport= inspectionReport.getText().toString().trim().length()>0?inspectionReport.getText().toString().trim():"N/A";
               String S_desc         =desc.getText().toString().trim().length()>0?desc.getText().toString().trim() :"N/A";
               String S_partnum= partnum.getText().toString().trim().length()>0?partnum.getText().toString().trim() :"N/A";
               String S_quantity=         quantity.getText().toString().trim().length()>0?quantity.getText().toString().trim()+"    litres":"N/A";
               String S_cost =cost.getText().toString().trim().length()>0?cost.getText().toString().trim():"0";
               String S_location=         location.getText().toString().trim().length()>0?location.getText().toString().trim():"N/A";
               String S_remark =remark.getText().toString().trim().length()>0?remark.getText().toString():"N/A";
               String S_model =  model.getText().toString().trim().length()>0?model.getText().toString().trim():"N/A";
                String S_action =       action.getText().toString().trim().length()>0? action.getText().toString():"N/A";
                Map<String,Object> equipment=new HashMap<>();
                ProgressDialog progressDialog=new ProgressDialog(getActivity());
                progressDialog.setContentView(R.layout.progressdialog);
                progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Date currentTime = Calendar.getInstance().getTime();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                equipment.put("vehiclename",S_vehiclename);
                equipment.put("model",S_model);
                equipment.put("inspectionReport",S_inspectionReport);
                equipment.put("desc",S_desc);
                equipment.put("partnum",S_partnum);
                equipment.put("quantity",S_quantity);
                equipment.put("cost",S_cost);
                equipment.put("location",S_location);
                equipment.put("remark",S_remark);
                equipment.put("action",S_action);
                String date=simpleDateFormat.format(calendar.getTime());
                String time=currentTime.toString().trim();
                equipment.put("date",date);
                equipment.put("time",time);
                progressDialog.show();
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                String userid=firebaseAuth.getCurrentUser().getUid();
                db.collection("users").document(userid).collection("Equipment_details").add(equipment).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressDialog.dismiss();
                        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(getActivity(),"Equipment Data added sucessfully !",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Error Occured!",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                });

            }
        });
        return v;
    }
}