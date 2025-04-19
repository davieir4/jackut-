package br.ufal.ic.p2.jackut.Exceptions;

public class AlreadyIsMemberException extends RuntimeException {
    public AlreadyIsMemberException() {
        super("Usuario já faz parte dessa comunidade.");
    }
}
