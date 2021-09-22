package citymanagementgame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The panel that will hold all the buttons that can be used for the game.
 * It should also have an explanation of each button next to them.
 * 
 * @author Bryan Rainbow
 */
public class InputPanel extends JPanel
{
    /**
     * The city. Required so that the buttons actually have something to work
     * with.
     */
    private CityManagementPanel CITY;
    
    public static int builderCost = 10;
    public static final int builderCostIncrement = 10;
    public static final int farmCost = 30;
    public static final int factoryCost = 20;
    public static final int entertainmentCost = 25;
    
    JPanel builderSection;
    JPanel farmSection;
    JPanel factorySection;
    JPanel entertainmentSection;
    JPanel waitSection;
    static JPanel messageSection;
    
    JButton makeBuilder;
    JButton makeFarm;
    JButton makeFactory;
    JButton makeEntertainment;
    JButton waitButton;
    
    static JLabel message;
    JLabel builderText;
    
    /**
     * Construct an InputPanel with the specified city to work with.
     * 
     * @param city the players city
     */
    public InputPanel(final CityManagementPanel city)
    {
        CITY = city;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        gameListener listener = new gameListener();
        
        builderSection = new JPanel();
        farmSection = new JPanel();
        factorySection = new JPanel();
        entertainmentSection = new JPanel();
        waitSection = new JPanel();
        messageSection = new JPanel();
        
        builderSection.setLayout(new FlowLayout(FlowLayout.LEFT));
        farmSection.setLayout(new FlowLayout(FlowLayout.LEFT));
        factorySection.setLayout(new FlowLayout(FlowLayout.LEFT));
        entertainmentSection.setLayout(new FlowLayout(FlowLayout.LEFT));
        waitSection.setLayout(new FlowLayout(FlowLayout.LEFT));
        messageSection.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        add(builderSection);
        add(farmSection);
        add(factorySection);
        add(entertainmentSection);
        add(waitSection);
        add(messageSection);
        
        builderText = new JLabel(builderCost + "G: Make a builder. Builders give you 1 point per turn.");
        makeBuilder = new JButton("Create Builder");
        makeBuilder.addActionListener(listener);
        builderSection.add(makeBuilder);
        builderSection.add(builderText);
        builderSection.setBackground(new Color(200,200,200));
        
        JLabel farmText = new JLabel(farmCost + "G: Make a farm. Farms give you 3 food. Food is used to"
                + " supply your builders. Excess food will increase the rate of growth in your city.");
        makeFarm = new JButton("Create Farm");
        makeFarm.addActionListener(listener);
        farmSection.add(makeFarm);
        farmSection.add(farmText);
        farmSection.setBackground(new Color(200,200,200));
        
        JLabel factoryText = new JLabel(factoryCost + "G: Make a factory. Factories give you +2 gold per turn, but"
                + " -1 happiness per turn.");
        makeFactory = new JButton("Create Factory");
        makeFactory.addActionListener(listener);
        factorySection.add(makeFactory);
        factorySection.add(factoryText);
        factorySection.setBackground(new Color(200,200,200));
        
        JLabel entertainmentText = new JLabel(entertainmentCost + "G: Make entertainment. Entertainment gives you +2 happiness"
                + " per turn. The more people in your city, the less happy they will be.");
        makeEntertainment = new JButton("Create Entertainment");
        makeEntertainment.addActionListener(listener);
        entertainmentSection.add(makeEntertainment);
        entertainmentSection.add(entertainmentText);
        entertainmentSection.setBackground(new Color(200,200,200));
        
        JLabel waitText = new JLabel("Do nothing this turn. It is not suggested you do this unless"
                + " there is nothing else to do.");
        waitButton = new JButton("Wait");
        waitButton.addActionListener(listener);
        waitSection.add(waitButton);
        waitSection.add(waitText);
        waitSection.setBackground(new Color(200,200,200));
        
        message = new JLabel("Messages will go here.");
        messageSection.add(message);
        messageSection.setBackground(Color.YELLOW);
    }
    
    private class gameListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            boolean valid = false;
            
            if (event.getSource() == makeBuilder)
            {
                if (CITY.getGold() >= builderCost)
                {
                    CITY.reduceGold(builderCost);
                    CITY.turnEnd();
                    CITY.addBuilder();
                    builderCost += builderCostIncrement;
                    builderText.setText(builderCost + "G: Make a builder. Builders give you 1 point per turn.");
                    valid = true;
                }
                else
                {
                    message.setText("You need at least " + builderCost + "G to make a builder.");
                }
            }
            else if (event.getSource() == makeFarm)
            {
                if (CITY.getGold() >= farmCost)
                {
                    CITY.reduceGold(farmCost);
                    CITY.turnEnd();
                    CITY.addFarm();
                    valid = true;
                }
                else
                {
                    message.setText("You need at least " + farmCost + "G to make a farm.");
                }
            }
            else if (event.getSource() == makeFactory)
            {
                if (CITY.getGold() >= factoryCost)
                {
                    CITY.reduceGold(factoryCost);
                    CITY.turnEnd();
                    CITY.addFactory();
                    valid = true;
                }
                else
                {
                    message.setText("You need at least " + factoryCost + "G to make a factory.");
                }
            }
            else if (event.getSource() == makeEntertainment)
            {
                if (CITY.getGold() >= entertainmentCost)
                {
                    CITY.reduceGold(entertainmentCost);
                    CITY.turnEnd();
                    CITY.addEntertainment();
                    valid = true;
                }
                else
                {
                    message.setText("You need at least " + entertainmentCost + "G to make entertainment.");
                }
            }
            else if (event.getSource() == waitButton)
            {
                CITY.turnEnd();
                valid = true;
            }
            
            if (valid)
            {
                if (CITY.lostABuilderThisTurn)
                {
                    InputPanel.messageSection.setBackground(Color.RED);
                    InputPanel.message.setText("A builder has left your city!");
                }
                else
                {
                    message.setText("Messages will go here.");
                    messageSection.setBackground(Color.YELLOW);
                }
                CITY.updateStatistics();
            }
        }
    }
}
