package br.ufal.ic.p2.jackut.Exceptions;
/**
 * Exceção lançada quando um usuário adiciona um inimigo existente.
 */
public class EnemyAlreadyExists extends RuntimeException {
    public EnemyAlreadyExists() {
        super("Usuário já está adicionado como inimigo.");
    }
}
