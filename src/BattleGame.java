// Morgane Bentzinger - 261062953

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
    BattleGame class is where the game is played.
 */
public class BattleGame {
    private static Random random = new Random(123);

    public static void playGame(String playerFile, String monsterFile, String spellsFile) {
        Character[] characters = new Character[2];
        Scanner userInput = new Scanner(System.in);

        // create characters, and terminate program in case of error
        boolean successCreationCharacter = createCharacters(characters, playerFile, monsterFile);
        if(!successCreationCharacter) {
            return;
        }

        // for code readability, assign each character to a variable
        Character player = characters[0];
        Character monster = characters[1];

        // create spells list from the provided file
        ArrayList<Spell> spellsList = FileIO.readSpells(spellsFile);
        if(spellsList == null) {
            System.out.println("The game will be played without spells.");
        } else {
            player.setSpells(spellsList);
            player.displaySpells();
        }

        do {
            System.out.println("Enter a command:");
            String command = userInput.nextLine();

            if (command.equals("quit")) {
                System.out.println("You coward! You better run fast!");
                break;
            }

            else if (command.equals("attack")) {
                System.out.println();
                attack(player, monster);

                // manage monster's death
                if(monster.getCurrHealth() <= 0) {
                    System.out.println(monster.getName() + " was knocked out!");
                    System.out.println();
                    System.out.println("Fantastic! You killed the monster!");
                    player.increaseWins();
                    FileIO.writeCharacter(player, "src/player.txt");
                    System.out.println(player.getName() + " has won: " + player.getNumWins() + " times.");
                    break;
                }
                System.out.println(monster);

                attack(monster, player);

                // manage player's death
                if(player.getCurrHealth() <= 0) {
                    System.out.println(player.getName() + " got knocked out!");
                    System.out.println();
                    System.out.println("Oh no ! You lost!");
                    monster.increaseWins();
                    System.out.println(monster.getName() + " has won: " + monster.getNumWins() + " times.");
                    FileIO.writeCharacter(monster, "src/monster.txt");
                    break;
                }
                System.out.println(player);

            }

            // if the player doesn't enter "attack" or "quit", we assume he's casting a spell
            else {
                int randomSpell = random.nextInt();
                double spellDamage = player.castSpell(command, randomSpell);

                // spell failure
                if(spellDamage <= 0) {
                    System.out.println(player.getName() + " tried to cast " + command + ", but they failed.");
                    System.out.println();
                }
                // spell success
                else  {
                    String spellStr = String.format("%1$,.2f", spellDamage);
                    System.out.println(player.getName() + " casts " + command + " dealing " + spellStr + " damage!");
                    monster.takeDamage(spellDamage);
                    System.out.println(monster);
                }

                // manage monster's death
                if(monster.getCurrHealth() <= 0) {
                    System.out.println(monster.getName() + " was knocked out!");
                    System.out.println();
                    System.out.println("Fantastic! You killed the monster!");
                    player.increaseWins();
                    FileIO.writeCharacter(player, "src/player.txt");
                    System.out.println(player.getName() + " has won: " + player.getNumWins() + " times.");
                    break;
                }

                attack(monster, player);

                // manage player's death
                if(player.getCurrHealth() <= 0) {
                    System.out.println(player.getName() + " got knocked out!");
                    System.out.println();
                    System.out.println("Oh no! You lost!");
                    monster.increaseWins();
                    FileIO.writeCharacter(monster, "src/monster.txt");
                    System.out.println(monster.getName() + " has won: " + monster.getNumWins() + " times.");
                    break;
                }
                System.out.println(player);
            }

        } while (player.getCurrHealth() > 0 && monster.getCurrHealth() > 0); // play as long as both characters are alive

    }

    // createCharacters creates an array of characters from two files provided as arguments
    private static boolean createCharacters(Character[] characters, String playerFile, String monsterFile) {
        // create both characters and check that they are valid
        characters[0] = FileIO.readCharacter(playerFile);
        characters[1] = FileIO.readCharacter(monsterFile);

        Character player = characters[0];
        Character monster = characters[1];

        // if there was an error creating a character, terminate the game and display a message.
        if(player == null || monster == null) {
            System.out.println("These are not valid character files. We cannot proceed with the game.");
            return false;
        }

        displayCharacterInfo(player);
        displayCharacterInfo(monster);

        return true;
    }

    // displayCharacterInfo displays the statistics of the character
    private static void displayCharacterInfo(Character character) {
        String characterInfo = "Name: " + character.getName() + "\n" +
                "Current health: " + character.getMaxHealth() + "\n" +
                "Attack value: " + character.getAttackValue() + "\n" +
                "Number of wins: " + character.getNumWins();

        System.out.println(characterInfo);
        System.out.println();
    }

    // attack manages a round of attack. It takes two characters as arguments, to set the attacker and the defender.
    private static void attack(Character attacker, Character defender) {
        int randomAttack = random.nextInt();
        Double attackValue = attacker.getAttackDamage(randomAttack);
        String attackStr = String.format("%1$,.2f", attackValue);
        System.out.println(attacker.getName() + " attacks for " + attackStr + " damage!");
        defender.takeDamage(attackValue);
    }

}