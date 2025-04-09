package br.ufal.ic.p2.jackut;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String login, senha, nome;

    //usuários agora é apenas um modelo de dados

    public void newUser(String login, String senha, String nome) {

        this.login = login;
        this.senha = senha;
        this.nome = nome;

    }


}
