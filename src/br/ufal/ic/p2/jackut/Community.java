package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.AlreadyIsMemberException;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Representa uma comunidade no sistema Jackut.
 *
 * Cada comunidade possui um dono, nome, descrição,
 * membros e uma fila de mensagens do grupo.
 */
public class Community implements Serializable {
    private static final long serialVersionUID = 1L;
    private User owner;
    private LinkedHashSet<String> members;
    private String name;
    private String description;
    private Queue<String> groupMessages = new LinkedList<>();

    /**
     * Construtor da comunidade.
     *
     * @param owner Usuário que criou e é dono da comunidade
     * @param name Nome da comunidade
     * @param description Descrição da comunidade
     */
    public Community(User owner, String name, String description){
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.members = new LinkedHashSet<>();
    }

    /**
     * Retorna o dono da comunidade.
     *
     * @return Usuário dono da comunidade
     */
    public User getOwner(){
        return this.owner;
    }

    /**
     * Retorna o nome da comunidade.
     *
     * @return Nome da comunidade
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retorna a descrição da comunidade.
     *
     * @return Descrição da comunidade
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Retorna o conjunto de logins dos membros da comunidade.
     *
     * @return Conjunto com os logins dos membros
     */
    public LinkedHashSet<String> getMembers(){
        return this.members;
    }

    /**
     * Adiciona um usuário à comunidade.
     *
     * @param user Usuário a ser adicionado
     * @throws AlreadyIsMemberException se o usuário já for membro da comunidade
     */
    public void addUser(User user) {
        if(this.members.contains(user.getLogin())){
            throw new AlreadyIsMemberException();
        }
        this.members.add(user.getLogin());
    }

    /**
     * Adiciona uma mensagem à fila de mensagens da comunidade.
     *
     * @param message Mensagem a ser adicionada
     */
    public void addMessage(String message){
        groupMessages.add(message);
    }
}
