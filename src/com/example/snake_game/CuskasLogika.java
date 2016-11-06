package com.example.snake_game;

import java.util.Random;

import android.os.Handler;

public class CuskasLogika {
	//veidojam stavoklu arraju 0-tukss kvadrats, 1-sark pts.(cuska aped to), 2 - cuskas galva(dzeltena), 3- cuskas aste;
	int[][] stavokli=new int[GrVirsma.daudzumsRinda][GrVirsma.daudzumsRinda];
	Random rand = new Random();
	static char virzienss='a';
	
	//aiztures vertiba
	boolean aizliegt=false;
	int navSarkana=0;
	Thread timer;
	final Handler handler = new Handler();
	int rindaa=GrVirsma.daudzumsRinda/2; //kustina uz leju ja samazinas un uz augsu ja palielinas
	int stabins=GrVirsma.daudzumsRinda/2; //kustina pa kreisi ja samazinas un pa labi ja palielinas
	 int garums; //glabas sevi cuuskas garumu
	
	public CuskasLogika(){
		garums=3;
		//sakuma uzstadam ka visi kvadratini ir tuksi
		for(int rinda=0;rinda<GrVirsma.daudzumsRinda;rinda++){
			for(int st=0;st<GrVirsma.daudzumsRinda;st++){
				stavokli[rinda][st]=0;
				
			}
		}
		
		//inicializejot klases konstruktoru sakam ka ir pirmais solis cuskas galva atrodas ekrana centraa
		stavokli[GrVirsma.daudzumsRinda/2][GrVirsma.daudzumsRinda/2]=2;
	}
	
	void solis(){  //sii metodde tiek izsaukta no GrVirsmas, seit tiek izsauktas visas Cuskas logikas metodes
		virziens();
		kustiba();
		sarkanais();
		izmainuSkenesana();
	}
	
	private void sarkanais() {
		for(int rinda=0;rinda<GrVirsma.daudzumsRinda;rinda++){
			for(int st=0;st<GrVirsma.daudzumsRinda;st++){
				int tagStavoklis=stavokli[rinda][st];
				if(tagStavoklis==1){//parbauda vai jau ieprieks nav sarkans uz laukuma neaizmirst pec sarkanaa apesanas nav sarkanaa pataisit par 0
					navSarkana++;
				}
			}
		}
		if(navSarkana==0){
			int pozicija = rand.nextInt(GrVirsma.daudzumsRinda);
			if(stavokli[pozicija][pozicija]==0){
				stavokli[pozicija][pozicija]=1;
			}
		}
		navSarkana=0;//lai ints neparpilditos un spele neizlektu
		
	}
	
	 static void virziens(){ //z-ziemeli,d-dienvidi,a-austrumi,r-rietumi
		if((Grafika.paKreisi || Grafika.paKreisiAk) && virzienss=='a' && (Grafika.taisni || Grafika.taisniAk)){
			virzienss='z';
			Grafika.taisni=false;
			Grafika.taisniAk=false;
		}else if((Grafika.paKreisi || Grafika.paKreisiAk) && virzienss=='z' && (Grafika.taisni || Grafika.taisniAk)){
			virzienss='r';
			Grafika.taisni=false;
			Grafika.taisniAk=false;
		}else if((Grafika.paKreisi || Grafika.paKreisiAk) && virzienss=='r' && (Grafika.taisni || Grafika.taisniAk)){
			virzienss='d';
			Grafika.taisni=false;
			Grafika.taisniAk=false;
		}else if((Grafika.paKreisi || Grafika.paKreisiAk) && virzienss=='d' && (Grafika.taisni || Grafika.taisniAk)){
			virzienss='a';
			Grafika.taisni=false;
			Grafika.taisniAk=false;
		}else if((Grafika.paLabi || Grafika.paLabiAk) && virzienss=='a' && (Grafika.taisni || Grafika.taisniAk)){
			virzienss='d';
			Grafika.taisni=false;
			Grafika.taisniAk=false;
		}else if((Grafika.paLabi || Grafika.paLabiAk) && virzienss=='d' && (Grafika.taisni || Grafika.taisniAk)){
			virzienss='r';
			Grafika.taisni=false;
			Grafika.taisniAk=false;
		}else if((Grafika.paLabi || Grafika.paLabiAk) && virzienss=='r' && (Grafika.taisni || Grafika.taisniAk)){
			virzienss='z';
			Grafika.taisni=false;
			Grafika.taisniAk=false;
		}else if((Grafika.paLabi || Grafika.paLabiAk) && virzienss=='z' && (Grafika.taisni || Grafika.taisniAk)){
			virzienss='a';
			Grafika.taisni=false;
			Grafika.taisniAk=false;
		}
	}
	
	void kustiba(){
		//astes cikls

			for(int rinda=0;rinda<GrVirsma.daudzumsRinda;rinda++){
				for(int st=0;st<GrVirsma.daudzumsRinda;st++){
					int tagStavoklis=stavokli[rinda][st];
					if(tagStavoklis>2){
							stavokli[rinda][st]++;
					}
				}
			}
		//lieko astu dzesana
		for(int rinda=0;rinda<GrVirsma.daudzumsRinda;rinda++){
			for(int st=0;st<GrVirsma.daudzumsRinda;st++){
				int tagStavoklis=stavokli[rinda][st];
				if(tagStavoklis>garums){
					stavokli[rinda][st]=0;
				}
			}
		}
		
		//cuskas staigasanas cilpas dazados virzienos
		if(virzienss=='a'){//uz austrumiem
				aizliegt=false;
				for(int rinda=0;rinda<GrVirsma.daudzumsRinda;rinda++){
					for(int st=0;st<GrVirsma.daudzumsRinda;st++){
						int tagStavoklis=stavokli[rinda][st];
						
						if(tagStavoklis==2 && aizliegt==false){
							stavokli[rinda][st]=3;
							if(st<GrVirsma.daudzumsRinda-1){
								if(stavokli[rinda][st+1]==1){//palielina garumu ja apeed sarkano
									garums++;
								}else if(stavokli[rinda][st+1]>2){//speles beigas ja cuuska uzskrien uz astes
									GrVirsma.gameOver();
								}
								stavokli[rinda][st+1]=2;
								aizliegt=true;
								
								
							}else {
								if(stavokli[rinda][0]==1){
									garums++;
								}else if(stavokli[rinda][0]>2){//speles beigas ja cuuska uzskrien uz astes
									GrVirsma.gameOver();
								}
								stavokli[rinda][0]=2;
								aizliegt=true;
							}
						}
					}
			}
		}else if(virzienss=='d'){//uz dienvidiem
				aizliegt=false;
				for(int rinda=0;rinda<GrVirsma.daudzumsRinda;rinda++){
					for(int st=0;st<GrVirsma.daudzumsRinda;st++){
						int tagStavoklis=stavokli[rinda][st];
						
						if(tagStavoklis==2 && aizliegt==false){
							stavokli[rinda][st]=3;
							if(rinda<GrVirsma.daudzumsRinda-1){
								if(stavokli[rinda+1][st]==1){
									garums++;
								}else if(stavokli[rinda+1][st]>2){//speles beigas ja cuuska uzskrien uz astes
									GrVirsma.gameOver();
								}
								stavokli[rinda+1][st]=2;
								aizliegt=true;
								
								
							}else {
								if(stavokli[0][st]==1){
									garums++;
								}else if(stavokli[0][st]>2){//speles beigas ja cuuska uzskrien uz astes
									GrVirsma.gameOver();
								}
								stavokli[0][st]=2;
								aizliegt=true;
							}
						}
					}
			}
		}else if(virzienss=='r'){//uz rietumiem
				aizliegt=false;
				for(int rinda=0;rinda<GrVirsma.daudzumsRinda;rinda++){
					for(int st=0;st<GrVirsma.daudzumsRinda;st++){
						int tagStavoklis=stavokli[rinda][st];
						
						if(tagStavoklis==2 && aizliegt==false){
							stavokli[rinda][st]=3;
							if(st>0){
								if(stavokli[rinda][st-1]==1){
									garums++;
								}else if(stavokli[rinda][st-1]>2){//speles beigas ja cuuska uzskrien uz astes
									GrVirsma.gameOver();
								}
								stavokli[rinda][st-1]=2;
								aizliegt=true;
								
								
							}else {
								if(stavokli[rinda][GrVirsma.daudzumsRinda-1]==1){
									garums++;
								}else if(stavokli[rinda][GrVirsma.daudzumsRinda-1]>2){//speles beigas ja cuuska uzskrien uz astes
									GrVirsma.gameOver();
								}
								stavokli[rinda][GrVirsma.daudzumsRinda-1]=2;
								aizliegt=true;
							}
						}
					}
			}
		}else if(virzienss=='z'){//uz ziemeliem
				aizliegt=false;
				for(int rinda=0;rinda<GrVirsma.daudzumsRinda;rinda++){
					for(int st=0;st<GrVirsma.daudzumsRinda;st++){
						int tagStavoklis=stavokli[rinda][st];
						
						if(tagStavoklis==2 && aizliegt==false){
							stavokli[rinda][st]=3;
							if(rinda>0){
								if(stavokli[rinda-1][st]==1){
									garums++;
								}else if(stavokli[rinda-1][st]>2){//speles beigas ja cuuska uzskrien uz astes
									GrVirsma.gameOver();
								}
								stavokli[rinda-1][st]=2;
								aizliegt=true;
								
								
							}else {
								if(stavokli[GrVirsma.daudzumsRinda-1][st]==1){
									garums++;
								}else if(stavokli[GrVirsma.daudzumsRinda-1][st]>2){//speles beigas ja cuuska uzskrien uz astes
									GrVirsma.gameOver();
								}
								stavokli[GrVirsma.daudzumsRinda-1][st]=2;
								aizliegt=true;
							}
						}
					}
				}
			}
	}
	
	

	void izmainuSkenesana(){
		for(int rinda=0;rinda<GrVirsma.daudzumsRinda;rinda++){
			for(int st=0;st<GrVirsma.daudzumsRinda;st++){
				int tagStav=stavokli[rinda][st];
				
				izmainuAttelosana(tagStav,rinda,st);
			}
		}
		
	}
	int n=1;
	private void izmainuAttelosana(int tagStav, int rinda, int st) {
		// TODO Auto-generated method stub
		
		//n++;
		//if(n>100){
			//GrVirsma.gameOver();
		//}
		if(tagStav==0){//tukss laukums
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
		if(tagStav==1){//sarkanais taisnsturis
			GrVirsma.r[rinda][st]=255;
			GrVirsma.g[rinda][st]=0;
			GrVirsma.b[rinda][st]=0;
			GrVirsma.alph[rinda][st]=255;
		}
		if(tagStav==2){//dzeltenais taisnsturis galva
			GrVirsma.r[rinda][st]=255;
			GrVirsma.g[rinda][st]=255;
			GrVirsma.b[rinda][st]=0;
			GrVirsma.alph[rinda][st]=255;
		}
		if(tagStav>2){//zalais taisnsturis aste
			if(tagStav<34){
				GrVirsma.g[rinda][st]=255-tagStav*7;
			}else{
				GrVirsma.g[rinda][st]=0;
			}
			if(tagStav<34){
				GrVirsma.r[rinda][st]=tagStav*7;
			}else{
				GrVirsma.r[rinda][st]=255;
			}
			GrVirsma.b[rinda][st]=0;
			GrVirsma.alph[rinda][st]=255;
		}
	}
}
