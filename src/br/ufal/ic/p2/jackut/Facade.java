package br.ufal.ic.p2.jackut;

import easyaccept.EasyAccept;

public class Facade {
    private Intern intern;

    public Facade() {
        this.intern = new Intern();
    }

    public void zerarSistema() {
        // Lógica para resetar o sistema, se necessário
    }
    public void encerrarSistema(){
        //encerra o sistema
    }
    public void criarUsuario(String login, String senha, String nome) {
        this.intern.criarUsuario(login, senha, nome);
    }

    public String abrirSessao(String login, String senha) throws Exception {
        return this.intern.openSession(login, senha);
    }

    public String getAtributoUsuario(String login, String atributo) {
        return this.intern.getUserAttribute(login, atributo);
    }
}
