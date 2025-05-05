package br.ufal.ic.p2.jackut.Exceptions;

/**
 * Exceção lançada quando uma tentativa de criar um usuário falha porque o usuário já existe.
 * Esta exceção é verificada e deve ser tratada pelo código chamador.
 */
public class UserAlreadyExistsException extends Exception{
    /**
     * Constrói uma nova exceção de usuário já existente com a mensagem especificada.
     *
     * @param message A mensagem detalhando o motivo da exceção
     */
    public UserAlreadyExistsException(String message){
        super(message);
    }
}