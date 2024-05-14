package src.model;

import java.util.HashMap;

public class Edges {
    private Vertex startVertex, endVertex;
    private double weight = 1;
    private int color = 0;

    public Edges(Vertex srcVertex, Vertex dstVertex) {
        this.startVertex = srcVertex;
        this.endVertex = dstVertex;
    }

    public Edges(Vertex srcVertex, Vertex dstVertex, double weight) {
        this.startVertex = srcVertex;
        this.endVertex = dstVertex;
        this.weight = weight;
    }

    public Vertex getSrcVertex(){ return startVertex; }
    public Vertex getDestVertex(){ return endVertex; }
    public double getWeight(){ return weight; }
    public double getColor(){ return color; }
    public void setSrcVertex(Vertex newSrcVertex){ this.startVertex = newSrcVertex; }
    public void setDestVertex(Vertex newDestVertex){ this.endVertex = newDestVertex; }
    public void setWeight(double weight){ this.weight = weight; }
    public void setColor(int color){ this.color = color; }

}
