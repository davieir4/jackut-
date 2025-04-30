package br.ufal.ic.p2.jackut.Exceptions;

public class CrushAlreadyExists extends RuntimeException {
    public CrushAlreadyExists() {
        super("Usuário já está adicionado como paquera.");
    }
}
