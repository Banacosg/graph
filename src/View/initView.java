package src.View;

import java.io.File;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.model.Edges;
import src.model.GraphModel;
import src.model.Vertex;
import src.Controller.CanvasController;
import src.model.Constants;

public class initView extends Application{
    
    private static Scene scene; 
    private static BorderPane borderPane;
    private static GraphicsContext gc;
    private static CanvasController controller;

    public static void startView(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Graph Visualizer");
        primaryStage.setMinHeight(Constants.getTitleBarHeight() + Constants.getToolBarHeight());
        borderPane = new BorderPane();
        createCanvas();
        scene = new Scene(borderPane);
        controller = new CanvasController();
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> controller.resizeWidth((double) oldVal, (double)newVal));
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> controller.resizeHeight((double)oldVal, (double)newVal));
        controller.readFile(new File("data/graph.grh")); //So no error occurs when clicking on screen before opening file
        borderPane.setTop(initFileChooser(primaryStage, controller));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createCanvas(){
        Canvas canvas = new Canvas(Constants.getScreenLength(), Constants.getCanvasHeight());
        gc = canvas.getGraphicsContext2D();
        borderPane.setCenter(canvas);
    }
    
    private Button initFileChooser(Stage primaryStage, CanvasController controller){
        FileChooser fileChooser = new FileChooser();
        Button button = new Button("Select File");
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            controller.readFile(selectedFile);
        });
        return button;
    }

    private static void outputGraph(GraphModel graph){
        Object[] vertices = graph.getVertices().values().toArray();
        Vertex curVertex;
        // double canvasHeight = Constants.SCREEN_HEIGHT - Constants.TITLE_BAR_HEIGHT - Constants.TOOL_BAR_HEIGHT;
        for(int i = 0; i < vertices.length; i++){ //Vertex output
            curVertex = (Vertex) vertices[i];
            if(curVertex.isSelected()){
                gc.setFill(Color.RED);
            }
            else{
                gc.setFill(Color.BLACK);
            }
            gc.fillOval(curVertex.getX() * Constants.getScreenLength() - Constants.getVertexOffset(), curVertex.getY() * Constants.getCanvasHeight() - Constants.getVertexOffset() , Constants.getVertexSize(), Constants.getVertexSize());
            // if(curVertex.getName().equals("A")){
            //     System.out.print("A's Height location: X: ");
            //     System.out.print(curVertex.getX() * Constants.SCREEN_LENGTH );
            //     System.out.print(" Y: ");
            //     System.out.println(Constants.SCREEN_HEIGHT);
            //     System.out.println(curVertex.getY() * (canvasHeight));
            // }
        }

        Object[] edges = graph.getEdges().values().toArray();
        gc.setLineWidth(Constants.getLineWidth());
        Vertex curSource;
        Vertex curDest;
        for(int i = 0; i < edges.length; i++){ //Edge output
            curSource = ((Edges) edges[i]).getSrcVertex();
            curDest = ((Edges) edges[i]).getDestVertex();
            gc.setStroke(Color.BLUE);
            gc.strokeLine(curSource.getX() * Constants.getScreenLength(), curSource.getY() * Constants.getCanvasHeight(), curDest.getX() * Constants.getScreenLength(), curDest.getY() * Constants.getCanvasHeight());
        }
    }

    private static void initButtons(){
        scene.setOnMouseClicked(e -> {
            controller.click(e.getX(), e.getY() - Constants.getToolBarHeight()); //Get's mouse location relativeto canvas top left corner
        });
    }

    public static void modelUpdate(GraphModel graph, String message){
        switch (message){
            case "read" -> {initButtons(); clearCanvas(); outputGraph(graph);}
            case "graphUpdate" -> {clearCanvas(); outputGraph(graph);}
            case "windowUpdate" -> {updateWindow(); clearCanvas(); outputGraph(graph);}
            default -> {System.out.println(message); System.exit(0);}
        }
    }

    private static void clearCanvas(){
        gc.clearRect(0,0,Constants.getScreenLength(), Constants.getScreenHeight());
    }

    private static void updateWindow(){
        // System.out.println("Canvas height: " + (Constants.SCREEN_HEIGHT - Constants.TITLE_BAR_HEIGHT - Constants.TOOL_BAR_HEIGHT));
        Canvas canvas = new Canvas(Constants.getScreenLength(), Constants.getCanvasHeight());
        gc = canvas.getGraphicsContext2D();
        borderPane.setCenter(canvas);
    }

}