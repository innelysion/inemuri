package jp.tnw.A18;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class STGBaseObj {

	// 画像の容器
	BufferedImage image[] = new BufferedImage[1];
	// 画像の切り替え用の変数
	int imageIndex[] = new int[1];
	// 表示するユニットの最大の数
	int UNIT_MAX = 1;
	// アニメとリクエストのカウンター
	int timerAni[] = new int[UNIT_MAX];
	double timerReq[] = new double[UNIT_MAX];
	// 表示スイッチ
	boolean display[] = new boolean[UNIT_MAX];
	// 座標
	double dx[] = new double[UNIT_MAX];
	double dy[] = new double[UNIT_MAX];
	// 速度と加速度
	double spdx[] = new double[UNIT_MAX];
	double spdy[] = new double[UNIT_MAX];
	double accspdx[] = new double[UNIT_MAX];
	double accspdy[] = new double[UNIT_MAX];
	// 角度
	double angle[] = new double[UNIT_MAX];
	// 円心と軸
	double centerx[] = new double[UNIT_MAX];
	double centery[] = new double[UNIT_MAX];
	double axisx[] = new double[UNIT_MAX];
	double axisy[] = new double[UNIT_MAX];

	// ◆コンストラクター Constructor◆
	STGBaseObj() {

		for (int i = 0; i < UNIT_MAX; i++) {

			timerAni[i] = 0;
			timerReq[i] = 0;
			display[i] = false;

			dx[i] = 0;
			dy[i] = 0;

			spdx[i] = 0;
			spdy[i] = 0;
			accspdx[i] = 0;
			accspdy[i] = 0;

			angle[i] = 0;

			centerx[i] = 0;
			centery[i] = 0;
			axisx[i] = 0;
			axisy[i] = 0;

		}

	}

	// ◆画像の読み込む処理◆
	public void loadImage(String filename, int fileqty) {

		try {
			if (fileqty > 0 || fileqty <= 10000) {

				// ファイルが一つしかいない場合
				if (fileqty == 1) {

					image[0] = ImageIO.read(getClass().getResource(filename + ".png"));

				} else {
					String num = "";
					for (int i = 0; i < fileqty; i++) {

						// ファイル名のゼロの処理
						// ファイル名例：gazou_0000.png gazou_0032.png gazou_9999.png
						if (i < 10) {
							num = "000" + i;
						} else if (i < 100) {
							num = "00" + i;
						} else if (i < 1000) {
							num = "0" + i;
						}

						image[i] = ImageIO.read(getClass().getResource(filename + "_" + num + ".png"));

					}

				}

			}

		} catch (IOException e) {

			// ファイルエラーの場合
			e.printStackTrace();

		}
	}

	// ◆画像の描画処理◆
	public void drawImage(Graphics2D Graphic, JFrame Window, boolean isArrImage) {

		// 配列画像を描画

		if (isArrImage) {
			for (int i = 0; i < UNIT_MAX; i++) {
				if (display[i]) {
					Graphic.drawImage(image[imageIndex[i]], (int) dx[i], (int) dy[i], Window);
				}
			}
		} else if (image.length == 1) {

			// 配列あるいはまとめ画像ではない、シングル画像の処理
			Graphic.drawImage(image[imageIndex[0]], (int) dx[0], (int) dy[0], Window);

		}

	}

	public void drawImage(Graphics2D Graphic, JFrame Window, int widthBlock, int heightBlock) {

		// 画像が一つしかいない場合の略
		this.drawImage(Graphic, Window, widthBlock, heightBlock, 0);

	}

	public void drawImage(Graphics2D Graphic, JFrame Window, int widthBlock, int heightBlock, int arrImage) {

		// まとめ画像を描画

		for (int i = 0; i < UNIT_MAX; i++) {
			// 一コマの幅をゲット
			int blockw = image[arrImage].getWidth() / widthBlock;
			// 一コマの高さをゲット
			int blockh = image[arrImage].getHeight() / heightBlock;
			// 描画したいコマの左上端座標をゲット
			int indexw = (imageIndex[i] % widthBlock == 0) ? blockw * (widthBlock - 1)
					: blockw * ((imageIndex[i] % widthBlock) - 1);
			int indexh = (imageIndex[i] % widthBlock == 0) ? blockh * (imageIndex[i] / widthBlock - 1)
					: blockh * (imageIndex[i] / widthBlock);

			// 描画
			if (display[i]) {
				Graphic.drawImage(image[arrImage],
						/////////////////////////////////////////////////////////////////////
						// 画面に出したいところ
						(int) dx[i], // 左上端X座標
						(int) dy[i], // 左上端Y座標
						(int) dx[i] + blockw, // 右下端X座標
						(int) dy[i] + blockh, // 右下端Y座標
						// 画像のどこを使う
						indexw, // 左上端X座標
						indexh, // 左上端Y座標
						indexw + blockw, // 右下端X座標
						indexh + blockh, // 右下端Y座標
						/////////////////////////////////////////////////////////////////////
						Window);
			}
		}
	}

}
