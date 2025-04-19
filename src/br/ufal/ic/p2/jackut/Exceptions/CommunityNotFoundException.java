package br.ufal.ic.p2.jackut.Exceptions;

public class CommunityNotFoundException extends RuntimeException {
    public CommunityNotFoundException() {
        super("Comunidade não existe.");
    }
}

