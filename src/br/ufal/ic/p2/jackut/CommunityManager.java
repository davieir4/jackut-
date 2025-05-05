package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.Exceptions.CommunityAlreadyExistsException;
import br.ufal.ic.p2.jackut.Exceptions.CommunityNotFoundException;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Gerencia as comunidades no sistema Jackut.
 */
public class CommunityManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, Community> communities;

    /**
     * Construtor que inicializa o gerenciador de comunidades.
     */
    public CommunityManager() {
        this.communities = new HashMap<>();
    }

    /**
     * Registra uma nova comunidade.
     *
     * @param owner       Usuário criador (dono) da comunidade
     * @param name        Nome da comunidade
     * @param description Descrição da comunidade
     * @throws CommunityAlreadyExistsException se uma comunidade com o mesmo nome já existir
     */
    public void registerCommunity(User owner, String name, String description) {
        if (communities.containsKey(name)) {
            throw new CommunityAlreadyExistsException();
        }
        Community newCommunity = new Community(owner, name, description);
        communities.put(name, newCommunity);
        newCommunity.addUser(owner);
        owner.addCommunity(name);
    }

    /**
     * Retorna uma comunidade pelo nome.
     *
     * @param name Nome da comunidade
     * @return Objeto Community correspondente
     * @throws CommunityNotFoundException se a comunidade não for encontrada
     */
    public Community getCommunity(String name) {
        if (!communities.containsKey(name)) {
            throw new CommunityNotFoundException();
        }
        return communities.get(name);
    }

    /**
     * Remove uma comunidade do sistema.
     *
     * @param communityName Nome da comunidade a ser removida
     */
    public void deleteCommunity(String communityName) {
        communities.remove(communityName);
    }
}
