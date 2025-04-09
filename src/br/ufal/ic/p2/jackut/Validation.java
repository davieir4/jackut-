package br.ufal.ic.p2.jackut;
import java.util.HashMap;
import java.util.Map;

public class Validation{

    //armazena as credenciais dos usuários
    private String login;
    private String senha;

    //mapeamento das credenciais dos usuários
    Map<String,String> validationMap = new HashMap<>();

    public void newValidation(String login, String senha){
        this.login = login;
        this.senha = senha;

        //registra as credenciais do perfil
        validationMap.put(login, senha);
    }

    public boolean isValid (String inputLogin, String inputSenha){
            //verifica se a senha está correta

        return validationMap.getOrDefault(inputLogin, "Usuário não existe.").equals(inputSenha);
    }

    }



