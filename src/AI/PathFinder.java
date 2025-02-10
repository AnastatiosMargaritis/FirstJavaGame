package AI;

import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    GamePanel gamePanel;
    Node[][] nodes;
    List<Node> openList = new ArrayList<>();
    public List<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        instantiateNodes();
    }

    public void instantiateNodes(){
        nodes = new Node[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int col = 0;
        int row = 0;

        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            nodes[col][row] = new Node(col, row);

            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row ++;
            }
        }
    }

    public void resetNodes(){
        int col = 0;
        int row = 0;
        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
            nodes[col][row].open = false;
            nodes[col][row].checked = false;
            nodes[col][row].solid = false;

            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row ++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){

        resetNodes();

        // Set start and goal node
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;
        while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){

            // SET SOLID NODE
            // CHECK TILES
            int tileNumber = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][col][row];
            if(gamePanel.tileManager.tile[tileNumber].collision){
                nodes[col][row].solid = true;
            }

            // CHECK INTERACTIVE TILES
            for(int i = 0; i < gamePanel.interactiveTile[1].length; i++){
                if(gamePanel.interactiveTile[gamePanel.currentMap][i] != null && gamePanel.interactiveTile[gamePanel.currentMap][i].destructible){
                    int itCol = gamePanel.interactiveTile[gamePanel.currentMap][i].worldX/gamePanel.tileSize;
                    int itRow = gamePanel.interactiveTile[gamePanel.currentMap][i].worldY/gamePanel.tileSize;
                    nodes[itCol][itRow].solid = true;
                }
            }

            // SET COST
            getCost(nodes[col][row]);
            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node){
        // G COST
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H COST
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F COST
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while(goalReached == false && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            // Check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the Up node
            if(row - 1 >= 0){
                openNode(nodes[col][row - 1]);
            }

            // Open the left node
            if(col - 1 >= 0){
                openNode(nodes[col - 1][row]);
            }

            // Open the down node
            if(row + 1 >= gamePanel.maxWorldRow){
                openNode(nodes[col][row + 1]);
            }

            // Open the right node
            if(col + 1 < gamePanel.maxWorldCol){
                openNode(nodes[col + 1][row]);
            }

            //Find the best node
            int bestNodeIndex = 0;
            int bestNodefcost = 999;

            for(int i = 0; i < openList.size(); i++){
                if(openList.get(i).fCost < bestNodefcost){
                    bestNodeIndex = i;
                    bestNodefcost = openList.get(i).fCost;
                }else if(openList.get(i).fCost == bestNodefcost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }

            // If there is no node in the openList end the loop
            if(openList.size() == 0){
                break;
            }

            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }

    public void openNode(Node node){
        if(!node.open && !node.checked && !node.solid){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath(){

        Node currentNode = goalNode;
        while(currentNode != startNode){
            pathList.add(0, currentNode);
            currentNode = currentNode.parent;
        }
    }
}
