package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;

public class LSTMLayerBuilder extends AbstractRecurrentLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.LSTM;
    }

    @Override
    protected BaseLayer.Builder createBuilder(Layer layer) {
        return new LSTM.Builder();
    }
}