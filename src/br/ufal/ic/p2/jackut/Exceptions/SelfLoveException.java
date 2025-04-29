package br.ufal.ic.p2.jackut.Exceptions;

public class SelfLoveException extends RuntimeException {
    public SelfLoveException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}
