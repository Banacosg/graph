package src.model;
import java.util.ArrayList;

public class Vertex{
    private String name;
    private int color;
    private double xCoord = 0; // x and y coord is between 0 and 1
    private double yCoord = 0; // it is a coefficient for the screen size
    private boolean selected = false;
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
    public int getColor(){ return color; }
    public double getX(){ return xCoord; }
    public double getY(){ return yCoord; }
    public boolean isSelected(){return selected;}
    
    public void addNeighbor(Vertex neighbor){
        neighbors.add(neighbor);
    }
    public void addNeighbors(ArrayList<Vertex> neighbors){
        neighbors.addAll(neighbors);
    }
    public void removeNeighbor(Vertex neighbor){
        neighbors.remove(neighbor);
    }
    public void removeNeighbors(ArrayList<Vertex> neighbors){
        neighbors.removeAll(neighbors);
    }
    public void setColor(int color){this.color = color;}
    public void setX(double x){ this.xCoord = x;}
    public void setY(double y){ this.yCoord = y;}
    public void flopSelected(){this.selected = !this.selected; }

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
