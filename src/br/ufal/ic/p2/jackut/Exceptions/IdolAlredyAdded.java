package br.ufal.ic.p2.jackut.Exceptions;

public class IdolAlredyAdded extends RuntimeException {
    public IdolAlredyAdded() {
        super("Usuário já está adicionado como ídolo.");
    }
}
