public class Vertex{
    private String name;
    private ArrayList<Vertex> neighbors = new ArrayList<Vertex>();


    public Vertex(String name){
        this.name = name;
    }

    public Vertex(String name, ArrayList<Vertex> neighbors){
        this.name = name;
        this.neighbors = neighbors;
    }

    public String getName(){ return name; }
    public ArrayList<Vertex> getNeighbors(){ return neighbors; }
    

}

public class Main {
    public static void main(String[] args){
        System.out.println("hi");
    
    }
}
