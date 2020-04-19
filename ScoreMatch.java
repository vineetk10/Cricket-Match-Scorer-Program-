import java.util.*;
class ScoreMatch {
	static String matchstatus="";
	public static void MatchToss(Match m,Team t1,Team t2) {
		System.out.println("Enter toss result");
		System.out.println("1."+t1.TeamName()+" wins and chooses to bat");
		System.out.println("2."+t1.TeamName()+" wins and chooses to bowl");
		System.out.println("3."+t2.TeamName()+" wins and chooses to bat");
		System.out.println("4."+t2.TeamName()+" wins and chooses to bowl");
		mtoss: while(true) {
			switch(GeneralUse.NextL().toLowerCase()) {
				case "1": MatchControl(m,t1,t2,1);
				break mtoss;
				case "2": MatchControl(m,t1,t2,2);
				break mtoss;
				case "3": MatchControl(m,t1,t2,3);
				break mtoss;
				case "4": MatchControl(m,t1,t2,4);
				break mtoss;
				default: System.out.println("Try again");
				break;
			}
		}
	}
	public static int ChoosePlayerNumber() {
		String[] nums={"1","2","3","4","5","6","7","8","9","10","11"};
		while(true) {
			String x=GeneralUse.NextL();
			for(String y:nums) {
				if(y.equals(x))
					return (Integer.parseInt(x)-1);
			}
			System.out.println("Try again");
			return ChoosePlayerNumber();
		}
	}
	public static int ChooseNumber() {
		String x=GeneralUse.NextL();
		for(int i=0;i<x.length();i++) {
			if(!Character.isDigit(x.charAt(i))) {
				System.out.println("Try again");
				return ChooseNumber();
			}
		}
		return Integer.parseInt(x);
	}
	public static void DisplayList(String[] list) {
		for(int i=0;i<11;i++)
			System.out.println((i+1)+"."+list[i]);
	}
	public static int Scorer(int n,Innings inn,Player[] batsmen,Player bowler,int onstrike,int inum) {
		int r=n%10;
		int ex=(n/10)%10; //1:No ball,2:wide,3:bye,4:leg-bye
		//5:No-ball+bye,6:No-ball+leg-bye
		if(ex==0||ex==1) {
			if(r==4)
				batsmen[onstrike].Bat(new int[] {1,4,1,1},inum);
			else if(r==6)
				batsmen[onstrike].Bat(new int[] {1,6,1,0,1},inum);
			else
				batsmen[onstrike].Bat(new int[] {1,r,1},inum);
		}
		else if(ex>=3&&ex<=6)
			batsmen[onstrike].Bat(new int[] {1,0,1},inum);
		if(ex==0)
			bowler.Bowl(new int[] {1,r,1},inum);
		else if(ex==1||ex==2)
			bowler.Bowl(new int[] {1,r+1,0,0,0,r+1},inum);
		else if(ex==3||ex==4)
			bowler.Bowl(new int[] {1,0,1},inum);
		else if(ex==5||ex==6)
			bowler.Bowl(new int[] {1,1,0,0,0,1},inum);
		if(ex==0)
			inn.Modify(new int[] {1,r,1});
		else if(ex==1||ex==2)
			inn.Modify(new int[] {1,r+1,0,0,1});
		else if(ex==3||ex==4)
			inn.Modify(new int[] {1,r,1,0,r});
		else
			inn.Modify(new int[] {1,r+1,0,0,r+1});
		if(r%2==1)
			return (1-onstrike);
		return onstrike;
	}
	public static void ChooseNewBat(Team batting,Player[] batsmen,Innings inn,
									int inum,int newno,String[] pbat,String s) {
		System.out.println("Batting team player list:");
		ScoreMatch.DisplayList(pbat);
		System.out.println("Enter "+s+" batsman");
		int n;
		while(true) {
			n=ScoreMatch.ChoosePlayerNumber();
			batsmen[newno]=batting.GetPlayer(n);
			if(batsmen[newno].GetFlagStats()[0][inum])
				System.out.println("Batsman already played!");
			else
				break;
		}
		batsmen[newno].BatPlayed(true,inum);
		inn.ModifyBatOrder(n+1);
	} 
	public static int ChooseDismissedBat(Player[] batsmen) {
		System.out.println("Enter batsman dismissed");
		System.out.println("1."+batsmen[0].GetName()+"\n2."+batsmen[1].GetName());
		while(true) {
			int outno=(ScoreMatch.ChooseNumber()-1);
			if(outno==0||outno==1)
				return outno;
			else
				System.out.println("Try again");
		}
	}
	public static boolean MatchStatusControl(Match m,Innings[] inn,Team t1,Team t2,int i) {
		if(i==0) {
			return false;
		}
		if(m.GetOvers()!=0&&i==1) {
			int s1=inn[0].Get()[0],s2=inn[1].Get()[0];
			if(s1-s2>0) {
				if(m.GetOvers()*6==inn[1].Get()[1]||inn[1].Get()[2]>=10) {
					matchstatus=(t1.TeamName()+" won by "+(s1-s2)+" runs");
					return true;
				}
				matchstatus=(t2.TeamName()+" need "+(s1-s2+1)+" runs to win");
				return false;
			}
			else if(s1-s2==0) {
				matchstatus="Scores tied";
				return false;
			}
			else {
				matchstatus=(t2.TeamName()+" won by "+(10-inn[1].Get()[2])+" wickets");
				return true;
			}
		}
		return false;
	}
	public static void MatchControl(Match m,Team t1,Team t2,int tossresult) {
		Team batfirst=t1;
		Team batsecond=t2;
		boolean followon=false;
		m.SetToss(tossresult);
		switch(tossresult) {
			case 1:
			case 4: batfirst=t1;batsecond=t2;
			break;
			case 2:
			case 3: batfirst=t2;batsecond=t1;
			break;
		}
		Innings[] inn=new Innings[2*m.GetInnings()];
		for(int i=0;i<2*m.GetInnings();i++) {
			inn[i]=new Innings();
			int inum=i<2?0:1,onstrike=1;
			Team batting=batfirst,bowling=batsecond;
			Player bowler=bowling.GetPlayer(10);
			Player[] batsmen=new Player[2];
			int n;
			switch(i) {
				case 0: batting=batfirst;bowling=batsecond;
				break;
				case 1: batting=batsecond;bowling=batfirst;
				break;
				case 2: {
					if(inn[0].Get()[0]-inn[1].Get()[1]>=75) {
					//Absoulute minimum (1 day match), for 5 day match minimum is 200
						System.out.println("Follow-on? (yes/no)");
						followon=GeneralUse.YesNo();
					}
					if(followon) {
						batting=batsecond;bowling=batfirst;
					}
					else {
						batting=batfirst;bowling=batsecond;
					}
				}
				break;
				case 3: {
					if(!followon) {
						batting=batsecond;bowling=batfirst;
					}
					else {
						batting=batfirst;bowling=batsecond;
					}
				}
				break;
			}
			String[] pbat=batting.PlayerList();
			String[] pbowl=bowling.PlayerList();
			ScoreMatch.ChooseNewBat(batting,batsmen,inn[i],inum,0,pbat,"first");
			ScoreMatch.ChooseNewBat(batting,batsmen,inn[i],inum,1,pbat,"second");
			int overballcount=6,initruns=1; //ballcount needed if there is a wide/no ball on first ball of over!
			while(m.GetOvers()==0||inn[i].Get()[1]<(m.GetOvers()*6)) {
				if(inn[i].Get()[1]%6==0&&overballcount>=6) {
					overballcount=0;
					if(bowler.GetBowlStats()[0][inum]==initruns)
						bowler.Bowl(new int[] {1,0,0,0,1},inum);
					initruns=bowler.GetBowlStats()[0][inum];
					System.out.println("Bowling team player list:");
					ScoreMatch.DisplayList(pbowl);
					System.out.println("Enter bowler");
					n=ScoreMatch.ChoosePlayerNumber();
					bowler=bowling.GetPlayer(n);
					if(bowler.GetFlagStats()[2][inum]==false) {
						bowler.BowlPlayed(true,inum);
						inn[i].ModifyBowlOrder(n+1);
					}
					onstrike=1-onstrike;
				}
				System.out.println("Enter code");
				int choice=ScoreMatch.ChooseNumber();
				if(choice<70) {
					onstrike=ScoreMatch.Scorer(choice,inn[i],batsmen,bowler,onstrike,inum);
					overballcount++;
					continue;
				}
				else if(choice>=100&&choice<=1100) {
					//catch=1,bowled=2,lbw=3,runout=4,stumped=5,hit wicket=6,
					//obs field=7,handled=8,hit twice=9,time out=10,mankad=11
					int r=choice%10;
					int ex=(choice/10)%10;
					int di=choice/100;
					if((ex==1&&!(di==4||di==7||di==8||di==9))||
					   (ex==2&&!(di==4||di==5||di==6||di==7))||
					   (ex>=3&&ex<=6&&!(di==4||di==7))||
					   (r>0&&!(di==4||di==7))) { //Sanity checks, for example bowled is not possible on noball
						System.out.println("Check input!");
						continue;
					}
					int outno;
					if(di>=1&&di<=9) {
						onstrike=ScoreMatch.Scorer(choice,inn[i],batsmen,bowler,onstrike,inum);
						if(di==4||di==7)
							outno=ScoreMatch.ChooseDismissedBat(batsmen);
						else
							outno=onstrike;
						batsmen[outno].BatDismissed(true,inum);
						System.out.println("Enter dismissal string");
						batsmen[outno].DismissalSet(GeneralUse.NextL(),inum);
						if(di==1||di==2||di==3||di==5||di==6)
							bowler.Bowl(new int[] {1,0,0,1},inum);
						overballcount++;
						inn[i].Modify(new int[] {1,0,0,1});
						if(inn[i].Get()[2]>=10)
							break;
						ScoreMatch.ChooseNewBat(batting,batsmen,inn[i],inum,outno,pbat,"new");
						if(di==1||di==4||di==7) {
							System.out.println("Enter new batsman on strike");
							System.out.println("1."+batsmen[0].GetName()+"\n2."+batsmen[1].GetName());
							while(true) {
								onstrike=(ScoreMatch.ChooseNumber()-1);
								if(onstrike==0||onstrike==1)
									break;
								else
									System.out.println("Try again");
							}
						}
					}
					else if(di==10||di==11) {
						outno=ScoreMatch.ChooseDismissedBat(batsmen);
						inn[i].Modify(new int[] {1,0,0,1});
						batsmen[outno].BatDismissed(true,inum);
						System.out.println("Enter dismissal string");
						batsmen[outno].DismissalSet(GeneralUse.NextL(),inum);
						ScoreMatch.ChooseNewBat(batting,batsmen,inn[i],inum,outno,pbat,"new");
					}
				}
				else if(choice==90) {
					ScoreCard.DisplayScore(m,t1,t2,inn,i,followon,matchstatus);
				}
				else {
					System.out.println("Check input!");
				}
				if(MatchStatusControl(m,inn,batfirst,batsecond,i)) {
					System.out.println(matchstatus);
					return;
				}
			}
		}
	}
}
