package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exceção lançada quando um usuário tenta se adicionar como paquera de si mesmo.
 */
public class SelfLoveException extends RuntimeException {
    public SelfLoveException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}
