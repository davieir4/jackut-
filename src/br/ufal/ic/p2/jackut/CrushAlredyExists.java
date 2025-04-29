package br.ufal.ic.p2.jackut;

public class CrushAlredyExists extends RuntimeException {
    public CrushAlredyExists() {
        super("Usuário já está adicionado como paquera.");
    }
}
