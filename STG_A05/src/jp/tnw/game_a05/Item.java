package jp.tnw.game_a05;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Item {
	final double FrameTime=0.017;//0.031f;
	final int Max = 15; //敵のmax number

	Font	f=new	Font("Default",Font.PLAIN,10);//使用するフォントクラス宣言
	BufferedImage ItemPNG;

	double[]	X		=	new double[Max]; //positions
	double[]	Y		=	new double[Max];
	double[]	Xsp		=	new double[Max]; // speeds
	double[]	Ysp		=	new double[Max];
	double[]	Xspg	=	new double[Max]; // speed multipliers
	double[]	Yspg	=	new double[Max];
	double[]	No		=	new double[Max];

	int[]		Ny		=	new int[Max];

	double[] 	kaku 	=	new double[Max];
	int			S_kaku	=	0;
	double[] 	kaku_P 	=	new double[Max];

	int[]		Flag	=	new int[Max];//交互切り替え
										//0 for when is waiting
										//1 for show on screen
										//
	double[] Timer = new double[8]; //time unhiding of stuff
	double timerFreq = 0.3;

	Jiki JK;

	Random rnd = new Random();

	int Speed;
	DecimalFormat df = new DecimalFormat("#.#");

	//--------------------------------
	//コンストラクタ
	//--------------------------------
	public Item(){
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
			ItemPNG = ImageIO.read(getClass().getResource("stg_image/Item.png"));//ファイル名

		} catch (IOException e) {//読み込みエラーの場合
				e.printStackTrace();
		}
	}

	//from top left
	public void Req(double Ix, double Iy,int character){
		//Timer[0]-=FrameTime;
		//if(Timer[0]<0){
			//Timer[0]=timerFreq;

			for (int i =0;i<Max;i++){
				if(Flag[i]==99){
					X[i]=Ix;	//startingpoint
					Y[i]=Iy;
					Xsp[i]=100;//初速度X start speed
					Ysp[i]=100;
					Xspg[i]=0;//初速度X
					Yspg[i]=0;
					kaku[i] = 90; //angle
					kaku_P[i]=200;
					Ny[i]=character;
					//kaku[i] = S_kaku*5; //angle
					//S_kaku++;
					Flag[i]=1; //show on screen
				break;
				}
			}//for loop end
		//} //timer end
	}

	public void move(){

		//Req(480, 0, 1);

		for (int i =0;i<Max;i++){
			if(Flag[i]!=0){
			//加速度 speed of increasing
			Xsp[i]+=(FrameTime*Xspg[i]);
			Ysp[i]+=(FrameTime*Yspg[i]);
			//初速が movingspeed
			X[i]+=(FrameTime*Xsp[i]*Math.cos(Math.toRadians(kaku[i])));
			Y[i]+=(FrameTime*Ysp[i]*Math.sin(Math.toRadians(kaku[i])));

			//円軌道
			//kaku[i]+=FrameTime*kaku_P[i];

			//軌道の切り替える
			switch(Flag[i]){
			case 1:
					kaku_P[i]=kaku_P[i]*50;
					kaku[i]=90;
					Flag[i]=2;
				break;
			}//switch end
			// if outside screen
			if(X[i]<-48 || X[i]>960+48 || Y[i]<-48 || Y[i]>540+48) Flag[i] = 99;

			double x_sa=(X[i]+10)-(JK.X+50);
			double y_sa=(Y[i]+10)-(JK.Y+50);
			double D = Math.sqrt(Math.pow(x_sa, 2)+Math.pow(y_sa, 2));
			if (D<=10+50){
				//BM.bomb_req(X[i]+24, Y[i]+24, 1);
				JK.ItemGet[Ny[i]]++;
				X[i]=-20;
				Y[i]=-20;
				if(Ny[i]==0 && JK.PW<3){
					if(JK.JT[JK.PW+1]!=null){
						JK.PW++;
					}
				}
				if(Ny[i]==2){
					if(JK.PW>0){
						JK.PW--;
					}
				}
				if(Ny[i]==3){
					JK.HP+=10;
				}
				Flag[i]=99;
			}
			//animate
			//No[i]=No[i]+FrameTime*Speed;
			//if (No[i]>=10)
			//	No[i]=0;
			}
			}
	}


	public void draw(Graphics2D g, JFrame frame){
		for (int i =0;i<Max;i++){
			if(Flag[i]!=99){
				g.drawImage(ItemPNG,
						(int)X[i],(int)Y[i],(int)X[i]+40,(int)Y[i]+40, //画面のどこに？x,y左上、右下
						40*(int)No[i],40*Ny[i],40*(int)No[i]+40,40*Ny[i]+40,//
						frame);
				}
		}
	}
}
