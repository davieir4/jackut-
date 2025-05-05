package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exceção lançada quando um usuário não está cadastrado no sistema
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
    super("Usuário não cadastrado.");
    }
}
