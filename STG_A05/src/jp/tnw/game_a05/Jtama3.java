package jp.tnw.game_a05;

public class Jtama3 extends Jtama {

	@Override
	public void Req(){
		Timer[0]-=FrameTime;
		if(Timer[0]<0){
			Timer[0]=timerFreq;
			
			int cnt=2;//bullet count
			
			for (int i =0;i<Max;i++){
				if(Flag[i]==99){
					X[i]=JK.X+20+20*cnt-8;	//startingpoint
					Y[i]=JK.Y+18;
					Xsp[i]=350;//初速度X start speed
					Ysp[i]=350;
					Xspg[i]=1;//初速度X speed multiplicator
					Yspg[i]=1;//-270/2;
					Ny[i]=2;
					kaku[i] = 270; //angle
					Flag[i]=1; //show on screen
					cnt--;
					if(cnt==0)break;
				}
				/*if(Flag[i+Max/2]==99){
					X[i+Max/2]=JK.X+44+15;	//startingpoint
					Y[i+Max/2]=JK.Y+18;
					Xsp[i+Max/2]=0;//初速度X start speed
					Ysp[i+Max/2]=-350;
					Xspg[i+Max/2]=1;//初速度X speed multiplicator
					Yspg[i+Max/2]=1;//-270/2;
					Ny[i+Max/2]=2;
					//kaku[i] = S_kaku*5; //angle
					//S_kaku++;
					Flag[i+Max/2]=1; //show on screen
					break;
				}*/
				
			}
		}
	}
}
