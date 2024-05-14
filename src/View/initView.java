package src.View;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import src.model.Edges;
import src.model.GraphModel;
import src.model.Vertex;
import src.model.graphReader;
import src.Controller.CanvasController;
import src.model.Constants;

public class initView extends Application{
    

    private static GraphicsContext gc;
    private static CanvasController controller;

    public static void startView(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Graph Visualizer");
        Scene scene = new Scene(createContent());
        CanvasController controller = new CanvasController(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
        controller.readFile(); //Initializes model
    }


    private Parent createContent(){
        Canvas canvas = new Canvas(Constants.SCREEN_LENGTH, Constants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Pane pane = new Pane(canvas);
        return pane;
    }

    private static void initGraph(GraphModel graph){
        Random random = new Random();
        int xCoord;
        int yCoord;
        Object[] vertices = graph.getVertices().values().toArray();
        Vertex curVertex;
        for(int i = 0; i < vertices.length; i++){
            xCoord = random.nextInt(Constants.SCREEN_LENGTH - 2 * Constants.VERTEX_OFFSET) + Constants.VERTEX_OFFSET; //Add 5 to garuntee no cut off vertices
            yCoord = random.nextInt(Constants.SCREEN_HEIGHT - 2 * Constants.VERTEX_OFFSET) + Constants.VERTEX_OFFSET;
            curVertex = (Vertex) vertices[i];
            curVertex.setX(xCoord);
            curVertex.setY(yCoord);
        }
    }

    private static void outputGraph(GraphModel graph){
        Object[] vertices = graph.getVertices().values().toArray();
        Vertex curVertex;
        for(int i = 0; i < vertices.length; i++){ //Vertex output
            curVertex = (Vertex) vertices[i];
            gc.fillOval(curVertex.getX() - Constants.VERTEX_OFFSET, curVertex.getY() - Constants.VERTEX_OFFSET, Constants.VERTEX_SIZE, Constants.VERTEX_SIZE);
        }

        Object[] edges = graph.getEdges().values().toArray();
        gc.setLineWidth(Constants.LINE_WIDTH);
        Vertex curSource;
        Vertex curDest;
        for(int i = 0; i < edges.length; i++){ //Edge output
            curSource = ((Edges) edges[i]).getSrcVertex();
            curDest = ((Edges) edges[i]).getDestVertex();
            gc.setStroke(Color.BLUE);
            gc.strokeLine((double) curSource.getX(), (double) curSource.getY(),(double) curDest.getX(), (double) curDest.getY());
        }
    }

    public static void modelUpdate(GraphModel graph, String message){
        switch (message){
            case "read" -> {initGraph(graph); outputGraph(graph);}
            case "graphUpdate" -> onUpdate();
            default -> {System.out.println(message); System.exit(0);}
        }
    }

    private static void onUpdate(){
        gc.clearRect(0,0,Constants.SCREEN_LENGTH, Constants.SCREEN_HEIGHT); //Clear screen

    }
}