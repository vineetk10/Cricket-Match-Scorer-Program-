# Cricket-Match-Scorer-Program-
Made a Cricket match game where user gives input as score of each ball. 
Output is the winner of the two teams. 
Implemented using Java.

Implementation and how to run:
    There are 9 classes in this project.
    class cricket.java consists of main function. User has to enter begin to start the game.All input by the user is made case insensitive by the class GeneralUse.java. User can enter help and exit at any point of the game.The help input will display the instructions to play the game.
    When the user enters begin, setUp method of Master.java class will be called.It will make one object of class Match and two objects of class Team.
    Then the game will ask default or not.
    If default then constructor of match  will initialize overs to 5 and inn to 1 and team names will be set to West Indies and India. Playing 11 of both the teams will be initialized by making objects of all the playing 11.The player class implements 2 interface playerBatStats and playerBowlStats. team names along with players will be passed onto constructor of team class.
    If not default user will enter no of overs and team names and playing 11.
    After that MatchToss method of ScoreMatch will be called and user has the option to select who won the toss and will bat first.
    Accordingly two objects team will be created(batfirst and batsecond).
    The batting and bowling team list will be displayed and the user will be asked to choose the 2 batsmen and bowler.
    Then user will be asked to enter a 3 digit code.
    The first digit is the runs scored on that ball. This digit is
    compulsory.
    For example , enter 4 if batsman hits 4 runs
    The second digit is extras, if any, that occured on that ball.
    This digit is optional. Run penalties are automatically added for
    noballs / wides.
    The third digit is dismissal, if any, that occurred on that ball.
    This digit is optional.
    Sanity checks are included. For example, caught is not possible
    on a no-ball.
    Enter code 90 to display scorecard.

       
