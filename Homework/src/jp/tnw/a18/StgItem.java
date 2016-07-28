package jp.tnw.a18;

public class StgItem extends StgUnitBase {
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

	StgItem() {

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

	public void request(double x, double y) {

		timerReq[0] = timerReq[0] - GVar.frameTime;
		if (timerReq[0] < 0) {
			timerReq[0] = 0.01;


			for (int i = 0; i < UNIT_MAX; i++) {
				if (flag[i] == 0) { // ONLY FOR WATING OBJECT

					imageIndex[i] = 1;
					dX[i] = x;
					dY[i] = y;
					spdX[i] = 0;
					spdY[i] = 100;
					accX[i] = 0;
					accY[i] = 50;
					isVisible[i] = true;
					isHitable[i] = true;
					flag[i] = 1;

	
						break;

				} // if end

			} // for end

		}

	}

	public void update() {
		
		//request(500,500);

		for (int i = 0; i < UNIT_MAX; i++) {

			spdX[i] += GVar.frameTime * accX[i];
			spdY[i] += GVar.frameTime * accY[i];
			dX[i] += GVar.frameTime * spdX[i];
			dY[i] += GVar.frameTime * spdY[i];

			if (isHitable[i] && isHit(dX[i], dY[i], UnitCircle.dblOffsetX[0], UnitCircle.dblOffsetY[0], 20, 24)) {
				GVar.energy = GVar.energy >= 4 ? 0 : GVar.energy + 1;
				UnitCircle.timerText = 60;
				isVisible[i] = false;
				isHitable[i] = false;
				flag[i] = 0;
			}

		}

	}

	public boolean isHit(double x1, double y1, double x2, double y2, double rad1, double rad2) {

		double dblSaX = Math.pow(((x1 + rad1) - (x2 + rad2)), 2);
		double dblSaY = Math.pow(((y1 + rad1) - (y2 + rad2)), 2);
		return Math.sqrt(dblSaX + dblSaY) <= rad1 + rad2;

	}

}
