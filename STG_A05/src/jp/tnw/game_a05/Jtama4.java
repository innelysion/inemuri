package jp.tnw.game_a05;

public class Jtama4 extends Jtama {

	@Override
	public void Req(){
		Timer[0]-=FrameTime;
		if(Timer[0]<0){
			Timer[0]=timerFreq;
			
			int cnt=3;//bullet count
			
			for (int i =0;i<Max;i++){
				if(Flag[i]==99){
					X[i]=JK.X+25*cnt-8;	//startingpoint
					Y[i]=JK.Y+18;
					Xsp[i]=350;//初速度X start speed
					Ysp[i]=350;
					Xspg[i]=1;//初速度X speed multiplicator
					Yspg[i]=1;//-270/2;
					Ny[i]=2;
					kaku[i] = 256+7*cnt; //angle
					Flag[i]=1; //show on screen
					cnt--;
					if(cnt==0)break;
				}
			}
		}
	}
}
