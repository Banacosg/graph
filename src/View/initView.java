package src.View;

import java.io.File;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import src.model.Edges;
import src.model.GraphModel;
import src.model.Vertex;
import src.Controller.CanvasController;
import src.model.Constants;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D; 

public class initView extends Application{
    
    private static Scene scene; 
    private static BorderPane borderPane;
    private static GraphicsContext gc;
    private static CanvasController controller;
    private static MenuBar mainMenu;

    public static void startView(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Graph Visualizer");
        primaryStage.setMinHeight(Constants.getTitleBarHeight() + Constants.getToolBarHeight());
        borderPane = new BorderPane();
        mainMenu = new MenuBar();
        borderPane.setTop(mainMenu);
        createCanvas();
        scene = new Scene(borderPane);
        initController(primaryStage);
        controller.readFile(new File("data/graph.grh")); //So no error occurs when clicking on screen before opening file
        mainMenu.getMenus().add(initFileChooser(primaryStage, controller));
        initUserButtons();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initController(Stage primaryStage){
        controller = new CanvasController();
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> controller.resizeWidth((double) oldVal, (double)newVal));
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> controller.resizeHeight((double)oldVal, (double)newVal));

    }

    private void createCanvas(){
        Canvas canvas = new Canvas(Constants.getScreenLength(), Constants.getCanvasHeight());
        gc = canvas.getGraphicsContext2D();
        borderPane.setCenter(canvas);
    }

    private Menu initFileChooser(Stage primaryStage, CanvasController controller){
        FileChooser fileChooser = new FileChooser();

        Menu fileMenu = new Menu();
        fileMenu.setText("File");
        MenuItem selectFile = new MenuItem();
        selectFile.setText("Select File");
        selectFile.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            controller.readFile(selectedFile);
        });
        fileMenu.getItems().add(selectFile);
        return fileMenu;
    }

    private void initUserButtons(){
        Menu editGraphChoices = new Menu();
        editGraphChoices.setText("Edit Graph");
        MenuItem moveOption = new MenuItem();
        moveOption.setText("Move Vertices");
        moveOption.setOnAction(e -> {
            System.out.println("moving");
            controller.setMode("move");
        });
        MenuItem addOption = new MenuItem();
        addOption.setText("Add Vertices/Edges");
        addOption.setOnAction(e -> {
            System.out.println("adding");
            controller.setMode("add");
        });
        MenuItem removeOption = new MenuItem();
        removeOption.setText("Remove Vertices/Edges");
        removeOption.setOnAction(e -> {
            System.out.println("Removing");
            controller.setMode("remove");
        });
        editGraphChoices.getItems().addAll(moveOption, addOption, removeOption);
        mainMenu.getMenus().addAll(editGraphChoices);

    }

    private static void drawArrow(Vertex curSource, Vertex curDest){
        Vector3D srcLoc = new Vector3D(curSource.getXCoeff() * Constants.getScreenLength(),curSource.getYCoeff() * Constants.getCanvasHeight(),0);
        Vector3D dstLoc = new Vector3D(curDest.getXCoeff() * Constants.getScreenLength(),curDest.getYCoeff() * Constants.getCanvasHeight(),0);
        Vector3D diffUnitVector = dstLoc.subtract(srcLoc).normalize();
        double x = diffUnitVector.getX();
        double y = diffUnitVector.getY();
        Vector3D leftArrowLine = new Vector3D((Math.cos(Constants.getArrowAngle() * Constants.TORADIANS) * x - Math.sin(Constants.getArrowAngle() * Constants.TORADIANS) * y), (Math.sin(Constants.getArrowAngle() * Constants.TORADIANS) * x + Math.cos(Constants.getArrowAngle() * Constants.TORADIANS) * y), 0);
        Vector3D rightArrowLine = new Vector3D((Math.cos(-Constants.getArrowAngle()* Constants.TORADIANS) * x - Math.sin(-Constants.getArrowAngle() * Constants.TORADIANS) * y), (Math.sin(-Constants.getArrowAngle() * Constants.TORADIANS) * x + Math.cos(-Constants.getArrowAngle() * Constants.TORADIANS) * y), 0);
        gc.setLineWidth(Constants.getArrowWidth());
        gc.setStroke(Color.DARKBLUE);
        gc.strokeLine(dstLoc.getX(), dstLoc.getY(), dstLoc.getX() - Constants.getArrowLength() * leftArrowLine.getX(), dstLoc.getY() - Constants.getArrowLength() * leftArrowLine.getY());
        gc.strokeLine(dstLoc.getX(), dstLoc.getY(), dstLoc.getX() - Constants.getArrowLength() * rightArrowLine.getX(), dstLoc.getY() - Constants.getArrowLength() * rightArrowLine.getY());
    }

    private static void outputGraph(GraphModel graph){
        Object[] vertices = graph.getVertices().values().toArray();
        Vertex curVertex;
        for(int i = 0; i < vertices.length; i++){ //Vertex output
            curVertex = (Vertex) vertices[i];
            if(curVertex.isSelected()){
                gc.setFill(Color.RED);
            }
            else{
                gc.setFill(Color.BLACK);
            }
            gc.fillOval(curVertex.getXCoeff() * Constants.getScreenLength() - Constants.getVertexOffset(), curVertex.getYCoeff() * Constants.getCanvasHeight() - Constants.getVertexOffset() , Constants.getVertexSize(), Constants.getVertexSize());
        }
        Vertex curSource;
        Vertex curDest;
        for(Object v : vertices){
            curSource = (Vertex) v;
            Object[] curEdges = curSource.getNeighbors().values().toArray();
            for(Object e : curEdges){
                curDest = ((Edges) e).getDestVertex();
                gc.setLineWidth(Constants.getLineWidth());
                gc.setStroke(Color.BLUE);
                gc.strokeLine(curSource.getXCoeff() * Constants.getScreenLength(), curSource.getYCoeff() * Constants.getCanvasHeight(), curDest.getXCoeff() * Constants.getScreenLength(), curDest.getYCoeff() * Constants.getCanvasHeight());
                if(Constants.isDirected()){
                    drawArrow(curSource, curDest);
                }
            }
        }
    }

    private static void initUserInputs(){
        scene.setOnMouseClicked(e -> {
            MouseButton button = e.getButton();
            if(button==MouseButton.PRIMARY){
                controller.leftClick(e.getX(), e.getY() - Constants.getToolBarHeight());
            }
            //controller.click(e.getX(), e.getY() - Constants.getToolBarHeight()); //Get's mouse location relativeto canvas top left corner
        });
    }

    public static void modelUpdate(GraphModel graph, String message){
        System.out.println("modelUpdate " + message);
        switch (message){
            case "read" -> {initUserInputs(); clearCanvas(); outputGraph(graph); System.out.println("read");}
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