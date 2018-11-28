package com.inspectionsheetdemo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import eu.thirdspaceauto.akka.hacksprint.FinalActivity;
import eu.thirdspaceauto.akka.hacksprint.MainActivity;
import eu.thirdspaceauto.akka.hacksprint.R;


public class InfoSheetFragment extends Fragment implements View.OnClickListener{
	private EditText countryEditText, serialNumberEditText, grConditionEditText, operationHrsEditText, shoeWidth;
	private RadioButton lowImpact, modImpact, highImpact, lowAbrasive, modAbrasive, highAbrasive, lowMoisture, modMoisture, highMoisture, lowPacking, modPacking, highPacking, tripleShoe,
	doubleShoe, singleShoe, tripleTrackTension, doubleTrackTension, singleTrackTension;
	private Button previousButton, nextButton;
	private MainActivity parentActivity;
	public InfoSheetFragment () {
		// Required empty public constructor
	}
	
	public static InfoSheetFragment newInstance () {
		InfoSheetFragment fragment = new InfoSheetFragment ();
		Bundle args = new Bundle ();
		
		fragment.setArguments (args);
		return fragment;
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstanceState) {
		View view = inflater.inflate (R.layout.fragment_info_sheet, container, false);
		init (view);
		
		return view;
	}
	
	private void init(View view){
		countryEditText = view.findViewById (R.id.countryText);
		serialNumberEditText = view.findViewById (R.id.serialNumberText);
		grConditionEditText = view.findViewById (R.id.grConditionText);
		operationHrsEditText = view.findViewById (R.id.operationHrsText);
		shoeWidth = view.findViewById (R.id.shoeWidthText);
		lowImpact = view.findViewById (R.id.lowImpactRadioButton);
		modImpact = view.findViewById (R.id.modImpactRadioButton);
		highImpact = view.findViewById (R.id.highImpactRadioButton);
		lowAbrasive = view.findViewById (R.id.lowAbrasiveRadioButton);
		modAbrasive = view.findViewById (R.id.modAbrasiveRadioButton);
		highAbrasive = view.findViewById (R.id.highAbrasiveRadioButton);
		lowMoisture = view.findViewById (R.id.lowMoistureRadioButton);
		modMoisture = view.findViewById (R.id.modMoistureRadioButton);
		highMoisture = view.findViewById (R.id.highMoistureRadioButton);
		lowPacking = view.findViewById (R.id.lowPackingRadioButton);
		modPacking = view.findViewById (R.id.modPackingRadioButton);
		highPacking = view.findViewById (R.id.highPackingRadioButton);
		tripleShoe = view.findViewById (R.id.tripleShoeRadioButton);
		doubleShoe = view.findViewById (R.id.doubleShoeRadioButton);
		singleShoe = view.findViewById (R.id.singleShoeRadioButton);
		tripleTrackTension = view.findViewById (R.id.tripleTrackTension);
		doubleTrackTension = view.findViewById (R.id.doubleTrackTension);
		singleTrackTension = view.findViewById (R.id.singleTrackTension);
		parentActivity = (MainActivity)getActivity ();
		
		previousButton = parentActivity.previousButton;
		nextButton = parentActivity.nextButton;
		
		previousButton.setOnClickListener (this);
		nextButton.setOnClickListener (this);
	}
	
	@Override
	public void onClick (View v) {
		switch (v.getId ()){
			case R.id.previousButton:
				if(parentActivity.currentFragment >= 1){
					--parentActivity.currentFragment;
					parentActivity.viewPager.setCurrentItem(parentActivity.currentFragment);
				}
				break;
			case R.id.nextButton:
				if(parentActivity.inspectionModel == null)return;
				parentActivity.inspectionModel.country = countryEditText.getText ().toString ();
				parentActivity.inspectionModel.serialNumber = countryEditText.getText ().toString ();
				parentActivity.inspectionModel.grConditions = grConditionEditText.getText ().toString ();
				parentActivity.inspectionModel.operatingHr = operationHrsEditText.getText ().toString ();
				parentActivity.inspectionModel.shoeWidth = shoeWidth.getText ().toString ();
				if(lowImpact.isChecked ())parentActivity.inspectionModel.impact = "Low";
				if(modImpact.isChecked ())parentActivity.inspectionModel.impact = "Mod";
				if(highImpact.isChecked ())parentActivity.inspectionModel.impact = "High";
				if(lowAbrasive.isChecked ())parentActivity.inspectionModel.abrasive = "Low";
				if(modAbrasive.isChecked ())parentActivity.inspectionModel.abrasive = "Mod";
				if(highAbrasive.isChecked ())parentActivity.inspectionModel.abrasive = "High";
				if(lowMoisture.isChecked ())parentActivity.inspectionModel.moisture = "Low";
				if(modMoisture.isChecked ())parentActivity.inspectionModel.moisture = "Mod";
				if(highMoisture.isChecked ())parentActivity.inspectionModel.moisture = "High";
				if(lowPacking.isChecked ())parentActivity.inspectionModel.packing = "Low";
				if(modPacking.isChecked ())parentActivity.inspectionModel.packing = "Mod";
				if(highPacking.isChecked ())parentActivity.inspectionModel.packing = "High";
				if(tripleShoe.isChecked ())parentActivity.inspectionModel.shoeType = "Triple";
				if(doubleShoe.isChecked ())parentActivity.inspectionModel.shoeType = "Double";
				if(singleShoe.isChecked ())parentActivity.inspectionModel.shoeType = "Single";
				if(tripleTrackTension.isChecked ())parentActivity.inspectionModel.trackTension = "Triple";
				if(doubleTrackTension.isChecked ())parentActivity.inspectionModel.trackTension = "Double";
				if(singleTrackTension.isChecked ())parentActivity.inspectionModel.trackTension = "Single";
				if(parentActivity.currentFragment <7){
					++parentActivity.currentFragment;
					parentActivity.viewPager.setCurrentItem (parentActivity.currentFragment);
				}else{
					Intent intent = new Intent (parentActivity, FinalActivity.class);
					parentActivity.startActivity (intent);
				}
				break;
		}
	}
}