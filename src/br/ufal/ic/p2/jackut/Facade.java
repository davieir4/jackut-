package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.InvalidLoginException;
import br.ufal.ic.p2.jackut.Exceptions.UserAlredyExistsException;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Facade class that provides access to the Jackut system functionality.
 * This class serves as the entry point for the EasyAccept tests.
 */
public class Facade {
    private UserManager userManager;
    private SessionManager sessionManager;
    private CommunityManager communityManager;
    private SystemPersistence persistence;

    /**
     * Constructor initializes the managers and loads data from persistent storage if available.
     */
    public Facade() {
        this.persistence = new SystemPersistence();
        this.userManager = persistence.loadUsers();
        this.communityManager = persistence.loadCommunities();

        if (this.userManager == null) {
            this.userManager = new UserManager();
        }
        if (this.communityManager == null) {
            this.communityManager = new CommunityManager();
        }

        this.sessionManager = new SessionManager();
    }

    /**
     * Clears all data from the system.
     */
    public void zerarSistema() {
        this.userManager = new UserManager();
        this.sessionManager = new SessionManager();
        this.communityManager = new CommunityManager();
    }

    /**
     * Creates a new user in the system.
     *
     * @param login User's login
     * @param senha User's password
     * @param nome User's name
     * @throws UserAlredyExistsException if a user with the given login already exists
     * @throws InvalidLoginException if login or password is invalid
     */
    public void criarUsuario(String login, String senha, String nome) throws UserAlredyExistsException {
        if (login == null || login.trim().isEmpty()) {
            throw new InvalidLoginException("Login inválido.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new InvalidLoginException("Senha inválida.");
        }
        userManager.registerUser(login, senha, nome);
    }

    /**
     * Opens a session for a user, authenticating them with login and password.
     *
     * @param login User's login
     * @param senha User's password
     * @return Session ID
     * @throws InvalidLoginException if login or password is incorrect
     */
    public String abrirSessao(String login, String senha) {
        User user = userManager.authenticateUser(login, senha);
        return sessionManager.createSession(user);
    }

    /**
     * Gets an attribute value from a user's profile.
     *
     * @param login User's login
     * @param atributo Attribute name
     * @return The value of the attribute
     * @throws InvalidLoginException if user does not exist
     * @throws IllegalArgumentException if attribute is not set
     */
    public String getAtributoUsuario(String login, String atributo) {
        User user = userManager.getUserByLogin(login);
        if (user == null) {
            throw new InvalidLoginException("Usuário não cadastrado.");
        }

        return user.getProfileAttribute(atributo);
    }

    /**
     * Edits a user's profile attribute.
     *
     * @param id Session ID
     * @param atributo Attribute name
     * @param valor Attribute value
     * @throws InvalidLoginException if session is invalid
     */
    public void editarPerfil(String id, String atributo, String valor) {
        User user = sessionManager.getUserFromSession(id);
        if (user == null) {
            throw new InvalidLoginException("Usuário não cadastrado.");
        }

        user.setProfileAttribute(atributo, valor);
    }

    /**
     * Adds another user as a friend.
     *
     * @param id Session ID
     * @param amigo Friend's login
     * @throws InvalidLoginException if session is invalid or friend doesn't exist
     * @throws IllegalArgumentException if trying to add self or already added
     */
    public void adicionarAmigo(String id, String amigo) {
        User user = sessionManager.getUserFromSession(id);
        if (user == null) {
            throw new InvalidLoginException("Usuário não cadastrado.");
        }

        if (user.getLogin().equals(amigo)) {
            throw new IllegalArgumentException("Usuário não pode adicionar a si mesmo como amigo.");
        }

        User friend = userManager.getUserByLogin(amigo);
        if (friend == null) {
            throw new InvalidLoginException("Usuário não cadastrado.");
        }

        userManager.addFriend(user, friend);
    }

    /**
     * Checks if two users are friends.
     *
     * @param login First user's login
     * @param amigo Second user's login
     * @return true if they are friends, false otherwise
     */
    public boolean ehAmigo(String login, String amigo) {
        return userManager.areFriends(login, amigo);
    }

    /**
     * Gets a list of a user's friends.
     *
     * @param login User's login
     * @return List of friends' logins
     */
    public String getAmigos(String login) {
        List<String> friends = userManager.getFriendsList(login);
        if (friends.isEmpty()) {
            return "{}";
        }

        return "{" + String.join(",", friends) + "}";
    }

    /**
     * Sends a message to another user.
     *
     * @param id Session ID
     * @param destinatario Recipient's login
     * @param recado Message content
     * @throws InvalidLoginException if session is invalid or recipient doesn't exist
     * @throws IllegalArgumentException if trying to send to self
     */
    public void enviarRecado(String id, String destinatario, String recado) {
        User sender = sessionManager.getUserFromSession(id);
        if (sender == null) {
            throw new InvalidLoginException("Usuário não cadastrado.");
        }

        if (sender.getLogin().equals(destinatario)) {
            throw new IllegalArgumentException("Usuário não pode enviar recado para si mesmo.");
        }

        User recipient = userManager.getUserByLogin(destinatario);
        if (recipient == null) {
            throw new InvalidLoginException("Usuário não cadastrado.");
        }

        userManager.sendMessage(sender, recipient, recado);
    }

    /**
     * Reads the next message from a user's inbox.
     *
     * @param id Session ID
     * @return The message content
     * @throws InvalidLoginException if session is invalid
     * @throws IllegalArgumentException if inbox is empty
     */
    public String lerRecado(String id) {
        User user = sessionManager.getUserFromSession(id);
        if (user == null) {
            throw new InvalidLoginException("Usuário não cadastrado.");
        }

        return user.readNextMessage();
    }
    public void criarComunidade(String session, String name, String description){
        User owner = sessionManager.getUserFromSession(session);
        communityManager.registerCommunity(owner, name, description);
    }
    public String getDescricaoComunidade(String name){
        Community community = communityManager.getCommunity(name);
        return community.getDescription();
    }
    public String getDonoComunidade(String name){
        Community community = communityManager.getCommunity(name);
        User owner = community.getOwner();
        return owner.getLogin();
    }
    public String getMembrosComunidade(String name) {
        Community community = communityManager.getCommunity(name);
        Set<String> members = community.getMembers();

        if (members.isEmpty()) {
            return "{}";
        }

        return "{" + String.join(",", members) + "}";
    }
    public String getComunidades(String login){
        User user = userManager.getUserByLogin(login);
        LinkedHashSet<String> comunidades = user.getCommunities();
        if (comunidades.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", comunidades) + "}";

    }
    public void adicionarComunidade(String session, String nome){
        User user = sessionManager.getUserFromSession(session);
        Community community = communityManager.getCommunity(nome);
        user.addCommunity(nome);
        community.addUser(user);
    }
    /**
     * Saves the system state and terminates.
     */
    public void encerrarSistema() {
        persistence.saveUsers(userManager);
        persistence.saveCommunities(communityManager);
    }
}