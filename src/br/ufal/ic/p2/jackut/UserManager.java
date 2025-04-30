package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.InvalidLoginException;
import br.ufal.ic.p2.jackut.Exceptions.UserAlreadyExistsException;
import br.ufal.ic.p2.jackut.Exceptions.UserNotFoundException;

import java.io.Serializable;
import java.util.*;

/**
 * Manages users in the Jackut system.
 * Handles user registration, authentication, and interactions between users.
 */
public class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, User> users;

    /**
     * Constructor initializes the user storage.
     */
    public UserManager() {
        this.users = new HashMap<>();
    }

    /**
     * Registers a new user.
     *
     * @param login User's login
     * @param password User's password
     * @param name User's name
     * @throws UserAlreadyExistsException if a user with the given login already exists
     */
    public void registerUser(String login, String password, String name) throws UserAlreadyExistsException {
        if (users.containsKey(login)) {
            throw new UserAlreadyExistsException("Conta com esse nome já existe.");
        }

        User newUser = new User(login, password, name);
        users.put(login, newUser);
    }

    /**
     * Authenticates a user with login and password.
     *
     * @param login User's login
     * @param password User's password
     * @return User object if authentication succeeds
     * @throws InvalidLoginException if authentication fails
     */
    public User authenticateUser(String login, String password) {


        User user = null;
        try {
            user = getUserByLogin(login);
        } catch (Exception e) {
            throw new InvalidLoginException("Login ou senha inválidos.");
        }

        if (user == null || !user.getPassword().equals(password)) {
            throw new InvalidLoginException("Login ou senha inválidos.");
        }

        return user;
    }

    /**
     * Gets a user by login.
     *
     * @param login User's login
     * @return User object or null if not found
     */
    public User getUserByLogin(String login) {
        if (!users.containsKey(login)){
            throw new UserNotFoundException();
        }
        return users.get(login);
    }

    /**
     * Adds a friend relationship between two users.
     *
     * @param requester User who initiated the request
     * @param recipient User who receives the request
     * @throws IllegalArgumentException if users are the same or already friends
     */
    public void addFriend(User requester, User recipient) {
        if (recipient.hasFriendRequest(requester.getLogin())) {
            // Reciprocate friend request
            requester.acceptFriendRequest(recipient.getLogin());
            recipient.acceptFriendRequest(requester.getLogin());
        } else {
            // Create new friend request
            requester.addFriendRequest(recipient.getLogin());
        }
    }

    /**
     * Checks if two users are friends.
     *
     * @param login1 First user's login
     * @param login2 Second user's login
     * @return true if they are friends, false otherwise
     */
    public boolean areFriends(String login1, String login2) {
        User user1 = getUserByLogin(login1);
        User user2 = getUserByLogin(login2);

        if (user1 == null || user2 == null) {
            return false;
        }

        return user1.isFriendWith(login2) && user2.isFriendWith(login1);
    }

    /**
     * Gets a list of a user's friends.
     *
     * @param login User's login
     * @return List of friends' logins
     */
    public Set<String> getFriendsList(String login) {
        User user = getUserByLogin(login);

        if (user == null) {
            return Collections.emptySet();
        }

        return user.getFriends();
    }

    /**
     * Sends a message from one user to another.
     *
     * @param sender Sender user
     * @param recipient Recipient user
     * @param content Message content
     */
    public void sendMessage(User sender, User recipient, String content) {
        Message message = new Message(sender.getLogin(), recipient.getLogin(), content);
        recipient.addMessage(message);
    }
    public void removeUser(String login){
        if(getUserByLogin(login) == null) throw new UserNotFoundException();
        User user = users.get(login);
        user.getEnemies().clear();
        user.getCrushes().clear();
        user.getCommunities().clear();
        user.getFriends().clear();
        user.idols.clear();
        user.fans.clear();
        for (String userReceiverLogin : users.keySet()){
            User userReceiver = users.get(userReceiverLogin);
            Queue<Message> messages = userReceiver.getAllMessages();
            messages.removeIf(message -> message.getSender().equals(login));
        }
        this.users.remove(login);
    }
}