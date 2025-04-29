package br.ufal.ic.p2.jackut;

public class SelfLoveException extends RuntimeException {
    public SelfLoveException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}
