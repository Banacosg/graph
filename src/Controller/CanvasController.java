package src.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Scene;
import src.model.GraphModel;
import src.model.Vertex;
import src.model.graphReader;
import src.model.Constants;
import src.model.Edges;

public class CanvasController {
    private GraphModel graph;
    private enum ClickMode{
        moveMode,
        addMode,
        removeMode,
        colorMode
    }
    private ClickMode clickMode = ClickMode.moveMode;
    
    /*
     * This method instantiates and sets up generic user interactions with
     * the GUI. Other inreractions such as buttons are done by concrete methods
     * 
     */
    public CanvasController(){}

    public void readFile(File file){
        if(file != null){
            String fileName = file.getName();
            if(fileName.substring(fileName.indexOf(".")).equals(".grh")){
                this.graph = graphReader.readGraph(file);
            }
            else{
                System.out.println("File name doesn't match .grh extension!");
            }
        }
    }

    public void resizeHeight(double oldVal, double newVal){
        // System.out.println(newVal);
        if(newVal <= Constants.getTitleBarHeight() + Constants.getToolBarHeight()){
            System.out.println("Too small");
        }
        else{
            graph.resizeHeight(newVal);
        }
    }

    public void resizeWidth(double oldVal, double newVal){
        // System.out.println("Current width" + newVal);
        if(newVal <= 100){
            System.out.println("Too small");
        }
        else{
            graph.resizeWidth(newVal);
        }
    }

    public void setMode(String mode){
        switch(mode){
            case "add" -> clickMode = ClickMode.addMode; 
            case "move" -> clickMode = ClickMode.moveMode;
            case "remove" -> clickMode = ClickMode.removeMode;
        }
    }

    private Vertex foundCollision(double mouseX, double mouseY){
        Object[] vertices = graph.getVertices().values().toArray();
        double topLeftX;
        double topLeftY;
        double bottomLeftX;
        double bottomLeftY;
        Vertex curVertex = null;
        boolean hit = false;
        for (Object v : vertices){
            curVertex = (Vertex) v;
            topLeftX = curVertex.getXCoeff() * Constants.getScreenLength() - Constants.getVertexOffset();
            topLeftY = curVertex.getYCoeff() * Constants.getCanvasHeight() - Constants.getVertexOffset();
            bottomLeftX = curVertex.getXCoeff() * Constants.getScreenLength() + Constants.getVertexOffset();
            bottomLeftY = curVertex.getYCoeff() * Constants.getCanvasHeight() + Constants.getVertexOffset();
            //System.out.println(topLeftX + " " + topLeftY + " " + bottomLeftX + " " + bottomLeftY + " " + curVertex.getName());
            if(mouseX >= topLeftX && mouseY >= topLeftY && mouseX <= bottomLeftX && mouseY <= bottomLeftY){
                hit = true;
                break;
            }
        }
        if(hit){
            return curVertex;
        }else{
            return null;
        }
    }

    private Edges foundEdge(double mouseX, double mouseY){
        for(Vertex curVertex : graph.getVertices().values()){
            Object[] curEdges = curVertex.getNeighbors().values().toArray();
            double srcX, srcY, destX, destY;
            double leftX, rightX, upperY, lowerY;
            for(Object e : curEdges){
                Edges curEdge = (Edges) e; 
                srcX = curEdge.getSrcVertex().getXCoeff() * Constants.getScreenLength();
                srcY = curEdge.getSrcVertex().getYCoeff() * Constants.getCanvasHeight();
                destX = curEdge.getDestVertex().getXCoeff() * Constants.getScreenLength();
                destY = curEdge.getDestVertex().getYCoeff() * Constants.getCanvasHeight();
                if(srcX > destX){
                    leftX = destX;
                    rightX = srcX;
                }else{
                    leftX = srcX;
                    rightX = destX;
                }
                if(srcY > destY){
                    upperY = destY; //Upper as in high on the screen
                    lowerY = srcY;
                }
                else{
                    upperY = srcY; //Upper as in high on the screen
                    lowerY = destY;
                }
                // System.out.println("leftx: " + (leftX - Constants.getVertexSize()) + ", rightx: " + (rightX + Constants.getVertexSize()) + ", uppery: " + (upperY + Constants.getVertexSize()) + ", lowerY: " + (lowerY - Constants.getVertexSize()));
                // System.out.println(mouseX);
                // System.out.println(mouseY);
                if(mouseX <= rightX  && mouseX >= leftX  && mouseY <= lowerY  && mouseY >= upperY){
                    double lineY = curEdge.getCorrespondingY(mouseX);
                    // System.out.println("Got Y " + lineY);
                    if(mouseY >= lineY - Constants.getLineHitBox() && mouseY <= lineY + Constants.getLineHitBox()){
                        return curEdge;
                    }
                }
            }
        }
        return null;
    }

    public void leftClick(double mouseX, double mouseY){
        if(mouseX >= 0 && mouseY >= 0 && mouseX <= Constants.getScreenLength() && mouseY <= Constants.getScreenHeight()){
            switch(clickMode){
                case ClickMode.moveMode -> moveLeftClick(mouseX, mouseY);
                case ClickMode.addMode -> addLeftClick(mouseX, mouseY);
                case ClickMode.removeMode -> removeRightClick(mouseX, mouseY);
                case ClickMode.colorMode -> System.out.println("Implement color mode");
                default -> System.out.println("Something else");
            }
        }
        else{
            System.out.println("Out of bounds");
        }
    }

    private void removeRightClick(double mouseX, double mouseY){
        Vertex v = foundCollision(mouseX, mouseY);
        ArrayList<Vertex> selectedVertices = graph.getSelectedVertices();
        Edges selectedEdge = foundEdge(mouseX, mouseY);
        if(selectedVertices.size() == 0){
            System.out.println("select");
            if(v != null){
                graph.flopSelected(v);
            }
            else{
                if(selectedEdge != null){
                    graph.removeNeighbor(selectedEdge.getSrcVertex(), selectedEdge.getDestVertex());
                }
            }
        }
        else if(selectedVertices.size() == 1){
            Vertex selectedVertex = selectedVertices.get(0);
            if(v != null){
                if(selectedVertex == v){
                    graph.flopSelected(selectedVertex);
                    graph.removeVertex(selectedVertex);
                }else{
                    graph.removeEdge(selectedVertex, v);
                }
            }else{
                System.out.println("Unselect");
                graph.flopSelected(selectedVertex);
            }
        }
    }

    private void addLeftClick(double mouseX, double mouseY){
        ArrayList<Vertex> selectedVertices = graph.getSelectedVertices();
        Vertex v = foundCollision(mouseX, mouseY);
        if(selectedVertices.size() == 0){
            if(v != null){
                graph.flopSelected(v);
            }
            else{
                graph.addVertex(mouseX, mouseY);
            }   
        }
        else if(selectedVertices.size() == 1) { //Issue with this case
            Vertex selectedVertex = selectedVertices.get(0);
            if(v == null){
                graph.addVertexWithNeighbor(mouseX, mouseY, selectedVertex);
            }else{
                if(v == selectedVertex){ //right here
                    graph.flopSelected(v);
                }
                else{
                    if(!selectedVertex.getNeighbors().containsKey(v.getName())){
                        graph.makeNeighbor(selectedVertex, v);
                        graph.flopSelected(selectedVertex);
                    }   
                    else{
                        graph.flopSelected(selectedVertex);
                    }
                }
            }
        }
        else{
            graph.addVertex(mouseX, mouseY);
        }
    }



    private void moveLeftClick(double mouseX, double mouseY){
        ArrayList<Vertex> selectedVertices = graph.getSelectedVertices();
        if(selectedVertices.size() == 1) { 
            Vertex curVertex = selectedVertices.get(0);
            graph.setXYVertex(curVertex, mouseX, mouseY);
            graph.flopSelected(curVertex);
        }
        else{
            Vertex v = foundCollision(mouseX, mouseY);
            if(v != null){
                graph.flopSelected(v);
            }
        }
    }
}
