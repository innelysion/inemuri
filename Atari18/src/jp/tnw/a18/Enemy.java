package jp.tnw.a18;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Enemy {

	// ■VALUE■
	// カウンター関係
	final double TIME_UNIT = 1.0 / 60.0;

	// 画像読み込む処理関係
	Font f = new Font("Default", Font.PLAIN, 11);// 使用するフォントクラス宣言
	final int ARRIMAGE_MAX = 100;
	BufferedImage Image;
	BufferedImage[] arrImage = new BufferedImage[ARRIMAGE_MAX];
	int intFileType;
	int intWidthBlockQty;
	int intHeightBlockQty;

	// アニメーション関係
	final int UNIT_MAX = 60;
	double dblTimerG; // Timer for Generation
	int intObjQty; // 表示しているオブジェクト
	double[] dblTimerA = new double[UNIT_MAX]; // Timer for Animation
	int[] intTimerF = new int[UNIT_MAX]; // Timer for Animation Flag
	boolean blnAniSwitch[] = new boolean[UNIT_MAX]; // アニメの実行スイッチ
	int intOffsetIndex[] = new int[UNIT_MAX]; // コマ
	double dblOffsetX[] = new double[UNIT_MAX]; // X座標補正
	double dblOffsetY[] = new double[UNIT_MAX]; // Y座標補正
	double dblSpdX[] = new double[UNIT_MAX]; // X速度
	double dblSpdY[] = new double[UNIT_MAX]; // Y速度
	double dblAccSpdX[] = new double[UNIT_MAX]; // X加速度
	double dblAccSpdY[] = new double[UNIT_MAX]; // Y加速度
	double dblOffsetCirX[] = new double[UNIT_MAX];
	double dblOffsetCirY[] = new double[UNIT_MAX];
	double dblCenterX[] = new double[UNIT_MAX];
	double dblCenterY[] = new double[UNIT_MAX];
	double dblAngle[] = new double[UNIT_MAX]; // 角度
	double dblRadians[] = new double[UNIT_MAX];

	int intFlag[] = new int[UNIT_MAX]; // フラグ

	// ■CONSTRUCTOR■
	// デフォルト
	Enemy() {

		for (int i = 0; i < UNIT_MAX; i++) {

			dblOffsetX[i] = 0; // 初期座標
			dblOffsetY[i] = 0; // 初期座標

		}

	}

	// ■LOAD RESOURCE■
	public void loadImage(String filename, int fileqty) {

		intFileType = 2;

		try {

			String num;
			for (int i = 0; i < fileqty; i++) {

				num = (i < 10) ? "0" + i : "" + i;// ファイル名の順番が10に足りない場合0をつく
				arrImage[i] = ImageIO.read(getClass().getResource(filename + "_" + num + ".png"));

			}

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public void loadImage(String filename, int widthblock, int heightblock) {

		intFileType = 1;
		intWidthBlockQty = widthblock;
		intHeightBlockQty = heightblock;
		try {

			Image = ImageIO.read(getClass().getResource(filename + ".png"));

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	// ■IMAGE■
	public void drawImage(Graphics2D Graphic, JFrame Window, int x, int y) {

		if (intFileType == 1) {

			for (int i = 0; i < UNIT_MAX; i++) {

				// indexをいじってアニメーション
				// 一コマの幅と高さを計算する
				int blockw = Image.getWidth() / intWidthBlockQty;
				int blockh = Image.getHeight() / intHeightBlockQty;
				int indexw = (intOffsetIndex[i] % intWidthBlockQty == 0) ? blockw * (intWidthBlockQty - 1)
						: blockw * ((intOffsetIndex[i] % intWidthBlockQty) - 1);
				int indexh = (intOffsetIndex[i] % intWidthBlockQty == 0)
						? blockh * (intOffsetIndex[i] / intWidthBlockQty - 1)
						: blockh * (intOffsetIndex[i] / intWidthBlockQty);

				// 画像を表示する
				Graphic.drawImage(Image, x + (int) dblOffsetX[i], y + (int) dblOffsetY[i],
						x + (int) dblOffsetX[i] + blockw, y + (int) dblOffsetY[i] + blockh, indexw, indexh,
						indexw + blockw, indexh + blockh, Window);

			}

		} else if (intFileType == 2) {

			for (int i = 0; i < UNIT_MAX; i++) {
				// indexをいじってアニメーション
				Graphic.drawImage(arrImage[intOffsetIndex[i]], x + (int) dblOffsetX[i], y + (int) dblOffsetY[i],
						Window);
			}

		}

	}

	public void drawFont(Graphics2D Graphic, JFrame Window) {

		Graphic.setColor(Color.BLACK);// 色指定
		Graphic.setFont(f);
		Graphic.drawString("QTY", 10, 30);
		// Graphic.drawString("[SPEED_X]", 10, 30);
		// Graphic.drawString("[SPEED_Y]", 10, 40);
		// Graphic.drawString("[ACCSPD_X]", 10, 50);
		// Graphic.drawString("[ACCSPD_Y]", 10, 60);
		// Graphic.drawString("[ANGLE]", 10, 70);
		// Graphic.drawString("[Y_BETWEEN 1 & 0]", 10, 80);

		Graphic.drawString(String.valueOf(intObjQty), 140, 30);

	}

	public boolean isOutScreen(int i) {

		boolean result;
		if (dblOffsetX[i] < -40 || dblOffsetX[i] > 1280 || dblOffsetY[i] < -40 || dblOffsetY[i] > 1280) {
			result = true;
		} else {
			result = false;
		}
		return result;

	}

	// ■REQUEST■
	public void request() {

		dblTimerG = dblTimerG - TIME_UNIT;
		if (dblTimerG < 0 && intObjQty <= 100) {
			dblTimerG = 0.02;

			for (int i = 0; i < UNIT_MAX; i++) {
				if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

					intOffsetIndex[i] = 1;
					dblOffsetX[i] = 640 - 24;
					dblOffsetY[i] = 320 - 24;
					dblSpdX[i] = 0;
					dblSpdY[i] = 0;
					dblAccSpdX[i] = 0;
					dblAccSpdY[i] = 0;
					dblOffsetCirX[i] = 250;
					dblOffsetCirY[i] = 250;
					dblCenterX[i] = 0;
					dblCenterY[i] = 0;
					dblAngle[i] = intTimerF[0] / 20;
					dblRadians[i] = 0;
					intFlag[i] = 1;
					intObjQty++;
					// dblTimerG -= i / 100.0;

					break;
				} // if end

			} // for end

		}

	}

	public boolean isHit(double x1, double y1, double x2, double y2, double rad1, double rad2) {

		double dblSaX = Math.pow(((x1 + rad1) - (x2 + rad2)), 2);
		double dblSaY = Math.pow(((y1 + rad1) - (y2 + rad2)), 2);
		return Math.sqrt(dblSaX + dblSaY) <= rad1 + rad2;

	}

	public boolean isBoxHit(double x1, double y1, double x2, double y2, double rad1x, double rad2x, double rad1y, double rad2y) {

		double dblSaX = Math.abs((x1 + rad1x) - (x2 + rad2x));
		double dblSaY = Math.abs((y1 + rad1y) - (y2 + rad2y));
		return (dblSaX <= rad1x + rad2x) && (dblSaY < rad1y + rad2y);

	}

	// ■UPDATE■
	double asdf = 0.8;
	public void update(Player jiki, int mb, Bomb b) {

		request();
		asdf+= 0.00001;
		for (int i = 0; i < UNIT_MAX; i++) {
			if (intFlag[i] == 1) {
				dblSpdX[i] += TIME_UNIT * dblAccSpdX[i];
				dblOffsetX[i] += TIME_UNIT * dblSpdX[i];
				dblSpdX[i] = dblOffsetCirX[i] * Math.cos(Math.toRadians(dblAngle[i] * asdf))
						- dblOffsetCirY[i] * Math.sin(Math.toRadians(dblAngle[i]));

				dblSpdY[i] += TIME_UNIT * dblAccSpdY[i];
				dblOffsetY[i] += TIME_UNIT * dblSpdY[i];
				dblSpdY[i] = dblOffsetCirY[i] * Math.sin(Math.toRadians(dblAngle[i]))
						+ dblOffsetCirX[i] * Math.cos(Math.toRadians(dblAngle[i] * asdf));


			}
			// 当たり判定
//			double dblSaX = Math.pow((dblOffsetX[i] + 20 - jiki.dblOffsetX[0] - 40), 2);
//			double dblSaY = Math.pow((dblOffsetY[i] + 20 - jiki.dblOffsetY[0] - 40), 2);
//			double Range = Math.sqrt(dblSaX + dblSaY);

			if (isBoxHit(dblOffsetX[i], dblOffsetY[i], jiki.dblOffsetX[0], jiki.dblOffsetY[0], 20, 5, 20, 100)) {
				dblSpdX[i] = 0;
				dblSpdY[i] = 0;
				if (mb == 1) {
					b.bomb_req(15, 15, 0);
					dblOffsetX[i] = 5555;
					dblOffsetY[i] = 5555;
					intFlag[i] = 0;
					intObjQty--;

				}
			}

			if (isOutScreen(i) && intFlag[i] == 1) {
				dblOffsetX[i] = 5000;
				dblOffsetY[i] = 5000;
				dblSpdX[i] = 0;
				dblSpdY[i] = 0;
				intFlag[i] = 0;
				intObjQty--;
			}
			intTimerF[0]++;

		} // for end

	}

	// ■DISPOSE■
	public void dispose() {

	}

}
