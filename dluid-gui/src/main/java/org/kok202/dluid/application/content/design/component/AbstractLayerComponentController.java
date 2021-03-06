package org.kok202.dluid.application.content.design.component;

import javafx.beans.InvalidationListener;
import javafx.scene.control.TextField;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.Layer;

public abstract class AbstractLayerComponentController extends AbstractComponentController {
    private InvalidationListener changeListener = (changeListener) -> textFieldChangedHandler();

    protected Layer layer;

    protected AbstractLayerComponentController(Layer layer) {
        this.layer = layer;
    }

    protected void textFieldChangedHandler(){}

    void detachTextChangedListener(TextField... textFields){
        for(TextField textField : textFields)
            textField.textProperty().removeListener(changeListener);
    }

    void attachTextChangedListener(TextField... textFields){
        for(TextField textField : textFields)
            textField.textProperty().addListener(changeListener);
    }

    protected void notifyLayerDataChanged(){
        // Do we need to change the height value of the block node according to the channel?
        // For reshape layers (when output dimension is 2D), we don't know that the y-values of output will be used as the depth of the block or as the height.
        // Because y value can be used as channel or non channel.
        CanvasFacade.notifyLayerDataChanged(layer.getId());
    }
}
