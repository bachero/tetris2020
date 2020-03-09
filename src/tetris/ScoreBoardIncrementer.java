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
public interface ScoreBoardIncrementer {
    public void incrementScore(int increment);
    public void resetScore();
    public int getScore();
}

