package jp.tnw.a18;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class UnitCircle {

	// ■VALUE■
	// カウンター関係
	final double TIME_UNIT = 1.0 / 60.0;

	// 画像読み込む処理関係
	final int ARRIMAGE_MAX = 100;
	BufferedImage Image;
	BufferedImage[] arrImage = new BufferedImage[ARRIMAGE_MAX];
	int intFileType;
	int intWidthBlockQty;
	int intHeightBlockQty;

	// アニメーション関係
	final int UNIT_MAX = 36;
	double dblTimerG; // Timer for Generation
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
	UnitCircle() {

		for (int i = 0; i < UNIT_MAX; i++) {

			dblOffsetX[i] = -1000; // 初期座標
			dblOffsetY[i] = -1000; // 初期座標

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

	// ■REQUEST■
	public void request() {

		dblTimerG = dblTimerG - TIME_UNIT;
		if (dblTimerG < 0) {
			dblTimerG = 0.05;

			for (int i = 0; i < UNIT_MAX; i++) {
				if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

					intOffsetIndex[i] = 31;
					dblOffsetX[i] = 640-24;
					dblOffsetY[i] = 720;
					dblSpdX[i] = 0;
					dblSpdY[i] = -1440;
					dblAccSpdX[i] = 0;
					dblAccSpdY[i] = 0;
					dblOffsetCirX[i] = 0;
					dblOffsetCirY[i] = 0;
					dblCenterX[i] = 0;
					dblCenterY[i] = 0;
					dblAngle[i] = 0;
					dblRadians[i] = 0;
					intFlag[i] = 1;
					intTimerF[i] = 0;
					// dblTimerG -= i / 100.0;

					break;
				} // if end

			} // for end

		}

	}

	private void requestTwo() {

		int c = 0;
		for(int i = 0; i < UNIT_MAX; i++) {
			if (intFlag[i] == 2){
				c++;
			}
		}

		if (c == 36){

			for(int i = 0; i < UNIT_MAX; i++) {
				intFlag[i] = 3;
				dblSpdX[i] = 200 * Math.cos(Math.toRadians(45 + i * 10))
						- 200 * Math.sin(Math.toRadians(45 + i * 10));
				dblSpdY[i] = 200 * Math.sin(Math.toRadians(45 + i * 10))
						+ 200 * Math.cos(Math.toRadians(45 + i * 10));


				intTimerF[i] = 55;

			}

		}

	}

	// ■UPDATE■

	public void update() {

		request();
		requestTwo();

		for (int i = 0; i < UNIT_MAX; i++) {

			if (dblTimerA[i] % 8 == 0) {

				intOffsetIndex[i] = (intOffsetIndex[i] > 39) ? 31 : intOffsetIndex[i] + 1;

			}
			dblTimerA[i]++;

			if (intFlag[i] != 0) {

				dblSpdX[i] += TIME_UNIT * dblAccSpdX[i];
				dblOffsetX[i] += TIME_UNIT * dblSpdX[i];
				//dblOffsetX[i] = dblOffsetCirX[i] * Math.cos(Math.toRadians(dblAngle[i]))
				//		- dblOffsetCirY[i] * Math.sin(Math.toRadians(dblAngle[i] * 0.91)) + dblCenterX[i];
				// dblOffsetX[i] += TIME_UNIT * dblSpdX[i] *
				// Math.cos(Math.toRadians(dblAngle[i]));

				dblSpdY[i] += TIME_UNIT * dblAccSpdY[i];
				dblOffsetY[i] += TIME_UNIT * dblSpdY[i];
				//dblOffsetY[i] = dblOffsetCirY[i] * Math.sin(Math.toRadians(dblAngle[i]))
				//		+ dblOffsetCirX[i] * Math.cos(Math.toRadians(dblAngle[i] * 0.91)) + dblCenterY[i];
				// dblOffsetY[i] += TIME_UNIT * dblSpdY[i] *
				// Math.sin(Math.toRadians(dblAngle[i]));

			} // if end

			switch (intFlag[i]){

			case 1:

				if(dblOffsetY[i] < 320){
					dblSpdX[i] = 0;
					dblSpdY[i] = 0;
					intFlag[i] = 2;
				}

				break;
			case 3:

				if(intTimerF[i] <= 0){
					dblAngle[i]++;
					dblAccSpdX[i] = -((800.0 * (1 + (i / 50.0))) * Math.cos(Math.toRadians(45 + i * 10))
							- (800.0 * (1 + (i / 50.0))) * Math.sin(Math.toRadians(45 + i * 10)));
					dblAccSpdY[i] = -((800.0 * (1 + (i / 50.0))) * Math.sin(Math.toRadians(45 + i * 10))
							+ (800.0 * (1 + (i / 50.0))) * Math.cos(Math.toRadians(45 + i * 10)));
				}
				intTimerF[i]--;

				break;

			}

		} // for end

	}



	// ■DISPOSE■
	public void dispose() {

	}

}
