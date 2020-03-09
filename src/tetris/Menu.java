/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author 10197825
 */
public class Menu {
    
    public Button playButton = new Button("Play");
    public Button helpButton = new Button("About");
    public Button quitButton = new Button("Quit");
    
   /* public void render(Graphics g){
        Graphics2D g2d =(Graphics2D) g;
        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("TETRIS GAME", Board.WIDTH / 2, 100);
        
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("Play", playButton.x + 19, playButton.y + 30);
        g2d.draw(playButton);
        g.drawString("Play", helpButton.x + 19, helpButton.y + 30);
        g2d.draw(helpButton);
        g.drawString("Play", quitButton.x + 19, quitButton.y + 30);
        g2d.draw(quitButton);
        
    }*/
    
}
