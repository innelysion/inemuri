package jp.tnw.gameA18;

import java.awt.Font;
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
	int arrImageQty;
	int intFileType;
	int intWidthBlockQty;
	int intHeightBlockQty;
	Font f = new Font("Default", Font.BOLD, 24);// 使用するフォントクラス宣言
	Font f2 = new Font("Default", Font.BOLD, 12);// 使用するフォントクラス宣言
	// アニメーション関係
	final int UNIT_MAX = 1;
	double dblTimerG; // Timer for Generation
	double[] dblTimerA = new double[UNIT_MAX]; // Timer for Animation
	int[] intTimerF = new int[UNIT_MAX]; // Timer for Animation Flag
	boolean blnAniSwitch[] = new boolean[UNIT_MAX]; // アニメの実行スイッチ
	int intOffsetIndex[] = new int[UNIT_MAX]; // コマ
	static double dblOffsetX[] = new double[1]; // X座標補正
	static double dblOffsetY[] = new double[1]; // Y座標補正
	double dblSpdX[] = new double[UNIT_MAX]; // X速度
	double dblSpdY[] = new double[UNIT_MAX]; // Y速度
	double dblAccSpdX[] = new double[UNIT_MAX]; // X加速度
	double dblAccSpdY[] = new double[UNIT_MAX]; // Y加速度
	double dblAngle[] = new double[UNIT_MAX]; // 角度
	int intFlag[] = new int[UNIT_MAX]; // フラグ
	static int timerText = 0;

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

//		Graphic.setColor(Color.MAGENTA);// 色指定
//		Graphic.setFont(f);
//		if (timerText > 0){
//			Graphic.drawString("気分転換!!", (int)UnitCircle.dblOffsetX[0] - 33, (int)UnitCircle.dblOffsetY[0] + 80);
//		}
//		Graphic.setFont(f2);
//		Graphic.drawString("★矢印キーで移動　★SHIFTキーで低速移動　★Zキーで射撃　★Xキーで強制パワーアップ", 10, 15);
//		Graphic.drawString("A18  チョウ カンフ", GVar.windSizeX - 115, GVar.windSizeY - 15);
//		Graphic.setColor(Color.WHITE);// 色指定
//		Graphic.drawString("[POWER]", 10, 30);
//		Graphic.drawString("[DIR8]", 10, 40);
//		Graphic.drawString("[DIR4]", 10, 50);
//		// Graphic.drawString("[ACCSPD_Y]", 10, 60);
//		// Graphic.drawString("[ANGLE]", 10, 70);
//		// Graphic.drawString("[FLAG]", 10, 80);
//
////		Graphic.drawString(String.valueOf(Input.LEFT) + "," + String.valueOf(Input.UP) + ","
////				+ String.valueOf(Input.RIGHT) + "," + String.valueOf(Input.DOWN), 140, 20);
//		Graphic.drawString(String.valueOf(GVar.energy), 140, 30);
//		Graphic.drawString(String.valueOf(Input.DIR8), 140, 40);
//		Graphic.drawString(String.valueOf(Input.DIR4), 140, 50);
//		// Graphic.drawString(String.valueOf((int) dblOffsetX[1]) + "," +
//		// String.valueOf((int) dblOffsetY[1]), 140, 20);
//		// Graphic.drawString(String.valueOf((int) dblSpdX[1]), 140, 30);
//		// Graphic.drawString(String.valueOf((int) dblSpdY[1]), 140, 40);
//		// Graphic.drawString(String.valueOf((int) dblAccSpdX[1]), 140, 50);
//		// Graphic.drawString(String.valueOf((int) dblAccSpdY[1]), 140, 60);
//		// Graphic.drawString(String.valueOf(dblAngle[1]) + "°", 140, 70);
//		// Graphic.drawString(String.valueOf(intFlag[1]), 140, 80);

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
			dblTimerG = 0.2;

			for (int i = 0; i < UNIT_MAX; i++) {
				if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

					intOffsetIndex[i] = 31;
					dblOffsetX[i] = 640 - 24;
					dblOffsetY[i] = 360 - 24;
					dblSpdX[i] = 0;
					dblSpdY[i] = 0;
					dblAccSpdX[i] = 0;
					dblAccSpdY[i] = 0;
					intFlag[i] = 1;
					intTimerF[i] = 0;
					// dblTimerG -= i / 100.0;

					break;
				} // if end

			} // for end

		}

	}

	// ■UPDATE■

	public void update() {

		request();
		timerText --;
		for (int i = 0; i < UNIT_MAX; i++) {
			double SPD;
			double SPM = 0.9;
			int DELAY = 2;

			SPD = Input.K_SHIFT ? 3.2 : 7.2;
			if (dblTimerA[i] % 8 == 0) {
				intOffsetIndex[i] = (intOffsetIndex[i] > 39) ? 31 : intOffsetIndex[i] + 1;
			}
			dblTimerA[i]++;

			dblSpdX[i] += TIME_UNIT * dblAccSpdX[i];
			dblOffsetX[i] += TIME_UNIT * dblSpdX[i] * Math.cos(Math.toRadians(dblAngle[i]));

			dblSpdY[i] += TIME_UNIT * dblAccSpdY[i];
			dblOffsetY[i] += TIME_UNIT * dblSpdY[i] * Math.sin(Math.toRadians(dblAngle[i]));

			switch (Input.DIR8) {
			case 1:
				if (intTimerF[i] > 0) {
					dblOffsetX[i] += -SPD;
				} else {
					dblOffsetX[i] += -SPD / 4;
				}
				intTimerF[i] = DELAY;
				break;
			case 3:
				if (intTimerF[i] > 0) {
					dblOffsetY[i] += -SPD;
				} else {
					dblOffsetY[i] += -SPD / 4;
				}
				intTimerF[i] = DELAY;
				break;
			case 5:
				if (intTimerF[i] > 0) {
					dblOffsetX[i] += SPD;
				} else {
					dblOffsetX[i] += SPD / 4;
				}
				intTimerF[i] = DELAY;
				break;
			case 7:
				dblOffsetY[i] += SPD;
				intTimerF[i] = DELAY;
				break;
			case 2:
				dblOffsetX[i] += -(SPD * SPM);
				dblOffsetY[i] += -(SPD * SPM);
				intTimerF[i] = DELAY;
				break;
			case 4:
				dblOffsetX[i] += (SPD * SPM);
				dblOffsetY[i] += -(SPD * SPM);
				intTimerF[i] = DELAY;
				break;
			case 6:
				dblOffsetX[i] += (SPD * SPM);
				dblOffsetY[i] += (SPD * SPM);
				intTimerF[i] = DELAY;
				break;
			case 8:
				dblOffsetX[i] += -(SPD * SPM);
				dblOffsetY[i] += (SPD * SPM);
				intTimerF[i] = DELAY;
				break;

			}

			if (dblOffsetX[i] < 0){

				dblOffsetX[i] = 0;

			}
			if (dblOffsetX[i] > GVar.windSizeX - 48){

				dblOffsetX[i] = GVar.windSizeX - 48;

			}
			if (dblOffsetY[i] < 0){

				dblOffsetY[i] = 0;

			}
			if (dblOffsetY[i] > GVar.windSizeY - 48){

				dblOffsetY[i] = GVar.windSizeY - 48;

			}

			intTimerF[i] = intTimerF[i] <= 0 ? 0 : intTimerF[i] - 1;
			/*
			 * switch (intFlag[i]){
			 *
			 * case 1: if(dblOffsetX[i] < 180){ dblSpdY[i] = -640; intFlag[i] =
			 * 2; } break; case 2: if(dblAngle[i] <= -626){
			 *
			 * intFlag[i] = 3;
			 *
			 * } dblAngle[i] -= 4; break; case 3: if(dblAngle[i] >= -94){
			 *
			 * intFlag[i] = 4;
			 *
			 * } dblAngle[i] += 4; break; case 4: if(dblAngle[i] <= -986){
			 * dblSpdX[i] = 0; dblAccSpdY[i] = 550; intFlag[i] = 5; }
			 * dblAngle[i] -= 4; break; case 5: if(dblOffsetY[i] <= 0){
			 * dblSpdX[i] = -940; dblSpdY[i] = 640 + i * 90; dblAccSpdX[i] =
			 * -10; dblAccSpdY[i] = -5; dblAngle[i] = 45; intFlag[i] = 6;
			 * intTimerF[i] = 20; } break; case 6: if(intTimerF[i] <= 0){
			 * intFlag[i] = 7; } intTimerF[i]--; break; case 7: dblAngle[i] += 1
			 * + i; break;
			 *
			 * }
			 */

		} // for end

	}

	// ■DISPOSE■
	public void dispose() {

	}

}
