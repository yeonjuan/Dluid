package org.kok202.dluid.application.common;

import javafx.fxml.FXML;
import javafx.scene.Node;
import lombok.Getter;

public abstract class AbstractController {
    @FXML
    @Getter
    protected Node itself;

    @FXML
    protected abstract void initialize() throws Exception;
}
