package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.UserAlreadyExistsException;

/**
 * Classe Facade que fornece acesso à funcionalidade do sistema Jackut.
 * Esta classe serve como ponto de entrada para os testes do EasyAccept.
 *
 * Padrão Facade: fornece uma interface simplificada para o sistema complexo.
 */
public class Facade {
    // Instância da classe Intern que contém a lógica real do sistema
    Intern intern = new Intern();

    /**
     * Reinicia o sistema, limpando todos os dados.
     */
    public void zerarSistema(){
        intern.zerarSistema();
    }

    /**
     * Cria um novo usuário no sistema.
     *
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @param nome Nome do usuário
     * @throws UserAlreadyExistsException se o login já estiver em uso
     */
    public void criarUsuario(String login, String senha, String nome) throws UserAlreadyExistsException {
        intern.criarUsuario(login, senha, nome);
    }

    /**
     * Abre uma sessão para o usuário.
     *
     * @param login Login do usuário
     * @param senha Senha do usuário
     * @return ID da sessão criada
     */
    public String abrirSessao(String login, String senha){
        return intern.abrirSessao(login, senha);
    }

    /**
     * Obtém um atributo do perfil do usuário.
     *
     * @param login Login do usuário
     * @param atributo Nome do atributo
     * @return Valor do atributo
     */
    public String getAtributoUsuario(String login, String atributo){
        return intern.getAtributoUsuario(login, atributo);
    }

    /**
     * Edita um atributo do perfil do usuário.
     *
     * @param id ID da sessão
     * @param atributo Nome do atributo
     * @param valor Novo valor do atributo
     */
    public void editarPerfil(String id, String atributo, String valor){
        intern.editarPerfil(id, atributo, valor);
    }

    /**
     * Adiciona um amigo ao usuário.
     *
     * @param id ID da sessão
     * @param amigo Login do amigo a ser adicionado
     */
    public void adicionarAmigo(String id, String amigo){
        intern.adicionarAmigo(id, amigo);
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param login Login do primeiro usuário
     * @param amigo Login do segundo usuário
     * @return true se forem amigos, false caso contrário
     */
    public boolean ehAmigo(String login, String amigo){
        return intern.ehAmigo(login, amigo);
    }

    /**
     * Obtém a lista de amigos de um usuário.
     *
     * @param login Login do usuário
     * @return String formatada com a lista de amigos
     */
    public String getAmigos(String login){
        return intern.getAmigos(login);
    }

    /**
     * Envia um recado para outro usuário.
     *
     * @param id ID da sessão do remetente
     * @param destinatario Login do destinatário
     * @param recado Conteúdo do recado
     */
    public void enviarRecado(String id, String destinatario, String recado){
        intern.enviarRecado(id, destinatario, recado);
    }

    /**
     * Lê o próximo recado na caixa de entrada do usuário.
     *
     * @param id ID da sessão
     * @return Conteúdo do recado
     */
    public String lerRecado(String id){
        return intern.lerRecado(id);
    }

    /**
     * Cria uma nova comunidade.
     *
     * @param id ID da sessão do criador
     * @param nome Nome da comunidade
     * @param descricao Descrição da comunidade
     */
    public void criarComunidade(String id, String nome, String descricao){
        intern.criarComunidade(id, nome, descricao);
    }

    /**
     * Obtém a descrição de uma comunidade.
     *
     * @param nome Nome da comunidade
     * @return Descrição da comunidade
     */
    public String getDescricaoComunidade(String nome) {
        return intern.getDescricaoComunidade(nome);
    }

    /**
     * Obtém o dono de uma comunidade.
     *
     * @param nome Nome da comunidade
     * @return Login do dono da comunidade
     */
    public String getDonoComunidade(String nome){
        return intern.getDonoComunidade(nome);
    }

    /**
     * Obtém os membros de uma comunidade.
     *
     * @param nome Nome da comunidade
     * @return String formatada com a lista de membros
     */
    public String getMembrosComunidade(String nome) {
        return intern.getMembrosComunidade(nome);
    }

    /**
     * Obtém as comunidades de um usuário.
     *
     * @param login Login do usuário
     * @return String formatada com a lista de comunidades
     */
    public String getComunidades(String login){
        return intern.getComunidades(login);
    }

    /**
     * Adiciona um usuário a uma comunidade.
     *
     * @param id ID da sessão do usuário
     * @param nome Nome da comunidade
     */
    public void adicionarComunidade(String id, String nome){
        intern.adicionarComunidade(id, nome);
    }

    /**
     * Lê a próxima mensagem de grupo do usuário.
     *
     * @param id ID da sessão
     * @return Conteúdo da mensagem
     */
    public String lerMensagem(String id){
        return intern.lerMensagem(id);
    }

    /**
     * Envia uma mensagem para uma comunidade.
     *
     * @param id ID da sessão do remetente
     * @param nomeComunidade Nome da comunidade
     * @param mensagem Conteúdo da mensagem
     */
    public void enviarMensagem(String id, String nomeComunidade, String mensagem){
        intern.enviarMensagem(id, nomeComunidade, mensagem);
    }

    /**
     * Adiciona um ídolo ao usuário.
     *
     * @param id ID da sessão
     * @param loginIdolo Login do ídolo
     */
    public void adicionarIdolo(String id, String loginIdolo){
        intern.adicionarIdolo(id, loginIdolo);
    }

    /**
     * Verifica se um usuário é fã de outro.
     *
     * @param loginUsuario Login do possível fã
     * @param loginIdolo Login do possível ídolo
     * @return true se for fã, false caso contrário
     */
    public boolean ehFa(String loginUsuario, String loginIdolo){
        return intern.ehFa(loginUsuario, loginIdolo);
    }

    /**
     * Obtém os fãs de um usuário.
     *
     * @param login Login do usuário
     * @return String formatada com a lista de fãs
     */
    public String getFas(String login) {
        return intern.getFas(login);
    }

    /**
     * Verifica se um usuário tem uma paquera por outro.
     *
     * @param id ID da sessão
     * @param paquera Login da possível paquera
     * @return true se for paquera, false caso contrário
     */
    public boolean ehPaquera(String id, String paquera){
        return intern.ehPaquera(id, paquera);
    }

    /**
     * Adiciona uma paquera ao usuário.
     *
     * @param id ID da sessão
     * @param paqueraLogin Login da paquera
     */
    public void adicionarPaquera(String id, String paqueraLogin){
        intern.adicionarPaquera(id, paqueraLogin);
    }

    /**
     * Obtém as paqueras do usuário.
     *
     * @param id ID da sessão
     * @return String formatada com a lista de paqueras
     */
    public String getPaqueras(String id){
        return intern.getPaqueras(id);
    }

    /**
     * Adiciona um inimigo ao usuário.
     *
     * @param id ID da sessão
     * @param loginInimigo Login do inimigo
     */
    public void adicionarInimigo(String id, String loginInimigo){
        intern.adicionarInimigo(id, loginInimigo);
    }

    /**
     * Remove um usuário do sistema.
     *
     * @param id ID da sessão do usuário a ser removido
     */
    public void removerUsuario(String id){
        intern.removerUsuario(id);
    }

    /**
     * Encerra o sistema, salvando os dados.
     */
    public void encerrarSistema(){
        intern.encerrarSistema();
    }
}