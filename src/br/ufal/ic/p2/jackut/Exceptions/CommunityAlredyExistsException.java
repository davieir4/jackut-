package br.ufal.ic.p2.jackut.Exceptions;

public class CommunityAlredyExistsException extends RuntimeException {
    public CommunityAlredyExistsException(){
        super("Comunidade com esse nome já existe.");
    }
}
