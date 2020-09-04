package com.example.vehiclemanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
        if(container!=null)
            container.removeAllViews();

        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressDialog.show();
        db.collection("users").document(userid).collection("filter_details").orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int v=0;
                if (task.isSuccessful()){
                    progressDialog.cancel();
                    progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    for(QueryDocumentSnapshot document:task.getResult())
                    {
                        String k="\uD835\uDE3F\uD835\uDE56\uD835\uDE69\uD835\uDE5A : "+document.get("date").toString()+"\n\uD835\uDE4F\uD835\uDE5E\uD835\uDE62\uD835\uDE5A : "+document.get("time").toString()+"\n\uD835\uDE48\uD835\uDE64\uD835\uDE59\uD835\uDE5A\uD835\uDE61 \uD835\uDE63\uD835\uDE6A\uD835\uDE62\uD835\uDE57\uD835\uDE5A\uD835\uDE67 : "+document.get("model_num")+"\n\uD835\uDE41\uD835\uDE5E\uD835\uDE61\uD835\uDE69\uD835\uDE5A\uD835\uDE67 1 : "+document.get("filter")+"    \uD835\uDE4B\uD835\uDE56\uD835\uDE67\uD835\uDE69 \uD835\uDE49\uD835\uDE6A\uD835\uDE62\uD835\uDE57\uD835\uDE5A\uD835\uDE67 1 : "+document.get("partnumber")+"\n\uD835\uDE41\uD835\uDE5E\uD835\uDE61\uD835\uDE69\uD835\uDE5A\uD835\uDE67 2 : "+document.get("filter2")+"    \uD835\uDE4B\uD835\uDE56\uD835\uDE67\uD835\uDE69 \uD835\uDE63\uD835\uDE6A\uD835\uDE62\uD835\uDE57\uD835\uDE5A\uD835\uDE67 2 : "+document.get("partnum2")+"\n\uD835\uDE40\uD835\uDE63\uD835\uDE5C\uD835\uDE5E\uD835\uDE63\uD835\uDE5A \uD835\uDE64\uD835\uDE5E\uD835\uDE61 : "+document.get("engine_oil")+"\n\uD835\uDE4E\uD835\uDE69\uD835\uDE56\uD835\uDE67\uD835\uDE69\uD835\uDE5E\uD835\uDE63\uD835\uDE5C \uD835\uDE4D\uD835\uDE5A\uD835\uDE56\uD835\uDE59\uD835\uDE5E\uD835\uDE63\uD835\uDE5C : "+document.get("starting")+"\n\uD835\uDE40\uD835\uDE63\uD835\uDE59\uD835\uDE5E\uD835\uDE63\uD835\uDE5C \uD835\uDE4D\uD835\uDE5A\uD835\uDE56\uD835\uDE59\uD835\uDE5E\uD835\uDE63\uD835\uDE5C : "+document.get("ending")+"\n\uD835\uDE3E\uD835\uDE64\uD835\uDE68\uD835\uDE69 : "+document.get("cost").toString()+"\n\uD835\uDE42\uD835\uDE5A\uD835\uDE56\uD835\uDE67 \uD835\uDE64\uD835\uDE5E\uD835\uDE61 : "+document.get("gearoil")+"\n\uD835\uDE4F\uD835\uDE67\uD835\uDE56\uD835\uDE63\uD835\uDE68\uD835\uDE62\uD835\uDE5E\uD835\uDE68\uD835\uDE68\uD835\uDE5E\uD835\uDE64\uD835\uDE63 : "+document.get("transmission");
                        TextView textView = new TextView(getActivity());
                        int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
                        textView.setPadding(padding,padding,padding,padding);
                        textView.setText(k);
                        String documentid=document.getId();
                        textView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                final    AlertDialog.Builder alertDialogueBuilder=new AlertDialog.Builder(getActivity());

                                alertDialogueBuilder.setTitle("Are you sure to update ? ");

                                alertDialogueBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        final    AlertDialog.Builder updateDialogBuilder=new AlertDialog.Builder(getActivity());
                                        View promt=getLayoutInflater().inflate(R.layout.update_filter_entry,null);
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
                                        EditText hydraulicoil,engineoil,transmissionoil,gearoil,coolantoil,filter,partnumm,estimated_cost,ending_reading,starting_reading,filter2,model_num,part2;
                                        String hydraulic_oil=document.get("hydraulic").toString();
                                        String engine_oil=document.get("engine_oil").toString();
                                        String transmission_oil=document.get("transmission").toString();
                                        String gear_oil=document.get("gearoil").toString();
                                        String coolant_oil=document.get("coolant_oil").toString();
                                        String filter_1=document.get("filter").toString();
                                        String part1=document.get("partnumber").toString();
                                        String start=document.get("starting").toString();
                                        String co=document.get("cost").toString();
                                        String end=document.get("ending").toString();
                                        String partnu=document.get("partnum2").toString();
                                               String model_no=document.get("model_num").toString();
                                        String filt2=document.get("filter2").toString();
                                        filter2=promt.findViewById(R.id.filter2);
                                        model_num=promt.findViewById(R.id.model_num);
                                        part2=promt.findViewById(R.id.partnum2);
                                        filter2.setText(filt2);
                                        model_num.setText(model_no);
                                        part2.setText(partnu);
                                        hydraulicoil=promt.findViewById(R.id.hydraulicoil);
                                        hydraulicoil.setText(hydraulic_oil);
                                        engineoil=promt.findViewById(R.id.engineoil);
                                        engineoil.setText(engine_oil);
                                        transmissionoil=promt.findViewById(R.id.transmissionoil);
                                        transmissionoil.setText(transmission_oil);
                                        gearoil=promt.findViewById(R.id.gearoil);
                                        gearoil.setText(gear_oil);
                                        coolantoil=promt.findViewById(R.id.coolantoil);
                                        coolantoil.setText(coolant_oil);
                                        filter=promt.findViewById(R.id.filter);
                                        filter.setText(filter_1);
                                        partnumm=promt.findViewById(R.id.partnum);
                                        partnumm.setText(part1);
                                        starting_reading=promt.findViewById(R.id.starting_reading);
                                        starting_reading.setText(start);
                                        estimated_cost=promt.findViewById(R.id.estimated_cost);
                                        estimated_cost.setText(co);
                                        ending_reading=promt.findViewById(R.id.ending_reading);
                                        ending_reading.setText(end);

                                        updateDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Map<String,Object> filler=new HashMap<>();
                                                ProgressDialog progressDialog=new ProgressDialog(getActivity());
                                                progressDialog.setContentView(R.layout.progressdialog);
                                                Date currentTime = Calendar.getInstance().getTime();
                                                Calendar calendar = Calendar.getInstance();
                                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                                                String hydraulic=hydraulicoil.getText().toString().trim().length()>0?hydraulicoil.getText().toString().trim():"N/A";
                                                String engine=engineoil.getText().toString().trim().length()>0?engineoil.getText().toString().trim():"N/A";
                                                String transmission=transmissionoil.getText().toString().trim().length()>0?transmissionoil.getText().toString().trim():"N/A";
                                                String gear=gearoil.getText().toString().trim().length()>0?gearoil.getText().toString().trim():"N/A";
                                                String coolant=coolantoil.getText().toString().trim().length()>0?coolantoil.getText().toString().trim():"N/A";
                                                String filt=filter.getText().toString().trim().length()>0?filter.getText().toString().trim():"N/A";
                                                String part=partnumm.getText().toString().trim().length()>0?partnumm.getText().toString().trim():"N/A";
                                                String ending=ending_reading.getText().toString().trim().length()>0?ending_reading.getText().toString().trim():"N/A";
                                                String cost=estimated_cost.getText().toString().trim().length()>0?estimated_cost.getText().toString().trim():"0";
                                                String starting=starting_reading.getText().toString().trim().length()>0?starting_reading.getText().toString().trim():"0";
                                                String mode=model_num.getText().toString().trim();
                                                String par2=part2.getText().toString().trim();
                                                String date=simpleDateFormat.format(calendar.getTime());
                                                String fill=filter2.getText().toString().trim();
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
                                                filler.put("partnum2",par2);
                                                filler.put("model_num",mode);
                                                filler.put("filter2",filt2);
                                                progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                progressDialog.show();
                                                db.collection("users").document(userid).collection("filter_details").document(documentid).update(filler);
                                                getChildFragmentManager().beginTransaction().replace(R.id.fill_hist,new filler_history()).commit();
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
                }
                else {
                    progressDialog.cancel();
                    progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getActivity(),"Problem occurred try after some time",Toast.LENGTH_LONG).show();
                    Log.d("Prob","Errro",task.getException());
                }
            }
        });

        return v;
    }
}