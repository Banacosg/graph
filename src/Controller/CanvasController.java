package src.Controller;

import javafx.scene.Scene;
import src.model.GraphModel;
import src.model.Vertex;
import src.model.graphReader;
import src.model.Constants;

public class CanvasController {
    private final Scene scene;
    private double mouseX = 0;
    private double mouseY = 0;
    private GraphModel graph;
    

    public CanvasController(Scene scene){
        this.scene = scene;
        // scene.setOnMouseMoved(e -> {
        //     mouseX = e.getX();
        //     mouseY = e.getY();
        // });

        // scene.setOnMouseClicked(e -> {
        //     System.out.println(mouseX);
        //     System.out.println(mouseY);

        // });
        clickOnVertex();

    }

    public void readFile(){
        this.graph = graphReader.readGraph("data/graph2.txt");
    }

    private void clickOnVertex(){
        scene.setOnMouseClicked(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
            System.out.println(mouseX);
            System.out.println(mouseY);
            Object[] vertices = graph.getVertices().values().toArray();
            int topLeftX;
            int topLeftY;
            int bottomLeftX;
            int bottomLeftY;
            Vertex curVertex = null;
            boolean hit = false;
            for (Object v : vertices){
                curVertex = (Vertex) v;
                topLeftX = curVertex.getX() - Constants.VERTEX_OFFSET;
                topLeftY = curVertex.getY() - Constants.VERTEX_OFFSET;
                bottomLeftX = curVertex.getX() + Constants.VERTEX_OFFSET;
                bottomLeftY = curVertex.getY() + Constants.VERTEX_OFFSET;
                if(mouseX >= topLeftX && mouseY >= topLeftY && mouseX <= bottomLeftX && mouseY <= bottomLeftY){
                    hit = true;
                    break;
                }
            }
            if(hit){
                System.out.println("HIT!");
                System.out.println(curVertex.getName());
            }else{
                System.out.println("MISS!");
            }
        });
    }
}
