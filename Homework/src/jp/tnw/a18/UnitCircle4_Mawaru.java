package jp.tnw.a18;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class UnitCircle4_Mawaru {

	// ■VALUE■
	// カウンター関係
	final double TIME_UNIT = 1.0 / 60.0;

	// 画像読み込む処理関係
	Font f = new Font("Default", Font.BOLD, 12);// 使用するフォントクラス宣言
	final int ARRIMAGE_MAX = 100;
	BufferedImage Image;
	BufferedImage[] arrImage = new BufferedImage[ARRIMAGE_MAX];
	int arrImageQty;
	int intFileType;
	int intWidthBlockQty;
	int intHeightBlockQty;

	// アニメーション関係
	final int UNIT_MAX = 9;
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
	double dblAngle[] = new double[UNIT_MAX]; // 角度
	int intFlag[] = new int[UNIT_MAX]; // フラグ

	// ■CONSTRUCTOR■
	// デフォルト
	UnitCircle4_Mawaru() {

		for (int i = 0; i < UNIT_MAX; i++) {

			dblOffsetX[i] = -1000; // 初期座標
			dblOffsetY[i] = -1000; // 初期座標

		}

	}

	// ■LOAD RESOURCE■
	public void loadImage(String filename, int fileqty) {

		intFileType = 2;
		arrImageQty = fileqty;
		try {

			String num;
			for (int i = 0; i < arrImageQty; i++) {

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

		dblTimerG = dblTimerG - TIME_UNIT;
		if (dblTimerG < 0) {
			dblTimerG = 0.32;

			for (int i = 0; i < UNIT_MAX; i++) {
				if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

					intOffsetIndex[i] = 21;
					dblOffsetX[i] = 500;
					dblOffsetY[i] = -48;
					dblSpdX[i] = 0;
					dblSpdY[i] = 220;
					dblAccSpdX[i] = 0;
					dblAccSpdY[i] = 0;
					dblAngle[i] = 90;
					intFlag[i] = 1;
					intTimerF[i] = 50;
					// dblTimerG -= i / 100.0;

					break;
				} // if end

			} // for end

		}

	}

	public void request2() {
		int c = 0;
		for (int i = 0; i < UNIT_MAX; i++) {
			if (intFlag[i] == 3) {
				c++;
			}
		}

		if (c == UNIT_MAX) {
			for (int i = 0; i < UNIT_MAX; i++) {
				dblSpdX[i] = 0;
				dblSpdY[i] = 0;
				intTimerF[i] = 140;
				intFlag[i]++;
			}

		}
	}

	// ■UPDATE■

	public void update() {

		request();
		request2();
		for (int i = 0; i < UNIT_MAX; i++) {

			if (dblTimerA[i] % 8 == 0) {

				intOffsetIndex[i] = (intOffsetIndex[i] > 29) ? 21 : intOffsetIndex[i] + 1;

			}
			dblTimerA[i]++;

			if (intFlag[i] != 0) {

				dblSpdX[i] += TIME_UNIT * dblAccSpdX[i];
				dblOffsetX[i] += TIME_UNIT * dblSpdX[i] * Math.cos(Math.toRadians(dblAngle[i]));

				dblSpdY[i] += TIME_UNIT * dblAccSpdY[i];
				dblOffsetY[i] += TIME_UNIT * dblSpdY[i] * Math.sin(Math.toRadians(dblAngle[i]));

			} // if end

			switch (intFlag[i]) {

			case 1:
				if (dblOffsetY[i] > 200) {
					dblSpdX[i] = 220;
					intFlag[i] = 2;
				}
				break;
			case 2:
				if (dblAngle[i] <= -267) {

					intFlag[i] = 3;

				}
				dblAngle[i] -= 2;
				break;
			case 3:
				dblAngle[i] -= 2;
				break;
			case 4:
				if (intTimerF[i] <= 0) {
					dblSpdX[i] = 220;
					dblSpdY[i] = 220;
					intFlag[i] = 5;
				}
				intTimerF[i]--;
				break;
			case 5:
				if (dblOffsetY[i] > 900) {
					intFlag[i] = 0;
				}
				dblAngle[i] -= 2;
				dblOffsetY[i] += 2;
				break;

			}

		} // for end

	}

	// ■DISPOSE■
	public void dispose() {

	}

}
