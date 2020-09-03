package com.example.vehiclemanager.ui.account;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehiclemanager.FillerDataEntry;
import com.example.vehiclemanager.R;
import com.example.vehiclemanager.vehicle_fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class accountFragment extends Fragment {
    private SlideshowViewModel slideshowViewModel;
    FirebaseAuth firebaseAuth;
    TextView textView,address,phone,user_name,mailid;
    FirebaseFirestore firebaseFirestore;
Button add;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String userid=firebaseAuth.getCurrentUser().getUid();
        Button add=root.findViewById(R.id.add_vehicle);
//        textView=root.findViewById(R.id.textview);

        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        LinearLayout linearLayout=root.findViewById(R.id.vehicles);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View promt=getLayoutInflater().inflate(R.layout.fragment_vehicle_fragment,null);
             final    AlertDialog.Builder alertDialogueBuilder=new AlertDialog.Builder(getActivity());
                alertDialogueBuilder.setView(promt);
                Map<String,Object> vehicles=new HashMap<>();
                alertDialogueBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog alertDialog=alertDialogueBuilder.create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      final   EditText machine_name,model_num,serial_num,year,reg_num;
                        machine_name=promt.findViewById(R.id.machine_name);
                        model_num=promt.findViewById(R.id.model_num);
                        serial_num=promt.findViewById(R.id.serial_num);
                        year=promt.findViewById(R.id.year);
                        reg_num=promt.findViewById(R.id.reg_num);
                        Boolean flag=true;
                        String s_machine_name=machine_name.getText().toString().trim().length()>0?machine_name.getText().toString().trim():"";
                        String s_model_num=model_num.getText().toString().trim().length()>0?model_num.getText().toString().trim():"";

                        String s_serial_num=serial_num.getText().toString().trim().length()>0?serial_num.getText().toString().trim():"";

                        String s_year=year.getText().toString().trim().length()>0?year.getText().toString().trim():"";

                        String s_reg_num=reg_num.getText().toString().trim().length()>0?reg_num.getText().toString().trim():"";
                         int  count=0;
                            if(s_machine_name.length()<=0)
                            {
                                machine_name.setError("Please fill out the field !");
                                count++;
                            }
                            if(s_model_num.length()<=0)
                            {
                                model_num.setError("Please fill out the field !");
                                count++;
                            }
                            if(s_year.length()<=0)
                            {
                                year.setError("Please fill out the field !");
                                count++;

                            }
                            if(s_serial_num.length()<=0)
                            {
                                serial_num.setError("Please fill out the field !");
                                count++;
                            }
                            if(s_reg_num.length()<=0)
                            {
                                reg_num.setError("Please fill out the field !");
                                count++;
                            }

                        if(count==0)
                        {
                            vehicles.put("machine_name",s_machine_name);
                            vehicles.put("model_num",s_model_num);
                            vehicles.put("serial_num",s_serial_num);
                            vehicles.put("year",s_year);
                            vehicles.put("reg_num",s_reg_num);
                            progressDialog.show();

                            db.collection("users").document(userid).collection("vehicles").add(vehicles).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getActivity(),"Vehicle Added Successfully",Toast.LENGTH_LONG).show();
                                    progressDialog.cancel();
                                    progressDialog.dismiss();
                                    progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    alertDialog.dismiss();
                                    linearLayout.removeAllViews();
                                    db.collection("users").document(userid).collection("vehicles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                int v=0;
                                                for(QueryDocumentSnapshot document:task.getResult())
                                                {
                                                    String k="Registration number :    "+document.getData().get("reg_num").toString()+"\nMachine name :     "+document.getData().get("machine_name").toString()+"\nModel number:    "+document.getData().get("model_num").toString();
                                                    Log.d("Fetched",k);
                                                    TextView textView = new TextView(getActivity());
                                                    int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
                                                    textView.setPadding(padding,padding,padding,padding);
                                                    textView.setText(k);
//                        textView.setTextColor(Color.BLACK);
                                                    textView.setBackground(getResources().getDrawable(R.drawable.text_vehicle));
                                                    linearLayout.addView(textView);
                                                    v++;
                                                }
                                                if(v==0)
                                                {
                                                    TextView textView = new TextView(getActivity());
                                                    textView.setGravity(Gravity.CENTER);
                                                    int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
                                                    textView.setPadding(padding,padding,padding,padding);
                                                    textView.setText("No Equipments Added ");
                                                    textView.setBackground(getResources().getDrawable(R.drawable.text_vehicle));
                                                    linearLayout.addView(textView);
                                                }

                                            }
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(),"Unable to add vehicle try after some time !",Toast.LENGTH_LONG).show();
                                    Log.w(TAG,"Error adding document", e);
                                    progressDialog.cancel();
                                    progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            });
                        }

                    }
                });
            }

        });
        ArrayList<String> arrayList=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        String uid=firebaseAuth.getUid();

        final int[] i = {0};
//todo ProgressDialog progressDialog1=new ProgressDialog();
        progressDialog.show();
        db.collection("users").document(userid).collection("vehicles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    int v=0;
                    for(QueryDocumentSnapshot document:task.getResult())
                    {
                         String k="Registration number :    "+document.getData().get("reg_num").toString()+"\nMachine name :     "+document.getData().get("machine_name").toString()+"\nModel number:    "+document.getData().get("model_num").toString();
                        Log.d("Fetched",k);
                        TextView textView = new TextView(getActivity());
                        int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
                        textView.setPadding(padding,padding,padding,padding);
                        textView.setText(k);
//                        textView.setTextColor(Color.BLACK);
                        textView.setBackground(getResources().getDrawable(R.drawable.text_vehicle));
                        linearLayout.addView(textView);
                        progressDialog.cancel();
                        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        v++;
                    }
                    if(v==0)
                    {
                        TextView textView = new TextView(getActivity());
                        textView.setGravity(Gravity.CENTER);
                        int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
                        textView.setPadding(padding,padding,padding,padding);
                        textView.setText("No Equipments Added ");
                        textView.setBackground(getResources().getDrawable(R.drawable.text_vehicle));
                        linearLayout.addView(textView);
                        progressDialog.cancel();
                        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(getContext(),"Error occured",Toast.LENGTH_LONG).show();
            }
        });
       db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if(task.isSuccessful())
               {
                   for (QueryDocumentSnapshot document : task.getResult()) {
                        phone=root.findViewById(R.id.phone);
                        mailid=root.findViewById(R.id.mailid);
                       user_name=root.findViewById(R.id.user_name);
                       address=root.findViewById(R.id.address);
                       String s_address=document.get("address").toString();
                       String s_username=document.get("user_name").toString();
                       String s_mailid=document.get("mailid").toString();
                       String s_phone=document.get("phone").toString();
                       user_name.setText(s_username);
                       address.setText(s_address);
                       phone.setText(s_phone);
                       mailid.setText(s_mailid);
                   }
               }
           }
       });

        return root;
    }
}