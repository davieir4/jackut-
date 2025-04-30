package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.CommunityAlreadyExistsException;
import br.ufal.ic.p2.jackut.Exceptions.CommunityNotFoundException;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CommunityManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Map<String, Community> communities;

    public CommunityManager(){
        this.communities = new HashMap<>();
    }
    public void registerCommunity(User owner, String name, String description){
        if(communities.containsKey(name)){
            throw new CommunityAlreadyExistsException();
        }
        Community newCommunity = new Community(owner, name, description);
        communities.put(name, newCommunity);
        newCommunity.addUser(owner);
        owner.addCommunity(name);
    }
    public Community getCommunity(String name){
        if (!communities.containsKey(name)){
            throw new CommunityNotFoundException();
        }
        return communities.get(name);
    }
    public void deleteCommunity (String communityName) {
        communities.remove(communityName);
    }
}
