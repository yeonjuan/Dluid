package org.kok202.dluid.domain.exception;

public class SwitchConnectionImpossibleException extends AbstractInvalidLayerException {
    public SwitchConnectionImpossibleException(long layerId){
        super(layerId, "Upward connection is impossible on merge and switch layer.");
    }
}
