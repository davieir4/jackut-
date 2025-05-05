package br.ufal.ic.p2.jackut;

import java.io.Serializable;

/**
 * Representa uma mensagem no sistema Jackut.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sender;
    private String receiver;
    private String content;

    /**
     * Construtor cria uma nova mensagem.
     *
     * @param sender   Login do remetente
     * @param receiver Login do destinatário
     * @param content    Conteúdo da mensagem
     */
    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    /**
     * Retorna o login do sender.
     *
     * @return Login do sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Retorna o login do destinatário.
     *
     * @return Login do destinatário
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Retorna o conteúdo da mensagem.
     *
     * @return Conteúdo da mensagem
     */
    public String getContent() {
        return content;
    }
}
