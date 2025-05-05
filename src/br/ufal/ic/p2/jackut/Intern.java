package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A classe "Intern": faz todo o trabalho pesado enquanto a Fachada leva o crédito.
 * Como um estagiário de verdade, ela gerencia usuários, comunidades e mensagens silenciosamente.
 */
public class Intern {
    private UserManager userManager;
    private SessionManager sessionManager;
    private CommunityManager communityManager;
    private SystemPersistence persistence;

    /**
     * Construtor que inicializa os gerenciadores e carrega dados do armazenamento persistente, se disponíveis.
     */
    public Intern() {
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
     * Limpa todos os dados do sistema.
     */
    public void zerarSistema() {
        this.userManager = new UserManager();
        this.sessionManager = new SessionManager();
        this.communityManager = new CommunityManager();
    }

    /**
     * Cria um novo usuário no sistema.
     *
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @param nome Nome do usuário
     * @throws UserAlreadyExistsException se já existir um usuário com o login fornecido
     * @throws InvalidLoginException se o login ou a senha forem inválidos
     */
    public void criarUsuario(String login, String senha, String nome) throws UserAlreadyExistsException {
        if (login == null || login.trim().isEmpty()) {
            throw new InvalidLoginException("Login inválido.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new InvalidLoginException("Senha inválida.");
        }
        userManager.registerUser(login, senha, nome);
    }

    /**
     * Abre uma sessão para um usuário, autenticando com login e senha.
     *
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @return ID da sessão
     * @throws InvalidLoginException se o login ou senha forem incorretos
     */
    public String abrirSessao(String login, String senha) {
        User user = userManager.authenticateUser(login, senha);
        return sessionManager.createSession(user);
    }

    /**
     * Retorna o valor de um atributo do perfil do usuário.
     *
     * @param login Login do usuário
     * @param atributo Nome do atributo
     * @return Valor do atributo
     * @throws InvalidLoginException se o usuário não estiver cadastrado
     * @throws IllegalArgumentException se o atributo não estiver definido
     */
    public String getAtributoUsuario(String login, String atributo) {
        User user = userManager.getUserByLogin(login);
        if (user == null) {
            throw new InvalidLoginException("Usuário não cadastrado.");
        }

        return user.getProfileAttribute(atributo);
    }

    /**
     * Edita um atributo do perfil do usuário.
     *
     * @param id ID da sessão
     * @param atributo Nome do atributo
     * @param valor Novo valor do atributo
     * @throws InvalidLoginException se a sessão for inválida
     */
    public void editarPerfil(String id, String atributo, String valor) {
        User user = sessionManager.getUserFromSession(id);
        if (user == null) {
            throw new InvalidLoginException("Usuário não cadastrado.");
        }

        user.setProfileAttribute(atributo, valor);
    }

    /**
     * Adiciona outro usuário como amigo.
     *
     * @param id ID da sessão
     * @param amigo Login do amigo
     * @throws InvalidLoginException se a sessão for inválida ou o amigo não existir
     * @throws IllegalArgumentException se tentar adicionar a si mesmo ou já for amigo
     */
    public void adicionarAmigo(String id, String amigo) {
        User user = sessionManager.getUserFromSession(id);
        User userAmigo = userManager.getUserByLogin(amigo);

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
        if (userAmigo.getEnemies().contains(user.getLogin())){
            throw new EnemyException(userAmigo.getName());
        }
        userManager.addFriend(user, friend);
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param login Login do primeiro usuário
     * @param amigo Login do segundo usuário
     * @return true se forem amigos, false caso contrário
     */
    public boolean ehAmigo(String login, String amigo) {
        return userManager.areFriends(login, amigo);
    }

    /**
     * Retorna a lista de amigos de um usuário.
     *
     * @param login Login do usuário
     * @return Lista de logins dos amigos
     */
    public String getAmigos(String login) {
        Set<String> friends = userManager.getFriendsList(login);
        if (friends.isEmpty()) {
            return "{}";
        }

        return "{" + String.join(",", friends) + "}";
    }

    /**
     * Envia um recado para outro usuário.
     *
     * @param id ID da sessão
     * @param destinatario Login do destinatário
     * @param recado Conteúdo do recado
     * @throws InvalidLoginException se a sessão ou o destinatário forem inválidos
     * @throws IllegalArgumentException se tentar enviar para si mesmo
     */
    public void enviarRecado(String id, String destinatario, String recado) {
        User sender = sessionManager.getUserFromSession(id);
        User receiver = userManager.getUserByLogin(destinatario);
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
        if (receiver.getEnemies().contains(sender.getLogin())){
            throw new EnemyException(receiver.getName());
        }
        userManager.sendMessage(sender, recipient, recado);
    }

    /**
     * Lê o próximo recado da caixa de entrada do usuário.
     *
     * @param id ID da sessão
     * @return Conteúdo do recado
     * @throws InvalidLoginException se a sessão for inválida
     * @throws IllegalArgumentException se a caixa de entrada estiver vazia
     */
    public String lerRecado(String id) {
        User user = sessionManager.getUserFromSession(id);
        if (user == null) {
            throw new InvalidLoginException("Usuário não cadastrado.");
        }

        return user.readNextMessage();
    }

    /**
     * Cria uma nova comunidade.
     *
     * @param session ID da sessão do dono
     * @param name Nome da comunidade
     * @param description Descrição da comunidade
     */
    public void criarComunidade(String session, String name, String description){
        User owner = sessionManager.getUserFromSession(session);
        communityManager.registerCommunity(owner, name, description);
    }

    /**
     * Retorna a descrição de uma comunidade.
     *
     * @param name Nome da comunidade
     * @return Descrição da comunidade
     */
    public String getDescricaoComunidade(String name){
        Community community = communityManager.getCommunity(name);
        return community.getDescription();
    }

    /**
     * Retorna o login do dono da comunidade.
     *
     * @param name Nome da comunidade
     * @return Login do dono
     */
    public String getDonoComunidade(String name){
        Community community = communityManager.getCommunity(name);
        User owner = community.getOwner();
        return owner.getLogin();
    }

    /**
     * Retorna os membros de uma comunidade.
     *
     * @param name Nome da comunidade
     * @return Conjunto dos logins dos membros
     */
    public String getMembrosComunidade(String name) {
        Community community = communityManager.getCommunity(name);
        Set<String> members = community.getMembers();

        if (members.isEmpty()) {
            return "{}";
        }

        return "{" + String.join(",", members) + "}";
    }

    /**
     * Retorna as comunidades de um usuário.
     *
     * @param login Login do usuário
     * @return Conjunto dos nomes das comunidades
     */
    public String getComunidades(String login){
        User user = userManager.getUserByLogin(login);
        LinkedHashSet<String> comunidades = user.getCommunities();
        if (comunidades.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", comunidades) + "}";
    }

    /**
     * Adiciona o usuário à comunidade.
     *
     * @param session ID da sessão do usuário
     * @param nome Nome da comunidade
     */
    public void adicionarComunidade(String session, String nome){
        User user = sessionManager.getUserFromSession(session);
        Community community = communityManager.getCommunity(nome);
        user.addCommunity(nome);
        community.addUser(user);
    }

    /**
     * Lê a próxima mensagem de grupo.
     *
     * @param session ID da sessão
     * @return Mensagem lida
     */
    public String lerMensagem(String session){
        User user = sessionManager.getUserFromSession(session);
        return user.readGroupMessage();
    }

    /**
     * Envia uma mensagem para uma comunidade.
     *
     * @param session ID da sessão do remetente
     * @param communityName Nome da comunidade
     * @param message Conteúdo da mensagem
     */
    public void enviarMensagem(String session, String communityName, String message){
        User user = sessionManager.getUserFromSession(session);
        Community community = communityManager.getCommunity(communityName);
        user.addGroupMessage(message, community);
        for (String memberLogin : community.getMembers()){
            User member = userManager.getUserByLogin(memberLogin);
            member.receiveGroupMessage(message);
        }
    }

    /**
     * Adiciona um ídolo ao usuário.
     *
     * @param session ID da sessão do fã
     * @param loginIdol Login do ídolo
     */
    public void adicionarIdolo(String session, String loginIdol){
        User fan = sessionManager.getUserFromSession(session);
        User idol = userManager.getUserByLogin(loginIdol);
        if(fan.idols.contains(loginIdol)) throw new IdolAlreadyAdded();
        if(loginIdol.equals(fan.getLogin())) throw new FanOfItself();
        if(idol.getEnemies().contains(fan.getLogin())) throw new EnemyException(idol.getName());
        fan.addIdol(loginIdol);
        idol.addFan(fan.getLogin());
    }

    /**
     * Verifica se um usuário é fã de outro.
     *
     * @param loginUser Login do fã
     * @param loginIdol Login do ídolo
     * @return true se for fã, false caso contrário
     */
    public boolean ehFa(String loginUser, String loginIdol){
        User user = userManager.getUserByLogin(loginUser);
        if (user == null || user.idols == null) return false;
        return user.idols.contains(loginIdol);
    }

    /**
     * Retorna os fãs de um usuário.
     *
     * @param login Login do ídolo
     * @return Lista de logins dos fãs
     */
    public String getFas(String login) {
        User user = userManager.getUserByLogin(login);
        return "{" + String.join(",", user.fans) + "}";
    }

    /**
     * Verifica se outro usuário é paquera.
     *
     * @param session ID da sessão
     * @param paquera Login do possível paquera
     * @return true se for paquera, false caso contrário
     */
    public boolean ehPaquera(String session, String paquera){
        User user = sessionManager.getUserFromSession(session);
        return user.getCrushes().contains(paquera);
    }

    /**
     * Adiciona um paquera ao perfil do usuário.
     *
     * @param session ID da sessão
     * @param paqueraLogin Login do paquera
     */
    public void adicionarPaquera(String session, String paqueraLogin){
        User user = sessionManager.getUserFromSession(session);
        User paquera = userManager.getUserByLogin(paqueraLogin);
        user.addCrush(paqueraLogin);
        if(paquera.getEnemies().contains(user.getLogin())) throw new EnemyException(paquera.getName());
        if(paquera.getCrushes().contains(user.getLogin())){
            String recadoJackutDefault = "%s é seu paquera - Recado do Jackut.";
            String recadoJackutUser = String.format(recadoJackutDefault, paquera.getName());
            String recadoJackutPaquera = String.format(recadoJackutDefault, user.getName());
            Message systemMessage = new Message("jackut", user.getLogin(), recadoJackutUser);
            Message systemMessagePaquera = new Message("jackut", paquera.getLogin(), recadoJackutPaquera);
            user.addMessage(systemMessage);
            paquera.addMessage(systemMessagePaquera);
        }
    }

    /**
     * Retorna os paqueras do usuário.
     *
     * @param session ID da sessão
     * @return Lista de logins dos paqueras
     */
    public String getPaqueras(String session){
        User user = sessionManager.getUserFromSession(session);
        return "{" + String.join(",", user.getCrushes()) + "}";
    }

    /**
     * Adiciona um inimigo ao perfil do usuário.
     *
     * @param session ID da sessão
     * @param loginInimigo Login do inimigo
     */
    public void adicionarInimigo(String session, String loginInimigo){
        User user = sessionManager.getUserFromSession(session);
        if(userManager.getUserByLogin(loginInimigo) == null) throw new UserNotFoundException();
        user.addEnemy(loginInimigo);
    }

    /**
     * Remove o usuário do sistema, incluindo suas comunidades se for dono.
     *
     * @param session ID da sessão
     */
    public void removerUsuario(String session){
        User user = sessionManager.getUserFromSession(session);
        for (String communityName : user.getCommunities()){
            Community community = communityManager.getCommunity(communityName);
            if (community.getOwner().equals(user)){
                for(String memberLogin : community.getMembers()){
                    User member = userManager.getUserByLogin(memberLogin);
                    member.getCommunities().remove(community.getName());
                }
                communityManager.deleteCommunity(communityName);
            }
        }
        userManager.removeUser(user.getLogin());
    }

    /**
     * Salva o estado do sistema e finaliza.
     */
    public void encerrarSistema() {
        persistence.saveUsers(userManager);
        persistence.saveCommunities(communityManager);
    }
}
