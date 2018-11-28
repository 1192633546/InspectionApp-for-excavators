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

import java.io.File;

import eu.thirdspaceauto.akka.hacksprint.Drawer.Preview;
import eu.thirdspaceauto.akka.hacksprint.R;

public class BottomRollerFragment extends Fragment implements View.OnClickListener {
	private String TAG=BottomRollerFragment.class.getSimpleName();
	private int REQUEST_CODE=103;
	Button left_link_pitch_btn,right_link_pitch_btn;
	ImageView right_link_pitch_image,left_link_pitch_image;
	private View rootView;
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate (R.layout.fragment_bottom_roller, container, false);
		left_link_pitch_btn = (Button) rootView.findViewById(R.id.left_link_pitch_btn);
		right_link_pitch_btn= (Button) rootView.findViewById(R.id.right_link_pitch_btn);
		left_link_pitch_image = rootView.findViewById(R.id.left_link_pitch_image);
		right_link_pitch_image= rootView.findViewById(R.id.right_link_pitch_image);

		left_link_pitch_btn.setOnClickListener(this);
		right_link_pitch_btn.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.left_link_pitch_btn:
				Intent intent_link = new Intent(getActivity(),Preview.class);
				intent_link.putExtra("component","left_link_pitch");
				intent_link.putExtra("request_code",REQUEST_CODE);
				startActivityForResult(intent_link,REQUEST_CODE);
				break;
			case R.id.right_link_pitch_btn:
				Intent intent_right = new Intent(getActivity(),Preview.class);
				intent_right.putExtra("component","right_link_pitch");
				intent_right.putExtra("request_code",REQUEST_CODE);
				startActivityForResult(intent_right ,REQUEST_CODE);
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG,"resultcode BottomRoller= "+resultCode + " data= "+data);
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
			if(component.equalsIgnoreCase("left_link_pitch")) {
				left_link_pitch_image.setImageBitmap(myBitmap);
			}else if(component.equalsIgnoreCase("right_link_pitch")){
				right_link_pitch_image.setImageBitmap(myBitmap);
			}
		}
	}
}
