package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exceção lançada quando um usuário tenta se adicionar como inimigo de si mesmo.
 */
public class SelfHateException extends RuntimeException {
    public SelfHateException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}
