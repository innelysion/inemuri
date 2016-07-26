package jp.tnw.game_a05;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class Jtama {
	
	final double FrameTime=0.017;//0.031f;
	final int Max = 100; //敵のmax number

	Font	f=new	Font("Default",Font.PLAIN,10);//使用するフォントクラス宣言
	BufferedImage bulletPNG;

	double[]	X		=	new double[Max]; //positions
	double[]	Y		=	new double[Max];
	double[]	Xsp		=	new double[Max]; // speeds
	double[]	Ysp		=	new double[Max];
	double[]	Xspg	=	new double[Max]; // speed multipliers
	double[]	Yspg	=	new double[Max];
	double[]	No		=	new double[Max];
	
	int[]		Ny		=	new int[Max];
	int[]		Flag	=	new int[Max];//交互切り替え
	
	double[] kaku	 	=	new double[Max];
	double[] kaku_P 	=	new double[Max];

	double[] Timer 		= 	new double[8]; //time unhiding of stuff
	double timerFreq 	= 	0.5;
	
	Jiki JK;

	int Speed;
	DecimalFormat df = new DecimalFormat("#.#");

	//--------------------------------
	//コンストラクタ
	//--------------------------------
	public Jtama(){
		load();
		for (int i =0;i<Max;i++){
		X[i]=0-16;
		Y[i]=0;
		Xsp[i]=0;//初速度X
		Ysp[i]=0;
		Xspg[i]=0;//初速度X
		Yspg[i]=0;
		Flag[i]=99;
		No[i]=17;
		}
		Speed=10;
	}

	//--------------------------------
	//ZAKOのクラス
	//--------------------------------
	public void load(){
		try {
			bulletPNG = ImageIO.read(getClass().getResource("stg_image/tama.png"));//ファイル名

		} catch (IOException e) {//読み込みエラーの場合
				e.printStackTrace();
		}
	}

	//from top left
	public abstract void Req();
	/*	Timer[0]-=FrameTime;
		if(Timer[0]<0){
			Timer[0]=timerFreq;
			for (int i =0;i<Max;i++){
				if(Flag[i]==99){
					X[i]=JK.X+44;	//startingpoint
					Y[i]=JK.Y+18;
					Xsp[i]=0;//初速度X start speed
					Ysp[i]=-350;
					Xspg[i]=1;//初速度X speed multiplicator
					Yspg[i]=1;//-270/2;
					Ny[i]=2;
					//kaku[i] = S_kaku*5; //angle
					//S_kaku++;
					Flag[i]=1; //show on screen
				break;
				}
			}
		}*/

	
	public boolean Hit(double x, double y){
		for (int i =0;i<Max;i++){
			if(Flag[i]!=99){
				//衝突判定----------------------------
				double x_sa=((X[i]+8)-(x+24));
				double y_sa=((Y[i]+8)-(y+24));
				double D = Math.sqrt(Math.pow(x_sa, 2)+Math.pow(y_sa, 2));
				if (D<=8+14){
					//Xsp[i]=0;
					//Ysp[i]=0;
					return true;
				}
			}//ifend
		}
		return false;
	}
	
	public void move(){

		//Req();

		for (int i =0;i<Max;i++){
			if(Flag[i]!=99){
			//加速度 speed of increasing
			//Xsp[i]+=(FrameTime*Xspg[i]);
			//Ysp[i]+=(FrameTime*Yspg[i]);
			//初速が movingspeed
			//X[i]+=(FrameTime*Xsp[i]);
			//Y[i]+=(FrameTime*Ysp[i]);
			X[i]+=FrameTime*Xsp[i]*Math.cos(Math.toRadians(kaku[i]));//-FrameTime*Xsp[i]*Math.sin(Math.toRadians(kaku[i]));
			Y[i]+=FrameTime*Ysp[i]*Math.sin(Math.toRadians(kaku[i]));//+FrameTime*Ysp[i]*Math.cos(Math.toRadians(kaku[i]));
			
			//軌道の切り替える

			
			}//switch end
			// if outside screen
			if(X[i]<-16 || X[i]>960+16 || Y[i]<-16 || Y[i]>540+16) Flag[i] = 99;
			//animate
			//No[i]=No[i]+FrameTime*Speed;
			//if (No[i]>=10)
				//No[i]=0;
		}
	}


	public void draw(Graphics2D g, JFrame frame){
		for (int i =0;i<Max;i++){
			if(Flag[i]!=99){
				g.drawImage(bulletPNG,
						(int)X[i],(int)Y[i],(int)X[i]+16,(int)Y[i]+16, //画面のどこに？x,y左上、右下
						16*(int)No[i],16*Ny[i],16*(int)No[i]+16,16*Ny[i]+16,//
						frame);
				}
		}
	}
}
