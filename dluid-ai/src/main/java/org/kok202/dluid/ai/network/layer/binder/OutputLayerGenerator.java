package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.layer.LayerFactory;

import java.util.List;

public class OutputLayerGenerator extends AbstractLayerGenerator {

    @Override
    public boolean support(Layer layer) {
        return layer.getType().isOutputLayerType();
    }

    @Override
    public void generate(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder) {
        graphBuilder.addLayer(layer.getId(), LayerFactory.create(layer), parseLayerIds(layerFroms));
        graphBuilder.setOutputs(layer.getId());
    }

}
