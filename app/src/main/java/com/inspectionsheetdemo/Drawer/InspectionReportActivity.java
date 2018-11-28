package com.inspectionsheetdemo.Drawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import eu.thirdspaceauto.akka.hacksprint.MainActivity;
import eu.thirdspaceauto.akka.hacksprint.R;

public class InspectionReportActivity extends AppCompatActivity implements View.OnClickListener {
	private Context context;
	private Activity activity;
	private Toolbar toolbar;
	private Button new_inspection, tripleShoeBuyNow, bottomRollerBuyNow, sprocketBuyNow;
	private ImageView addSprocket, addTripleShoe, addBottomRoller, removeSprocket, removeTripleShoe, removeBottomRoller;
	private int tripleShoeAmount, bottomRollerAmount, sprocketAmount = 0;
	private TextView tripleShoeTextView, bottomRollerTextView, sprocketTextView,dealerTextView;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_inspection_report);
		init();
	}
	
	private void init(){
		context = this;
		activity = this;
		toolbar = findViewById(R.id.toolbar_2);
		new_inspection = findViewById(R.id.new_inspection);
		toolbar.setTitle(getResources().getString(R.string.inspection_report_title));
		toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(ContextCompat.getDrawable(context,R.drawable.ic_arrow_back_white_24dp));
		
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoHome();
			}
		});
		tripleShoeBuyNow = findViewById (R.id.triple_shoe_buy_button);
		bottomRollerBuyNow = findViewById (R.id.buy_button_bottom_roller);
		sprocketBuyNow = findViewById (R.id.buy_button_sprocket);
		addTripleShoe = findViewById (R.id.addTripleShoe);
		addBottomRoller = findViewById (R.id.addBottomRoller);
		addSprocket = findViewById (R.id.addSprocket);
		removeTripleShoe = findViewById (R.id.removeTripleShoe);
		removeBottomRoller = findViewById (R.id.removeBottomRoller);
		removeSprocket = findViewById (R.id.removeSprocket);
		tripleShoeTextView = findViewById (R.id.tripleShoeTextView);
		bottomRollerTextView = findViewById (R.id.bottomRollerTextView);
		sprocketTextView = findViewById (R.id.sprocketTextView);

		dealerTextView = (TextView) findViewById(R.id.dealer_text);
		SpannableString content = new SpannableString("Nearby Dealers");
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		dealerTextView.setText(content);
		
		addSprocket.setOnClickListener (this);
		addBottomRoller.setOnClickListener (this);
		addTripleShoe.setOnClickListener (this);
		removeSprocket.setOnClickListener (this);
		removeBottomRoller.setOnClickListener (this);
		removeTripleShoe.setOnClickListener (this);
		tripleShoeBuyNow.setOnClickListener (this);
		bottomRollerBuyNow.setOnClickListener (this);
		sprocketBuyNow.setOnClickListener (this);
	}
	
	private void gotoHome() {
		Log.e (InspectionReportActivity.class.getSimpleName (), "onClick being called");
		Intent newIntent = new Intent(InspectionReportActivity.this, MainActivity.class);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(newIntent);
	}
	
	@Override
	public void onClick (View v) {
		switch (v.getId ()){
			case R.id.triple_shoe_buy_button:
				tripleShoeBuyNow.setVisibility (View.GONE);
				findViewById (R.id.tripleShoeBuyLayout).setVisibility (View.VISIBLE);
				tripleShoeTextView.setText (String.valueOf (tripleShoeAmount));
				break;
			case R.id.buy_button_bottom_roller:
				bottomRollerBuyNow.setVisibility (View.GONE);
				findViewById (R.id.bottomRollerBuyLayout).setVisibility (View.VISIBLE);
				bottomRollerTextView.setText (String.valueOf (bottomRollerAmount));
				break;
			case R.id.buy_button_sprocket:
				sprocketBuyNow.setVisibility (View.GONE);
				findViewById (R.id.sprocketBuyLayout).setVisibility (View.VISIBLE);
				sprocketTextView.setText (String.valueOf (sprocketAmount));
				break;
			case R.id.addBottomRoller:
				++bottomRollerAmount;
				bottomRollerTextView.setText (String.valueOf (bottomRollerAmount));
				break;
			case R.id.addSprocket:
				++sprocketAmount;
				sprocketTextView.setText (String.valueOf (sprocketAmount));
				break;
			case R.id.addTripleShoe:
				++tripleShoeAmount;
				tripleShoeTextView.setText (String.valueOf (tripleShoeAmount));
				break;
			case R.id.removeBottomRoller:
				if(bottomRollerAmount == 0)return;
				--bottomRollerAmount;
				bottomRollerTextView.setText (String.valueOf (bottomRollerAmount));
				break;
			case R.id.removeSprocket:
				if(sprocketAmount == 0)return;
				--sprocketAmount;
				sprocketTextView.setText (String.valueOf (sprocketAmount));
				break;
			case R.id.removeTripleShoe:
				if(tripleShoeAmount == 0)return;
				--tripleShoeAmount;
				tripleShoeTextView.setText (String.valueOf (tripleShoeAmount));
				break;
		}
	}
}