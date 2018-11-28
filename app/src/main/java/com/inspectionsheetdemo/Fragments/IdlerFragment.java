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


public class IdlerFragment extends Fragment implements View.OnClickListener {
    private String TAG = IdlerFragment.class.getSimpleName();
    private TextView std_max_value,std_min_value,right_height_btn,left_height_btn;
    private ImageView right_height_image, left_height_image;
    private View rootView;
    private int REQUEST_CODE=109;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_idler, container, false);
//        std_max_value = (TextView) rootView.findViewById(R.id.std_max_value);
//        std_min_value = (TextView) rootView.findViewById(R.id.std_min_value);
        left_height_btn= (Button) rootView.findViewById(R.id.left_height_btn);
        right_height_btn= (Button) rootView.findViewById(R.id.right_height_btn);
        left_height_image = (ImageView) rootView.findViewById(R.id.left_height_image);
        right_height_image= (ImageView) rootView.findViewById(R.id.right_height_image);

        right_height_btn.setOnClickListener(this);
        left_height_btn.setOnClickListener(this);
        return rootView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_height_btn:
                Intent intent_link = new Intent(getActivity(),Preview.class);
                intent_link.putExtra("component","left_height");
                intent_link.putExtra("request_code",REQUEST_CODE);
                startActivityForResult(intent_link,REQUEST_CODE);
                break;
            case R.id.right_height_btn:
                Intent intent_right = new Intent(getActivity(),Preview.class);
                intent_right.putExtra("component","right_height");
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
            if(component.equalsIgnoreCase("left_height")) {
                left_height_image.setImageBitmap(myBitmap);
            }else if(component.equalsIgnoreCase("right_height")){
                right_height_image.setImageBitmap(myBitmap);
            }
        }
    }
}
