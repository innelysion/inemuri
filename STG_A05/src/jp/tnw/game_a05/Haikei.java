package jp.tnw.game_a05;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Haikei {
	BufferedImage BG;
	double X; //positions
	double Y;
	double X2; //positions
	double Y2;

	public Haikei(){
		load();
		X=0;
		Y=0;
		X2=960;
		Y2=540;
	}

	public Haikei(int bg_sel){
		load();

		X=0;
		Y=0;
		X2=960;
		Y2=540;
	}

	public void load(){
		try {
			BG = ImageIO.read(getClass().getResource("stg_image/bg_01.png"));//ファイル名

		} catch (IOException e) {//読み込みエラーの場合
				e.printStackTrace();
		}
	}

	public void draw(Graphics2D g, JFrame frame){
		g.drawImage(BG,(int)X,(int)Y,(int)X2,(int)Y2,frame);
}
}
