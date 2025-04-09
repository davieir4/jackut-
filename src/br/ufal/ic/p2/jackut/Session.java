package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.UUID;

/**
 * Representa uma sessão de usuário no sistema Jackut.
 */
public class Session implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private User user;

    /**
     * Cria uma nova sessão para o usuário especificado.
     *
     * @param user O usuário autenticado
     */
    public Session(User user) {
        this.user = user;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Obtém o ID da sessão.
     *
     * @return O identificador único da sessão
     */
    public String getId() {
        return id;
    }

    /**
     * Obtém o usuário associado a esta sessão.
     *
     * @return O usuário da sessão
     */
    public User getUser() {
        return user;
    }
}