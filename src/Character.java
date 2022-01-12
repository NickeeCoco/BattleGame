// Morgane Bentzinger - 261062953

import java.util.ArrayList;
import java.util.Random;

/*
    The Character class represents a character in the game.
 */
public class Character {
    private String name;
    private double attackValue;
    private double maxHealth;
    private double currHealth;
    private int numWins;
    private static ArrayList<Spell> spellsList = new ArrayList<Spell>();

    // Character constructor
    public Character(String name, double attackValue, double maxHealth, int numWins) {
        this.name = name;
        this.attackValue = attackValue;
        this.maxHealth = maxHealth;
        this.currHealth = maxHealth;
        this.numWins = numWins;
    }

    /****** GETTERS ******/

    public String getName() {
        return this.name;
    }

    public double getAttackValue() {
        return this.attackValue;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public double getCurrHealth() {
        return this.currHealth;
    }

    public int getNumWins() {
        return this.numWins;
    }

    // getAttackDamage calculates the attack damage using a random value
    public double getAttackDamage(int seed) {
        Random random = new Random(seed);
        double multiplier = 0.7 + ( 1.0 - 0.7 ) * random.nextDouble();

        double attackDamage = this.attackValue * multiplier;

        return attackDamage;
    }

    // takeDamage calculates the remaining health after an attack. It takes the damage taken as an argument.
    public double takeDamage(double damageTaken) {
        double newHealth = this.currHealth - damageTaken;
        this.currHealth = newHealth;
        return this.currHealth;
    }

    // increaseWins increments the number of wins for the character
    public void increaseWins() {
        this.numWins++;
    }

    // toString prints some information about the character
    public String toString() {
        String currHealthString = String.format("%1$,.2f", this.currHealth);
        String displayCharInfo = this.name + "'s current health is " + currHealthString + ". \n";
        return displayCharInfo;
    }

    // setSpells creates a list of available spells for the character. It takes a list of spells as an argument.
    public void setSpells(ArrayList<Spell> spells) {
        spellsList.clear();
        for (Spell spell:
                spells) {
            spellsList.add(spell);
        }
    }

    // display spells displays the character's spells' information
    public void displaySpells() {
        String spellsString = "Here are the available spells: \n";
        for (Spell spell:
                spellsList) {
            spellsString += spell.toString() + "\n";
        }
        System.out.println(spellsString);
    }

    // castSpells manages the casting of a spell. It takes the spell's name and a seed as parameters.
    public double castSpell(String spellName, int seed) {
        int index = -1;
        String spellNameLC = spellName.toLowerCase();

        // loop through the array of spells to check if the spell exists
        for(int i = 0; i < spellsList.size(); i++) {
            if(spellNameLC.equals(spellsList.get(i).getName())) {
                index = i;
                break;
            }
        }

        // if the spell exists in the array, we return the damage dealt by the spell
        if(index != -1) {
            double damageDealt = spellsList.get(index).getMagicDamage(seed);
            return damageDealt;
        }

        // if it does not exist, we return -1 (original index value)
        return index;
    }
}
