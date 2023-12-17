import java.util.HashMap;

public class CategoryInfo {
    private String name;
    private HashMap<String, Double> costs;

    public CategoryInfo(String name){
        this.name = name;
        costs = new HashMap<>();
    }
    public void addCost(String date, Double cost){
        if(!costs.containsKey(date)){
            costs.put(date,0.0);
        }
        costs.put(date,costs.get(date) + cost);
    }
    public String getName(){
        return name;
    }
    public HashMap<String,Double> getCosts(){
        return costs;
    }
    public Double getAllCost(){
        var result = 0.0;
        var values = costs.values();
        for(var i : values){
            result += i;
        }
        return result;
    }
}
