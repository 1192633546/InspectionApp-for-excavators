package com.inspectionsheetdemo.Drawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import eu.thirdspaceauto.akka.hacksprint.Adapter.ModelViewAdapter;
import eu.thirdspaceauto.akka.hacksprint.Common;
import eu.thirdspaceauto.akka.hacksprint.MainActivity;
import eu.thirdspaceauto.akka.hacksprint.Models.Excavators;
import eu.thirdspaceauto.akka.hacksprint.R;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class ModelActivity extends AppCompatActivity implements ModelViewAdapter.ItemClickListener {
    private String TAG = ModelActivity.class.getSimpleName();
    Intent mainActivityIntent = null;
    Activity activity;
    private ModelViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button create_model_btn;
    private ArrayList<Excavators> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_model);
        create_model_btn = findViewById(R.id.create_model_btn);
        create_model_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModelActivity.this,CreateModelActivity.class));
            }
        });
        mainActivityIntent = new Intent(ModelActivity.this, MainActivity.class);
		
        
        mRecyclerView = findViewById(R.id.model_recycler);
        mRecyclerView.setHasFixedSize(true);
        
        if(getIntent ().getExtras () != null){
        	Bundle bundle = getIntent ().getExtras ();
        	if(bundle.getString ("type").equals ("saveData")){
        		Excavators excavators = new Excavators ();
        		excavators.name = bundle.getString ("title");
        		excavators.info = bundle.getString ("info");
        		Common.excavators.add (excavators);
			}
		}else {
			Common.excavators = getAllItemList();
		}
		
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
		DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
		mRecyclerView.addItemDecoration(decoration);
        mAdapter = new ModelViewAdapter(Common.excavators,activity);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
    }

    private ArrayList<Excavators> getAllItemList() {
        allItems = new ArrayList<>();
        Excavators excavators = new Excavators();
        excavators.name = "Volvo EC220E";
        excavators.info = "Crawler excavator";
        excavators.image = R.drawable.excavator;
		Excavators excavators2 = new Excavators();
		excavators2.name = "Volvo EC480D";
		excavators2.info = "Crawler excavator";
		excavators2.image = R.drawable.excavator;
	
		allItems.add (excavators);
		allItems.add (excavators2);
        return allItems;
    }

    @Override
    public void itemClick(View view, int position) {
    	Intent modelActivityIntent = new Intent(this, MainActivity.class);
		String model = ((TextView)view.findViewById (R.id.list_title)).getText ().toString ();
		modelActivityIntent.putExtra ("model", model);
    	startActivity(modelActivityIntent);
    }

}
