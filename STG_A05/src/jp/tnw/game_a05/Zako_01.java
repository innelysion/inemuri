package jp.tnw.game_a05;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Zako_01 {
	final double FrameTime=0.017;//0.031f;
	final int Max = 15; //敵のmax number

	Font	f=new	Font("Default",Font.PLAIN,10);//使用するフォントクラス宣言
	BufferedImage ZakoPNG;
	BufferedImage ItemPNG;

	double[]	X		=	new double[Max]; //positions
	double[]	Y		=	new double[Max];
	double[]	Xsp	=	new double[Max]; // speeds
	double[]	Ysp	=	new double[Max];
	double[]	Xspg	=	new double[Max]; // speed multipliers
	double[]	Yspg	=	new double[Max];
	double[]	No		=	new double[Max];

	int[]		Ny		=	new int[Max];

	double[] kaku = new double[Max];
	int			S_kaku=0;
	double[] kaku_P = new double[Max];

	int[]		Flag	=	new int[Max];//交互切り替え
										//0 for when is waiting
										//1 for show on screen
										//
	double[] Timer = new double[8]; //time unhiding of stuff
	double timerFreq = 0.1;

	Jiki JK;
	Jtama[] JT = new Jtama[10];
	Bomb BM;
	Item IT;
	
	int Speed;
	DecimalFormat df = new DecimalFormat("#.#");

	//--------------------------------
	//コンストラクタ
	//--------------------------------
	public Zako_01(){
		load();
		for (int i =0;i<Max;i++){
		X[i]=-48;
		Y[i]=-48;
		Xsp[i]=0;//初速度X
		Ysp[i]=0;
		Xspg[i]=0;//初速度X
		Yspg[i]=0;
		kaku[i]=0;
		Flag[i]=99;
		}
		Speed=10;
	}

	//--------------------------------
	//ZAKOのクラス
	//--------------------------------
	public void load(){
		try {
			ZakoPNG = ImageIO.read(getClass().getResource("stg_image/zako.png"));//ファイル名
			ItemPNG = ImageIO.read(getClass().getResource("stg_image/Item.png"));
		} catch (IOException e) {//読み込みエラーの場合
				e.printStackTrace();
		}
	}

	//from top left
	public void Req(int character){
		Timer[0]-=FrameTime;
		if(Timer[0]<0){
			Timer[0]=timerFreq;
			for (int i =0;i<Max;i++){
				if(Flag[i]==99){
					X[i]=960+48;	//startingpoint
					Y[i]=100;
					Xsp[i]=-500;//初速度X start speed
					Ysp[i]=0;
					Xspg[i]=0;//初速度X speed multiplicator
					Yspg[i]=0;//-270/2;

					kaku[i] = 0; //angle
					kaku_P[i]=200;
					Ny[i]=character;
					//kaku[i] = S_kaku*5; //angle
					//S_kaku++;
					Flag[i]=1; //show on screen
				break;
				}
			}
		}
	}



	public void move(){

		Req(1);

		for (int i =0;i<Max;i++){
			if(Flag[i]!=99 && Flag[i]!=80){
			//加速度 speed of increasing
			Xsp[i]+=(FrameTime*Xspg[i]);
			Ysp[i]+=(FrameTime*Yspg[i]);
			//初速が movingspeed
			X[i]+=(FrameTime*Xsp[i]*Math.cos(Math.toRadians(kaku[i])));
			Y[i]+=(FrameTime*Ysp[i]*Math.sin(Math.toRadians(kaku[i])));

			//円軌道
			kaku[i]+=FrameTime*kaku_P[i];

			//軌道の切り替える
			switch(Flag[i]){
			case 1:
					kaku_P[i]=kaku_P[i]*0;
					kaku[i]=0;
					Flag[i]=2;
				break;
			case 2:
				if(X[i]<=200-48){
					kaku_P[i]=200;
					kaku[i]=0;
					Ysp[i]=500;
					Flag[i]=3;
				}
				break;
			case 3:
				if(kaku[i]>=360+270){
					kaku_P[i]=kaku_P[i]*-1;
					Flag[i]=4;
				}
				break;
			case 4:
				if(kaku[i]<=90){
					kaku_P[i]=kaku_P[i]*-1;
					Flag[i]=5;
				}
				break;
			case 5:
				if(kaku[i]>=360+270){
					kaku_P[i]=0;
					Xsp[i]=0;
					//Ysp[i]=400;
					Flag[i]=5;
				}
				break;
			}//switch end

			//player hit detect
			double x_sa=(X[i]+16)-(JK.X+50);
			double y_sa=(Y[i]+16)-(JK.Y+50);
			double D = Math.sqrt(Math.pow(x_sa, 2)+Math.pow(y_sa, 2));
			if (D<=24+16){
				BM.bomb_req(X[i]+24, Y[i]+24, 1);
				JK.HP-=10;
				Flag[i]=99;
			}

			if(JT[JK.PW].Hit(X[i],Y[i])){
				Flag[i]=80;
				BM.bomb_req(X[i]+24, Y[i]+24, 2);
				IT.Req(X[i],Y[i],IT.rnd.nextInt(4));
			}



			// if outside screen
			if(X[i]<-48 || X[i]>960+48 || Y[i]<-48 || Y[i]>540+48) Flag[i] = 99;


			//animate
			No[i]=No[i]+FrameTime*Speed;
			if (No[i]>=10)
				No[i]=0;
			}
			}
	}


	public void draw(Graphics2D g, JFrame frame){
		for (int i =0;i<Max;i++){
			if(Flag[i]!=99 && Flag[i]!=80){
				g.drawImage(ZakoPNG,
						(int)X[i],(int)Y[i],(int)X[i]+48,(int)Y[i]+48, //画面のどこに？x,y左上、右下
						48*(int)No[i],48*Ny[i],48*(int)No[i]+48,48*Ny[i]+48,//
						frame);
				}
		}
	}
}
