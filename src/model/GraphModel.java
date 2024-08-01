package src.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import src.View.initView;

public class GraphModel {
    private HashMap<String, Vertex> vertices;
    private ArrayList<Vertex> selectedVertices = new ArrayList<Vertex>();

    public GraphModel(HashMap<String, Vertex> vertices){
        this.vertices = vertices;
    }

    private void setEdgeEquations(Vertex changedVertex){
        for(Edges e : changedVertex.getNeighbors().values()){
            e.setLinearEquation();
        }
        for(Vertex v : vertices.values()){
            if(v.getNeighbors().containsKey(changedVertex.getName())){
                v.getNeighbors().get(changedVertex.getName()).setLinearEquation();
            }
        }
    }

    public void initGraph(){
        Random random = new Random();
        double xCoord;
        double yCoord;
        Object[] verticesArray = vertices.values().toArray();
        Vertex curVertex;
        for(int i = 0; i < verticesArray.length; i++){
            xCoord = random.nextDouble(Constants.getXYInit_Proportion() * Constants.getScreenLength()); 
            yCoord = random.nextDouble(Constants.getXYInit_Proportion() * Constants.getCanvasHeight());
            curVertex = (Vertex) verticesArray[i];
            curVertex.setXCoeff(xCoord);
            curVertex.setYCoeff(yCoord);
            setEdgeEquations(curVertex);
        }
        initView.modelUpdate(this, "read");
    }

    public void resizeHeight(double height){
        Constants.setScreenHeight(height);
        initView.modelUpdate(this, "windowUpdate");
    }
    public void resizeWidth(double width){
        Constants.setScreenLength(width);
        initView.modelUpdate(this, "windowUpdate");
    }
    public HashMap<String, Vertex> getVertices(){ return this.vertices; }
    public ArrayList<Vertex> getSelectedVertices(){ return this.selectedVertices;}
    public void setVertices(HashMap<String, Vertex> vertices){ this.vertices = vertices; }
    public void setSelectedVertices(ArrayList<Vertex> vertices){ this.selectedVertices = vertices;}

    private String genRandomString(){
        Random rand = new Random();
        String randString = "";
        for(int i = 0; i < 20; i++){
            char c = (char) rand.nextInt(Constants.getEightBitLimit());
            randString += c;
        }
        return randString;
    }

    public void addVertex(double x, double y){
        String name = genRandomString();
        Vertex v = new Vertex(name);
        v.setXCoeff(x);
        v.setYCoeff(y);
        vertices.put(name, v);
        setEdgeEquations(v);
        initView.modelUpdate(this, "graphUpdate");
    }

    public void removeVertex(Vertex v){
        for(Vertex curVertex : vertices.values()){
            HashMap<String, Edges> neighborVertices = curVertex.getNeighbors();
            if(neighborVertices.containsKey(v.getName())){
                curVertex.removeNeighbor(v);
            }
        }
        vertices.remove(v.getName());
        initView.modelUpdate(this, "graphUpdate");
    }

    public void removeEdge(Vertex srcVertex, Vertex dstVertex){
        srcVertex.removeNeighbor(dstVertex);
        initView.modelUpdate(this, "graphUpdate");
    }

    public void addVertexWithNeighbor(double x, double y, Vertex neighbor){
        String name = genRandomString();
        Vertex v = new Vertex(name);
        v.setXCoeff(x);
        v.setYCoeff(y);
        vertices.put(name, v);
        neighbor.addNeighbor(v);
        setEdgeEquations(neighbor);
        initView.modelUpdate(this, "graphUpdate");
    }

    public void makeNeighbor(Vertex srcVertex, Vertex dstVertex){
        if(srcVertex.getNeighbors().containsKey(dstVertex.getName())){
            System.out.println("This vertex pair already exists!");
        }
        else{
            srcVertex.addNeighbor(dstVertex);
            setEdgeEquations(srcVertex);
        }
    }
    public void removeNeighbor(Vertex srcVertex, Vertex dstVertex){
        if(!srcVertex.getNeighbors().containsKey(dstVertex.getName())){
            System.out.println("This vertex pair doesn't exists!");
        }
        else{
            srcVertex.removeNeighbor(dstVertex);
            initView.modelUpdate(this, "graphUpdate");
        }
    }

    public void setXYVertex(Vertex v, double x, double y){
        v.setXCoeff(x);
        v.setYCoeff(y);
        setEdgeEquations(v);
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
