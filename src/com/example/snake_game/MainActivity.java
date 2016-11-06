package com.example.snake_game;

import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener {
	
	static boolean akselStr=false;
	static int highVert[] = new int[5];
	
	static TextView tvUp;
	Button sakt,iziet,highscore;
	ToggleButton aksIesl;
	Typeface font;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gravitytest);
		
		tvUp = (TextView) findViewById(R.id.tvUp);
		font= Typeface.createFromAsset(getAssets(),"yoshisst.ttf");
		tvUp.setTypeface(font);
		aksIesl=(ToggleButton) findViewById(R.id.toggleButton1);
		aksIesl.setOnClickListener(this);
		
		sakt = (Button) findViewById(R.id.bNolasit);
		iziet = (Button) findViewById(R.id.bExit);
		highscore = (Button) findViewById(R.id.bhighsc);
		sakt.setOnClickListener(this);
		iziet.setOnClickListener(this);
		highscore.setOnClickListener(this);
		
		if(highVert==null){
			for(int i=0; i<5;i++){
				
				highVert[i]=0;
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	//ja aktivitate tiek nopauzeta tad atslegs sensora radijumu nolasisanu lai taupitu ierices energiju
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//menedzeris.unregisterListener(this);
		
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(GrVirsma.gameOver){
			tvUp.setText("You Lose Try Again !");
		}else if(!GrVirsma.gameOver){
			tvUp.setText(" ");
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		
		case R.id.bNolasit:
			GrVirsma.gameOver=false;
			Intent grafiskaisLogs = new Intent("com.example.snake_game.Grafika");
			startActivity(grafiskaisLogs);
			break;
		
		case R.id.bExit:
			System.exit(0);
			break;
		
		case R.id.toggleButton1:
			if(aksIesl.isChecked()){
				akselStr=true;
			}else{
				akselStr = false;
			}
			break;
			
		case R.id.bhighsc:
			Intent hgh = new Intent("com.example.snake_game.Highscore");
			startActivity(hgh);
			
			break;
		}
		
	}
}
