package br.ufal.ic.p2.jackut;

import java.io.*;

/**
 * Handles persistence of the Jackut system state.
 */
public class SystemPersistence {
    private static final String DATA_FILE = "jackut_data.ser";

    /**
     * Saves the system state to a file.
     *
     * @param userManager The UserManager to save
     */
    public void saveSystem(UserManager userManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(userManager);
        } catch (IOException e) {
            System.err.println("Error saving system: " + e.getMessage());
        }
    }

    /**
     * Loads the system state from a file.
     *
     * @return The loaded UserManager or null if file doesn't exist or error occurs
     */
    public UserManager loadSystem() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (UserManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading system: " + e.getMessage());
            return null;
        }
    }
}