package jp.tnw.A18;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Item extends UnitBase {

	Font f = new Font("Default", Font.CENTER_BASELINE, 25);
	int UNIT_MAX = 5;
	Player player;
	int itemText[] = new int[UNIT_MAX];
	double itemTextX[] = new double[UNIT_MAX];
	double itemTextY[] = new double[UNIT_MAX];

	Item() {

		for (int i = 0; i < UNIT_MAX; i++) {
			intOffsetIndex[i] = 1;
		}
	}

	public void drawText(Graphics2D g) {

		for (int i = 0; i < UNIT_MAX; i++) {
			if (itemText[i] > 0) {
				g.setColor(Color.GREEN);// 色指定
				g.setFont(f);
				g.drawString("ITEM GET!!", 320, 500);
			}
			itemText[i]--;
		}

	}

	public void request(double Ix, double Iy, int index) {

		if (intObjQty < UNIT_MAX) {
			dblOffsetX[intObjQty] = Ix;
			dblOffsetY[intObjQty] = Iy;
			intOffsetIndex[intObjQty] = index > 3 ? 1 : index;
			dblSpdX[intObjQty] = (Math.random() * 100 - 50) + ((Math.random() - 0.5) <= 0 ? -250 : 250);
			dblSpdY[intObjQty] = (Math.random() * 100 - 50) + ((Math.random() - 0.5) <= 0 ? -250 : 250);
			intFlag[intObjQty] = 1;
			intObjQty++;
		}

	}

	public void update() {



		for (int i = 0; i < UNIT_MAX; i++) {

			walkanime(i,12,1,3);

			dblSpdX[i] += TIME_UNIT * dblAccSpdX[i];
			dblOffsetX[i] += TIME_UNIT * dblSpdX[i];
			dblSpdY[i] += TIME_UNIT * dblAccSpdY[i];
			dblOffsetY[i] += TIME_UNIT * dblSpdY[i];

			if (intFlag[i] >= 8) {
				dblOffsetX[i] = 5000;
				dblOffsetY[i] = 5000;
				dblSpdX[i] = 0;
				dblSpdY[i] = 0;
				intObjQty--;
				intFlag[i] = 0;

			}

			if (isRoundHit(dblOffsetX[i] + 12, dblOffsetY[i] + 12, player.dblOffsetX[0] + 48, player.dblOffsetY[0] + 48,
					24, 12)) {
				dblOffsetX[i] = 5000;
				dblOffsetY[i] = 5000;
				itemTextX[i] = dblOffsetX[i] + 12;
				itemTextY[i] = dblOffsetY[i] + 12;
				itemText[i] = 100;
				dblSpdX[i] = 0;
				dblSpdY[i] = 0;
				intObjQty--;
				intFlag[i] = 0;
				Bullet.jikiPower = Bullet.jikiPower >= 5 ? 5 : Bullet.jikiPower + 1;
			}

			if (isOutScreen(i) && intFlag[i] >= 1) {
				if (dblOffsetX[i] > 800 - 80 || dblOffsetX[i] < 80) {
					dblSpdX[i] *= -1;
					intFlag[i]++;
				}
				if (dblOffsetY[i] > 600 - 80 || dblOffsetY[i] < 80) {
					dblSpdY[i] *= -1;
					intFlag[i]++;
				}

			}
			System.out.println(intObjQty);
		}
	}

	public void walkanime(int i, int t, int begin, int end){
		if (dblTimerA[i] % t == 0) {

			intOffsetIndex[i] = (intOffsetIndex[i] > end) ? begin : intOffsetIndex[i] + 1;

		}
		dblTimerA[i]++;
	}

	public boolean isOutScreen(int i) {

		boolean result;
		if (dblOffsetX[i] < -40 || dblOffsetX[i] > 800 || dblOffsetY[i] < -40 || dblOffsetY[i] > 600) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
}
