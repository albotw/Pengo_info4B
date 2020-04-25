package com.generic.AI;

import com.generic.coreClasses.MapObject;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;

public class PathFinder {
    private ArrayList<Node> open_list;
    private ArrayList<MapObject> closed_list;

    int start_x;
    int start_y;

    int end_x;
    int end_y;

    public PathFinder()
    {

    }
    public void findPath()
    {
        
    }
}

class Node
{
    int weight;
    int x;
    int y;
}
