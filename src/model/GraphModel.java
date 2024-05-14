package src.model;

import java.util.HashMap;

import src.View.initView;

public class GraphModel {
    private HashMap<String, Edges> edges;
    private HashMap<String, Vertex> vertices;

    public GraphModel(HashMap<String, Edges> edges, HashMap<String, Vertex> vertices){
        this.edges = edges;
        this.vertices = vertices;

    }

    public HashMap<String, Edges> getEdges(){ return this.edges;}
    public HashMap<String, Vertex> getVertices(){ return this.vertices; }
    public void setEdges(HashMap<String, Edges> edges){ this.edges = edges;}
    public void setVertices(HashMap<String, Vertex> vertices){ this.vertices = vertices; }

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

}
