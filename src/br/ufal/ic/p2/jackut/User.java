package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a user in the Jackut system.
 * Contains all user information including profile, friends and messages.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String password;
    private String name;
    private Map<String, String> profile;
    private Set<String> friendRequests;
    private Set<String> friends;
    private Queue<Message> messages;
    private LinkedHashSet<String> communities = new LinkedHashSet<>();

    /**
     * Constructor creates a new user with the given credentials.
     *
     * @param login User's login
     * @param password User's password
     * @param name User's name
     */
    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.profile = new HashMap<>();
        this.profile.put("nome", name);
        this.friendRequests = new HashSet<>();
        this.friends = new HashSet<>();
        this.messages = new LinkedList<>();
    }

    /**
     * Gets the user's login.
     *
     * @return User's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Gets the user's password.
     *
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the user's name.
     *
     * @return User's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets a profile attribute value.
     *
     * @param attribute Attribute name
     * @return The attribute value
     * @throws IllegalArgumentException if attribute is not set
     */
    public String getProfileAttribute(String attribute) {
        if (profile.containsKey(attribute)) {
            return profile.get(attribute);
        }
        throw new IllegalArgumentException("Atributo não preenchido.");
    }

    /**
     * Sets a profile attribute value.
     *
     * @param attribute Attribute name
     * @param value Attribute value
     */
    public void setProfileAttribute(String attribute, String value) {
        profile.put(attribute, value);
    }

    /**
     * Adds a friend request.
     *
     * @param login Friend's login
     * @throws IllegalArgumentException if request already exists
     */
    public void addFriendRequest(String login) {
        if (friendRequests.contains(login)) {
            throw new IllegalArgumentException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        }
        if (friends.contains(login)) {
            throw new IllegalArgumentException("Usuário já está adicionado como amigo.");
        }
        friendRequests.add(login);
    }

    /**
     * Accepts a friend request.
     *
     * @param login Friend's login
     */
    public void acceptFriendRequest(String login) {
        friendRequests.remove(login);
        friends.add(login);
    }

    /**
     * Checks if the user has sent a friend request to another user.
     *
     * @param login Other user's login
     * @return true if request exists, false otherwise
     */
    public boolean hasFriendRequest(String login) {
        return friendRequests.contains(login);
    }

    /**
     * Checks if the user is friends with another user.
     *
     * @param login Other user's login
     * @return true if they are friends, false otherwise
     */
    public boolean isFriendWith(String login) {
        return friends.contains(login);
    }

    /**
     * Gets the list of friends.
     *
     * @return List of friends' logins
     */
    public List<String> getFriends() {
        return new ArrayList<>(friends);
    }

    /**
     * Adds a message to the user's inbox.
     *
     * @param message The message to add
     */
    public void addMessage(Message message) {
        messages.add(message);
    }

    /**
     * Reads the next message from the inbox.
     *
     * @return The message content
     * @throws IllegalArgumentException if inbox is empty
     */
    public String readNextMessage() {
        if (messages.isEmpty()) {
            throw new IllegalArgumentException("Não há recados.");
        }
        return messages.poll().getContent();
    }
    public LinkedHashSet<String> getCommunities(){
        return this.communities;
    }
    public void addCommunity(String name){
        communities.add(name);
    }
}