package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exceção lançada quando um usuário tenta adicionar um inimigo em seus relacionamentos ou tenta enviar recados.
 */
public class EnemyException extends RuntimeException {
    public EnemyException(String login) {
        super(String.format("Função inválida: %s é seu inimigo.", login));
    }
}
