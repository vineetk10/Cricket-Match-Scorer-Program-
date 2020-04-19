import java.util.*;

class GeneralUse {
	public static boolean YesNo() {
		while(true) {
			Scanner sc=new Scanner(System.in);
			String choice=sc.next();
			switch(choice.toLowerCase()) {
				case "y": 
				case "yes": return true;
				case "n":
				case "no": return false;
				default: System.out.println("Try again");
				break;
			}
		}
	}
	public static String NextL() {
		Scanner sc=new Scanner(System.in);
		String line=sc.nextLine();
		if(line.equalsIgnoreCase("help")) {
			System.out.println( "The program records and displays scorecard according to ball-by-ball "+
								"data entered by the user\nMatch details could be entered by the user or "+
								"default details could be used.\nFor each ball, the scorer enters a 'code'"+
								" to indicate what happened on that ball.\nThe code is a number with a "+
								"maximum of 3 digits\nThe first digit is the runs scored on that ball. "+
								"This digit is compulsory.\nFor example, enter '4' if batsman hits 4 runs\n"+
								"The second digit is extras, if any, that occured on that ball.\nThis digit "+
								"is optional. Run penalties are automatically added for noballs/wides.\nEnter "+
								"digits according to the following codes:\n1:No ball\n2:Wide\n3:Bye\n"+
								"4:Leg-bye\n5:No-ball+bye\n6:No-ball+leg-bye\nThe third digit is dismiassal, "+
								"if any, that occured on that ball. This digit is optional.\nSanity checks are "+
								"included. For example, caught is not possible on a no-ball.\nEnter digits "+
								"according to the following codes:\n1:Caught\n2:Bowled\n3:LBW\n4:RunOut\n"+
								"5:Stumped\n6:Hit wicket\n7:Obs field\n8:Handled\n9:Hit twice\n10:Time out\n"+
								"11:Mankad\nFor example, '520' means batsman was stumped off a wide ball\n"+
								"Enter code '90' to display scorecard.");
			return NextL();
		}
		else if(line.equalsIgnoreCase("exit")) {
			System.out.println("Do you really want to quit? (yes/no)");
			if(YesNo())
				System.exit(0);
			else
				return NextL();
		}
		return line;
	}
}

class Innings {
	//private int runs,balls,wickets,extras;
	private int[] Stats=new int[4];
	private int[] BatOrder=new int[11];
	private int[] BowlOrder=new int[11];
	public void Modify(int[] r) {
		switch(r[0]) {
			case 1: {
				for(int j=1;j<r.length;j++)
					Stats[j-1]+=r[j];
			}
			break;
			case 2: {
				for(int j=1;j<r.length;j++)
					Stats[j-1]-=r[j];
			}
			break;
			case 3: {
				for(int j=1;j<r.length;j++) {
					if(r[j]!=-1)
						Stats[j-1]=r[j];
				}
			}
			break;
		}
	}
	public void ModifyBatOrder(int n) {
		int i;
		for(i=0;BatOrder[i]!=0;i++);
		BatOrder[i]=n;
	}
	public void ModifyBowlOrder(int n) {
		int i;
		for(i=0;BowlOrder[i]!=0;i++);
		BowlOrder[i]=n;
	}
	public int[] GetBatOrder(int n) {
		return BatOrder;
	}
	public int[] GetBowlOrder(int n) {
		return BowlOrder;
	}
	public int[] Get() {
		return Stats;
	}
}

interface PlayerBatStats {
	void BatPlayed(boolean f,int i);
	void Bat(int[] r,int i);
	void BatDismissed(boolean f,int i);
	void DismissalSet(String s,int i);
	String DismissalGet(int i);
	int[][] GetBatStats();
}

interface PlayerBowlStats {
	void BowlPlayed(boolean f,int i);
	void Bowl(int[] r,int i);
	int[][] GetBowlStats();
}

class Player implements PlayerBatStats,PlayerBowlStats {
	private String name;
	private int inn;
	//private int[] batruns,batballs,batfours,batsixes;
	private int[][] BatStats;
	//private int[] bowlruns,bowlbowls,bowlwickets,bowlmaidens,bowlextras;
	private int[][] BowlStats;
	//private boolean[] batplayed,batwicket,bowlplayed;
	private boolean[][] FlagStats;
	private String[] Dismissal;
	Player(String n,int i) {
		name=n;
		inn=i;
		BatStats=new int[4][i];
		BowlStats=new int[5][i];
		FlagStats=new boolean[3][i];
		Dismissal=new String[i];
	}
	public String GetName() {
		return name;
	}
	public void BatPlayed(boolean f,int i) {
		FlagStats[0][i]=f;
	}
	public void Bat(int[] r,int i) {
		//r[0] is flag, 1 is add, 2 is subtract, 3 is set
		//r[1]=runs,r[2]=balls,r[3]=fours,r[4]=sixes
		switch(r[0]) {
			case 1: {
				for(int j=1;j<r.length;j++)
					BatStats[j-1][i]+=r[j];
			}
			break;
			case 2: {
				for(int j=1;j<r.length;j++)
					BatStats[j-1][i]-=r[j];
			}
			break;
			case 3: {
				for(int j=1;j<r.length;j++) {
					if(r[j]!=-1)
						BatStats[j-1][i]=r[j];
				}
			}
			break;
		}
	}
	public void BatDismissed(boolean f,int i) {
		FlagStats[1][i]=f;
	}
	public void DismissalSet(String s,int i) {
		Dismissal[i]=s;
	}
	public String DismissalGet(int i) {
		return Dismissal[i];
	}
	public int[][] GetBatStats() {
		return BatStats;
	}
	public void BowlPlayed(boolean f,int i) {
		FlagStats[2][i]=f;
	}
	public void Bowl(int[] r,int i) {
		switch(r[0]) {
			case 1: {
				for(int j=1;j<r.length;j++)
					BowlStats[j-1][i]+=r[j];
			}
			break;
			case 2: {
				for(int j=1;j<r.length;j++)
					BowlStats[j-1][i]-=r[j];
			}
			break;
			case 3: {
				for(int j=1;j<r.length;j++) {
					if(r[j]!=-1)
						BowlStats[j-1][i]=r[j];
				}
			}
			break;
		}
	}
	public int[][] GetBowlStats() {
		return BowlStats;
	}
	public boolean[][] GetFlagStats() {
		return FlagStats;
	}
}

class Team {
	private String name;
	private int inn;
	private Player[] players=new Player[11];
	Team(String n,int i,String[] p) {
		name=n;
		inn=i;
		for(int j=0;j<11;j++)
			players[j]=new Player(p[j],i);
	}
	public String TeamName() {
		return name;
	}
	public String[] PlayerList() {
		String[] s=new String[11];
		for(int i=0;i<11;i++)
			s[i]=players[i].GetName();
		return s;
	}
	public Player GetPlayer(int i) {
		return players[i];
	}
}

class Match { //Used to store match format details.
	private int overs,inn,tossresult;
	Match(int o,int i) {
		overs=o;
		inn=i;
	}
	public int GetInnings() {
		return inn;
	}
	public int GetOvers() {
		return overs;
	}
	public void SetToss(int t) {
		tossresult=t;
	}
	public int GetToss() {
		return tossresult;
	}
}

class ScoreCard {
	private static String BowlsToOvers(int balls) {
		return ((balls/6)+"."+(balls%6));
	}
	public static void DisplayScore(Match m,Team t1,Team t2,Innings[] inn,int currinn,boolean followon,String matchstatus) {
		Team batfirst=t1,batsecond=t2;
		if(m.GetToss()==2||m.GetToss()==3) {
			batfirst=t2;batsecond=t1;
		}
		System.out.println("\n"+matchstatus);
		for(int i=0;i<=currinn;i++) {
			Team bat=batfirst,bowl=batsecond;
			if(i==1||(i==3&&followon==false)||(i==2&&followon==true)) {
				bat=batsecond;bowl=batfirst;
			}
			int inum=i/2;
			String ordinal=inum==0?"1st":"2nd";
			System.out.printf("\n%s %s Innings   %4d/%d (%s ov)\n",
				bat.TeamName(),ordinal,inn[i].Get()[0],inn[i].Get()[2],BowlsToOvers(inn[i].Get()[1]));
			System.out.printf("%-50s%4s%4s%4s%4s%9s\n","Batsman","R","B","4s","6s","SR");
			int[] BatOrder=inn[i].GetBatOrder(inum),BowlOrder=inn[i].GetBowlOrder(inum);
			for(int j=0;j<11&&BatOrder[j]!=0;j++) {
				Player b=bat.GetPlayer(BatOrder[j]-1);
				int[][] BatStats=b.GetBatStats();
				String disstring=b.DismissalGet(inum)==null?"not out":b.DismissalGet(inum);
				double strikerate=
					BatStats[1][inum]==0?0.0:(((double)BatStats[0][inum]/(double)BatStats[1][inum])*100.0);
				System.out.printf("%-28s %-20s %4d%4d%4d%4d%9.2f\n",b.GetName(),disstring,
					BatStats[0][inum],BatStats[1][inum],BatStats[2][inum],BatStats[3][inum],strikerate);
			}
			System.out.printf("%-50s%4d\n","Extras",inn[i].Get()[3]);
			System.out.printf("%-50s%4d\n\n","Total",inn[i].Get()[0]);
			System.out.println(bowl.TeamName()+" bowling");
			System.out.printf("%-29s%5s%5s%5s%5s%5s%7s\n","Bowler","O","M","R","W","E","Econ");
			for(int j=0;j<11&&BowlOrder[j]!=0;j++) {
				Player b=bowl.GetPlayer(BowlOrder[j]-1);
				int[][] BowlStats=b.GetBowlStats();
				double econrate=
					BowlStats[1][inum]==0?0.0:(((double)BowlStats[0][inum]/(double)BowlStats[1][inum])*6.0);
				System.out.printf("%-28s %5s%5d%5d%5d%5d%7.2f\n",b.GetName(),
					BowlsToOvers(BowlStats[1][inum]),BowlStats[3][inum],BowlStats[0][inum],
					BowlStats[2][inum],BowlStats[4][inum],econrate);
			}
		}
		System.out.println();
	}
}

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



class cricket {
	public static void main(String[] args) {
		System.out.println("Welcome to cricket scorecard manager");
		System.out.println("Enter ball by ball data and display all scorecard details on demand");
		System.out.println("Enter 'begin' to start program.");
		System.out.println("Enter 'help' to enter help menu or 'exit' to terminate the program at any stage");
		while(true) { //Loop till we get valid input.
			switch(GeneralUse.NextL().toLowerCase()) {
			//Function returns user input, which is converted to lowercase to make all input case insensitive.
			//Function is used to check every user input for 'help' and 'exit'.
				case "begin": {
					Master.SetUp();
				}
				break;
				default: System.out.println("Try again");
				break;
			}
		}
	}
}