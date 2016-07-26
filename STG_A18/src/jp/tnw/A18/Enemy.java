package jp.tnw.A18;

public class Enemy extends UnitBase {

	final int UNIT_MAX = 60;
	Player player;
	Bomb bomb;
	Item item;


	Enemy() {

		for (int i = 0; i < UNIT_MAX; i++) {

			intOffsetIndex[i] = 1;
			dblOffsetX[i] = -500; // 初期座標
			dblOffsetY[i] = -500; // 初期座標

		}

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

	public void request() {

		dblTimerG = dblTimerG - TIME_UNIT;
		if (dblTimerG < 0 && intObjQty <= 100) {
			dblTimerG = 0.02;

			for (int i = 0; i < UNIT_MAX; i++) {
				if (intFlag[i] == 0) { // ONLY FOR WATING OBJECT


					if(i % 47 == 0){
						intOffsetIndex[i] = 11;
					}else{
						intOffsetIndex[i] = 1;
					}
					dblOffsetX[i] = 400 - 24;
					dblOffsetY[i] = 200 - 24;
					dblSpdX[i] = 0;
					dblSpdY[i] = 0;
					dblAccSpdX[i] = 0;
					dblAccSpdY[i] = 0;
					dblOffsetCirX[i] = 220;
					dblOffsetCirY[i] = 220;
					dblCenterX[i] = 0;
					dblCenterY[i] = 0;
					dblAngle[i] = intTimerF[0] / 20;
					dblRadians[i] = 0;
					intFlag[i] = 1;
					intObjQty++;
					// dblTimerG -= i / 100.0;

					break;
				} // if end

			} // for end

		}
	}

	double asdf = -0.6;

	public void walkanime(int i, int t, int begin, int end){
		if (dblTimerA[i] % t == 0) {

			intOffsetIndex[i] = (intOffsetIndex[i] > end) ? begin : intOffsetIndex[i] + 1;

		}
		dblTimerA[i]++;
	}

	public void update() {

		request();
		asdf += 0.0001;
		for (int i = 0; i < UNIT_MAX; i++) {

			if(i % 47 == 0){
				walkanime(i,8,11,19);
			}else{
				walkanime(i,8,1,9);
			}


			if (isRoundHit(dblOffsetX[i] + 12, dblOffsetY[i] + 12, player.dblOffsetX[0] + 48, player.dblOffsetY[0] + 48,
					24, 12)) {
				bomb.bomb_req(dblOffsetX[i] + 24, dblOffsetY[i] + 24, 0);
				if (i % 47 == 0) {
					item.request(dblOffsetX[i] + 24, dblOffsetY[i] + 24, 1);
				}
				dblSpdX[i] = 0;
				dblSpdY[i] = 0;
				dblOffsetX[i] = 5555;
				dblOffsetY[i] = 5555;
				intFlag[i] = 0;
				intObjQty--;

			}



			if (intFlag[i] == 1) {
				dblSpdX[i] += TIME_UNIT * dblAccSpdX[i];
				dblOffsetX[i] += TIME_UNIT * dblSpdX[i];
				dblSpdX[i] = dblOffsetCirX[i] * Math.cos(Math.toRadians(dblAngle[i] * asdf))
						- dblOffsetCirY[i] * Math.sin(Math.toRadians(dblAngle[i]));

				dblSpdY[i] += TIME_UNIT * dblAccSpdY[i];
				dblOffsetY[i] += TIME_UNIT * dblSpdY[i];
				dblSpdY[i] = dblOffsetCirY[i] * Math.sin(Math.toRadians(dblAngle[i]))
						+ dblOffsetCirX[i] * Math.cos(Math.toRadians(dblAngle[i] * asdf));

			}
			// 当たり判定

			if (isOutScreen(i) && intFlag[i] == 1) {
				dblOffsetX[i] = 5000;
				dblOffsetY[i] = 5000;
				dblSpdX[i] = 0;
				dblSpdY[i] = 0;
				intFlag[i] = 0;
				intObjQty--;
			}
			intTimerF[0]++;

		} // for end

	}

}
