package jp.tnw.A18;

public class Bullet2 extends Bullet {

	int UNIT_MAX = 5000;
	Player player;

	Item item;
	double optionX[] = { 0, 0, 0, 0 };
	double optionY[] = { 0, 0, 0, 0 };

	public void request() {

		dblTimerG = dblTimerG - TIME_UNIT;
		if (dblTimerG < 0) {
			dblTimerG = jikiPower < 4 ? 0.05 : 0.02;

			if (jikiPower == 1) {

				for (int i = 0; i < UNIT_MAX; i += 2) {
					if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

						intOffsetIndex[i] = 1;
						intOffsetIndex[i + 1] = 1;
						dblOffsetX[i] = player.dblOffsetX[0] + 28 - 8;
						dblOffsetY[i] = player.dblOffsetY[0] + 28 - 8;
						dblOffsetX[i + 1] = player.dblOffsetX[0] + 68 - 8;
						dblOffsetY[i + 1] = player.dblOffsetY[0] + 28 - 8;
						dblSpdX[i] = 0;
						dblSpdY[i] = -800;
						dblSpdX[i + 1] = 0;
						dblSpdY[i + 1] = -800;
						intFlag[i] = 1;
						intFlag[i + 1] = 1;
						intObjQty += 2;

						break;
					} // if end
				} // for end

			} else if (jikiPower == 2) {

				for (int i = 0; i < UNIT_MAX; i += 2) {
					if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

						intOffsetIndex[i] = 1;
						intOffsetIndex[i + 1] = 1;
						dblOffsetX[i] = player.dblOffsetX[0] + 18 - 8;
						dblOffsetY[i] = player.dblOffsetY[0] + 28 - 8;
						dblOffsetX[i + 1] = player.dblOffsetX[0] + 78 - 8;
						dblOffsetY[i + 1] = player.dblOffsetY[0] + 28 - 8;
						dblSpdX[i] = -250;
						dblSpdY[i] = -800;
						dblSpdX[i + 1] = 250;
						dblSpdY[i + 1] = -800;
						intFlag[i] = 1;
						intFlag[i + 1] = 1;
						intObjQty += 2;

						break;
					} // if end
				} // for end

			} else if (jikiPower == 3) {

				for (int i = 0; i < UNIT_MAX; i += 4) {
					if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

						for (int j = 0; j < 4; j++) {
							intOffsetIndex[i + j] = 1;
							dblOffsetX[i + j] = player.dblOffsetX[0] + (-75 + 30 * j) + 70;
							dblOffsetY[i + j] = player.dblOffsetY[0] + 28 - 8;
							dblSpdX[i + j] = -450 + 300 * j;
							dblSpdY[i + j] = -800 + (i + 1) * 10;
							intFlag[i + j] = 1;
						}
						intObjQty += 4;

						break;
					} // if end
				} // for end

			}

		} else if (jikiPower == 4) {

			for (int i = 0; i < UNIT_MAX; i += 4) {
				if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

					for (int j = 0; j < 4; j++) {
						intOffsetIndex[i + j] = 1;
						dblOffsetX[i + j] = player.dblOffsetX[0] + (-75 + 30 * j) + 70;
						dblOffsetY[i + j] = player.dblOffsetY[0] + 28 - 8;
						dblSpdX[i + j] = 0;
						dblSpdY[i + j] = -600;

					}
					intFlag[i] = 2;
					intFlag[i + 1] = 2;
					intFlag[i + 2] = 3;
					intFlag[i + 3] = 3;
					intObjQty += 4;

					break;
				} // if end
			} // for end

		} else if (jikiPower == 5) {

			for (int i = 0; i < UNIT_MAX; i += 4) {
				if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT

					for (int j = 0; j < 4; j++) {
						intOffsetIndex[i + j] = 1;
						dblSpdX[i + j] = 0;
						dblSpdY[i + j] = -900;

					}
					dblOffsetX[i] = optionX[0];
					dblOffsetY[i] = optionY[0];
					dblOffsetX[i + 1] = optionX[1];
					dblOffsetY[i + 1] = optionY[1];
					dblOffsetX[i + 2] = optionX[2];
					dblOffsetY[i + 2] = optionY[2];
					dblOffsetX[i + 3] = optionX[3];
					dblOffsetY[i + 3] = optionY[3];
					intFlag[i] = 4;
					intFlag[i + 1] = 4;
					intFlag[i + 2] = 4;
					intFlag[i + 3] = 4;
					intObjQty += 4;

					break;
				} // if end
			} // for end

		} else if (jikiPower == 0) {
			intFlag[0] = 0;
			intFlag[1] = 0;
			intFlag[2] = 0;
			intFlag[3] = 0;
		}

	}// request end

	public void update(Enemy em,Bomb bomb,Item item) {
		super.update(em, bomb, item);

		for (int i = 0; i < 4; i++) {
			optionX[i] = 70 * Math.cos(Math.toRadians(dblAngle[i] + i * 90))
					- 70 * Math.sin(Math.toRadians(dblAngle[i] + i * 90)) + (int) player.dblOffsetX[0] + 40;
			optionY[i] = 70 * Math.sin(Math.toRadians(dblAngle[i] + i * 90))
					+ 70 * Math.cos(Math.toRadians(dblAngle[i] + i * 90)) + (int) player.dblOffsetY[0] + 50;
			dblAngle[i] -= 3;
		}

		for (int i = 0; i < UNIT_MAX; i++) {

			if (intFlag[i] == 2) {
				dblOffsetX[i] += 20;
			} else if (intFlag[i] == 3) {
				dblOffsetX[i] -= 20;
			} else if (intFlag[i] == 5) {
				dblSpdX[i] += TIME_UNIT * dblAccSpdX[i];
				dblOffsetX[i] += TIME_UNIT * dblSpdX[i];
				dblSpdY[i] += TIME_UNIT * dblAccSpdY[i];
				dblOffsetY[i] += TIME_UNIT * dblSpdY[i];
			}

			if (intTimerF[0] < 0) {
				intTimerF[0] = 8;
				if (intFlag[i] == 2) {
					intFlag[i] = 3;
				} else if (intFlag[i] == 3) {
					intFlag[i] = 2;
				}
			}

			intTimerF[0]--;

		}

	}

}
