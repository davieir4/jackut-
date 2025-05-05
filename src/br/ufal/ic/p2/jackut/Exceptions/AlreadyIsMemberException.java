package br.ufal.ic.p2.jackut.Exceptions;
/**
 * Exceção lançada quando um usuário tenta cadastrar um membro já existente numa comunidade.
 */
public class AlreadyIsMemberException extends RuntimeException {
    public AlreadyIsMemberException() {
        super("Usuario já faz parte dessa comunidade.");
    }
}
