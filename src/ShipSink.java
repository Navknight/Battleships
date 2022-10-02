import java.util.ArrayList;

public class ShipSink {
    private GameHelper helper = new GameHelper();
    private ArrayList<Ship> ships = new ArrayList<Ship>();
    private int guessNum = 0;

    private void setUpGame(){
        Ship one = new Ship();
        one.setName("julia");
        Ship two = new Ship();
        two.setName("Black Pearl");
        Ship three = new Ship();
        three.setName("Rocinante");
        ships.add(one);
        ships.add(two);
        ships.add(three);

        System.out.println("Sink the three Ships - Julia, Black Pearl and Rocinante");
        System.out.println("Do it in the least number of guesses you can");

        for(Ship ship : ships){
            ArrayList<String> newLoc = helper.placeship(3);
            ship.setLocationCells(newLoc);
        }
    }

    private void startPlaying(){
        while (!ships.isEmpty()){
            String userGuess = helper.getUserInput("Enter your guess: ");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    private void checkUserGuess(String userGuess){
        guessNum++;
        String result = "miss";

        for(Ship shipToTest : ships){
            result = shipToTest.checkYourself(userGuess);

            if(result.equals("hit"))
                break;
            if(result.equals("kill")){
                ships.remove(shipToTest);
                break;
            }
        }
        System.out.println(result);
    }

    private void finishGame(){
        System.out.println("All ships sank!");
        if(guessNum <= 18){
            System.out.println("you took: "+guessNum+". Well done!!");
        }
        else{
            System.out.println("you took: "+guessNum+". You should practice more!");
        }
    }

    public static void main(String[] args){
        ShipSink game = new ShipSink();
        game.setUpGame();
        game.startPlaying();
    }
}
