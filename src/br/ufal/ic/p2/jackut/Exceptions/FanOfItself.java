package br.ufal.ic.p2.jackut.Exceptions;

public class FanOfItself extends RuntimeException {
    public FanOfItself() {
        super("Usuário não pode ser fã de si mesmo.");
    }
}
