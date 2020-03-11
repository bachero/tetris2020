/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author 10197825
 */
public class Board extends JPanel {

    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;
    public static final int INITIAL_ROWS = -2;
    public static final int INITIAL_DELTA_TIME = 500;
    private Tetrominoes[][] playBoard;
    boolean isFallingFinished = false;
    private Shape currentShape;
    private int currentRow;
    private int currentCol;
    private Timer timer;
    private int deltaTime;
    private ScoreBoardIncrementer scoreboard;
    private int resumeScore;
    private Tetris parent;
    private Player namePlayer;

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(canMove(currentShape, currentRow, currentCol - 1)){
                        currentCol--;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(canMove(currentShape, currentRow, currentCol + 1)){
                        currentCol++;
                    }     
                    break;
                case KeyEvent.VK_UP:
                    Shape s = currentShape.rotateShape();
                    if(canMove(s, currentRow, currentCol)){
                        currentShape = s;
                    }
            
                    break;
                case KeyEvent.VK_DOWN:
                    if(canMove(currentShape, currentRow + 1, currentCol)){
                        currentRow++;
                    }
                    break;
                default:
                    break;
            }
            repaint();
        }
    }
   
    public Board() {
        super();
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        playBoard = new Tetrominoes[NUM_ROWS][NUM_COLS];
        deltaTime = INITIAL_DELTA_TIME;
        createTimer();
        initGame();

    }
    
    public Board(ScoreBoardIncrementer inc, Tetris parent){
        this();
        this.parent = parent;
        scoreboard = inc;
        
    }


    public void initGame() {
        for (int row = 0; row < playBoard.length; row++) {
            for (int col = 0; col < playBoard[0].length; col++) {
                playBoard[row][col] = Tetrominoes.NoShape;
            }
        }
        resetCurrentShape();
        timer.start();
        repaint();
        
    }
    
            
    
    
     private void createTimer(){
        timer = new Timer(deltaTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(canMove(currentShape, currentRow + 1, currentCol)) {
                    currentRow ++;
                    repaint();
                    Toolkit.getDefaultToolkit().sync();
                } else {
                    if(currentRow == INITIAL_ROWS){
                        gameOver();
                    }else{
                        moveCurrentShapeToBoard();
                        fullLine();
                        resetCurrentShape();
                    }
                }
            }
        });
    }
     
            
    private void moveCurrentShapeToBoard() {
      
      for(int i = 0; i < 4; i++){
          int row = currentRow + currentShape.getY(i);
          int col = currentCol + currentShape.getX(i);
          if(row >= 0){
            playBoard[row][col] = currentShape.getShape();
          }
      }        
    }
      
    public void setScoreBoard(ScoreBoardIncrementer scBoard){
        scoreboard = scBoard;
    }
    
    public void renamePlayer(Player p){
        namePlayer = p;
    }
    
    

    
    private void resetCurrentShape(){
        currentRow = INITIAL_ROWS;
        currentCol = NUM_COLS / 2;
        currentShape = new Shape();
    }
    
    public void startNewGame(){
       
        initGame();
        scoreboard.resetScore();
        
   }
    
    public void pauseGame(){
        timer.stop();
        JOptionPane.showMessageDialog(null,
            "Pause");
        timer.start();
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintPlayBoard(g2d);
        paintShape(g2d);
    }

    private void paintPlayBoard(Graphics2D g2d) {
        for (int row = 0; row < playBoard.length; row++) {
            for (int col = 0; col < playBoard[0].length; col++) {
                drawSquare(g2d, row, col, playBoard[row][col]);
            }
        }
    }

    private void paintShape(Graphics2D g2d) {
        for (int i = 0; i < 4; i++) {
            int x = currentCol + currentShape.getX(i);
            int y = currentRow + currentShape.getY(i);
            drawSquare(g2d, y, x, currentShape.getShape());
        }
    }
    
    
    private void gameOver() {
        timer.stop();
        resumeScore = scoreboard.getScore();
        PlayerDialog i = new PlayerDialog(parent, true, scoreboard.getScore());
        i.setVisible(true);
        Scores s = new Scores(parent, true, namePlayer);
        s.setVisible(true);
        startNewGame();
    }

    private int squareWidth() {
        return getWidth() / NUM_COLS;
    }

    private int squareHeight() {
        return getHeight() / NUM_ROWS;
    }

    private void drawSquare(Graphics g, int row, int col, Tetrominoes shape) {
        Color colors[] = {new Color(0, 0, 0),
            new Color(204, 102, 102),
            new Color(102, 204, 102),
            new Color(102, 102, 204),
            new Color(204, 204, 102),
            new Color(204, 102, 204),
            new Color(102, 204, 204),
            new Color(218, 170, 0)};
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = colors[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
    }
    
    public boolean canMove(Shape shape, int newRow, int newCol){
        int leftBorder = newCol + shape.minX();
        int rightBorder = newCol + shape.maxX();
        if(leftBorder < 0 || newRow + shape.maxY() >= NUM_ROWS || rightBorder >= NUM_COLS){
            return false;
        }
        if(currentPieceHitsBoard(newRow, newCol)){
            return false;
        }
        return true;
    }
    
    
    public boolean currentPieceHitsBoard(int newRow, int newCol){
        for(int i = 0; i < 4; i++){
            int row = newRow + currentShape.getY(i);
            int col = newCol + currentShape.getX(i);
            if(row>=0){
                if(playBoard[row][col] != Tetrominoes.NoShape){
                return true;
                }
            }
            
        }
        return false;
        
    }
    
    public void fullLine(){
        
        for (int row = 0; row < NUM_ROWS; row++) {
            int count = 0; 
            for (int col = 0; col < NUM_COLS; col++) {
                if(playBoard[row][col] != Tetrominoes.NoShape){
                    count++;
                }
            }
            if (count == NUM_COLS){
                delLines(row);
                scoreboard.incrementScore(10);
            }
        }
    }
    
    public void delLines(int rowDeleted){
        for (int row = rowDeleted; row > 1; row--) {
            for (int col = 0; col < NUM_COLS; col++) {
                playBoard[row][col] = playBoard[row-1][col];
            }
        }
        for(int col = 0; col < NUM_COLS; col++){
            playBoard[0][col] = Tetrominoes.NoShape;
            
        }
        
        
    }
    
    public void resumeTimer(){
        timer.start();
    }
    
    
    public void stopTimer(){
        timer.stop();
    }
    
    
    
}
    

