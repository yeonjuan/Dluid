package org.kok202.dluid.domain.exception;

public class ModelIsChangedException extends RuntimeException {
    public ModelIsChangedException(){
        super("Model is changed. You need to initialize model again.");
    }
}
