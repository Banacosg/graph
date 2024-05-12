package Graph;
import java.util.ArrayList;

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
    
    public void addneighbor(Vertex neighbor){
        neighbors.add(neighbor);
    }

    public void addneighbors(ArrayList<Vertex> neighbors){
        neighbors.addAll(neighbors);
    }

    public void removeNeighbor(Vertex neighbor){
        neighbors.remove(neighbor);
    }

    public void removeneighbors(ArrayList<Vertex> neighbors){
        neighbors.removeAll(neighbors);

    }

    @Override
    public int hashCode(){
        int hash = name.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()){
            return false;
        }
        Vertex other = (Vertex) obj;
        if(this.getName() != other.getName()){
            return false;
        }
        if(this.neighbors.size() != other.neighbors.size()){
            return false;
        }
        for(int i = 0; i < this.neighbors.size(); i++){
            if(other.getName() != this.getName()){
                return false;
            }
        }
        return true;
    }
}
