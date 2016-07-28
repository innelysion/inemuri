package jp.tnw.a18;

//使いたいｸﾗｽの取り込み
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class GameMain {

	// グロバール変数

	// final float FrameTime = 1.0f / 60.0f ;// 0.031f;
	// int GVar.windSizeX = 1280;// ｹﾞｰﾑｳｨﾝﾄﾞの横ｻｲｽﾞ
	// final int GVar.windSizeY = 720;// ｹﾞｰﾑｳｨﾝﾄﾞの縦ｻｲｽﾞ
	// BufferedImage img;
	// Font f = new Font("Default", Font.PLAIN, 11);// 使用するフォントクラス宣言
	// static int mx, my, mb;// マウスの座標

	static JFrame wind = new JFrame("STGProject");// JFrame の初期化(ﾒﾆｭｰﾊﾞｰの表示ﾀｲﾄﾙ
	static Insets sz;// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
	BufferStrategy offimage;// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧでちらつき防止

	// ■FOR OTHER CLASSES 実例化■

	UnitCircle UnitCircle = new UnitCircle();
	VFX bom = new VFX();
	StgItem item = new StgItem();
	StgBullet bullet = new StgBullet();
	StgEnemy enemy = new StgEnemy();


	// -----------------------------
	// 初期化用の関数
	// ・window生成
	// ・window大きさの指定
	// ・windowの場所

	// -----------------------------
	GameMain() {

		Input setupInput = new Input();
		StgSystem.setup();

		wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 閉じﾎﾞﾀﾝ許可
		wind.setBackground(new Color(0, 0, 0));// 色指定
		wind.setResizable(false);// ｻｲｽﾞ変更不可
		wind.setVisible(true);// 表示or非表示
		sz = wind.getInsets();// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
		wind.setSize(GVar.windSizeX + sz.left + sz.right, GVar.windSizeY + sz.top + sz.bottom);// ｳｨﾝﾄﾞｳのｻｲｽﾞ
		wind.setLocationRelativeTo(null);// 中央に表示

		// ちらつき防止の設定(ﾀﾞﾌﾞﾙﾊﾞｯﾌｧﾘﾝｸﾞ)-------------------------------------
		wind.setIgnoreRepaint(true);// JFrameの標準書き換え処理無効
		wind.createBufferStrategy(2);// 2でﾀﾞﾌﾞﾙ
		offimage = wind.getBufferStrategy();

		// ファイルの読み込み
		// ■LOAD■

		UnitCircle.loadImage("Image/Zako", 10, 9);
		bom.Load();
		item.loadImage("Image/Item", 1);
		bullet.loadImage("Image/tama", 1);
		enemy.loadImage("Image/gal/zako", 1);

		// ﾀｲﾏｰﾀｽｸ起動----------------------------
		Timer TM = new Timer();// ﾀｲﾏｰｸﾗｽの実体化
		TM.schedule(new timer_TSK(), 17, 17);// 17ms後から 17msおきに
		// どこ？ 17[ms]=プログラムが動き出す最初の時間
		// 17[ms]その後は17[ms]繰り返し

	}// Main_Game end

	// ---------------------------
	// ﾀｲﾏｰｸﾗｽ 1/60秒で1回動作
	// extends=継承
	// ---------------------------
	class timer_TSK extends TimerTask {

		public void run() {

			// ■UPDATE■

			UnitCircle.update();
			item.update();
			bom.UpDate();
			bullet.update(bom);
			enemy.update(bullet, bom, item);

			Graphics g2 = offimage.getDrawGraphics();// ｸﾞﾗﾌｨｯｸ初期化
			Graphics2D g = (Graphics2D) g2;

			if (offimage.contentsLost() == false) {//
				// Clear the graphic for next frame
				g.translate(sz.left, sz.top); // ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ補正
				g.clearRect(0, 0, GVar.windSizeX, GVar.windSizeY); // 画面ｸﾘｱ(左上X、左上Y、右下x、右下y)
				g.setColor(Color.darkGray);// 色指定
				g.fillRect(0, 0, GVar.windSizeX, GVar.windSizeY);// 塗りつぶし

				// Draw background

				// Draw game objects

				// Draw UI

				// Draw others
				
				UnitCircle.drawImage(g, wind, 0, 0);
				bom.draw(g, wind);
				enemy.drawImage(g, wind, 10, 10);
				item.drawImage(g, wind, 1, 12);
				bullet.drawImage(g, wind, 20, 10);
				
				// ---------------------------------------------------
				offimage.show();// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
				g.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄

			} // if end ｸﾞﾗﾌｨｯｸOK??

		}// run end

	}// timer task class end
		// -----------------------------------
		// Main ここからプログラムが動く
		// -----------------------------------

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		// Main_GameのｸﾗｽをGMという名前で動かします
		// 動かす前に初期化してから動かす！！

		GameMain Game = new GameMain();

	}

}