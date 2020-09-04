package com.example.vehiclemanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link history#newInstance} factory method to
 * create an instance of this fragment.
 */
public class history extends Fragment {


    public history() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment history.
     */
    // TODO: Rename and change types and number of parameters
    public static history newInstance(String param1, String param2) {
        history fragment = new history();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View v=inflater.inflate(R.layout.fragment_history, container, false);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        if(container!=null)
            container.removeAllViews();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String userid=firebaseAuth.getCurrentUser().getUid();
        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        progressDialog.show();
        LinearLayout linearLayout=v.findViewById(R.id.equipment_history);
        db.collection("users").document(userid).collection("Equipment_details").orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    progressDialog.cancel();
                    progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    int v=0;
                    for(QueryDocumentSnapshot document:task.getResult())
                    {
                        String k="\uD835\uDE3F\uD835\uDE56\uD835\uDE69\uD835\uDE5A : "+document.get("date").toString()+"\n\uD835\uDE4F\uD835\uDE5E\uD835\uDE62\uD835\uDE5A : "+document.get("time").toString()+"\n\uD835\uDE51\uD835\uDE5A\uD835\uDE5D\uD835\uDE5E\uD835\uDE58\uD835\uDE61\uD835\uDE5A \uD835\uDE63\uD835\uDE56\uD835\uDE62\uD835\uDE5A : "+document.get("vehiclename")+"\n\uD835\uDE48\uD835\uDE64\uD835\uDE59\uD835\uDE5A\uD835\uDE61 : "+document.get("model")+"\n\uD835\uDE44\uD835\uDE63\uD835\uDE68\uD835\uDE65\uD835\uDE5A\uD835\uDE58\uD835\uDE69\uD835\uDE5E\uD835\uDE64\uD835\uDE63 \uD835\uDE4D\uD835\uDE5A\uD835\uDE65\uD835\uDE64\uD835\uDE67\uD835\uDE69 : "+document.get("inspectionReport")+"\n\uD835\uDE3F\uD835\uDE5A\uD835\uDE68\uD835\uDE58\uD835\uDE67\uD835\uDE5E\uD835\uDE65\uD835\uDE69\uD835\uDE5E\uD835\uDE64\uD835\uDE63 : "+document.get("desc")+"\n\uD835\uDE4B\uD835\uDE56\uD835\uDE67\uD835\uDE69 \uD835\uDE49\uD835\uDE6A\uD835\uDE62\uD835\uDE57\uD835\uDE5A\uD835\uDE67 : "+document.get("partnum")+"\n\uD835\uDE4C\uD835\uDE6A\uD835\uDE56\uD835\uDE63\uD835\uDE69\uD835\uDE5E\uD835\uDE69\uD835\uDE6E : "+document.get("quantity")+"\n\uD835\uDE3E\uD835\uDE64\uD835\uDE68\uD835\uDE69 : "+document.get("cost")+"\n\uD835\uDE47\uD835\uDE64\uD835\uDE58\uD835\uDE56\uD835\uDE69\uD835\uDE5E\uD835\uDE64\uD835\uDE63 : "+document.get("location")+"\n\uD835\uDE4D\uD835\uDE5A\uD835\uDE62\uD835\uDE56\uD835\uDE67\uD835\uDE60\uD835\uDE68 : "+document.get("remark")+"\n\uD835\uDE3C\uD835\uDE58\uD835\uDE69\uD835\uDE5E\uD835\uDE64\uD835\uDE63 : "+document.get("action");
                        TextView textView = new TextView(getActivity());

                        int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
                        textView.setPadding(padding,padding,padding,padding);
                        textView.setText(k);
                        String documentid=document.getId();
                        textView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                final    AlertDialog.Builder alertDialogueBuilder=new AlertDialog.Builder(getActivity());

                                alertDialogueBuilder.setTitle("Are you sure to update ?");

                                alertDialogueBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        final    AlertDialog.Builder updateDialogBuilder=new AlertDialog.Builder(getActivity());
                                        View promt=getLayoutInflater().inflate(R.layout.update_equipment_entry,null);
                                        updateDialogBuilder.setView(promt);
                                        updateDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        final AlertDialog updateDialog=updateDialogBuilder.create();
                                        updateDialog.show();
                                        EditText hydraulicoil,engineoil,transmissionoil,gearoil,coolantoil,filter,partnumm,estimated_cost,ending_reading,starting_reading;
                                        EditText vehiclename,model,inspectionReport,desc,partnum,quantity,cost,action,location,remark;
                                        vehiclename=promt.findViewById(R.id.vehiclename);
                                        model=promt.findViewById(R.id.model);
                                        inspectionReport=promt.findViewById(R.id.inspectionReport);
                                        desc=promt.findViewById(R.id.desc);
                                        partnum=promt.findViewById(R.id.partnum);
                                        quantity=promt.findViewById(R.id.quantity);
                                        cost=promt.findViewById(R.id.cost);
                                        location=promt.findViewById(R.id.location);
                                        remark=promt.findViewById(R.id.remark);
                                        action=promt.findViewById(R.id.action);
                                        vehiclename.setText(document.get("vehiclename").toString());
                                                model.setText(document.get("model").toString());
                                        inspectionReport.setText(document.get("inspectionReport").toString());
                                                desc.setText(document.get("desc").toString());
                                        partnum.setText(document.get("partnum").toString());
                                                quantity.setText(document.get("quantity").toString());
                                        cost.setText(document.get("cost").toString());
                                                location.setText(document.get("location").toString());
                                        remark.setText(document.get("remark").toString());
                                                action.setText(document.get("action").toString());

                                        updateDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String S_vehiclename = vehiclename.getText().toString().trim().length()>0?(vehiclename.getText().toString()).trim():"N/A";
                                                String S_inspectionReport= inspectionReport.getText().toString().trim().length()>0?inspectionReport.getText().toString().trim():"N/A";
                                                String S_desc         =desc.getText().toString().trim().length()>0?desc.getText().toString().trim() :"N/A";
                                                String S_partnum= partnum.getText().toString().trim().length()>0?partnum.getText().toString().trim() :"N/A";
                                                String S_quantity=         quantity.getText().toString().trim().length()>0?quantity.getText().toString().trim():"N/A";
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
                                                progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                progressDialog.show();
                                                db.collection("users").document(userid).collection("Equipment_details").document(documentid).update(equipment);
                                                getChildFragmentManager().beginTransaction().replace(R.id.equip_hist,new history()).commit();
                                                progressDialog.dismiss();
                                                updateDialog.dismiss();
                                            }
                                        });
                                    }
                                });
                                alertDialogueBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                final AlertDialog alertDialog=alertDialogueBuilder.create();
                                alertDialog.show();
                                return true;
                            }
                        });
                        textView.setBackground(getResources().getDrawable(R.drawable.text_vehicle));
                        v++;
                        if(v%2==0)
                            textView.setBackground(getResources().getDrawable(R.drawable.oddtext));
                        linearLayout.addView(textView);
                    }
                    if(v==0)
                    {
                        TextView textView = new TextView(getActivity());
                        int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
                        textView.setPadding(padding,padding,padding,padding);
                        textView.setGravity(Gravity.CENTER_HORIZONTAL);
                        textView.setText("No Datas Found");
                        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                        linearLayout.addView(textView);
                    }
                }
                else {
                    progressDialog.cancel();
                    progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getActivity(),"Problem occured try after some time",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(getContext(),"Add Equipment details",Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}