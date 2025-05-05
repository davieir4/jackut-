package br.ufal.ic.p2.jackut.Exceptions;
/**
 * Exceção lançada quando um usuário tenta cadastrar uma comunidade existente.
 */
public class CommunityAlreadyExistsException extends RuntimeException {
    public CommunityAlreadyExistsException(){
        super("Comunidade com esse nome já existe.");
    }
}
