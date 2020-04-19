import java.util.*;
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