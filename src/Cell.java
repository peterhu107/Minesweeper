import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Peter Hu on 2016-04-29.
 */
public class Cell extends JButton {
    /**
     * A class representing a single cell on a Minesweeper board.
     */
    private boolean isBomb; // true if this cell contains a bomb.
    private boolean isFlag; // true if this cell is flagged
    private int numBombs; // ignore if this cell is a bomb, is the number of adjacent bombs if cell is not
    private static MineSweeper sweeper; // the container
    private static Icon defaultIcon = new ImageIcon(); // add these later
    private static Icon flagIcon = new ImageIcon(Cell.class.getResource("/resources/Flag.jpg"));
    private static Icon bombIcon = new ImageIcon(Cell.class.getResource("/resources/Bomb.jpg"));
    private static KeyListener listener = new KeyboardSweeper();
    private static int deathCount = 0;
    protected static Font numberStyle = new Font("Dialog",Font.BOLD,20);

    public Cell(boolean bomb, MineSweeper s) {
        super();
        setVisible(true);
        //setPreferredSize(new Dimension(60,60));
        isBomb = bomb;
        isFlag = false;
        sweeper = s;
        addMouseListener(new CellListener());
        addKeyListener(listener);
        setFont(numberStyle);
    }

    public void setBomb(boolean bomb) {
        isBomb = true;
    }

    public boolean getBomb() {
        return isBomb;
    }

    public void setNumBombs(int num) {
        numBombs = num;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public boolean getFlag() {
        return isFlag;
    }

    private void gameOver() {
        // some end
        deathCount++;
        System.out.println("BOOM!: " + "deaths: " + deathCount);
    }

    public void pressButton() {
        // presses the button, only called on adjacent to zero.
        setText(Integer.toString(numBombs));
    }

    private class CellListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) { // left mouse
                if (isBomb) {
                    gameOver();
                    Cell.this.setIcon(bombIcon);
                }
                else if (numBombs == 0){
                    sweeper.pressAdjacent(Cell.this);
                }
                else {
                    Cell.this.setText(Integer.toString(numBombs));
                }
            }
            else if (e.getButton() == 3) { // right mouse
                isFlag = !isFlag;
                if (isFlag) {
                    Cell.this.setIcon(flagIcon);
                    if (sweeper.checkWin()) {
                        System.out.println("YOU WIN!");
                        System.out.println("Total deaths: " + deathCount);
                    }
                }
                else {
                    Cell.this.setIcon(defaultIcon);
                }
            }
        }
    }

    private static class KeyboardSweeper implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                System.out.println("Reset once I get it working");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }


}
