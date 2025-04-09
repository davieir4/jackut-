package br.ufal.ic.p2.jackut;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sessions{

    //Sessions apenas cria e armazena sessões de usuários

    private String inputLogin;

    //map das sessões ativas
    Map<String,String> activeSessions = new HashMap<>();

    public Sessions(String inputLogin){
        this.inputLogin = inputLogin;
        String sessionId = UUID.randomUUID().toString();
        activeSessions.put(this.inputLogin, sessionId);

    }
    public String getSessions(String login){
        return activeSessions.get(login); //retorna a session pelo login
    }




}