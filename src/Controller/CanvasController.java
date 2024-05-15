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

    public void click(double mouseX, double mouseY){
        //Mouse location is relative to the canvas top left corner
        if(mouseX >= 0 && mouseY >= 0 && mouseX <= Constants.getScreenLength() && mouseY <= Constants.getScreenHeight()){
            ArrayList<Vertex> selectedVertices = graph.getSelectedVertices();
            if(selectedVertices.size() == 1) { 
                Vertex curVertex = selectedVertices.get(0);
                // if(curVertex.getName().equals("A")){
                //     System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                //     System.out.println((mouseY  / Constants.SCREEN_HEIGHT));
                // }
                graph.setXYVertex(curVertex, mouseX / Constants.getScreenLength(), (mouseY  / Constants.getCanvasHeight()));
                graph.flopSelected(curVertex);
            }
            else{
                // System.out.println(mouseX);
                // System.out.println(mouseY);
                Object[] vertices = graph.getVertices().values().toArray();
                double topLeftX;
                double topLeftY;
                double bottomLeftX;
                double bottomLeftY;
                Vertex curVertex = null;
                boolean hit = false;
                // System.out.println("Length: " + Constants.getScreenLength());
                for (Object v : vertices){
                    curVertex = (Vertex) v;
                    topLeftX = curVertex.getX() * Constants.getScreenLength() - Constants.getVertexOffset();
                    topLeftY = curVertex.getY() * Constants.getCanvasHeight() - Constants.getVertexOffset();
                    bottomLeftX = curVertex.getX() * Constants.getScreenLength() + Constants.getVertexOffset();
                    bottomLeftY = curVertex.getY() * Constants.getCanvasHeight() + Constants.getVertexOffset();
                    // System.out.println(topLeftX + " " + topLeftY + " " + bottomLeftX + " " + bottomLeftY + " " + curVertex.getName());
                    if(mouseX >= topLeftX && mouseY >= topLeftY && mouseX <= bottomLeftX && mouseY <= bottomLeftY){
                        hit = true;
                        break;
                    }
                }
                if(hit){
                    graph.flopSelected(curVertex);
                }
            }
        }
        else{
            System.out.println("Out of bounds");
            // System.out.println("Mouse X: " + mouseX);
            // System.out.println("Mouse Y: " + mouseY);
        }
    }
}
