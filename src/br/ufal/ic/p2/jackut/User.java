package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.*;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usuário no sistema Jackut.
 * Contém todas as informações do usuário incluindo perfil, amigos e mensagens.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    // Dados básicos do usuário
    private String login;
    private String password;
    private String name;

    // Atributos do perfil (chave-valor)
    private Map<String, String> profile;

    // Solicitações de amizade pendentes
    private Set<String> friendRequests;

    // Lista de amigos do usuário
    private Set<String> friends;

    // Mensagens recebidas (fila)
    private Queue<Message> messages;

    // Mensagens de comunidade (fila)
    private Queue<String> groupMessages = new LinkedList<>();

    // Comunidades que o usuário participa
    private LinkedHashSet<String> communities = new LinkedHashSet<>();

    // Ídolos do usuário (quem ele admira)
    public Set<String> idols = new LinkedHashSet<>();

    // Fãs do usuário (quem admira ele)
    public Set<String> fans = new LinkedHashSet<>();

    // Paqueras do usuário
    private Set<String> crushes = new LinkedHashSet<>();

    // Inimigos do usuário
    private Set<String> enemies = new LinkedHashSet<>();

    /**
     * Construtor cria um novo usuário com as credenciais fornecidas.
     *
     * @param login Login do usuário
     * @param password Senha do usuário
     * @param name Nome do usuário
     */
    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.profile = new HashMap<>();
        this.profile.put("nome", name); // Adiciona o nome ao perfil
        this.friendRequests = new HashSet<>();
        this.friends = new LinkedHashSet<>();
        this.messages = new LinkedList<>();
    }

    /**
     * Obtém o login do usuário.
     *
     * @return Login do usuário
     */
    public String getLogin() {
        return login;
    }

    /**
     * Obtém a senha do usuário.
     *
     * @return Senha do usuário
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtém o nome do usuário.
     *
     * @return Nome do usuário
     */
    public String getName() {
        return name;
    }

    /**
     * Obtém o valor de um atributo do perfil.
     *
     * @param attribute Nome do atributo
     * @return Valor do atributo
     * @throws IllegalArgumentException se o atributo não estiver preenchido
     */
    public String getProfileAttribute(String attribute) {
        if (profile.containsKey(attribute)) {
            return profile.get(attribute);
        }
        throw new IllegalArgumentException("Atributo não preenchido.");
    }

    /**
     * Define o valor de um atributo do perfil.
     *
     * @param attribute Nome do atributo
     * @param value Valor do atributo
     */
    public void setProfileAttribute(String attribute, String value) {
        profile.put(attribute, value);
    }

    /**
     * Adiciona uma solicitação de amizade.
     *
     * @param login Login do amigo
     * @throws IllegalArgumentException se a solicitação já existir
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
     * Aceita uma solicitação de amizade.
     *
     * @param login Login do amigo
     */
    public void acceptFriendRequest(String login) {
        friendRequests.remove(login);
        friends.add(login);
    }

    /**
     * Verifica se o usuário enviou solicitação de amizade para outro usuário.
     *
     * @param login Login do outro usuário
     * @return true se existe solicitação, false caso contrário
     */
    public boolean hasFriendRequest(String login) {
        return friendRequests.contains(login);
    }

    /**
     * Verifica se o usuário é amigo de outro usuário.
     *
     * @param login Login do outro usuário
     * @return true se são amigos, false caso contrário
     */
    public boolean isFriendWith(String login) {
        return friends.contains(login);
    }

    /**
     * Obtém a lista de amigos.
     *
     * @return Lista de logins dos amigos
     */
    public Set<String> getFriends() {
        return new LinkedHashSet<>(friends);
    }

    /**
     * Adiciona uma mensagem à caixa de entrada do usuário.
     *
     * @param message Mensagem a ser adicionada
     */
    public void addMessage(Message message) {
        messages.add(message);
    }

    /**
     * Lê a próxima mensagem da caixa de entrada.
     *
     * @return Conteúdo da mensagem
     * @throws IllegalArgumentException se a caixa de entrada estiver vazia
     */
    public String readNextMessage() {
        if (messages.isEmpty()) {
            throw new MessageNotFound();
        }
        return messages.poll().getContent();
    }

    /**
     * Obtém as comunidades do usuário.
     *
     * @return Lista de nomes das comunidades
     */
    public LinkedHashSet<String> getCommunities(){
        return this.communities;
    }

    /**
     * Adiciona o usuário a uma comunidade.
     *
     * @param name Nome da comunidade
     */
    public void addCommunity(String name){
        communities.add(name);
    }

    /**
     * Adiciona uma mensagem de grupo à comunidade.
     *
     * @param message Conteúdo da mensagem
     * @param community Comunidade relacionada
     */
    public void addGroupMessage(String message, Community community){
        community.addMessage(message);
    }

    /**
     * Recebe uma mensagem de grupo.
     *
     * @param message Conteúdo da mensagem
     */
    public void receiveGroupMessage(String message){
        groupMessages.add(message);
    }

    /**
     * Lê a próxima mensagem de grupo.
     *
     * @return Conteúdo da mensagem
     */
    public String readGroupMessage() {
        if (groupMessages.isEmpty()) {
            throw new GroupMessageNotFound();
        }
        return groupMessages.poll();
    }

    /**
     * Adiciona um ídolo à lista do usuário.
     *
     * @param loginIdol Login do ídolo
     */
    public void addIdol(String loginIdol){
        idols.add(loginIdol);
    }

    /**
     * Adiciona um fã à lista do usuário.
     *
     * @param loginFan Login do fã
     */
    public void addFan(String loginFan){
        fans.add(loginFan);
    }

    /**
     * Adiciona uma paquera à lista do usuário.
     *
     * @param loginCrush Login da paquera
     */
    public void addCrush(String loginCrush){
        if (loginCrush.equals(this.login)){
            throw new SelfLoveException();
        }
        if(crushes.contains(loginCrush)){
            throw new CrushAlreadyExists();
        }
        crushes.add(loginCrush);
    }

    /**
     * Obtém a lista de paqueras do usuário.
     *
     * @return Lista de logins das paqueras
     */
    public Set<String> getCrushes(){
        return crushes;
    }

    /**
     * Adiciona um inimigo à lista do usuário.
     *
     * @param enemyLogin Login do inimigo
     */
    public void addEnemy(String enemyLogin){
        if (enemies.contains(enemyLogin)) throw new EnemyAlreadyExists();
        if (enemyLogin.equals(this.login)) throw new SelfHateException();
        enemies.add(enemyLogin);
    }

    /**
     * Obtém a lista de inimigos do usuário.
     *
     * @return Lista de logins dos inimigos
     */
    public Set<String> getEnemies(){
        return this.enemies;
    }

    /**
     * Obtém todas as mensagens do usuário.
     *
     * @return Fila de mensagens
     */
    public Queue<Message> getAllMessages(){
        return this.messages;
    }
}