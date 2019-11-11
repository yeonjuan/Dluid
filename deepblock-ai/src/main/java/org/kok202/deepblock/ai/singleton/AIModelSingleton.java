package org.kok202.deepblock.ai.singleton;

import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.optimize.api.TrainingListener;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.helper.DataSetConverter;
import org.kok202.deepblock.ai.network.NetworkBuilder;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.kok202.deepblock.domain.structure.GraphManager;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public class AIModelSingleton {

    private static class GlobalDataHolder{
        private static final AIModelSingleton instance = new AIModelSingleton();
    }

    public static AIModelSingleton getInstance(){
        return AIModelSingleton.GlobalDataHolder.instance;
    }

    private AIModelSingleton(){
    }

    private int prevLayerGraphHash;
    private ComputationGraph model;

    public void initialize(GraphManager<Layer> layerGraphManager) {
        int currLayerGraphHash = layerGraphManager.getHashCode();
        if(prevLayerGraphHash == currLayerGraphHash)
            return;
        prevLayerGraphHash = currLayerGraphHash;

        AIPropertiesSingleton
                .getInstance()
                .getModelLayersProperty()
                .setLayerGraphManager(layerGraphManager);
        model = NetworkBuilder.build();
        model.init();
    }

    public void setTrainListener(TrainingListener trainingListener){
        model.getListeners().clear();
        model.addListeners(trainingListener);
    }

    public void train(NumericRecordSet featureDataSet, NumericRecordSet resultDataSet){
        DataSetConverter trainDataSetConverter = new DataSetConverter(featureDataSet, resultDataSet);
        train(trainDataSetConverter.toDataSet());
    }

    public void train(DataSet dataSet){
        DataSetIterator dataSetIterator = new ListDataSetIterator<>(dataSet.asList());
        train(dataSetIterator);
    }

    public void train(DataSetIterator dataSetIterator){
        // If using ComputationGraph with dataSetIterator, it can work only if input layer is unique.
        model.fit(dataSetIterator, AIPropertiesSingleton.getInstance().getTrainProperty().getEpoch());
    }

    public Evaluation test(DataSet dataSet){
        INDArray output = model.outputSingle(dataSet.getFeatures());
        Evaluation evaluation = new Evaluation();
        evaluation.eval(dataSet.getLabels(), output);
        return evaluation;
    }

    public Evaluation test(DataSetIterator dataSetIterator){
        return model.evaluate(dataSetIterator);
    }
}