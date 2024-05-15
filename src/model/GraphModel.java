package src.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import src.View.initView;

public class GraphModel {
    private HashMap<String, Edges> edges;
    private HashMap<String, Vertex> vertices;
    private ArrayList<Vertex> selectedVertices = new ArrayList<Vertex>();

    public GraphModel(HashMap<String, Edges> edges, HashMap<String, Vertex> vertices){
        this.edges = edges;
        this.vertices = vertices;
    }

    public void initGraph(){
        Random random = new Random();
        double xCoord;
        double yCoord;
        Object[] verticesArray = vertices.values().toArray();
        Vertex curVertex;
        for(int i = 0; i < verticesArray.length; i++){
            xCoord = random.nextDouble(Constants.getXYInit_Proportion()); 
            yCoord = random.nextDouble(Constants.getXYInit_Proportion());
            curVertex = (Vertex) verticesArray[i];
            curVertex.setX(xCoord);
            curVertex.setY(yCoord);
        }
        initView.modelUpdate(this, "read");
    }

    public void resizeHeight(double height){
        Constants.setScreenHeight(height);
        // System.out.println("Screen height " + height);
        initView.modelUpdate(this, "windowUpdate");
    }
    public void resizeWidth(double width){
        Constants.setScreenLength(width);
        initView.modelUpdate(this, "windowUpdate");
    }

    public HashMap<String, Edges> getEdges(){ return this.edges;}
    public HashMap<String, Vertex> getVertices(){ return this.vertices; }
    public ArrayList<Vertex> getSelectedVertices(){ return this.selectedVertices;}
    public void setEdges(HashMap<String, Edges> edges){ this.edges = edges;}
    public void setVertices(HashMap<String, Vertex> vertices){ this.vertices = vertices; }
    public void setSelectedVertices(ArrayList<Vertex> vertices){ this.selectedVertices = vertices;}

    public void makeNeighbor(Vertex srcVertex, Vertex dstVertex){
        if(edges.containsKey(srcVertex.getName() + dstVertex.getName())){
            System.out.println("This vertex pair already exists!");
        }
        else{
            srcVertex.addNeighbor(dstVertex);
            edges.put(srcVertex.getName() + dstVertex.getName(), new Edges(srcVertex, dstVertex));
        }
    }
    public void removeNeighbor(Vertex srcVertex, Vertex dstVertex){
        if(!edges.containsKey(srcVertex.getName() + dstVertex.getName())){
            System.out.println("This vertex pair doesn't exists!");
        }
        else{
            srcVertex.removeNeighbor(dstVertex);
            edges.remove(srcVertex.getName() + dstVertex.getName());
            initView.modelUpdate(this, "graphUpdate");
        }
    }

    public void setXYVertex(Vertex v, double x, double y){
        v.setX(x);
        v.setY(y);
        initView.modelUpdate(this, "graphUpdate");
    }
    public void flopSelected(Vertex v){
        if(v.isSelected()){
            selectedVertices.remove(v);
        }
        else{
            selectedVertices.add(v);
        }
        v.flopSelected();
        initView.modelUpdate(this, "graphUpdate"); //Possible to only update color
    }
    public void setColor(Vertex v, int color){
        v.setColor(color);
        initView.modelUpdate(this, "graphUpdate"); //Possible to only update color
    }
    public void setWeight(Edges e, double weight){
        e.setWeight(weight);
        initView.modelUpdate(this, "graphUpdate"); //Possible to only update name/color
    }
    public void setColor(Edges e, int color){
        e.setColor(color);
        initView.modelUpdate(this, "graphUpdate"); //Possible to only update color
    }

}
