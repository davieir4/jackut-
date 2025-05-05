package br.ufal.ic.p2.jackut.Exceptions;
/**
 * Exceção lançada quando um usuário tenta adicionar um paquera existente.
 */
public class CrushAlreadyExists extends RuntimeException {
    public CrushAlreadyExists() {
        super("Usuário já está adicionado como paquera.");
    }
}
