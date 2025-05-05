package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.InvalidLoginException;
import br.ufal.ic.p2.jackut.Exceptions.UserAlreadyExistsException;
import br.ufal.ic.p2.jackut.Exceptions.UserNotFoundException;

import java.io.Serializable;
import java.util.*;

/**
 * Gerencia os usuários no sistema Jackut.
 * Responsável pelo registro, autenticação e interações entre usuários.
 */
public class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;

    // Mapa que armazena os usuários (login -> User)
    private Map<String, User> users;

    /**
     * Construtor que inicializa o armazenamento de usuários.
     */
    public UserManager() {
        this.users = new HashMap<>();
    }

    /**
     * Registra um novo usuário no sistema.
     *
     * @param login Login do usuário
     * @param password Senha do usuário
     * @param name Nome do usuário
     * @throws UserAlreadyExistsException se já existir um usuário com o mesmo login
     */
    public void registerUser(String login, String password, String name) throws UserAlreadyExistsException {
        if (users.containsKey(login)) {
            throw new UserAlreadyExistsException("Conta com esse nome já existe.");
        }

        User newUser = new User(login, password, name);
        users.put(login, newUser);
    }

    /**
     * Autentica um usuário com login e senha.
     *
     * @param login Login do usuário
     * @param password Senha do usuário
     * @return Objeto User se a autenticação for bem-sucedida
     * @throws InvalidLoginException se a autenticação falhar
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
     * Obtém um usuário pelo login.
     *
     * @param login Login do usuário
     * @return Objeto User ou null se não encontrado
     * @throws UserNotFoundException se o usuário não existir
     */
    public User getUserByLogin(String login) {
        if (!users.containsKey(login)){
            throw new UserNotFoundException();
        }
        return users.get(login);
    }

    /**
     * Adiciona uma relação de amizade entre dois usuários.
     *
     * @param requester Usuário que iniciou a solicitação
     * @param recipient Usuário que recebeu a solicitação
     * @throws IllegalArgumentException se os usuários forem iguais ou já forem amigos
     */
    public void addFriend(User requester, User recipient) {
        if (recipient.hasFriendRequest(requester.getLogin())) {
            // Reciprocidade de solicitação de amizade
            requester.acceptFriendRequest(recipient.getLogin());
            recipient.acceptFriendRequest(requester.getLogin());
        } else {
            // Cria nova solicitação de amizade
            requester.addFriendRequest(recipient.getLogin());
        }
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param login1 Login do primeiro usuário
     * @param login2 Login do segundo usuário
     * @return true se forem amigos, false caso contrário
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
     * Obtém a lista de amigos de um usuário.
     *
     * @param login Login do usuário
     * @return Conjunto de logins dos amigos
     */
    public Set<String> getFriendsList(String login) {
        User user = getUserByLogin(login);

        if (user == null) {
            return Collections.emptySet();
        }

        return user.getFriends();
    }

    /**
     * Envia uma mensagem de um usuário para outro.
     *
     * @param sender Usuário remetente
     * @param recipient Usuário destinatário
     * @param content Conteúdo da mensagem
     */
    public void sendMessage(User sender, User recipient, String content) {
        Message message = new Message(sender.getLogin(), recipient.getLogin(), content);
        recipient.addMessage(message);
    }

    /**
     * Remove um usuário do sistema.
     *
     * @param login Login do usuário a ser removido
     * @throws UserNotFoundException se o usuário não existir
     */
    public void removeUser(String login){
        if(getUserByLogin(login) == null) throw new UserNotFoundException();
        User user = users.get(login);

        // Limpa todas as relações do usuário
        user.getEnemies().clear();
        user.getCrushes().clear();
        user.getCommunities().clear();
        user.getFriends().clear();
        user.idols.clear();
        user.fans.clear();

        // Remove todas as mensagens enviadas pelo usuário
        for (String userReceiverLogin : users.keySet()){
            User userReceiver = users.get(userReceiverLogin);
            Queue<Message> messages = userReceiver.getAllMessages();
            messages.removeIf(message -> message.getSender().equals(login));
        }

        // Remove o usuário do mapa de usuários
        this.users.remove(login);
    }
}