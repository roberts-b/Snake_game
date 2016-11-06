package com.example.snake_game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Highscore extends Activity implements OnClickListener {
	Typeface fontss;
	TextView highsc;
	Button clear;
	TextView tv[] = new TextView[5];
	int vertibas[] = new int[5];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.highscore);
		fontss= Typeface.createFromAsset(getAssets(),"yoshisst.ttf");
		
		clear = (Button) findViewById(R.id.bClear);
		clear.setOnClickListener(this);
		highsc = (TextView) findViewById(R.id.textView1);
		highsc.setTypeface(fontss);
		highsc.setTextColor(Color.YELLOW);
		tv[0] = (TextView) findViewById(R.id.tv01);
		tv[1] = (TextView) findViewById(R.id.tv02);
		tv[2] = (TextView) findViewById(R.id.tv03);
		tv[3] = (TextView) findViewById(R.id.tv04);
		tv[4] = (TextView) findViewById(R.id.tv05);
		for(int i=0;i<5;i++){
			tv[i].setTextColor(Color.RED);
			tv[i].setTypeface(fontss);
			
		}
		SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		for(int i=0; i<5;i++){
			vertibas[i] = prefs.getInt(Integer.toString(i), 0);
			tv[i].setText((i+1)+":   "+vertibas[i]);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.bClear:
			SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
			Editor editor = prefs.edit();
			
			for (int i=0;i<5;i++){
				editor.putInt(Integer.toString(i), 0);
			}
			editor.commit();
			for(int i=0; i<5;i++){
				vertibas[i] = prefs.getInt(Integer.toString(i), 0);
				tv[i].setText((i+1)+":   "+vertibas[i]);
			}
			break;
		
		}
	}
	
	

}
