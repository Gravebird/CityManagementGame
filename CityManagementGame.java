package citymanagementgame;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The startup point for the program. This will create the two
 * windows required by the game: The city window, which will show the
 * statistics of the city, and the button window, which contains all
 * the buttons used to play the game.
 * 
 * @author Bryan Rainbow
 */
public class CityManagementGame {

    /**
     * The main entry point of the program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        JFrame cityFrame = new JFrame("City Building Game");
        cityFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cityFrame.setLayout(new BorderLayout());
        
        JLabel split = new JLabel("          ");
        CityManagementPanel cityManagement = new CityManagementPanel();
        
        cityFrame.getContentPane().add(cityManagement, BorderLayout.WEST);
        cityFrame.getContentPane().add(split, BorderLayout.CENTER);
        cityFrame.getContentPane().add(new InputPanel(cityManagement), BorderLayout.EAST);
        
        cityFrame.pack();
        cityFrame.setVisible(true);
    }
}
