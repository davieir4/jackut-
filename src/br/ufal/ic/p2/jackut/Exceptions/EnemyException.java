package br.ufal.ic.p2.jackut.Exceptions;

public class EnemyException extends RuntimeException {
    public EnemyException(String login) {
        super(String.format("Função inválida: %s é seu inimigo.", login));
    }
}
