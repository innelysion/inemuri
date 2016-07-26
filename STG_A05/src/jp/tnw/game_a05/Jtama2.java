package jp.tnw.game_a05;
//継承の気泡

//page 430 ??

public class Jtama2 extends Jtama {
	
	@Override
	public void Req(){
		Timer[0]-=FrameTime;
		if(Timer[0]<0){
			Timer[0]=timerFreq;
			for (int i =0;i<Max;i++){
				if(Flag[i]==99){
					X[i]=JK.X+50-8;	//startingpoint
					Y[i]=JK.Y+18;
					Xsp[i]=350;//初速度X start speed
					Ysp[i]=350;
					Xspg[i]=1;//初速度X speed multiplicator
					Yspg[i]=1;//-270/2;
					Ny[i]=2;
					kaku[i] = 270; //angle
					//S_kaku++;
					Flag[i]=1; //show on screen
				break;
				}
			}
		}
	}
}
