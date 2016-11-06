package com.example.snake_game;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GrVirsma extends SurfaceView implements Runnable {
	static int daudzumsRinda=20, taisnstMala,atlikums;
	static int[][] r=new int[daudzumsRinda][daudzumsRinda];
	static int[][] g=new int[daudzumsRinda][daudzumsRinda];
	static int[][] b=new int[daudzumsRinda][daudzumsRinda];
	static int[][] alph=new int[daudzumsRinda][daudzumsRinda];
	
	static int platums;
	static int augstums;
	static SurfaceHolder ourHolder;
	Thread mansThread = null;
	static volatile boolean solis=false;
	static boolean gameOver = false;
	Paint zimetajs = new Paint();
	Paint[][] pKvadratiem = new Paint[daudzumsRinda][daudzumsRinda];
	Random random = new Random();
	CuskasLogika logika;
	//aiztures vertiba
	int aizture = 1000;
	static int score,speed,krasa;
	static Context kontekstss;
	Typeface fonts;
	Bitmap paPlkst,pretPlkst;

	public GrVirsma(Context konteksts) {
		// TODO Auto-generated constructor stub
		super(konteksts);
		kontekstss = konteksts;
		ourHolder = getHolder();
		score=0;
		speed=0;
		krasa=0;
		fonts= Typeface.createFromAsset(konteksts.getAssets(),"yoshisst.ttf");
		//paint inicializacija
		for(int rinda=0;rinda<daudzumsRinda;rinda++){
			for(int st=0;st<daudzumsRinda;st++){
				pKvadratiem[rinda][st] = new Paint();
				if ( (rinda % 2) == 0){
					if(!((st % 2) ==0)){
					GrVirsma.r[rinda][st]=197;
					GrVirsma.g[rinda][st]=0;
					GrVirsma.b[rinda][st]=254;
					GrVirsma.alph[rinda][st]=255;
					}else{
						GrVirsma.r[rinda][st]=63;
						GrVirsma.g[rinda][st]=0;
						GrVirsma.b[rinda][st]=81;
						GrVirsma.alph[rinda][st]=255;
					}
				}else{
					if(((st % 2) ==0)){
						GrVirsma.r[rinda][st]=197;
						GrVirsma.g[rinda][st]=0;
						GrVirsma.b[rinda][st]=254;
						GrVirsma.alph[rinda][st]=255;
					}else{
						GrVirsma.r[rinda][st]=63;
						GrVirsma.g[rinda][st]=0;
						GrVirsma.b[rinda][st]=81;
						GrVirsma.alph[rinda][st]=255;
					}
				}
			}
		}
		
		
		
	}
	
	
	public void pause(){
		solis = false;
		//logika.garums=3;
		while(true){
			try {
				mansThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		mansThread = null;
		
	}
	static void gameOver() {//izpildas metode ja cuska uzskrien uz savas astes
		// TODO Auto-generated method stub
		gameOver = true;
		synchronized (ourHolder) {
            //quit to mainmenu
            ((Grafika) kontekstss).finish();
        }            
		
	}
	
	public void resume(){
		solis = true;
		mansThread = new Thread(this);
		mansThread.start();
		logika = new CuskasLogika();
	}
	
	//pogu zimesanas metode
	void zimesana(Canvas canvas, int augstums, int platums, Paint zimetajs2){
		paPlkst = BitmapFactory.decodeResource(getResources(), R.drawable.pa_pulksteni);
		pretPlkst = BitmapFactory.decodeResource(getResources(), R.drawable.pret_pulksteni);
		canvas.drawBitmap(pretPlkst, platums/4-55, augstums-42, zimetajs2);
		canvas.drawBitmap(paPlkst, platums-90, augstums-42, zimetajs2);
	}
	//teksta attelosanas metode
	void teksts(Canvas darbVirsma){
		score = logika.garums-3;
		//iegustam atrumu kurs pieaug ik peec 5 punktiem
		if(score/5>speed){
			speed=score/5;
			krasa=krasa+40;
			if(aizture>1){
				if(aizture>600){
					aizture = aizture - 100;
				}else{
					aizture = aizture - 50;
				}
			}
		}
		if(krasa<=255){
			darbVirsma.drawRGB(krasa, 204, 255);
		}else{
			darbVirsma.drawRGB(255, 204, 255);
		}
		zimetajs.setARGB(255,255, 171, 0);
		zimetajs.setTextSize(22);
		zimetajs.setStyle(Style.FILL);
		zimetajs.setAntiAlias(true);
		zimetajs.setShadowLayer((float) 0.2, 2, 2, Color.GRAY);
		zimetajs.setTypeface(fonts);
		this.zimesana(darbVirsma, augstums, platums,zimetajs);
		darbVirsma.drawText("Score: "+(score), 5,25, zimetajs);
		darbVirsma.drawText("Speed: "+(speed), platums-115,25, zimetajs);
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(solis){
			//sis if parbauda lidz virsma ir valid un tikai tad turpinaas darit nakamo, ja nav valid tad atkal izpidas while kamer klust valid
			if(!ourHolder.getSurface().isValid()){
				continue;
			}
			try {
				
				Thread.sleep(aizture);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//izsauc galveno logikas metodi katru reizi parzimejot logu
			logika.solis();
			
			Canvas darbVirsma = ourHolder.lockCanvas();
			platums = darbVirsma.getWidth();
			augstums = darbVirsma.getHeight();
			//iegustam taisnstura malas garumu
			taisnstMala = platums/daudzumsRinda;
			//iegustam atlikumu augstumam, kur zimes tekstu (score)
			atlikums = (augstums - platums)/2;
			teksts(darbVirsma);
			
			
			
			
			//laukumaparzimesana pec logikas izpildes 
			for(int rinda=0;rinda<daudzumsRinda;rinda++){
				for(int st=0;st<daudzumsRinda;st++){
					pKvadratiem[rinda][st].setARGB(alph[rinda][st], r[rinda][st], g[rinda][st], b[rinda][st]);
				}
			}
			
			//taisnsturu zimesana
				for(int rinda=0;rinda<daudzumsRinda;rinda++){
					for(int st=0;st<daudzumsRinda;st++){
						darbVirsma.drawRect(st*taisnstMala, atlikums+rinda*taisnstMala,
								st*taisnstMala+taisnstMala, atlikums+rinda*taisnstMala+taisnstMala, pKvadratiem[rinda][st]);
					}
				}
			
			//zime canvas sij funkcijai vienmer jabut run metodes beigas, lai uzzimetu galarezultatu peec visaam darbibam
			ourHolder.unlockCanvasAndPost(darbVirsma);
			
		}
	}

}
