package com.inspectionsheetdemo.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import eu.thirdspaceauto.akka.hacksprint.Drawer.Preview;
import eu.thirdspaceauto.akka.hacksprint.R;


public class SprocketFragment extends Fragment implements View.OnClickListener {
    private String TAG = SprocketFragment.class.getSimpleName();
    private TextView std_max_value,std_min_value,left_sprocket_btn,right_sprocket_btn;
    private ImageView right_sprocket_image, left_sprocket_image;
    private View rootView;
    private int REQUEST_CODE=110;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sprocket, container, false);
//        std_max_value = (TextView) rootView.findViewById(R.id.std_max_value);
//        std_min_value = (TextView) rootView.findViewById(R.id.std_min_value);
        left_sprocket_btn= (Button) rootView.findViewById(R.id.left_sprocket_btn);
        right_sprocket_btn= (Button) rootView.findViewById(R.id.right_sprocket_btn);
        left_sprocket_image = (ImageView) rootView.findViewById(R.id.left_sprocket_image);
        right_sprocket_image= (ImageView) rootView.findViewById(R.id.right_sprocket_image);

        right_sprocket_btn.setOnClickListener(this);
        left_sprocket_btn.setOnClickListener(this);
        return rootView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_sprocket_btn:
                Intent intent_link = new Intent(getActivity(),Preview.class);
                intent_link.putExtra("component","left_sprocket");
                intent_link.putExtra("request_code",REQUEST_CODE);
                startActivityForResult(intent_link,REQUEST_CODE);
                break;
            case R.id.right_sprocket_btn:
                Intent intent_right = new Intent(getActivity(),Preview.class);
                intent_right.putExtra("component","right_sprocket");
                intent_right.putExtra("request_code",REQUEST_CODE);
                startActivityForResult(intent_right ,REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"resultcode TrackLink= "+resultCode + " data= "+data);
        if(resultCode==REQUEST_CODE){
            String path = data.getStringExtra("path");
            String component = data.getStringExtra("component");
            showImageFromStorage(path,component);
        }
    }

    private void showImageFromStorage(String path,String component) {
        File imgFile = new  File(path);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            if(component.equalsIgnoreCase("left_sprocket")) {
                left_sprocket_image.setImageBitmap(myBitmap);
            }else if(component.equalsIgnoreCase("right_sprocket")){
                right_sprocket_image.setImageBitmap(myBitmap);
            }
        }
    }
}
