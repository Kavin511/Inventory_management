package com.example.vehiclemanager;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link filler_graph#newInstance} factory method to
 * create an instance of this fragment.
 */
public class filler_graph extends Fragment {



    public filler_graph() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment filler_graph.
     */
    // TODO: Rename and change types and number of parameters
    public static filler_graph newInstance(String param1, String param2) {
        filler_graph fragment = new filler_graph();
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
        // Inflate the layout for this fragment
         View v= inflater.inflate(R.layout.fragment_filler_graph, container, false);
        BarChart bar_chart=v.findViewById(R.id.chart);
        List<BarEntry> entries = new ArrayList<>();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String userid=firebaseAuth.getCurrentUser().getUid();
        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        progressDialog.show();
        db.collection("users").document(userid).collection("filter_details").orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    progressDialog.cancel();
                    progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    float start=1;
                    for(QueryDocumentSnapshot document:task.getResult())
                    {
                        Float value=Float.parseFloat(document.get("cost").toString().trim());
                        entries.add(new BarEntry(start,value));
                        start++;
                    }
                    if(start>1)
                    {
                        BarDataSet set = new BarDataSet(entries, "BarDataSet");
                        BarData data = new BarData(set);
                        set.setColors(new int[] {Color.rgb(97,182,120), Color.rgb(23,115,155), Color.rgb(33,229,225), Color.rgb(132,189,206), Color.rgb(162,182,250)});
                        data.setBarWidth(0.9f); // set custom bar width
                        bar_chart.setData(data);
                        bar_chart.setScrollX(10);
                        // make the x-axis fit exactly all bars
                        bar_chart.setDescription(null);
                        bar_chart.setDrawBarShadow(false);
                        bar_chart.setDrawValueAboveBar(true);
                        bar_chart.setMaxVisibleValueCount(50);
                        bar_chart.setPinchZoom(false);
                        bar_chart.setDrawGridBackground(false);


                        XAxis xl = bar_chart.getXAxis();
                        xl.setGranularity(1f);
                        xl.setCenterAxisLabels(true);

                        YAxis leftAxis = bar_chart.getAxisLeft();

                        leftAxis.setDrawGridLines(false);
                        leftAxis.setSpaceTop(30f);
                        bar_chart.getAxisRight().setEnabled(false);

//data
//                        bar_chart.getBarData().setBarWidth(barWidth);
                        bar_chart.getXAxis().setAxisMinValue(1f);
//                        bar_chart.setDrawBarShadow(true);
////                        bar_chart.groupBars(groupSpace, barSpace);
//                        bar_chart.groupBars(1f,groupSpace,barSpace);
                        bar_chart.setVisibleXRangeMaximum(5); // allow 5 values to be displayed
                        bar_chart.moveViewToX(1);
                        bar_chart.invalidate();
                    }
                    else
                    {
                        bar_chart.setVisibility(View.INVISIBLE);
                        LinearLayout linearLayout=v.findViewById(R.id.graph);
                        TextView textView=new TextView(getActivity());
                        textView.setGravity(Gravity.CENTER_HORIZONTAL);
                        textView.setText("No Datas Found");
                        linearLayout.addView(textView);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(getContext(),"No Data Found",Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}