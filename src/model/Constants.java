package src.model;

public class Constants {
    private final static double VERTEX_SIZE = 10;
    private final static double VERTEX_OFFSET = 5;
    private final static double LINE_WIDTH = 2.0;
    private final static double ARROW_WIDTH = 3.0;
    private final static double ARROW_ANGLE = 20.0;
    private final static double ARROW_LENGTH = 20.0;
    private final static double TOOL_BAR_HEIGHT = 26;
    private final static double TITLE_BAR_HEIGHT = 37.3333333;
    private final static double LINE_HIT_BOX = 5.0;
    private final static double LINE_ENDS_CUTOFF = 5.0;
    private final static int EIGHT_BIT_LIMIT = 255;
    public final static double PI = 3.14159;
    public final static double TORADIANS = PI / 180; 
    private static boolean DIRECTED = true;
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
    public static double getArrowWidth(){
        return ARROW_WIDTH;
    }
    public static double getArrowAngle(){
        return ARROW_ANGLE;
    }
    public static double getArrowLength(){
        return ARROW_LENGTH;
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
    public static int getEightBitLimit(){
        return EIGHT_BIT_LIMIT;
    }
    public static boolean isDirected(){
        return DIRECTED;
    }
    public static double getLineHitBox(){
        return LINE_HIT_BOX;
    }
    public static double getLineEndsCutoff(){
        return LINE_ENDS_CUTOFF;
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
    public static void setDirected(boolean isDirected){
        DIRECTED = isDirected;
    }
}
