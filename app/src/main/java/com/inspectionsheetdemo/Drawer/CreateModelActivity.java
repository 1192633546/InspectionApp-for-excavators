package com.inspectionsheetdemo.Drawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eu.thirdspaceauto.akka.hacksprint.R;

public class CreateModelActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity;
    EditText title,info;
    EditText contry;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.create_new_model);
        title = (EditText) findViewById(R.id.title);
        info = (EditText) findViewById(R.id.info);
        contry = (EditText) findViewById(R.id.country);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (title.getText().toString().equalsIgnoreCase("") && info.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                } else {
                    String title_text = title.getText().toString();
                    String info_text = info.getText().toString();
                    
                    Toast.makeText(getApplicationContext(),"Model saved",Toast.LENGTH_SHORT).show();
                    
                    Intent saveModelIntent = new Intent (CreateModelActivity.this, ModelActivity.class);
                    saveModelIntent.putExtra ("title", title_text);
                    saveModelIntent.putExtra ("info", info_text);
                    saveModelIntent.putExtra ("type", "saveData");
                    startActivity(saveModelIntent);
                }
                break;
        }
    }
}