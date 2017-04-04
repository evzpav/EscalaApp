package br.com.evandro.exceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class NotFoundException extends Exception {

    private List<String> errors;
    private String error;

    public NotFoundException() {
        this.errors = new ArrayList<>();
    }

    public NotFoundException(List<String> errors) {
        this.errors = errors;
    }

    public NotFoundException(String error) {
        this.error = error;
    }

    public void add(String msg) {
        this.errors.add(msg);
    }

    public void add(Collection<String> msgs) {
        errors.addAll(msgs);
    }

    public List<String> getErrors() {
        return this.errors;
    }


}
