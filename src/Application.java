import javax.swing.*;

/**
 * Created by Peter Hu on 2016-04-29.
 */
public class Application {
    private static void setUp() {
        JFrame sweeper = new MineSweeper();
    }
    public static void main(String ... args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setUp();
            }
        });
    }
}
