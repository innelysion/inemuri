package jp.tnw.a18;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class $Base {

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

	// アニメーション関係
	final int UNIT_MAX = 69;
	double dblTimerG; // Timer for Anime & Generation
	double[] dblTimerA = new double[UNIT_MAX];
	boolean blnAniSwitch[] = new boolean[UNIT_MAX]; // アニメの実行スイッチ
	int intOffsetIndex[] = new int[UNIT_MAX]; // コマ
	double dblOffsetX[] = new double[UNIT_MAX]; // X座標補正
	double dblOffsetY[] = new double[UNIT_MAX]; // Y座標補正
	double dblSpdX[] = new double[UNIT_MAX]; // X速度
	double dblSpdY[] = new double[UNIT_MAX]; // Y速度
	double dblAccSpdX[] = new double[UNIT_MAX]; // X加速度
	double dblAccSpdY[] = new double[UNIT_MAX]; // Y加速度
	int intFlag[] = new int[UNIT_MAX]; // フラグ

	// ■CONSTRUCTOR■
	// デフォルト
	$Base() {

		for (int i = 0; i < UNIT_MAX; i++) {

			dblOffsetX[i] = -1000; // X座標補正
			dblOffsetY[i] = -1000; // Y座標補正

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
	public void drawImage(Graphics2D Graphic, JFrame Window) {

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
				Graphic.drawImage(Image, (int) dblOffsetX[i], (int) dblOffsetY[i], (int) dblOffsetX[i] + blockw,
						(int) dblOffsetY[i] + blockh, indexw, indexh, indexw + blockw, indexh + blockh, Window);

			}

		} else if (intFileType == 2) {

			for (int i = 0; i < UNIT_MAX; i++) {
				// indexをいじってアニメーション
				Graphic.drawImage(arrImage[intOffsetIndex[i]], (int) dblOffsetX[i], (int) dblOffsetY[i], Window);
			}

		}

	}

	// ■REQUEST■
	public void request() {

		dblTimerG = dblTimerG - TIME_UNIT;
		if (dblTimerG < 0) {
			dblTimerG = 0.1;

			for (int i = 0; i < UNIT_MAX; i++) {
				if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

					intOffsetIndex[i] = 1;
					dblOffsetX[i] = 0;
					dblOffsetY[i] = 150;
					dblSpdX[i] = 600;
					dblSpdY[i] = 0;
					dblAccSpdX[i] = 0;
					dblAccSpdY[i] = 0;
					intFlag[i] = 1;

					break;
				} // if end

			} // for end

		}

	}

	// ■UPDATE■

	public void update() {

		request();
		for (int i = 0; i < UNIT_MAX; i++) {

			if (dblTimerA[i] % 8 == 0) {

				intOffsetIndex[i] = (intOffsetIndex[i] > 9) ? 1 : intOffsetIndex[i] + 1;

			}
			dblTimerA[i]++;

			/* 统一加速度控制
			 * dblSpdX[i] = dblSpdX[i] + TIME_UNIT * dblAccSpdX[i];
			 * dblSpdY[i] = dblSpdY[i] + TIME_UNIT * dblAccSpdY[i];
			 * 
			 * 直线速度控制 
			 * dblOffsetX[i] = dblOffsetX[i] + TIME_UNIT * dblSpdX[i];
			 * dblOffsetY[i] = dblOffsetY[i] + TIME_UNIT * dblSpdY[i];
			 * 
			 * 指定出发点、速度和方向进行圆周、椭圆或螺旋运动（控制初速度、初始加速度、初始角度及控制其变化）
			 * dblOffsetX[i] += TIME_UNIT * dblSpdX[i] * Math.cos(Math.toRadians(dblAngle[i]));
			 * dblOffsetY[i] += TIME_UNIT * dblSpdY[i] * Math.sin(Math.toRadians(dblAngle[i]));
			 * dblAngle[i]++;
			 * 
			 * 指定圆心、半径的圆周运动或复杂曲线运动（直接指定位置、速度、加速度无效）（角度运动控制单位移动速度、控制角度倍率、M1~8）
			 * （M1M5&M4M8和M2M6&M3M7的基础值相等）
			 * dblOffsetX[i] = dblOffsetCirX[i] * Math.cos(Math.toRadians(dblAngle[i] * M1)) * M5 - dblOffsetCirY[i] *
			 * Math.sin(Math.toRadians(dblAngle[i] * M2)) * M6 + dblCenterX[i]; //
			 * dblOffsetY[i] = dblOffsetCirY[i] * Math.sin(Math.toRadians(dblAngle[i] * M3)) * M7 + dblOffsetCirX[i] *
			 * Math.cos(Math.toRadians(dblAngle[i] * M4)) * M8 + dblCenterY[i];
			 * dblAngle[i]++;
			 * 
			 */

		} // for end

	}

	// ■DISPOSE■
	public void dispose() {

	}

}
