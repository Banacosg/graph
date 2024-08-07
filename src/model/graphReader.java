package src.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import src.View.initView;

public class graphReader {
    public static GraphModel readGraph(File file){
        HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
        //HashMap<String, Edges> edges = new HashMap<String, Edges>();
        boolean notVerticesInit = true;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line != null && notVerticesInit){
                if(new String(line.substring(0,1)).equals("-")){
                    notVerticesInit = false;
                }
                else{
                    Vertex v = new Vertex(line);
                    if(vertices.containsValue(line)){
                        System.out.println("Duplicate vertex names not allowed! Terminating...");
                        System.exit(0);
                    }
                    else{
                        vertices.put(line, v);
                    }
                }
                line = reader.readLine();
            }
            Vertex curVertex = null;
            int endOfName = 0;
            int endOfNeighbor = 0;
            while(line != null){
                //System.out.println(line); fixme
                endOfName = line.indexOf('|');
                if(endOfName == -1){
                    System.out.println("Bad formatting, missing \"|\". Terminating...");
                    System.exit(0);
                }
                curVertex = vertices.get(line.substring(0, endOfName));
                if(curVertex == null){
                    System.out.println("Vertex not found! Terminating...");
                    System.exit(0);
                }
                endOfNeighbor = line.indexOf(',');
                // System.out.println(endOfName); fixme
                // System.out.println(endOfNeighbor);
                while(endOfNeighbor != -1){
                    addNeighbor(curVertex, vertices, line, endOfName + 1, endOfNeighbor);
                    endOfName = endOfNeighbor; //end of name is now where next neighbor begins
                    if(line.substring(endOfName + 1).indexOf(',') == -1){
                        endOfNeighbor = -1;
                    }
                    else{
                        endOfNeighbor = line.substring(endOfName + 1).indexOf(',') + endOfNeighbor + 1;
                    }
                    //System.out.println(line.substring(endOfName + 1)); fixme
                    //System.out.println("here1");
                    //System.out.println(line.substring(endOfName + 1, endOfNeighbor));
                    // System.out.println("here2");
                    // System.out.println(endOfName);
                    // System.out.println(endOfNeighbor);
                }
                if(endOfNeighbor == -1){ //Last name case
                    addNeighbor(curVertex, vertices, line, endOfName + 1, line.length());
                }
                line = reader.readLine();
            }
            if(reader != null){
                reader.close();
            }
        }catch(IOException e){
            System.out.println("An IO error has occurred " + e.getMessage());
            System.exit(0);
        }
        // System.out.println(vertices.toString()); //fixme
        // System.out.println(vertices.get("A").getNeighbors().toString());
        // System.out.println(vertices.get("B").getNeighbors().toString());
        // System.out.println(vertices.get("C").getNeighbors().toString());
        // System.out.println(vertices.get("F").getNeighbors().toString());
        // System.out.println(edges.toString());
        GraphModel graph = new GraphModel(vertices);
        //init graph
        graph.initGraph();
        return graph;
    }

    public static void addNeighbor(Vertex curVertex, HashMap<String, Vertex> vertices, String line, int start, int end){
        String neighborName = line.substring(start, end);
        Vertex neighbor = vertices.get(neighborName);
        if(!vertices.containsKey(neighborName)){
            System.out.println("Vertex \"" + neighborName + "\" doesn't exit! Terminating...");
            System.exit(0);
        }
        if(vertices.get(neighborName).getName() == curVertex.getName()){
            System.out.println("Can't be neighbor of self! Terminating...");
            System.exit(0);
        }
        System.out.println("This:" + curVertex.getNeighbors());
        if(curVertex.getNeighbors().containsKey(neighbor.getName())){
            System.out.println(neighborName + " already a neighbor of " + curVertex.getName() + "! Terminating...");
            System.exit(0);
        }
        curVertex.addNeighbor(neighbor);
        //edges.put(curVertex.getName() + neighborName, new Edges(curVertex, neighbor));   
    }
}