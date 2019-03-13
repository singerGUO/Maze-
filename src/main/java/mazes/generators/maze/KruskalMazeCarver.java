package mazes.generators.maze;

import datastructures.concrete.Graph;
import datastructures.interfaces.ISet;
import mazes.entities.Maze;
import mazes.entities.Room;
import mazes.entities.Wall;


import java.util.Random;

/**
 * Carves out a maze based on Kruskal's algorithm.
 *
 * See the spec for more details.
 */
public class KruskalMazeCarver implements MazeCarver {
    @Override
    public ISet<Wall> returnWallsToRemove(Maze maze) {
        ISet<Room> vertex=maze.getRooms();
        ISet<Wall> edge=maze.getWalls();
        Graph<Room,Wall> graph=new Graph<>(vertex,edge);
        Random rand=new Random();
        //assign each wall of differnet wieghts;
        for(Wall wall:edge){
            wall.setDistance(rand.nextInt(50));

        }
        ISet<Wall> mst= graph.findMinimumSpanningTree();
        for(Wall wall:edge){
            wall.resetDistanceToOriginal();
        }
        return mst;

        // Note: make sure that the input maze remains unmodified after this method is over.
        //
        // In particular, if you call 'wall.setDistance()' at any point, make sure to
        // call 'wall.resetDistanceToOriginal()' on the same wall before returning.


    }
}
