package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Gerencia as sessões de usuários no sistema Jackut.
 */
public class SessionManager {
    private Map<String, User> sessions;

    /**
     * Construtor que inicializa o armazenamento das sessões.
     */
    public SessionManager() {
        this.sessions = new HashMap<>();
    }

    /**
     * Cria uma nova sessão para um usuário.
     *
     * @param user Objeto do tipo User
     * @return ID da sessão criada
     */
    public String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, user);
        return sessionId;
    }

    /**
     * Recupera o usuário associado a uma sessão.
     *
     * @param sessionId ID da sessão
     * @return Objeto User correspondente
     * @throws UserNotFoundException se a sessão não existir
     */
    public User getUserFromSession(String sessionId) {
        if (!sessions.containsKey(sessionId)) {
            throw new UserNotFoundException();
        }
        return sessions.get(sessionId);
    }

    /**
     * Encerra uma sessão.
     *
     * @param sessionId ID da sessão a ser encerrada
     */
    public void closeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
