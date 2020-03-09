/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

/**
 *
 * @author 10197825
 */
public class Shape {

    private static final int[][][] coordsTable = new int[][][]{{{0, 0}, {0, 0}, {0, 0}, {0, 0}},
    {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
    {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
    {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
    {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
    {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
    {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
    {{1, -1}, {0, -1}, {0, 0}, {0, 1}}};

    private Tetrominoes pieceShape;
    private int coords[][];

    public Shape() {
        coords = new int[4][2];
        /*for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                coords[i][j] = 0;
            }
        }*/
        pieceShape = Tetrominoes.NoShape;
        setRandomShape();

    }
    
    public Shape(Tetrominoes shapeType){
        this();
        setShape(shapeType);
    }

    public void setShape(Tetrominoes shapeType) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                coords[i][j] = coordsTable[shapeType.ordinal()][i][j];
            }
        }
        pieceShape = shapeType;
    }

    private void setX(int index, int x) {
        coords[index][0] = x;
    }

    private void setY(int index, int y) {
        coords[index][1] = y;
    }

    public int getX(int index) {
        return coords[index][0];
    }

    public int getY(int index) {
        return coords[index][1];
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }

    public void setRandomShape() {
        int number = (int) (Math.random() * 7 + 1);
        Tetrominoes[] values = Tetrominoes.values();
        setShape(values[number]);
    }

    public int minX(){
        int m = coords[0][0];
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coords[i][0]);
        }
        return m;  
    }

    
    public int minY(){
        int m = coords[0][1];
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coords[i][1]);
        }
        return m;
    }

    
    public int maxX(){
        int m = coords[0][0];
        for (int i = 0; i < 4; i++) {
            m = Math.max(m, coords[i][0]);
        }
        return m;  
    }

    public int maxY(){
        int m = coords[0][1];
        for (int i = 0; i < 4; i++) {
            m = Math.max(m, coords[i][1]);
        }
        return m;  
    }
    

    
    public Shape rotateShape() {
        if (pieceShape == Tetrominoes.SquareShape){
            return this;
        }
        Shape rotatedShape = new Shape(pieceShape);
        for (int point = 0; point < 4; point++) {
            rotatedShape.setX(point, -getY(point));
            rotatedShape.setY(point, getX(point));
        }
        return rotatedShape;
    }
        
    
    
    /*
    public Shape rotateRight(){
        
    }
    */

}
