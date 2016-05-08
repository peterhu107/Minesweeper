import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Peter Hu on 2016-04-29.
 */
public class MineSweeper extends JFrame {
    /**
     * Framework for the actual application. Represents an instance of the game (should only be one running at any time).
     */
    protected static final int BOARD_HEIGHT = 10; // constants for the board's size
    protected static final int BOARD_WIDTH = 20;
    protected static int NUM_MINES = 40; // currently not enforced
    protected List<List<Cell>> cells;
    protected JPanel gamePanel;
    private Set<Cell> adjacentHelper;

    public MineSweeper() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200,600));
        setBackground(Color.WHITE);
        adjacentHelper = new HashSet<Cell>();
        gamePanel = new JPanel();
        gamePanel.setVisible(true);
        gamePanel.setPreferredSize(new Dimension(1200,600));
        this.add(gamePanel);
        makeCells();
        LayoutManager m = new GridLayout(BOARD_HEIGHT,BOARD_WIDTH);
        addLayoutComponents(m);
        gamePanel.setLayout(m);
        pack();
        setVisible(true);
    }

    protected void makeCells() {
        // create all the cells, add to panel
        cells = new ArrayList<List<Cell>>();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            List<Cell> row = new ArrayList<Cell>();
            for (int j = 0; j < BOARD_WIDTH; j++) {
                int val = (int) (Math.random() * 100); // a number between 0 and 99
                if (val > 84) {
                    Cell c = new Cell(true,this);
                    row.add(c);
                    gamePanel.add(c);
                }
                else {
                    Cell c = new Cell(false,this);
                    row.add(c);
                    gamePanel.add(c);
                }
            }
            cells.add(row);
        }

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                Cell c = cells.get(i).get(j);
                if (!c.getBomb()) {
                    c.setNumBombs(getAdjacentBombs(i,j));
                }
            }
        }
    }

    private int getAdjacentBombs(int row, int col) {
        int num = 0;
        num += checkBomb(row-1,col-1);
        num += checkBomb(row-1,col);
        num += checkBomb(row-1,col+1);
        num += checkBomb(row,col-1);
        num += checkBomb(row,col+1);
        num += checkBomb(row+1,col-1);
        num += checkBomb(row+1,col);
        num += checkBomb(row+1,col+1);
        return num;
    }

    private int checkBomb(int row, int col) {
        if (row < 0 || row > BOARD_HEIGHT - 1 || col < 0 || col > BOARD_WIDTH - 1) {
            return 0;
        }
        else {
            if (cells.get(row).get(col).getBomb()) {
                return 1;
            }
            return 0;
        }
    }

    private void addLayoutComponents(LayoutManager l) {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                l.addLayoutComponent("Cell",cells.get(i).get(j));
            }
        }
    }

    public boolean checkWin() {
        boolean win = true;
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (cells.get(i).get(j).getBomb() && !cells.get(i).get(j).getFlag()) {
                    win = false;
                }
            }
        }
        return win;
    }

    public void pressAdjacent(Cell c) {
        c.pressButton();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (cells.get(i).get(j).equals(c)) {
                    pressButtons(i,j);
                    return;
                }
            }
        }
    }

    private void pressButtons (int row, int col) {
        buttonHelper(row-1,col-1);
        buttonHelper(row-1,col);
        buttonHelper(row-1,col+1);
        buttonHelper(row,col-1);
        buttonHelper(row,col+1);
        buttonHelper(row+1,col-1);
        buttonHelper(row+1,col);
        buttonHelper(row+1,col+1);
    }

    private void buttonHelper(int row, int col) {
        if (row < 0 || row > BOARD_HEIGHT - 1 || col < 0 || col > BOARD_WIDTH - 1) {
            return;
        }
        else {
            Cell c = cells.get(row).get(col);
            c.pressButton();
            if (c.getNumBombs() == 0 && !adjacentHelper.contains(c)) {
                adjacentHelper.add(c);
                pressAdjacent(c);
            }
        }

    }

}
