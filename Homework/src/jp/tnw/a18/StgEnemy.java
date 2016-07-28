package jp.tnw.a18;

public class StgEnemy extends StgUnitBase {
	int UNIT_MAX = 50;

	double spdX[] = new double[UNIT_MAX];
	double spdY[] = new double[UNIT_MAX];
	double accX[] = new double[UNIT_MAX];
	double accY[] = new double[UNIT_MAX];
	// 角度
	double angle[] = new double[UNIT_MAX];
	// 円心と軸
	double centerX[] = new double[UNIT_MAX];
	double centerY[] = new double[UNIT_MAX];
	double axisX[] = new double[UNIT_MAX];
	double axisY[] = new double[UNIT_MAX];
	// 当たり判定
	boolean isHitable[] = new boolean[UNIT_MAX];
	double hitCir[] = new double[UNIT_MAX];
	double hitBoxW[] = new double[UNIT_MAX];
	double hitBoxH[] = new double[UNIT_MAX];

	int flag[] = new int[UNIT_MAX];
	int bulletType[] = new int[UNIT_MAX];
	int itemCount = 0;
	boolean itemFlag[] = new boolean[UNIT_MAX];

	StgEnemy() {

		for (int i = 0; i < UNIT_MAX; i++) {

			timerAni[i] = 0;
			timerReq[i] = 0;

			spdX[i] = 0;
			spdY[i] = 0;
			accX[i] = 0;
			accY[i] = 0;

			angle[i] = 0;

			centerX[i] = 0;
			centerY[i] = 0;
			axisX[i] = 0;
			axisY[i] = 0;

			isHitable[i] = false;
			hitCir[i] = 16;
			hitBoxW[i] = 16;
			hitBoxH[i] = 16;

		}

	}

	public void request() {

		timerReq[0] = timerReq[0] - GVar.frameTime;
		if (timerReq[0] < 0) {
			timerReq[0] = 0.3;

			int cnt = 2;

			for (int i = 0; i < UNIT_MAX; i++) {
				if (flag[i] == 0) { // ONLY FOR WATING OBJECT

					if (itemCount % 23 == 0){
						imageIndex[i] = 11;
						itemFlag[i] = true;
					}else{
						imageIndex[i] = 1;
					}

					if (cnt == 2) {
						dX[i] = 0;
						dY[i] = 48;
						spdX[i] = 150;
						spdY[i] = 0;
					} else if (cnt == 1) {
						dX[i] = GVar.windSizeX - 48;
						dY[i] = 128;
						spdX[i] = -150;
						spdY[i] = 0;
					}

					accX[i] = 0;
					accY[i] = 0;
					isVisible[i] = true;
					isHitable[i] = true;
					flag[i] = 1;
					itemCount++;

					cnt--;

					if (cnt == 0) {
						break;
					}
				} // if end

			} // for end

		}

	}

	public void update(StgBullet bullet, VFX bom, StgItem item) {

		request();

		for (int i = 0; i < UNIT_MAX; i++) {

			if (timerAni[i] % 8 == 0) {
				if (itemFlag[i]) {
					imageIndex[i] = (imageIndex[i] > 19) ? 11 : imageIndex[i] + 1;
				} else {
					imageIndex[i] = (imageIndex[i] > 9) ? 1 : imageIndex[i] + 1;
				}

			}
			timerAni[i]++;

			spdX[i] += GVar.frameTime * accX[i];
			spdY[i] += GVar.frameTime * accY[i];
			dX[i] += GVar.frameTime * spdX[i];
			dY[i] += GVar.frameTime * spdY[i];
			angle[i]++;

			for (int j = 0; j < 1000; j++) {

				if ((isHitable[i] && isHit(dX[i], dY[i], bullet.dX[j], bullet.dY[j], 24, 4)
						|| (isHit(dX[i], dY[i], UnitCircle.dblOffsetX[0] + 12, UnitCircle.dblOffsetY[0] + 12, 24, 8)))) {
					bom.bomb_req(dX[i] + 24, dY[i] + 24, 0);
					if (itemFlag[i]) {
						item.request(dX[i], dY[i]);
					}

					dX[i] = 0;
					dY[i] = 9998;
					spdX[i] = 0;
					spdY[i] = 0;
					accX[i] = 0;
					accY[i] = 0;
					itemFlag[i] = false;
					isVisible[i] = false;
					isHitable[i] = false;
					flag[i] = 0;

					bullet.isVisible[j] = false;
					bullet.isHitable[j] = false;
					bullet.imageIndex[j] = 1;
					bullet.dX[j] = 5000;
					bullet.spdX[j] = 0;
					bullet.spdY[j] = 0;
					bullet.accX[j] = 0;
					bullet.accY[j] = 0;
					bullet.bulletType[j] = 0;
					bullet.flag[j] = 0;

				}

			}

		}

	}

	public boolean isHit(double x1, double y1, double x2, double y2, double rad1, double rad2) {

		double dblSaX = Math.pow(((x1 + rad1) - (x2 + rad2)), 2);
		double dblSaY = Math.pow(((y1 + rad1) - (y2 + rad2)), 2);
		return Math.sqrt(dblSaX + dblSaY) <= rad1 + rad2;

	}

}
