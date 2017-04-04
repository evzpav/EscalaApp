package br.com.evandro.exceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class BusinessException extends Exception {
    private List<String> errors;
    private String error;

    public BusinessException() {
        this.errors = new ArrayList<>();
    }

    public BusinessException(List<String> errors) {
        this.errors = errors;
    }

    public BusinessException(String error) {
        this.errors = new ArrayList<>();
        errors.add(error);
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
