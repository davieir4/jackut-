package br.ufal.ic.p2.jackut;

import java.io.*;

/**
 * Responsável pela persistência do estado do sistema Jackut.
 *
 * Esta classe salva e carrega os dados dos usuários e comunidades
 * utilizando serialização de objetos para arquivos locais.
 */
public class SystemPersistence {
    private static final String USERS_DATA_FILE = "jackut_users.ser";
    private static final String COMMUNITIES_DATA_FILE = "jackut_communities.ser";

    /**
     * Salva os dados dos usuários em um arquivo.
     *
     * @param userManager Gerenciador contendo os usuários do sistema
     */
    public void saveUsers(UserManager userManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_DATA_FILE))) {
            oos.writeObject(userManager);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    /**
     * Salva os dados das comunidades em um arquivo.
     *
     * @param communityManager Gerenciador contendo as comunidades do sistema
     */
    public void saveCommunities(CommunityManager communityManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COMMUNITIES_DATA_FILE))) {
            oos.writeObject(communityManager);
        } catch (IOException e) {
            System.err.println("Error saving communities: " + e.getMessage());
        }
    }

    /**
     * Carrega os dados dos usuários a partir do arquivo de persistência.
     *
     * @return Instância de UserManager contendo os usuários carregados, ou null se o arquivo não existir ou ocorrer erro
     */
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

    /**
     * Carrega os dados das comunidades a partir do arquivo de persistência.
     *
     * @return Instância de CommunityManager contendo as comunidades carregadas, ou null se o arquivo não existir ou ocorrer erro
     */
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
