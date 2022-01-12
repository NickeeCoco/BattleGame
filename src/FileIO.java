// Morgane Bentzinger - 261062953

import java.io.*;
import java.util.ArrayList;

/*
    The FileIO Class manages reading from files to create characters and manage spells, and writing to files at the end of the game to save the characters.
 */
public class FileIO {
    // readCharacter creates a new character from a file
    public static Character readCharacter(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String characterName = br.readLine();
            double attackValue = Double.parseDouble(br.readLine());
            double maxHealth = Double.parseDouble(br.readLine());
            int numWins = Integer.parseInt(br.readLine());

            br.close();
            fr.close();

            return new Character(characterName, attackValue, maxHealth, numWins);

        } catch (FileNotFoundException e) {
            System.out.println("The file " + filename + " does not exist");
            return null;
        } catch (IOException e) {
            System.out.println("The IO request could not be processed.");
            return null;
        } catch (Exception e) {
            System.out.println("There was an error creating your character.");
            return null;
        }
    }

    // readSpells generates a spells list for a character from a file
    public static ArrayList<Spell> readSpells(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String spellLine = br.readLine();
            ArrayList<Spell> spellsArray = new ArrayList<Spell>();

            while(spellLine != null) {
                String[] spellCharacteristics = spellLine.split("\t");
                String spellName = spellCharacteristics[0];
                double minDamage = Double.parseDouble(spellCharacteristics[1]);
                double maxDamage = Double.parseDouble(spellCharacteristics[2]);
                double chanceOfSuccess = Double.parseDouble(spellCharacteristics[3]);

                Spell spell = new Spell(spellName, minDamage, maxDamage, chanceOfSuccess);
                spellsArray.add(spell);

                spellLine = br.readLine();
            }

            br.close();
            fr.close();

            return spellsArray;

        } catch (FileNotFoundException e) {
            System.out.println("The file " + filename + " does not exist");
            return null;
        } catch (IOException e) {
            System.out.println("The IO request could not be processed.");
            return null;
        } catch (Exception e) {
            System.out.println("There was an error creating your character.");
            return null;
        }
    }

    // writeCharacter allows to save the wins of the characters after each game by overwriting their file
    public static void writeCharacter(Character character, String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            String characterName = character.getName();
            String attackValue = Double.toString(character.getAttackValue());
            String maxHealth = Double.toString(character.getMaxHealth());
            String numWins = Integer.toString(character.getNumWins());

            String characterInfo = characterName + "\n" + attackValue + "\n" + maxHealth + "\n" + numWins;
            bw.write(characterInfo);

            bw.close();
            fw.close();

            System.out.println("Successfully wrote to file " + filename);

        } catch (FileNotFoundException e) {
            System.out.println("The file " + filename + " does not exist");
        } catch (IOException e) {
            System.out.println("The IO request could not be processed.");
        } catch (Exception e) {
            System.out.println("There was an error saving your character.");
        }
    }
}
