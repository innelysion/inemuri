package jp.tnw.a18;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class Player extends Enemy {

	final int UNIT_MAX = 1;
	int intObjQty;

	// ■IMAGE■
	public void drawFont(Graphics2D Graphic, JFrame Window) {

		Graphic.setColor(Color.BLACK);// 色指定
		Graphic.setFont(f);
		// Graphic.drawString("[MOUSE_X]", 10, 50);
		// Graphic.drawString("[MOUSE_Y]", 10, 60);
		// Graphic.drawString("[PLAYERBULLET]", 10, 70);

		// Graphic.drawString(String.valueOf((int) dblSpdX[0]), 140, 50);
		// Graphic.drawString(String.valueOf((int) dblSpdY[0]), 140, 60);
		// Graphic.drawString(String.valueOf((int) dblAccSpdX[0]), 140, 70);

	}

	public void request() {

		dblTimerG = dblTimerG - TIME_UNIT;
		if (dblTimerG < 0) {
			dblTimerG = 0.05;

			for (int i = 0; i < UNIT_MAX; i++) {
				if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

					intOffsetIndex[i] = 1;
					dblOffsetX[i] = 1280 - 100;
					dblOffsetY[i] = 720 - 100;
					// intFlag[i] = 1;
					intObjQty++;

					break;
				} // if end

			} // for end

		}

	}

	public void update(int mx, int my) {
		request();
		dblOffsetX[0] = mx - 5;
		dblOffsetY[0] = my - 100;

	}

}
