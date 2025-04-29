package br.ufal.ic.p2.jackut.Exceptions;

public class MessageNotFound extends RuntimeException {
    public MessageNotFound() {
        super("Não há recados.");
    }
}
