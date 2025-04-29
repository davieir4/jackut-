package br.ufal.ic.p2.jackut.Exceptions;

public class EnemyAlredyExists extends RuntimeException {
    public EnemyAlredyExists() {
        super("Usuário já está adicionado como inimigo.");
    }
}
