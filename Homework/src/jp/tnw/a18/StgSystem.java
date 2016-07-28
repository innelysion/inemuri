package jp.tnw.a18;

//◆System Control◆//
public class StgSystem {
	
	public static void setup(){
		GVar.frameTime = 0.017;
		GVar.windSizeX = 1024;
		GVar.windSizeY = 720;
		GVar.life = GVar.bomb = GVar.energy = GVar.score = 0;
	}
	
}
