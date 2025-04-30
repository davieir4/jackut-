package br.ufal.ic.p2.jackut.Exceptions;

public class EnemyAlreadyExists extends RuntimeException {
    public EnemyAlreadyExists() {
        super("Usuário já está adicionado como inimigo.");
    }
}
