package com.inspectionsheetdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;


public class FinalActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = FinalActivity.class.getSimpleName();
    private Context context;
    private Activity activity;
    private ImageView excel;
    private Toolbar toolbar;
    private Button new_inspection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        init();
        try {
            saveExcelToCache();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void saveExcelToCache() throws FileNotFoundException {
        InputStream in = getResources().openRawResource(R.raw.uc_inspection_sheet_volvo_ec220e);

        File pathOnSd= new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/volvo","uc_inspection_sheet_volvo_ec220e.xls");
        FileOutputStream out = new FileOutputStream(pathOnSd);
        byte[] buff = new byte[1024];
        int read=0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        context = this;
        activity = this;
        excel= (ImageView) findViewById(R.id.excel);
        excel.setOnClickListener (this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_finish);
        new_inspection = (Button) findViewById(R.id.new_inspection);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(context,R.drawable.ic_arrow_back_white_24dp));
        toolbar.setTitle("Inspection Summary");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"clicked");
                gotoHome();
            }
        });
        new_inspection.setOnClickListener(this);
    }
    
    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.excel:
				if(Build.VERSION.SDK_INT>=24){
					try{
						Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
						m.invoke(null);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
                File sdCard = Environment.getExternalStorageDirectory();
                File myDir= new File(sdCard.getAbsolutePath() + "/volvo/","UC_Inspection_sheet_Volvo_EC220E.pdf");
                Intent intentXlsx = new Intent(Intent.ACTION_VIEW);
                intentXlsx.setDataAndType(Uri.fromFile (myDir), "application/pdf");
                intentXlsx.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentXlsx.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION );
                startActivity(Intent.createChooser(intentXlsx, "Open pdf file"));
                Log.e (TAG, Uri.parse (myDir.toString ()).toString ());
                break;
            case R.id.new_inspection:
                gotoHome();
                break;
        }
    }

    private void gotoHome() {
        Intent newIntent = new Intent(FinalActivity.this,MainActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
        FinalActivity.this.finish();
    }


}
