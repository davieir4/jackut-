package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.InvalidLoginException;
import br.ufal.ic.p2.jackut.Exceptions.UserAlredyExistsException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A classe Facade serve como a principal interface para o sistema Jackut.
 * Ela implementa todos os comandos definidos na linguagem de script EasyAccept.
 */
public class Facade {
    private Map<String, User> users;
    private Map<String, Session> activeSessions;
    private static final String DATA_FILE = "jackut_data.ser";

    /**
     * Construtor inicializa o repositório de usuários e o gerenciador de sessões.
     * Carrega dados salvos anteriormente, se existirem.
     */
    public Facade() {
        this.users = new HashMap<>();
        this.activeSessions = new HashMap<>();
        carregarDados();
    }

    /**
     * Limpa todos os dados do sistema.
     */
    public void zerarSistema() {
        users.clear();
        activeSessions.clear();
        salvarDados();
    }

    /**
     * Cria um novo usuário com o login, senha e nome fornecidos.
     *
     * @param login Login do usuário (identificador único)
     * @param senha Senha do usuário
     * @param nome Nome de exibição do usuário
     * @throws Exception Se um usuário com o login especificado já existir
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        if (login == null || login.isEmpty()) {
            throw new Exception("Login inválido");
        }
        if (senha == null || senha.isEmpty()) {
            throw new Exception("Senha inválida");
        }
        if (nome == null || nome.isEmpty()) {
            throw new Exception("Nome inválido");
        }
        if (users.containsKey(login)) {
            throw new UserAlredyExistsException("Já existe um usuário com este login");
        }

        User newUser = new User(login, senha, nome);
        users.put(login, newUser);
        salvarDados();
    }

    /**
     * Abre uma sessão para um usuário com o login e senha fornecidos.
     *
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @return Um ID de sessão para o usuário autenticado
     * @throws Exception Se a autenticação falhar
     */
    public String abrirSessao(String login, String senha) throws Exception {
        User user = users.get(login);
        if (user == null) {
            throw new InvalidLoginException("Usuário inexistente");
        }
        if (!user.authenticate(senha)) {
            throw new Exception("Login ou senha inválidos");
        }

        Session session = new Session(user);
        activeSessions.put(session.getId(), session);
        return session.getId();
    }

    /**
     * Obtém o valor de um atributo específico do perfil de um usuário.
     *
     * @param login Login do usuário
     * @param atributo O nome do atributo a ser recuperado
     * @return O valor do atributo ou null se não for encontrado
     * @throws Exception Se o usuário não existir
     */
    public String getAtributoUsuario(String login, String atributo) throws Exception {
        User user = users.get(login);
        if (user == null) {
            throw new InvalidLoginException("Usuário inexistente");
        }
        return user.getProfileAttribute(atributo);
    }

    /**
     * Modifica um atributo de perfil para o usuário na sessão especificada.
     *
     * @param id ID da sessão
     * @param atributo Nome do atributo a ser modificado
     * @param valor Novo valor para o atributo
     * @throws Exception Se a sessão for inválida ou ocorrerem outros erros
     */
    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        Session session = activeSessions.get(id);
        if (session == null) {
            throw new Exception("Sessão inválida");
        }

        User user = session.getUser();
        user.setProfileAttribute(atributo, valor);
        salvarDados();
    }

    /**
     * Adiciona um amigo ao usuário na sessão especificada.
     *
     * @param id ID da sessão
     * @param amigo Login do usuário a ser adicionado como amigo
     * @throws Exception Se a sessão for inválida ou o amigo não existir
     */
    public void adicionarAmigo(String id, String amigo) throws Exception {
        Session session = activeSessions.get(id);
        if (session == null) {
            throw new Exception("Sessão inválida");
        }

        User friend = users.get(amigo);
        if (friend == null) {
            throw new InvalidLoginException("Usuário inexistente");
        }

        User user = session.getUser();
        user.addFriend(friend);
        salvarDados();
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param login Login do primeiro usuário
     * @param amigo Login do segundo usuário
     * @return true se forem amigos, false caso contrário
     * @throws Exception Se algum dos usuários não existir
     */
    public boolean ehAmigo(String login, String amigo) throws Exception {
        User user = users.get(login);
        if (user == null) {
            throw new InvalidLoginException("Usuário inexistente");
        }

        User friend = users.get(amigo);
        if (friend == null) {
            throw new InvalidLoginException("Usuário inexistente");
        }

        return user.isFriendWith(friend);
    }

    /**
     * Obtém uma lista de amigos para o usuário especificado.
     *
     * @param login Login do usuário
     * @return Uma string contendo a lista de amigos
     * @throws Exception Se o usuário não existir
     */
    public String getAmigos(String login) throws Exception {
        User user = users.get(login);
        if (user == null) {
            throw new InvalidLoginException("Usuário inexistente");
        }

        return user.getFriendsListAsString();
    }

    /**
     * Envia uma mensagem do usuário na sessão para um destinatário especificado.
     *
     * @param id ID da sessão
     * @param destinatario Login do destinatário
     * @param mensagem Conteúdo da mensagem
     * @throws Exception Se a sessão for inválida ou o destinatário não existir
     */
    public void enviarRecado(String id, String destinatario, String mensagem) throws Exception {
        Session session = activeSessions.get(id);
        if (session == null) {
            throw new Exception("Sessão inválida");
        }

        User recipient = users.get(destinatario);
        if (recipient == null) {
            throw new InvalidLoginException("Usuário inexistente");
        }

        User sender = session.getUser();
        Message message = new Message(sender, mensagem);
        recipient.receiveMessage(message);
        salvarDados();
    }

    /**
     * Lê a primeira mensagem na fila para o usuário na sessão especificada.
     *
     * @param id ID da sessão
     * @return O conteúdo da mensagem ou uma notificação se não houver mensagens disponíveis
     * @throws Exception Se a sessão for inválida
     */
    public String lerRecado(String id) throws Exception {
        Session session = activeSessions.get(id);
        if (session == null) {
            throw new Exception("Sessão inválida");
        }

        User user = session.getUser();
        String mensagem = user.readNextMessage();
        salvarDados();
        return mensagem;
    }

    /**
     * Salva todos os dados e encerra o sistema.
     */
    public void encerrarSistema() {
        salvarDados();
    }

    /**
     * Salva os dados do sistema em um arquivo.
     */
    private void salvarDados() {
        try {
            FileOutputStream fileOut = new FileOutputStream(DATA_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Carrega os dados do sistema a partir de um arquivo.
     */
    @SuppressWarnings("unchecked")
    private void carregarDados() {
        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                users = (Map<String, User>) in.readObject();
                in.close();
                fileIn.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            users = new HashMap<>(); // Se houver erro, inicializa com um mapa vazio
        }
    }
}