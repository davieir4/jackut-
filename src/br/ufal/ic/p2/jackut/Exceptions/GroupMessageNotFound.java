package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exceção lançada quando uma mensagem de grupo não é encontrada.
 */
public class GroupMessageNotFound extends RuntimeException {
    public GroupMessageNotFound() {
        super("Mensagem não encontrada no grupo.");
    }
}
