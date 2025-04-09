package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Representa uma mensagem enviada entre usuários no sistema Jackut.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private User sender;
    private String content;
    private Date timestamp;

    /**
     * Cria uma nova mensagem com o remetente e conteúdo especificados.
     *
     * @param sender O usuário que envia a mensagem
     * @param content O conteúdo da mensagem
     */
    public Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = new Date();
    }

    /**
     * Obtém o remetente desta mensagem.
     *
     * @return O usuário que enviou a mensagem
     */
    public User getSender() {
        return sender;
    }

    /**
     * Obtém o conteúdo da mensagem.
     *
     * @return O texto da mensagem
     */
    public String getContent() {
        return content;
    }

    /**
     * Obtém o conteúdo formatado da mensagem, incluindo informações do remetente.
     *
     * @return Uma string formatada com o remetente e o conteúdo da mensagem
     */
    public String getFormattedContent() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sender.getLogin() + " - " + dateFormat.format(timestamp) + "\n" + content;
    }
}