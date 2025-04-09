package br.ufal.ic.p2.jackut;
import br.ufal.ic.p2.jackut.Exceptions.UserNotFoundException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Intern {
    private User user;  // Armazena o usuário criado
    private Validation validation; // Instância única de Validation

    // Controle de usuários
    private Map<String, Map<String, String>> users = new HashMap<>();

    public Intern() {
        this.validation = new Validation();  // Inicializa a validação apenas uma vez
    }

    public void criarUsuario(String login, String senha, String nome) {
        this.user = new User();
        try {
            if (users.containsKey(login)) {
                throw new Error("Conta com esse nome já existe.");
            }
            if (login == null || login.isBlank()) throw new Error("Login inválido.");
            if (senha == null || senha.isBlank()) throw new Error("Senha inválida.");

            this.user.newUser(login, senha, nome);

            // Adiciona o usuário no map de validações, usando a instância de Intern
            validation.newValidation(login, senha);

            // Criar um mapa de atributos e armazenar o usuário
            Map<String, String> attributesMap = new HashMap<>();
            attributesMap.put("nome", nome);

            users.put(login, attributesMap);
        } catch (Exception e) {
            //
        }
        salvarUsuario();
    }
    public String getUserAttribute(String login, String attribute) {
        // Verifica se o login existe antes de tentar acessar os atributos
        if (users.containsKey(login)) {
            return users.get(login).getOrDefault(attribute, "Atributo não encontrado");
        }
        throw new UserNotFoundException();
    }

    public String openSession(String login, String senha) throws Exception {
        if (!this.validation.isValid(login, senha)) {
            throw new Exception("Login ou senha inválidos.");
        }
        Sessions newSession = new Sessions(login);
        System.out.println("Sessão Aberta com Sucesso!");
        return newSession.getSessions(login);
    }



    private void salvarUsuario() {
        if (user != null) {
            try (FileOutputStream fileOut = new FileOutputStream("UserInfo.ser");
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(user);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar usuário.", e);
            }
        }
    }
}
