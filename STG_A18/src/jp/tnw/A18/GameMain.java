package jp.tnw.A18;

//使いたいｸﾗｽの取り込み
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class GameMain implements MouseListener, MouseMotionListener, KeyListener {

	// グロバール変数

	final float FrameTime = 1.0f / 60.0f;// 0.031f;
	static final int X_Size = 800;// ｹﾞｰﾑｳｨﾝﾄﾞの横ｻｲｽﾞ
	static final int Y_Size = 600;// ｹﾞｰﾑｳｨﾝﾄﾞの縦ｻｲｽﾞ
	BufferedImage img;

	JFrame Wind = new JFrame("A18ﾁｮｳｶﾝﾌ");// JFrame の初期化(ﾒﾆｭｰﾊﾞｰの表示ﾀｲﾄﾙ
	BufferStrategy offimage;// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧでちらつき防止
	Insets sz;// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ

	Font f = new Font("Default", Font.PLAIN, 11);// 使用するフォントクラス宣言
	int mx, my, mb;// マウスの座標

	// ■FOR OTHER CLASSES 実例化■

	Background bg = new Background();
	Enemy enemy = new Enemy();
	Bullet tama = new Bullet();
	Bullet2 tama2 = new Bullet2();
	Player player = new Player();
	Item item = new Item();
	Bomb bomb = new Bomb();

	// -----------------------------
	// 初期化用の関数
	// ・Window生成
	// ・Window大きさの指定
	// ・Windowの場所

	// -----------------------------
	GameMain() {

		Wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 閉じﾎﾞﾀﾝ許可
		Wind.setBackground(new Color(0, 0, 0));// 色指定
		Wind.setResizable(false);// ｻｲｽﾞ変更不可
		Wind.setVisible(true);// 表示or非表示
		sz = Wind.getInsets();// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
		Wind.setSize(X_Size + sz.left + sz.right, Y_Size + sz.top + sz.bottom);// ｳｨﾝﾄﾞｳのｻｲｽﾞ
		Wind.setLocationRelativeTo(null);// 中央に表示

		// ちらつき防止の設定(ﾀﾞﾌﾞﾙﾊﾞｯﾌｧﾘﾝｸﾞ)-------------------------------------
		Wind.setIgnoreRepaint(true);// JFrameの標準書き換え処理無効
		Wind.createBufferStrategy(2);// 2でﾀﾞﾌﾞﾙ
		offimage = Wind.getBufferStrategy();

		Wind.addMouseMotionListener(this);
		Wind.addMouseListener(this);
		Wind.addKeyListener(this);

		// ファイルの読み込み
		// ■LOAD■

		bg.loadimage();
		enemy.loadImage("Image/zako", 10, 10);
		tama.loadImage();
		tama2.loadImage();
		player.loadImage("Image/jiki2", 2, 5);
		item.loadImage("Image/Item", 1, 12);
		bomb.Load();

		// クラスの変数へ代入
		tama.player = player;

		tama.item = item;
		tama2.player = player;

		tama2.item = item;
		enemy.player = player;
		enemy.bomb = bomb;
		enemy.item = item;
		item.player = player;
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

			bg.update();
			enemy.update();
			tama.update(enemy,bomb,item);
			tama2.update(enemy,bomb,item);
			player.update(mx, my);
			item.update();
			bomb.UpDate();

			Graphics g2 = offimage.getDrawGraphics();// ｸﾞﾗﾌｨｯｸ初期化
			Graphics2D g = (Graphics2D) g2;

			if (offimage.contentsLost() == false) {//
				g.translate(sz.left, sz.top);// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ補正
				g.clearRect(0, 0, X_Size, Y_Size);// 画面ｸﾘｱ(左上X、左上Y、右下x、右下y)
				g.setColor(Color.WHITE);// 色指定
				g.fillRect(0, 0, X_Size, Y_Size);// 塗りつぶし

				// --------------------------------------------------
				// 絵や文字を表示
				// ■DRAW■

				bg.drawimage(g, Wind);
				enemy.drawImage(g, Wind);
				tama.drawImage(g, Wind);
				tama.drawText(g);
				tama2.drawImage(g, Wind);
				player.drawImage(g, Wind);
				item.drawImage(g, Wind);
				item.drawText(g);
				bomb.Disp(g, Wind);

				g.setColor(Color.WHITE);// 色指定
				g.setFont(f);
				g.drawString("自機パワー", mx - 30, my + 50);
				g.drawString(String.valueOf(Bullet.jikiPower + 1), mx + 30, my + 50);

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

		GameMain GM = new GameMain();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Insets sz = Wind.getInsets();
		mx = e.getX() - sz.left;
		my = e.getY() - sz.top;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Insets sz = Wind.getInsets();
		mx = e.getX() - sz.left;
		my = e.getY() - sz.top;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		mb = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		mb = 0;
		Bullet.jikiPower = Bullet.jikiPower > 4 ? 0 : Bullet.jikiPower + 1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
