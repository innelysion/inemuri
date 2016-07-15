package jp.tnw.a18;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Bullet {

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
	final int UNIT_MAX = 100;
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

	Bullet() {

		for (int i = 0; i < UNIT_MAX; i++) {

			dblOffsetX[i] = -50; // 初期座標
			dblOffsetY[i] = -50; // 初期座標

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

	public boolean is_outScreen(int i) {

		boolean result;
		if (dblOffsetX[i] < -40 || dblOffsetX[i] > 1280 || dblOffsetY[i] < -40 || dblOffsetY[i] > 1280) {
			result = true;
		} else {
			result = false;
		}
		return result;

	}

	public void request(int mx, int my) {

		dblTimerG = dblTimerG - TIME_UNIT;
		if (dblTimerG < 0) {
			dblTimerG = 0.1;

			for (int i = 0; i < UNIT_MAX; i += 3) {
				if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

					intOffsetIndex[i] = intOffsetIndex[i + 1] = intOffsetIndex[i + 2] = 1;
					dblOffsetX[i] = dblOffsetX[i + 1] = dblOffsetX[i + 2] = mx + -20;
					dblOffsetY[i] = dblOffsetY[i + 1] = dblOffsetY[i + 2] = my + -120;
					dblSpdX[i] = 0;
					dblSpdY[i] = -800;
					dblSpdX[i + 1] = 300;
					dblSpdY[i + 1] = -800;
					dblSpdX[i + 2] = -300;
					dblSpdY[i + 2] = -800;
					intFlag[i] = intFlag[i + 1] = intFlag[i + 2] = 1;
					intObjQty += 3;

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

	public boolean isOutScreen(int i) {

		boolean result;
		if (dblOffsetX[i] < -40 || dblOffsetX[i] > 1280 || dblOffsetY[i] < -40 || dblOffsetY[i] > 1280) {
			result = true;
		} else {
			result = false;
		}
		return result;

	}

	public void update(int mx, int my, Enemy En) {

		request(mx, my);

		for (int i = 0; i < UNIT_MAX; i++) {

			dblSpdX[i] = dblSpdX[i] + TIME_UNIT * dblAccSpdX[i];
			dblSpdY[i] = dblSpdY[i] + TIME_UNIT * dblAccSpdY[i];

			dblOffsetX[i] = dblOffsetX[i] + TIME_UNIT * dblSpdX[i];
			dblOffsetY[i] = dblOffsetY[i] + TIME_UNIT * dblSpdY[i];

			if (isOutScreen(i) && intFlag[i] >= 1) {
				dblOffsetX[i] = 5000;
				dblOffsetY[i] = 5000;
				dblSpdX[i] = 0;
				dblSpdY[i] = 0;
				intFlag[i] = 0;
				intObjQty--;
			}
			intTimerF[0]++;

			for (int j = 0; j < 60; j++) {

				if (isHit(dblOffsetX[i], dblOffsetY[i], En.dblOffsetX[j], En.dblOffsetY[j], 20, 20)
						&& intFlag[i] == 1) {

					En.dblOffsetX[j] = -100;
					En.dblOffsetY[j] = -100;
					dblSpdX[i] = 1500;
					dblSpdY[i] = 0;
					intFlag[i] = 2;

				}

			}

		}

	}

}
