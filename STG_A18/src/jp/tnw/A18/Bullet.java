package jp.tnw.A18;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Bullet extends UnitBase {

	int UNIT_MAX = 5000;
	Player player;

	Item item;

	int score;
	static int jikiPower = 0;

	public void loadImage() {

		try {

			Image = ImageIO.read(getClass().getResource("Image/tama.png"));

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	final void drawText(Graphics2D Graphic){

		Graphic.drawString("得点:  " + String.valueOf(score), 30, 30);
	}

	public void drawImage(Graphics2D Graphic, JFrame Window) {

		// 画像を表示する
		for (int i = 0; i < UNIT_MAX; i++) {
			if (jikiPower > 1 && jikiPower < 4) {
				Graphic.drawImage(Image, (int) dblOffsetX[i], (int) dblOffsetY[i], (int) dblOffsetX[i] + 16,
						(int) dblOffsetY[i] + 16, 272, 32 - 16, 272 + 16, 32, Window);
			} else if (jikiPower >= 4) {
				Graphic.drawImage(Image, (int) dblOffsetX[i] - 8, (int) dblOffsetY[i] - 8, (int) dblOffsetX[i] + 24,
						(int) dblOffsetY[i] + 24, 0, 16 * 3, 32, 16 * 5, Window);
			} else {
				Graphic.drawImage(Image, (int) dblOffsetX[i], (int) dblOffsetY[i], (int) dblOffsetX[i] + 16,
						(int) dblOffsetY[i] + 16, 272, 32, 272 + 16, 32 + 16, Window);
			}
		}


	}

	public void request() {

		dblTimerG = dblTimerG - TIME_UNIT;
		if (dblTimerG < 0) {

			dblTimerG = jikiPower < 4 ? 0.05 : 0.03;

			for (int i = 0; i < UNIT_MAX; i++) {
				if (intFlag[i] == 0 && jikiPower != 1 && jikiPower != 4 && jikiPower != 5) { // ONLY
																								// FOR
																								// WATING
																								// OBJECT

					intOffsetIndex[i] = 1;
					dblOffsetX[i] = player.dblOffsetX[0] + 48 - 8;
					dblOffsetY[i] = player.dblOffsetY[0] + 28 - 8;
					dblSpdX[i] = 0;
					dblSpdY[i] = -800;
					intFlag[i] = 1;
					intObjQty++;

					break;
				} // if end

			} // for end

		}

	}

	public boolean isOutScreen(int i) {

		boolean result;
		if (dblOffsetX[i] < -800 || dblOffsetX[i] > 1600 || dblOffsetY[i] < -200 || dblOffsetY[i] > 800) {
			result = true;
		} else {
			result = false;
		}
		return result;

	}

	public void update(Enemy em,Bomb bomb,Item item) {

		request();

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

			for (int j = 0; j < 60; j++) {
				if (isRoundHit(dblOffsetX[i] + 12, dblOffsetY[i] + 12, em.dblOffsetX[j] + 24, em.dblOffsetY[j] + 24, 12, 12)) {
					bomb.bomb_req(em.dblOffsetX[j] + 24, em.dblOffsetY[j] + 24, 0);
					 if (j % 47 == 0) {

					 item.request(em.dblOffsetX[j] + 24, em.dblOffsetY[j] + 24, 1);
					 }
					em.dblOffsetX[j] += 5000;
					em.dblOffsetY[j] += 5000;
//					em.dblSpdX[i] = 0;
//					em.dblSpdY[i] = 0;


						dblOffsetX[i] = 5000;
						dblOffsetY[i] = 5000;
						dblSpdX[i] = 0;
						dblSpdY[i] = 0;
						intFlag[i] = 0;
						intObjQty--;

				}
			}
		}

	}

}
