package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exceção lançada quando uma mensagem pessoal não é encontrada.
 */
public class MessageNotFound extends RuntimeException {
    public MessageNotFound() {
        super("Não há recados.");
    }
}
