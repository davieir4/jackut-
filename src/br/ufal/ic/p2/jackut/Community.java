package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.AlreadyIsMemberException;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Represents a Community in the Jackut system.
 */

public class Community implements Serializable {
    private static final long serialVersionUID = 1L;
    private User owner;
    private LinkedHashSet<String> members;
    private String name;
    private String description;

    public Community(User owner, String name, String description){
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.members = new LinkedHashSet<>();
    }
    public User getOwner(){
        return this.owner;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public LinkedHashSet<String> getMembers(){
        return this.members;
    }
    public void addUser (User user) {
        if(this.members.contains(user.getLogin())){
            throw new AlreadyIsMemberException();
        }
        this.members.add(user.getLogin());
    }
}
