// Morgane Bentzinger - 261062953

import java.util.Random;

/*
    The Spell class represents a spell in the game.
 */

public class Spell {
    private String name;
    private double minDamage;
    private double maxDamage;
    double chanceOfSuccess;

    // Spell constructor
    public Spell(String name, double minDamage, double maxDamage, double chanceOfSuccess) {

        // check that the values for the spell are correct before creating it
        if(minDamage < 0 ||
                minDamage > maxDamage ||
                chanceOfSuccess < 0 ||
                chanceOfSuccess > 1) {
            throw new IllegalArgumentException("The values for the spell are not correct.");
        }

        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.chanceOfSuccess = chanceOfSuccess;
    }

    // getter
    public String getName() {
        return this.name;
    }

    // getMagicDamage calculates the spell damage using a random value
    public double getMagicDamage(int seed) {
        Random random = new Random(seed);
        double successCheck = random.nextDouble();

        if(successCheck > this.chanceOfSuccess) {
            return 0;
        }

        double magicDamage = this.minDamage + ( this.maxDamage - this.minDamage ) * random.nextDouble();
        return magicDamage;
    }

    // toString prints some information about the spell
    public String toString() {
        String successPercent = String.format("%1.1f", this.chanceOfSuccess * 100);
        String spellInfo = "Name: " + this.name + " | Damage: " + this.minDamage + "-" + this.maxDamage + " | Chance: " + successPercent + "%";
        return spellInfo;
    }

}
