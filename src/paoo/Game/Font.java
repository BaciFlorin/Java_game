package paoo.Game;

import java.awt.*;

public class Font {

    private java.awt.Font arial;
    private java.awt.Font segoe;

    public Font() {
        setArial(new java.awt.Font("Arial", java.awt.Font.BOLD, 27));
        setSegoe(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 27));
    }

    public void render(String msg, Graphics g, int x, int y) {
        g.setFont(this.getArial());
        g.drawString(msg,x,y);
    }

    public java.awt.Font getArial() {
        return arial;
    }

    public void setArial(java.awt.Font arial) {
        this.arial = arial;
    }

    public java.awt.Font getSegoe() {
        return segoe;
    }

    public void setSegoe(java.awt.Font segoe) {
        this.segoe = segoe;
    }
}