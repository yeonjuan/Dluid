package org.kok202.dluid.application.content.design.material.block;

import org.kok202.dluid.ai.entity.enumerator.LayerType;

public class MaterialLSTMController extends AbstractMaterialController {
    @Override
    protected void initialize() throws Exception{
        this.setLayerType(LayerType.LSTM);
        super.setStyleByBlockType(layerType);
    }
}
