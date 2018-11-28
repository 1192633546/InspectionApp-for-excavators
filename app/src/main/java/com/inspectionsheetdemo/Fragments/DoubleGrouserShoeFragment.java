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

public class DoubleGrouserShoeFragment extends Fragment implements View.OnClickListener {
	private String TAG = DoubleGrouserShoeFragment.class.getSimpleName();
	private Button left_link_pitch_btn, right_link_pitch_btn;
	private ImageView left_link_pitch_image, right_link_pitch_image;
	private int REQUEST_CODE = 100;
	
	public DoubleGrouserShoeFragment () {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstanceState) {
		View view = inflater.inflate (R.layout.fragment_double_grouser_shoe, container, false);
		left_link_pitch_btn = (Button) view.findViewById (R.id.left_link_pitch_btn);
		right_link_pitch_btn = (Button) view.findViewById (R.id.right_link_pitch_btn);
		left_link_pitch_image = view.findViewById (R.id.left_link_pitch_image);
		right_link_pitch_image = view.findViewById (R.id.right_link_pitch_image);
		
		left_link_pitch_btn.setOnClickListener (this);
		right_link_pitch_btn.setOnClickListener (this);
		return view;
	}
	
	@Override
	public void onClick (View v) {
		switch (v.getId ()) {
			case R.id.left_link_pitch_btn:
				Intent intent_link = new Intent (getActivity (), Preview.class);
				intent_link.putExtra ("component", "left_link_pitch");
				startActivityForResult (intent_link, REQUEST_CODE);
				break;
			case R.id.right_link_pitch_btn:
				Intent intent_right = new Intent (getActivity (), Preview.class);
				intent_right.putExtra ("component", "right_link_pitch");
				startActivityForResult (intent_right, REQUEST_CODE);
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
			if(component.equalsIgnoreCase("left_link_pitch")) {
				left_link_pitch_image.setImageBitmap(myBitmap);
			}else if(component.equalsIgnoreCase("right_link_pitch")){
				right_link_pitch_image.setImageBitmap(myBitmap);
			}
		}
	}
}
