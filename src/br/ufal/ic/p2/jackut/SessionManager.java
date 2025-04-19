package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages user sessions in the Jackut system.
 */
public class SessionManager {
    private Map<String, User> sessions;

    /**
     * Constructor initializes the session storage.
     */
    public SessionManager() {
        this.sessions = new HashMap<>();
    }

    /**
     * Creates a new session for a user.
     *
     * @param user User object
     * @return Session ID
     */
    public String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, user);
        return sessionId;
    }

    /**
     * Gets a user from a session.
     *
     * @param sessionId Session ID
     * @return User object or null if session is invalid
     */
    public User getUserFromSession(String sessionId) {
        if(!sessions.containsKey(sessionId)){
            throw new UserNotFoundException();
        }
        return sessions.get(sessionId);
    }

    /**
     * Closes a session.
     *
     * @param sessionId Session ID
     */
    public void closeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}