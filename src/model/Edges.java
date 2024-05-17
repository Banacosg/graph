package src.model;

public class Edges {
    private Vertex startVertex, endVertex;
    private double weight = 1;
    private int color = 0;
    private double linearCoefficent;
    private double startPointX;
    private double startPointY;

    public Edges(Vertex srcVertex, Vertex dstVertex) {
        this.startVertex = srcVertex;
        this.endVertex = dstVertex;
        setLinearEquation();
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
    public double getLinearCoefficent(){ return linearCoefficent; }
    public double startPointX(){ return startPointX; }
    public double startPointY(){ return startPointY; }
    public void setSrcVertex(Vertex newSrcVertex){ this.startVertex = newSrcVertex; setLinearEquation();}
    public void setDestVertex(Vertex newDestVertex){ this.endVertex = newDestVertex; setLinearEquation();}
    public void setWeight(double weight){ this.weight = weight; }
    public void setColor(int color){ this.color = color; }
    
    
    public void setLinearEquation(){
        double startX = startVertex.getXCoeff() * Constants.getScreenLength();
        double startY = startVertex.getYCoeff() * Constants.getCanvasHeight();
        double endX = endVertex.getXCoeff() * Constants.getScreenLength();
        double endY = endVertex.getYCoeff() * Constants.getCanvasHeight();

        linearCoefficent = (endY - startY) / (endX - startX);
        startPointX = startX;
        startPointY = startY;
    }
    public double getCorrespondingY(double mouseX){
        //Equation obtained from rearranging point slope form
        return LinearEquation.LinearFunction((x) -> linearCoefficent * (x - startPointX) + startPointY, mouseX);
    }

}
