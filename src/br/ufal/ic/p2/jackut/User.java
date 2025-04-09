package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usuário no sistema Jackut.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String password;
    private String name;
    private Map<String, String> profile;
    private List<User> friends;
    private Queue<Message> messages;
    private Set<User> pendingFriendRequests;

    /**
     * Cria um novo usuário com o login, senha e nome fornecidos.
     *
     * @param login Login do usuário (identificador único)
     * @param password Senha do usuário
     * @param name Nome de exibição do usuário
     */
    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.profile = new HashMap<>();
        this.friends = new ArrayList<>();
        this.messages = new LinkedList<>();
        this.pendingFriendRequests = new HashSet<>();

        // Inicializa o perfil com o nome
        profile.put("nome", name);
    }

    /**
     * Autentica o usuário com a senha fornecida.
     *
     * @param password Senha a ser verificada
     * @return true se a autenticação for bem-sucedida, false caso contrário
     */
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    /**
     * Obtém o login do usuário.
     *
     * @return O login do usuário
     */
    public String getLogin() {
        return login;
    }

    /**
     * Obtém o nome do usuário.
     *
     * @return O nome do usuário
     */
    public String getName() {
        return name;
    }

    /**
     * Obtém o valor de um atributo do perfil.
     *
     * @param attribute O nome do atributo
     * @return O valor do atributo ou null se não for encontrado
     */
    public String getProfileAttribute(String attribute) {
        return profile.get(attribute);
    }

    /**
     * Define o valor de um atributo do perfil.
     *
     * @param attribute O nome do atributo
     * @param value O novo valor para o atributo
     */
    public void setProfileAttribute(String attribute, String value) {
        profile.put(attribute, value);
    }

    /**
     * Adiciona um amigo à lista de amigos deste usuário se o outro usuário já
     * tiver enviado uma solicitação de amizade.
     *
     * @param user O usuário a ser adicionado como amigo
     */
    public void addFriend(User user) {
        if (user.pendingFriendRequests.contains(this)) {
            // Amizade mútua estabelecida
            friends.add(user);
            user.confirmFriendship(this);
        } else {
            // Envia solicitação de amizade
            user.pendingFriendRequests.add(this);
        }
    }

    /**
     * Confirma amizade com outro usuário.
     * Chamado quando o outro usuário aceita a solicitação de amizade deste usuário.
     *
     * @param user O usuário que aceitou a solicitação de amizade
     */
    private void confirmFriendship(User user) {
        pendingFriendRequests.remove(user);
        friends.add(user);
    }

    /**
     * Verifica se este usuário é amigo de outro usuário.
     *
     * @param user O usuário para verificar a amizade
     * @return true se forem amigos, false caso contrário
     */
    public boolean isFriendWith(User user) {
        return friends.contains(user);
    }

    /**
     * Obtém uma string formatada com a lista de amigos.
     *
     * @return Uma string contendo nomes de amigos separados por vírgulas
     */
    public String getFriendsListAsString() {
        if (friends.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < friends.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(friends.get(i).getLogin());
        }
        return result.toString();
    }

    /**
     * Recebe uma mensagem de outro usuário.
     *
     * @param message A mensagem a ser recebida
     */
    public void receiveMessage(Message message) {
        messages.add(message);
    }

    /**
     * Lê a próxima mensagem na fila.
     *
     * @return O conteúdo da mensagem ou notificação se não houver mensagens disponíveis
     */
    public String readNextMessage() {
        if (messages.isEmpty()) {
            return "Não há recados";
        }

        Message message = messages.poll();
        return message.getFormattedContent();
    }
}