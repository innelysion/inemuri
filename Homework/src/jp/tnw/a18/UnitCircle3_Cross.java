package jp.tnw.a18;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class UnitCircle3_Cross {

	// ■VALUE■
	// カウンター関係
	final double TIME_UNIT = 1.0 / 60.0;

	// 画像読み込む処理関係
	Font f = new Font("Default", Font.BOLD, 12);// 使用するフォントクラス宣言
	final int ARRIMAGE_MAX = 100;
	BufferedImage Image;
	BufferedImage[] arrImage = new BufferedImage[ARRIMAGE_MAX];
	int intFileType;
	int intWidthBlockQty;
	int intHeightBlockQty;

	// アニメーション関係
	final int UNIT_MAX = 40;
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
	UnitCircle3_Cross() {

		for (int i = 0; i < 20; i++) {

			intOffsetIndex[i] = 61;
			dblOffsetX[i] = 0 + i * ((1280) / 20); // 初期座標
			dblOffsetY[i] = 0;// 初期座標

		}
		for (int i = 20; i < UNIT_MAX; i++) {

			intOffsetIndex[i] = 61;
			dblOffsetX[i] = 1280 - 48 - (i - 20) * ((1280) / 20); // 初期座標
			dblOffsetY[i] = 720 - 48; // 初期座標

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

		Graphic.setColor(Color.MAGENTA);// 色指定
		Graphic.setFont(f);
		Graphic.drawString("[X,Y]", 10, 20);
		Graphic.drawString("[SPEED_X]", 10, 30);
		Graphic.drawString("[SPEED_Y]", 10, 40);
		Graphic.drawString("[ACCSPD_X]", 10, 50);
		Graphic.drawString("[ACCSPD_Y]", 10, 60);
		Graphic.drawString("[ANGLE]", 10, 70);
		Graphic.drawString("[FLAG]", 10, 80);

		Graphic.drawString(String.valueOf((int) dblOffsetX[0]) + "," + String.valueOf((int) dblOffsetY[0]), 140, 20);
		Graphic.drawString(String.valueOf((int) dblSpdX[0]), 140, 30);
		Graphic.drawString(String.valueOf((int) dblSpdY[0]), 140, 40);
		Graphic.drawString(String.valueOf((int) dblAccSpdX[0]), 140, 50);
		Graphic.drawString(String.valueOf((int) dblAccSpdY[0]), 140, 60);
		Graphic.drawString(String.valueOf(dblAngle[0]) + "°", 140, 70);
		Graphic.drawString(String.valueOf(intFlag[0]), 140, 80);

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
		int c = 0;
		for (int i = 0; i < UNIT_MAX; i++) {
			if (intFlag[i] == 2) {
				c++;
			}
		}
		if (c == UNIT_MAX) {
			for (int i = 0; i < UNIT_MAX; i++) {
				if (i < 20) {
					dblSpdX[i] = -360;
				} else {
					dblSpdX[i] = 360;
				}
				intFlag[i]++;
			}

		}
		/*
		 * //dblTimerG = dblTimerG - TIME_UNIT; //if (dblTimerG < 0) { //
		 * dblTimerG = 0.2;
		 * 
		 * for (int i = 0; i < UNIT_MAX; i++) { if (intFlag[i] == 0) { // ONLY
		 * FOR WATING OBJECT
		 * 
		 * dblSpdX[i] = 0; dblSpdY[i] = 360; dblAccSpdX[i] = 0; dblAccSpdY[i] =
		 * 0; dblOffsetCirX[i] = 300; dblOffsetCirY[i] = 300; dblCenterX[i] =
		 * 640 - 24; dblCenterY[i] = 360 - 24; dblAngle[i] = 45; dblRadians[i] =
		 * 0; intFlag[i] = 1; intTimerF[i] = 0; // dblTimerG -= i / 100.0;
		 * 
		 * break; } // if end
		 * 
		 * // } // for end
		 * 
		 * //}
		 */
	}

	public void request2() {
		int c = 0;
		for (int i = 0; i < UNIT_MAX; i++) {
			if (intFlag[i] == 4) {
				c++;
			}
		}

		if (c == UNIT_MAX) {
			for (int i = 0; i < UNIT_MAX; i++) {
				if (i < 20) {
					dblSpdY[i] = -360;
				} else {
					dblSpdY[i] = 360;
				}
				intFlag[i]++;
			}

		}
	}

	public void reset() {
		int c = 0;
		for (int i = 0; i < UNIT_MAX; i++) {
			if (intFlag[i] == 6) {
				c++;
			}
		}

		if (c == UNIT_MAX) {
			for (int i = 0; i < UNIT_MAX; i++) {
				for (int j = 0; j < 20; j++) {

					intOffsetIndex[j] = 61;
					dblOffsetX[j] = 0 + j * ((1280) / 20); // 初期座標
					dblOffsetY[j] = 0;// 初期座標

				}
				for (int j = 20; j < UNIT_MAX; j++) {

					intOffsetIndex[j] = 61;
					dblOffsetX[j] = 1280 - 48 - (j - 20) * ((1280) / 20); // 初期座標
					dblOffsetY[j] = 720 - 48; // 初期座標

				}
					intFlag[i] = 0;
			}
		}
	}
	// ■UPDATE■

	public void update() {

		request();
		request2();
		reset();

		for (int i = 0; i < UNIT_MAX; i++) {

			if (dblTimerA[i] % 8 == 0) {

				intOffsetIndex[i] = (intOffsetIndex[i] > 77) ? 61 : intOffsetIndex[i] + 1;

			}
			dblTimerA[i]++;

			dblSpdX[i] += TIME_UNIT * dblAccSpdX[i];
			dblOffsetX[i] += TIME_UNIT * dblSpdX[i];

			dblSpdY[i] += TIME_UNIT * dblAccSpdY[i];
			dblOffsetY[i] += TIME_UNIT * dblSpdY[i];

			switch (intFlag[i]) {

			case 0:
				if (i < 20) {
					dblSpdY[i] = 360;
				} else {
					dblSpdY[i] = -360;
				}
				intFlag[i]++;
				break;
			case 1:
				if (i < 20 && dblOffsetY[i] > i * ((720 - 10) / 20)) {
					dblSpdY[i] = 0;
					intFlag[i]++;
				}
				if (i >= 20 && dblOffsetY[i] < (i - 20) * ((720 - 10) / 20)) {
					dblSpdY[i] = 0;
					intFlag[i]++;
				}
				break;
			case 3:
				if (i < 20 && dblOffsetX[i] <= 0) {
					dblOffsetX[i] = 0;
					dblSpdX[i] = 0;
					intFlag[i]++;
				}
				if (i >= 20 && dblOffsetX[i] >= 1280 - 48) {
					dblOffsetX[i] = 1280 - 48;
					dblSpdX[i] = 0;
					intFlag[i]++;
				}
				break;
			case 5:

				if (i < 20 && dblOffsetY[i] <= -48) {
					dblSpdY[i] = 0;
					intFlag[i]++;
				}
				if (i >= 20 && dblOffsetY[i] >= 720) {
					dblSpdY[i] = 0;
					intFlag[i]++;
				}

				break;
			}

		} // for end

	}

	// ■DISPOSE■
	public void dispose() {

	}

}
