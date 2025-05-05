package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exceção lançada quando uma tentativa de login é inválida.
 * Esta exceção é lançada quando um usuário tenta realizar login com credenciais inválidas.
 */
public class InvalidLoginException extends RuntimeException {
    /**
     * Constrói uma nova exceção de login inválido com a mensagem especificada.
     *
     * @param message A mensagem detalhando o motivo da exceção
     */
    public InvalidLoginException(String message) {
        super(message);
    }
}