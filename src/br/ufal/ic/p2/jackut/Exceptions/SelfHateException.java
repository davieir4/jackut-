package br.ufal.ic.p2.jackut.Exceptions;

public class SelfHateException extends RuntimeException {
    public SelfHateException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}
