package br.ufal.ic.p2.jackut.Exceptions;
/**
 * Exceção lançada quando um usuário tenta acessar uma comunidade não cadastrada.
 */
public class CommunityNotFoundException extends RuntimeException {
    public CommunityNotFoundException() {
        super("Comunidade não existe.");
    }
}

