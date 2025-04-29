package br.ufal.ic.p2.jackut.Exceptions;

public class GroupMessageNotFound extends RuntimeException {
    public GroupMessageNotFound() {
        super("Não há mensagens.");
    }
}
