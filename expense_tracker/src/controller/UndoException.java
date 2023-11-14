package controller;

// This is a class/method for undoTransaction to throw exception
public class UndoException extends Exception {
    public UndoException(String message) {
        super(message);
    }
}