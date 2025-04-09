package br.ufal.ic.p2.jackut;

import java.io.Serializable;

/**
 * Represents a message in the Jackut system.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sender;
    private String recipient;
    private String content;

    /**
     * Constructor creates a new message.
     *
     * @param sender Sender's login
     * @param recipient Recipient's login
     * @param content Message content
     */
    public Message(String sender, String recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    /**
     * Gets the sender's login.
     *
     * @return Sender's login
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the recipient's login.
     *
     * @return Recipient's login
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Gets the message content.
     *
     * @return Message content
     */
    public String getContent() {
        return content;
    }
}