package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.UserAlreadyExistsException;
/**
 * Facade class that provides access to the Jackut system functionality.
 * This class serves as the entry point for the EasyAccept tests.
 */
public class Facade {
    Intern intern = new Intern();

    public void zerarSistema(){
        intern.zerarSistema();
    }
    public void criarUsuario(String login, String senha, String nome) throws UserAlreadyExistsException {
        intern.criarUsuario(login, senha, nome);
    }
    public String abrirSessao(String login, String senha){
       return intern.abrirSessao(login, senha);
    }
    public String getAtributoUsuario(String login, String atributo){
        return intern.getAtributoUsuario(login, atributo);
    }
    public void editarPerfil(String id, String atributo, String valor){
        intern.editarPerfil(id, atributo, valor);
    }
    public void adicionarAmigo(String id, String amigo){
        intern.adicionarAmigo(id, amigo);
    }
    public boolean ehAmigo(String login, String amigo){
        return intern.ehAmigo(login, amigo);
    }
    public String getAmigos(String login){
        return intern.getAmigos(login);
    }
    public void enviarRecado(String id, String destinatario, String recado){
        intern.enviarRecado(id, destinatario, recado);
    }
    public String lerRecado(String id){
        return intern.lerRecado(id);
    }
    public void criarComunidade(String id, String nome, String descricao){
        intern.criarComunidade(id, nome, descricao);
    }
    public String getDescricaoComunidade(String nome) {
        return intern.getDescricaoComunidade(nome);
    }
    public String getDonoComunidade(String nome){
        return intern.getDonoComunidade(nome);
    }
    public String getMembrosComunidade(String nome) {
        return intern.getMembrosComunidade(nome);
    }
    public String getComunidades(String login){
        return intern.getComunidades(login);
    }
    public void adicionarComunidade(String id, String nome){
        intern.adicionarComunidade(id, nome);
    }
    public String lerMensagem(String id){
        return intern.lerMensagem(id);
    }
    public void enviarMensagem(String id, String nomeComunidade, String mensagem){
        intern.enviarMensagem(id, nomeComunidade, mensagem);
    }
    public void adicionarIdolo(String id, String loginIdolo){
        intern.adicionarIdolo(id, loginIdolo);
    }
    public boolean ehFa(String loginUsuario, String loginIdolo){
        return intern.ehFa(loginUsuario, loginIdolo);
    }
    public String getFas(String login) {
        return intern.getFas(login);
    }
    public boolean ehPaquera(String id, String paquera){
        return intern.ehPaquera(id, paquera);
    }
    public void adicionarPaquera(String id, String paqueraLogin){
        intern.adicionarPaquera(id, paqueraLogin);
    }
    public String getPaqueras(String id){
        return intern.getPaqueras(id);
    }
    public void adicionarInimigo(String id, String loginInimigo){
        intern.adicionarInimigo(id, loginInimigo);
    }
    public void removerUsuario(String id){
        intern.removerUsuario(id);
    }
    public void encerrarSistema(){
        intern.encerrarSistema();
    }

}
