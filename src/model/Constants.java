package src.model;

public class Constants {
    private final static double VERTEX_SIZE = 10;
    private final static double VERTEX_OFFSET = 5;
    private final static double LINE_WIDTH = 2.0;
    private final static double TOOL_BAR_HEIGHT = 26;
    private final static double TITLE_BAR_HEIGHT = 37.3333333;
    private static double SCREEN_LENGTH = 1920;
    private static double SCREEN_HEIGHT = 1080;
    private final static double XYINIT_PROPORTION = (SCREEN_LENGTH - 16) / SCREEN_LENGTH; //To keep vertices on part of canvas that is displayed
    private static double CANVAS_HEIGHT = SCREEN_HEIGHT - TOOL_BAR_HEIGHT - TITLE_BAR_HEIGHT;

    public static double getVertexSize(){
        return VERTEX_SIZE;
    }
    public static double getVertexOffset(){
        return VERTEX_OFFSET;
    }
    public static double getLineWidth(){
        return LINE_WIDTH;
    }
    public static double getToolBarHeight(){
        return TOOL_BAR_HEIGHT;
    }
    public static double getTitleBarHeight(){
        return TITLE_BAR_HEIGHT;
    }
    public static double getScreenLength(){
        return SCREEN_LENGTH;
    }
    public static double getScreenHeight(){
        return SCREEN_HEIGHT;
    }
    public static double getCanvasHeight(){
        return CANVAS_HEIGHT;
    }
    public static double getXYInit_Proportion(){
        return XYINIT_PROPORTION;
    }
    public static void setScreenLength(double screenLength){
        SCREEN_LENGTH = screenLength;
    }
    public static void setCanvasHeight(double screenHeight){
        SCREEN_HEIGHT = screenHeight;
    }
    public static void setScreenHeight(double height){
        SCREEN_HEIGHT = height;
        CANVAS_HEIGHT = SCREEN_HEIGHT - TOOL_BAR_HEIGHT - TITLE_BAR_HEIGHT;
    }

}
