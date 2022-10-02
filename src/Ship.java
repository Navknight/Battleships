import java.util.ArrayList;

public class Ship {
    private ArrayList<String> locationCells;
    private String name;

    public void setLocationCells(ArrayList<String> loc){
        locationCells = loc;
    }
    public void setName(String n){
        name = n;
    }

    public String checkYourself (String userInput){
        String result = "miss";
        int ind = locationCells.indexOf(userInput);
        if(ind >=0){
            locationCells.remove(ind);
            if(locationCells.isEmpty()){
                result = "kill";
                System.out.println("Damn! your sunk "+name);
            }
            else{
                result = "hit";
            }
        }
        return result;
    }
}
