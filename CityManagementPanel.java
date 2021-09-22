package citymanagementgame;

import java.awt.Color;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The panel containing all the statistics of the city.
 * There will be no input in this panel, only output.
 * 
 * @author Bryan Rainbow
 */
public class CityManagementPanel extends JPanel
{
    public static final Random rng = new Random();
    /**
     * The points required to win the game.
     */
    public static final int POINTS_TO_WIN = 1000;
    
    /**
     * The number of turns until the game ends in defeat.
     */
    public static final int TURN_LIMIT = 100;
    
    public static boolean lostABuilderThisTurn = false;
    
    /**
     * The points that the player currently has.
     */
    private int points;
    
    /**
     * The current turn of the game. The player is only allowed
     * to do 1 thing per turn.
     */
    private int turnNumber;
    
    /**
     * The number of builders in the city. Builders increase your
     * points by 1 each turn, but require 1 food per builder to
     * prevent happiness from falling.
     */
    private int builders;
    
    /**
     * The happiness of the city. If the happiness falls below 50,
     * the population will begin to decrease, as well as a builder
     * may leave (25%) each turn. If the happiness ever hits 0, the
     * game will end in defeat.
     * 
     * <p>The happiness must always be between 0 and 100 inclusive.</p>
     */
    private int happiness;
    
    /**
     * The farms of the city. This is an abstraction, as this number is only
     * used to feed the builders. The rest of the city is assumed to have enough food.
     * If there is more farms than builders, the city will increase population quicker.
     */
    private int farms;
    
    /**
     * The population of the city. The player will earn 1/10 of the population as
     * gold each turn. As the population increases the happiness of the city will decrease.
     * <p>A starting city will make 5 gold each turn.</p>
     */
    private int population;
    
    /**
     * The factories of the city. Each factory decreases the happiness of the city, but increases
     * the income.
     */
    private int factories;
    
    /**
     * The number of entertainment buildings in the city. These buildings increase the happiness of
     * the city.
     */
    private int entertainment;
    
    /**
     * The gold of the city. Gold is used to buy improvements
     * and will be necessary for winning the game.
     */
    private int gold;
    
    /**
     * The label that shows how many points you have and how
     * many you need to win.
     */
    private JLabel pointsLabel;
    
    /**
     * The label that shows how many builders you have.
     */
    private JLabel buildersAndFoodLabel;
    
    /**
     * The label that shows what turn it is as well as how many turns
     * are left.
     */
    private JLabel turnsLabel;
    
    /**
     * The label that shows the population of the city.
     */
    private JLabel populationLabel;
    
    /**
     * The label that shows the income and current gold of the city.
     */
    private JLabel incomeAndGoldLabel;
    
    /**
     * The label that shows the farms, factories, 
     * and entertainment buildings in the city.
     */
    private JLabel farmsFactoriesEntertainmentLabel;
    
    /**
     * The label that shows the builders and happiness of the city.
     */
    private JLabel happinessLabel;
    
    public CityManagementPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        points = 0;
        turnNumber = 1;
        builders = 0;
        happiness = 80;
        farms = 0;
        population = 40;
        factories = 0;
        entertainment = 0;
        gold = 100;
        
        pointsLabel = new JLabel("Points: " + points + " / " + POINTS_TO_WIN);
        buildersAndFoodLabel = new JLabel("Builders: " + builders + ", Food: " + getFood());
        turnsLabel = new JLabel("Turn: " + turnNumber + " / " + TURN_LIMIT);
        populationLabel = new JLabel("Populaton: " + population);
        incomeAndGoldLabel = new JLabel("Gold: " + gold + ", Income: " + getIncome() + "G/turn");
        farmsFactoriesEntertainmentLabel = new JLabel("Farms: " + farms + ", Factories: " + factories + ", Entertainment: " + entertainment);
        happinessLabel = new JLabel("Happiness: " + happiness + "% " + getFormattedHappinessModifier());
        
        add(pointsLabel);
        add(buildersAndFoodLabel);
        add(turnsLabel);
        add(populationLabel);
        add(incomeAndGoldLabel);
        add(farmsFactoriesEntertainmentLabel);
        add(happinessLabel);
        
        setBackground(new Color(200,200,200));
    }
    
    /**
     * Updates all the labels to their new values.
     */
    public void updateStatistics()
    {
        pointsLabel.setText("Points: " + points + " / " + POINTS_TO_WIN);
        buildersAndFoodLabel.setText("Builders: " + builders + ", Food: " + getFood());
        turnsLabel.setText("Turn: " + turnNumber + " / " + TURN_LIMIT);
        populationLabel.setText("Populaton: " + population);
        incomeAndGoldLabel.setText("Gold: " + gold + ", Income: " + getIncome() + "G/turn");
        farmsFactoriesEntertainmentLabel.setText("Farms: " + farms + ", Factories: " + factories + ", Entertainment: " + entertainment);
        happinessLabel.setText("Happiness: " + happiness + "% " + getFormattedHappinessModifier());
        
        if (happiness < 1)
        {
            System.exit(0);
        }
    }
    
    /**
     * Get the formatted String version of the happiness modifier.
     * For example: +5 becomes (+5) while -3 becomes (-3)
     * 
     * @return the string of the happiness modifier
     */
    private String getFormattedHappinessModifier()
    {
        int happinessMod = getHappinessModifier();
        
        String formattedHappinessMod;
        if (happinessMod >= 0)
        {
            formattedHappinessMod = "(+";
        }
        else
        {
            formattedHappinessMod = "(";
        }
        
        formattedHappinessMod += happinessMod + ")";
        
        return formattedHappinessMod;
    }
    
    /**
     * Get the modifier that will be applied to the happiness of the city at
     * the end of every turn. This value will change as the game progresses.
     * 
     * @return the happiness modifier of the city
     */
    private int getHappinessModifier()
    {
        int happinessMod = 0;
        
        happinessMod += (entertainment * 2);
        happinessMod -= factories;
        happinessMod -= (population / 50);
        if (builders > getFood())
        {
            happinessMod -= (builders - getFood());
        }
        
        return happinessMod;
    }
    
    public final int getFood()
    {
        return farms * 3;
    }
    
    /**
     * Changes all values that need to change at the end of the turn.
     * This method should always be called just before adding something (like a builder or factory)
     * to the city.
     */
    public void turnEnd()
    {
        turnNumber++;
        gold += getIncome();
        happiness += getHappinessModifier();
        if (happiness > 100)
        {
            happiness = 100;
        }
        points += builders;
        
        if (happiness > 50)
        {
            population += (happiness / 10);
            if (getFood() > builders)
            {
                population += (getFood() - builders);
            }
        }
        else if (happiness > 0)
        {
            population += ((happiness - 60) / 10);
            
            if (builders > 0 && rng.nextInt(100) < 25)
            {
                lostABuilderThisTurn = true;
                builders--;
            }
            else
            {
                lostABuilderThisTurn = false;
            }
        }
        else
        {
            updateStatistics();
            JOptionPane.showMessageDialog(this, "Happiness hit 0%, you lose!");
            System.exit(0);
        }
        
        if (points >= POINTS_TO_WIN)
        {
            updateStatistics();
            JOptionPane.showMessageDialog(this, "You win!");
            System.exit(0);
        }
        else if (turnNumber >= TURN_LIMIT)
        {
            updateStatistics();
            JOptionPane.showMessageDialog(this, "Ran out of turns, you lose!");
            System.exit(0);
        }
    }
    
    /**
     * Calculates the income of the city.
     * 
     * @return the city's income
     */
    private int getIncome()
    {
        int income = 0;
        income += (factories * 2) + (population / 20);
        
        return income;
    }
    
    public int getGold()
    {
        return gold;
    }
    
    /**
     * Reduces the cities gold by the specified amount.
     * 
     * @param amount the amount to reduce the gold by
     */
    public void reduceGold(final int amount)
    {
        gold -= amount;
    }
    
    public void addBuilder()
    {
        builders++;
    }
    
    public void addFarm()
    {
        farms++;
    }
    
    public void addFactory()
    {
        factories++;
    }
    
    public void addEntertainment()
    {
        entertainment++;
    }
}
