package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exceção lançada quando um usuário tenta se adicionar como fã de si mesmo.
 */
public class FanOfItself extends RuntimeException {
    public FanOfItself() {
        super("Usuário não pode ser fã de si mesmo.");
    }
}
