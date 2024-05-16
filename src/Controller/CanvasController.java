package src.Controller;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.Scene;
import src.model.GraphModel;
import src.model.Vertex;
import src.model.graphReader;
import src.model.Constants;

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

    public void leftClick(double mouseX, double mouseY){
        if(mouseX >= 0 && mouseY >= 0 && mouseX <= Constants.getScreenLength() && mouseY <= Constants.getScreenHeight()){
            switch(clickMode){
                case ClickMode.moveMode -> moveLeftClick(mouseX, mouseY);
                case ClickMode.addMode -> addLeftClick(mouseX, mouseY);
                case ClickMode.removeMode -> System.out.println("Implement remove mode");
                case ClickMode.colorMode -> System.out.println("Implement color mode");
                default -> System.out.println("Something else");
            }
        }
        else{
            System.out.println("Out of bounds");
        }
    }

    private void addLeftClick(double mouseX, double mouseY){
        ArrayList<Vertex> selectedVertices = graph.getSelectedVertices();
        Vertex v = foundCollision(mouseX, mouseY);
        System.out.print(selectedVertices.size() + " ");
        System.out.println(v);
        if(selectedVertices.size() == 0){
            if(v != null){
                graph.flopSelected(v);
            }
            else{
                graph.addVertex(mouseX, mouseY);
            }   
        }
        else if(selectedVertices.size() == 1) { //Issue with this case
            if(v == null){
                graph.addVertexWithNeighbor(mouseX, mouseY, selectedVertices.get(0));
            }else{
                if(v == selectedVertices.get(0)){ //right here
                    graph.flopSelected(v);
                }
                else{
                    graph.makeNeighbor(selectedVertices.get(0), v);
                    graph.flopSelected(selectedVertices.get(0));
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
            // System.out.println(mouseX);
            // System.out.println(mouseY);
            Vertex v = foundCollision(mouseX, mouseY);
            if(v != null){
                graph.flopSelected(v);
            }
        }
    }
}
