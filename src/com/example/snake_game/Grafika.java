package com.example.snake_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class Grafika extends Activity  implements SensorEventListener, OnTouchListener {
	static float x,y;
	SensorManager menedzeris;
	Sensor akselom;
	GrVirsma grafiskaVirsma;
	WakeLock lock;
	int robeza = 3;
	static boolean aksel=false, paLabi=false, paKreisi = false, taisni =true, paLabiAk=false, paKreisiAk=false, taisniAk=true, sakumst=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		//pilnekraana reziims, pie tam to ir jaizsauc pirms onCreate() metodes
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//seit ir oncreate metodes izsauksana
		super.onCreate(savedInstanceState);
		
		grafiskaVirsma = new GrVirsma(this);
		setContentView(grafiskaVirsma);
		grafiskaVirsma.setOnTouchListener(this);
		
		if(MainActivity.akselStr){
			menedzeris = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		}
		//uztaisaam lai programma neaizmigtu ja neko nespiez
		PowerManager powerManager =(PowerManager) getSystemService(Context.POWER_SERVICE);
		lock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "mans bloketajs");
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		
		super.onPause();
		//ieliekam jauno highscore vertibu
		iezime:
		for(int i=0;i<5;i++){
			
			if(MainActivity.highVert[i]<=GrVirsma.score){
				MainActivity.highVert[i]=GrVirsma.score;
				break iezime;
			}
		}
		//saglabajam highscore 
		SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		
		for (int i=0;i<5;i++){
			editor.putInt(Integer.toString(i), MainActivity.highVert[i]);
		}
		editor.commit();
		//izsledzam iemigsanas bloketaju kad aktivitate tiek pauzeeta
		lock.release();
		grafiskaVirsma.pause();
		//kad aktivitate tiek pauzeta izsledzam akselometra listeneru lai taupitu energiju
		if(MainActivity.akselStr){
			menedzeris.unregisterListener(this);
		}
		
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//iniciejam iemigsanas bloketaju
		lock.acquire();
		grafiskaVirsma.resume();
		//uztaisam akselometra listeneru
		if(MainActivity.akselStr){
			if(menedzeris.getSensorList(Sensor.TYPE_ACCELEROMETER).size()==0){
				
			}else{
				akselom = menedzeris.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
				
			}
			menedzeris.registerListener(this, akselom, menedzeris.SENSOR_DELAY_GAME);
		}
	}
		
	
	// kontroles metodes
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		
		//akselometra radijumus glaba arrajaa, kuram 0 poz. ir x ass radijumi, 1 ir y ass un 2 poz. ir z ass
				float x = arg0.values[0];//cuskas vadisanai var izmantot tikai x asi 
				//ja telefonu griez pa kreisi aks rada piem -5 un vairak tad cuska griezas pret pulkstenraditaja virzienu
				//ja griez pa vairak par 5 labi tad cuska griezas pa pulkstenraditaja virzienu
				//ja x radijums atrodas robezas no -5 lidz 5 tad cuska negriezas iet taisni uz prieksu
				
				
				if(x>=robeza && sakumst){
					paKreisiAk = true;
					paLabiAk = false;
					taisniAk = true;
					sakumst = false;
					CuskasLogika.virziens();
				}else if(x<=-robeza && sakumst){
					paLabiAk = true;
					paKreisiAk = false;
					taisniAk = true;
					sakumst = false;
					CuskasLogika.virziens();
				}else if((x>-robeza) & (x<robeza)){
					sakumst = true;
					paKreisiAk= false;
					paLabiAk = false;
					CuskasLogika.virziens();
					
				}
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		/*
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			x=event.getX();
			y=event.getY();
			if(y>(GrVirsma.augstums-60) & x<(GrVirsma.platums/2)){//grieziis cuusku pret plkstenraditaj virzienu
				paKreisi=true;
				paLabi=false;
				taisni=true;
				CuskasLogika.virziens();
			}else if(y>(GrVirsma.augstums-60) & x>(GrVirsma.platums/2)){// grieziis cusku pa plkstrad. virzienu
				paKreisi=false;
				paLabi=true;
				taisni=true;
				CuskasLogika.virziens();
			}
		}
		return true;
	}


}
