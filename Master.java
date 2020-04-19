import java.util.*;
class Master {
	public static void SetUp() {
		Match mat; //mat would contain over and innings info.
		Team t1,t2; //t1 and t2 would contain team name and player info.
		System.out.println("Use default match details? (yes/no)"); //Avoid typing details every time.
		if(GeneralUse.YesNo()) { //Function returns true for yes/y and false for n/no.
			mat=new Match(5,1);
			String tn1="West Indies";
			String tn2="India";
			String[] p1={ "J Charles","E Lewis","AD Russell","KA Pollard","CR Brathwaite","DJ Bravo",
			"LMP Simmons","MN Samuels","ADS Fletcher","SP Narine","S Badree"};
			String[] p2={"RG Sharma","AM Rahane","V Kohli","KL Rahul","MS Dhoni","STR Binny","R Ashwin"
			,"RA Jadeja","M Shami","JJ Bumrah","B Kumar"};
			t1=new Team(tn1,mat.GetInnings(),p1);
			t2=new Team(tn2,mat.GetInnings(),p2);
		}
		else {
			System.out.println("Enter type of match");
			System.out.println("1. 5 overs\n2. 20 overs\n3. 50 overs\n4. Unlimited overs (2 innings)");
			mformat: while(true) {
				switch(GeneralUse.NextL().toLowerCase()) {
					case "1": mat=new Match(5,1); //First paramter is overs, second is innings.
					break mformat; //break block-label; is used to break out of multiple loops.
					case "2": mat=new Match(20,1);
					break mformat;
					case "3": mat=new Match(50,1);
					break mformat;
					case "4": mat=new Match(0,2); //0 denotes no over limit.
					break mformat;
					default: System.out.println("Try again");
					break;
				}
			}
			System.out.println("Enter team names");
			String tn1=GeneralUse.NextL(); //Strings to store team names.
			String tn2=GeneralUse.NextL();
			String[] p1=new String[11]; //String arrays to store player names.
			String[] p2=new String[11];
			System.out.println("Enter starting XI of "+tn1);
			for(int i=0;i<11;i++) {
				p1[i]=GeneralUse.NextL();
			}
			System.out.println("Enter starting XI of "+tn2);
			for(int i=0;i<p2.length;i++){
				p2[i]=GeneralUse.NextL();
			}
			t1=new Team(tn1,mat.GetInnings(),p1);
			t2=new Team(tn2,mat.GetInnings(),p2);
		}
		ScoreMatch.MatchToss(mat,t1,t2);
	}
}