package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exceção lançada quando o usuário já foi adicionado como ídolo anteriormente.
 */
public class IdolAlreadyAdded extends RuntimeException {
    public IdolAlreadyAdded() {
        super("Usuário já está adicionado como ídolo.");
    }
}
