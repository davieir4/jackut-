package br.ufal.ic.p2.jackut;

import java.io.*;

/**
 * Handles persistence of the Jackut system state.
 */
public class SystemPersistence {
    private static final String USERS_DATA_FILE = "jackut_users.ser";
    private static final String COMMUNITIES_DATA_FILE = "jackut_communities.ser";

    public void saveUsers(UserManager userManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_DATA_FILE))) {
            oos.writeObject(userManager);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public void saveCommunities(CommunityManager communityManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COMMUNITIES_DATA_FILE))) {
            oos.writeObject(communityManager);
        } catch (IOException e) {
            System.err.println("Error saving communities: " + e.getMessage());
        }
    }

    public UserManager loadUsers() {
        File file = new File(USERS_DATA_FILE);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (UserManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
            return null;
        }
    }

    public CommunityManager loadCommunities() {
        File file = new File(COMMUNITIES_DATA_FILE);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (CommunityManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading communities: " + e.getMessage());
            return null;
        }
    }
}