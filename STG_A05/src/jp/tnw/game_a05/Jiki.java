package jp.tnw.game_a05;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Jiki {
	BufferedImage PlayerPNG;
	final double FrameTime=0.017;//0.031f;

	int HP = 100;

	double X; //positions
	double Y;
	double No;
	int Flag=0;
	int Speed;
	int PW;

	int[] ItemGet = new int[12];

	Jtama[] JT=new Jtama[5];

	public Jiki(){
		load();
		X=100;
		Y=100;
		PW=0;
		Speed=10;
	}

	public void load(){
		try {
			PlayerPNG = ImageIO.read(getClass().getResource("stg_image/jiki1.png"));//ファイル名

		} catch (IOException e) {//読み込みエラーの場合
				e.printStackTrace();
		}
	}

	public void move(int MX, int MY){

		JT[PW].Req();
		//JT[1].Req();


		X=MX-100/2;
		Y=MY-100/2;
		switch(Flag){
		case 0:
			No+=FrameTime*Speed;
			if(No>6.5){
					//時間の概念の共有
				Flag=1;
			}
			break;
		case 1:
			No-=FrameTime*Speed;
			if(No<0.5){
				//時間の概念の共有
				Flag=0;
			}
			break;
		}
	}

	public void draw(Graphics2D g, JFrame frame){
		g.drawImage(PlayerPNG,
				(int)X,(int)Y,(int)X+100,(int)Y+100, //画面のどこに？x,y左上、右下
				0,100*(int)No,100,100*(int)No+100,//
				frame);
	}
}
