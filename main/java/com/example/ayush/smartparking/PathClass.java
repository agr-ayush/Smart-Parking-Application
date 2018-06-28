package com.example.ayush.smartparking;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PathClass {
    public static ArrayList<Integer> points;
    static boolean [][]maze;
    static String data="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    PathClass(int x,int y)
    {
        data = "5,1";
        maze = new boolean[][]{{true,false,false,false,false,true},
                {true,false,true,true,false,true},
                {true,false,true,true,false,true},
                {true,false,true,true,false,true},
                {true,false,false,false,false,true},
                {false,false,false,false,false,false}};

        maze[5][1] = false;
        maze[x][y] = false;
        List path = findShortestPath(x,y);
        if (path == null)
        {
            System.out.println("No path possible");
            return;
        }
        path.forEach((cell) -> {
            System.out.print(cell);

        });
        String [] data1 =data.split(",");
        points = new ArrayList<>();
        for (String data11 : data1) {
            if (!"".equals(data11)) {
                points.add(Integer.parseInt(data11));
            }
        }

    }

    private static List findShortestPath(int x,int y)
    {
        int[][] levelMatrix = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; ++i)
            for (int j = 0; j < maze[0].length; ++j)
            {
                levelMatrix[i][j] = maze[i][j] == true ? -1 : 0;
            }
        LinkedList < Cell > queue = new LinkedList <>();
        Cell start = new Cell(5, 1);
        Cell end = new Cell(x , y );
        queue.add(start);
        levelMatrix[start.row][start.col] = 1;
        while (!queue.isEmpty())
        {
            Cell cell = queue.poll();
            if (cell == end)
                break;
            int level = levelMatrix[cell.row][cell.col];
            Cell[] nextCells = new Cell[4];
            nextCells[3] = new Cell(cell.row, cell.col - 1);
            nextCells[2] = new Cell(cell.row - 1, cell.col);
            nextCells[1] = new Cell(cell.row, cell.col + 1);
            nextCells[0] = new Cell(cell.row + 1, cell.col);

            for (Cell nextCell : nextCells)
            {
                if (nextCell.row < 0 || nextCell.col < 0)
                    continue;
                if (nextCell.row == maze.length
                        || nextCell.col == maze[0].length)
                    continue;
                if (levelMatrix[nextCell.row][nextCell.col] == 0)
                {
                    queue.add(nextCell);
                    levelMatrix[nextCell.row][nextCell.col] = level + 1;
                }
            }
        }
        if (levelMatrix[end.row][end.col] == 0)
            return null;
        LinkedList < Cell > path = new LinkedList <  >();
        Cell cell = end;
        while (!cell.equals(start))
        {
            path.push(cell);
            int level = levelMatrix[cell.row][cell.col];
            Cell[] nextCells = new Cell[4];
            nextCells[0] = new Cell(cell.row + 1, cell.col);
            nextCells[1] = new Cell(cell.row, cell.col + 1);
            nextCells[2] = new Cell(cell.row - 1, cell.col);
            nextCells[3] = new Cell(cell.row, cell.col - 1);
            for (Cell nextCell : nextCells)
            {
                if (nextCell.row < 0 || nextCell.col < 0)
                    continue;
                if (nextCell.row == maze.length
                        || nextCell.col == maze[0].length)
                    continue;
                if (levelMatrix[nextCell.row][nextCell.col] == level - 1)
                {
                    cell = nextCell;
                    break;
                }
            }
        }
        return path;
    }

    private static class Cell
    {
        public int row;
        public int col;

        public Cell(int row, int column)
        {
            this.row = row;
            this.col = column;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if ((obj == null) || (obj.getClass() != this.getClass()))
                return false;
            Cell cell = (Cell) obj;
            if (row == cell.row && col == cell.col)
                return true;
            return false;
        }

        @Override
        public String toString()
        {
            data = data + "," + row + "," + col;
            return "";
        }
    }


}