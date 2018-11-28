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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import eu.thirdspaceauto.akka.hacksprint.Drawer.Preview;
import eu.thirdspaceauto.akka.hacksprint.R;

public class TripleGrouserShoeFragment extends Fragment implements View.OnClickListener {
	private String TAG = TripleGrouserShoeFragment.class.getSimpleName();
	private TextView std_max_value,std_min_value,right_triple_shoe_btn,light_triple_shoe_btn;
	private ImageView right_triple_shoe_image, left_triple_shoe_image;
	private View rootView;
	private EditText left_measurement,right_measurement;
	private int REQUEST_CODE=102;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_shoe_grouser_height, container, false);
//		std_max_value = rootView.findViewById(R.id.std_max_value);
//		std_min_value = rootView.findViewById(R.id.std_min_value);
		right_triple_shoe_btn = (Button) rootView.findViewById(R.id.right_triple_show_btn);
		light_triple_shoe_btn= (Button) rootView.findViewById(R.id.left_triple_show_btn);
		left_triple_shoe_image = rootView.findViewById(R.id.left_triple_show_image);
		right_triple_shoe_image= rootView.findViewById(R.id.right_triple_show_image);
//		std_max_value=(TextView) rootView.findViewById(R.id.std_max_value);
//		std_min_value=(TextView) rootView.findViewById(R.id.std_min_value);
		left_measurement=(EditText) rootView.findViewById(R.id.left_measurement);
		right_measurement=(EditText) rootView.findViewById(R.id.right_measurement);
		right_triple_shoe_btn.setOnClickListener(this);
		light_triple_shoe_btn.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.left_triple_show_btn:
				Intent intent_link = new Intent(getActivity(),Preview.class);
				intent_link.putExtra("component","left_triple_show");
				intent_link.putExtra("request_code",REQUEST_CODE);
				startActivityForResult(intent_link,REQUEST_CODE);
				break;
			case R.id.right_triple_show_btn:
				Intent intent_right = new Intent(getActivity(),Preview.class);
				intent_right.putExtra("component","right_triple_show");
				intent_right.putExtra("request_code",REQUEST_CODE);
				startActivityForResult(intent_right ,REQUEST_CODE);

				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG,"resultcode triple_grouser= "+resultCode + " data= "+data);
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
			if(component.equalsIgnoreCase("left_triple_show")) {
				left_triple_shoe_image.setImageBitmap(myBitmap);
			}else if(component.equalsIgnoreCase("right_triple_show")){
				right_triple_shoe_image.setImageBitmap(myBitmap);
			}
		}
	}
}
