package br.ufal.ic.p2.jackut.Exceptions;

public class IdolAlreadyAdded extends RuntimeException {
    public IdolAlreadyAdded() {
        super("Usuário já está adicionado como ídolo.");
    }
}
