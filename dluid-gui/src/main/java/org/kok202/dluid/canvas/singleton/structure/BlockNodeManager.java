package org.kok202.dluid.canvas.singleton.structure;

import javafx.geometry.Point3D;
import lombok.Data;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.LayerProperties;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.CanNotFindGraphNodeException;
import org.kok202.dluid.domain.structure.GraphManager;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.Collection;
import java.util.List;

@Data
public class BlockNodeManager extends GraphManager<BlockNode>{

    public void removeGraphNode(long layerId) {
        removeGraphNode(
                blockNodeObj -> {
                    BlockNode blockNode = (BlockNode) blockNodeObj;
                    return blockNode.getBlockInfo().getLayer().getId() == layerId;
                },
                graphNode -> {
                    GraphNode<BlockNode> blockGraphNode = (GraphNode<BlockNode>) graphNode;
                    blockGraphNode.getData().deleteHexahedrons();
                });
    }

    public GraphNode<BlockNode> findTestInputGraphNode(){
        for(BlockNode blockNode : getDataNodes()) {
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if (layerType == LayerType.INPUT_LAYER || layerType == LayerType.TEST_INPUT_LAYER)
                return findGraphNodeByData(blockNode);
        }
        throw new CanNotFindGraphNodeException("Test input block node");
    }

    public GraphNode<BlockNode> findTrainInputGraphNode(){
        Collection<BlockNode> blockNodes = getDataNodes();
        for(BlockNode blockNode : blockNodes) {
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if(layerType == LayerType.INPUT_LAYER || layerType == LayerType.TRAIN_INPUT_LAYER)
                return findGraphNodeByData(blockNode);
        }
        throw new CanNotFindGraphNodeException("Train input block node");
    }

    public GraphNode<BlockNode> findGraphNodeByLayerId(long layerId) {
        return findGraphNode(blockNodeObj -> {
            BlockNode blockNode = (BlockNode) blockNodeObj;
            return blockNode.getBlockInfo().getLayer().getId() == layerId;
        });
    }

    public void notifyLayerDataChanged(long layerId){
        GraphNode<BlockNode> graphNode = findGraphNodeByLayerId(layerId);
        BlockNode blockNode = graphNode.getData();
        Layer layer = blockNode.getBlockInfo().getLayer();
        LayerProperties layerProperties = layer.getProperties();

        if(layer.getType() == LayerType.MERGE_LAYER){
        }

        // Reshape parent nodes
        graphNode.getIncomingNodes().forEach(incomingNode -> {
            LayerType parentLayerType = incomingNode.getData().getBlockInfo().getLayer().getType();
            if(parentLayerType.isInputLayerType()) {
                incomingNode.getData().getBlockInfo().getLayer().getProperties().setInputSize(layerProperties.getInputSize()[0], layerProperties.getInputSize()[1]);
                incomingNode.getData().getBlockInfo().getLayer().getProperties().setOutputSize(layerProperties.getInputSize()[0], layerProperties.getInputSize()[1]);
                incomingNode.getData().reshapeBlockModel();
            }
        });

        // Reshape child nodes
        graphNode.getOutgoingNodes().forEach(outgoingNode -> {
            LayerType childLayerType = outgoingNode.getData().getBlockInfo().getLayer().getType();
            // Dimension of pipe layer is decided by source layer
            if(childLayerType == LayerType.PIPE_LAYER) {
                outgoingNode.getData().getBlockInfo().getLayer().getProperties().setInputSize(layerProperties.getOutputSize()[0], layerProperties.getOutputSize()[1]);
                outgoingNode.getData().getBlockInfo().getLayer().getProperties().setOutputSize(layerProperties.getOutputSize()[0], layerProperties.getOutputSize()[1]);
                outgoingNode.getData().reshapeBlockModel();
            }
        });

        // Reshape me
        blockNode.reshapeBlockModel();
    }

    public void alignBlockNode(){
        getGraphNodes().forEach(blockNodeGraphNode -> {
            List<GraphNode<BlockNode>> incomingNodes = blockNodeGraphNode.getIncomingNodes();
            List<GraphNode<BlockNode>> outgoingNodes = blockNodeGraphNode.getOutgoingNodes();
            if(incomingNodes.isEmpty() && outgoingNodes.isEmpty()){
                // Independent node
                blockNodeGraphNode.getData().setHeight(CanvasConstant.NODE_DEFAULT_HEIGHT);
                blockNodeGraphNode.getData().getBlockInfo().getLayer().setExtra(null);
                blockNodeGraphNode.getData().reshapeBlockModel();
            }
            else if(incomingNodes.isEmpty() && !outgoingNodes.isEmpty()){
                // Top node
                blockNodeGraphNode.getData().setHeight(CanvasConstant.NODE_DEFAULT_HEIGHT);
                blockNodeGraphNode.getData().getBlockInfo().getLayer().setExtra(null);
                GraphNode<BlockNode> outgoingBlockNodeGraphNode = outgoingNodes.get(0);
                Point3D pivotPosition = outgoingBlockNodeGraphNode.getData()
                        .getTopCenterPosition()
                        .add(0,-blockNodeGraphNode.getData().getBlockInfo().getHeight()/2,0);
                blockNodeGraphNode.getData().setPosition(
                        pivotPosition.getX(),
                        pivotPosition.getY(),
                        pivotPosition.getZ());
                blockNodeGraphNode.getData().reshapeBlockModel();
            }
            else if(!incomingNodes.isEmpty() && outgoingNodes.isEmpty()){
                // Bottom node
                blockNodeGraphNode.getData().setHeight(CanvasConstant.NODE_DEFAULT_HEIGHT);
                blockNodeGraphNode.getData().getBlockInfo().getLayer().setExtra(null);
                GraphNode<BlockNode> incomingBlockNodeGraphNode = incomingNodes.get(0);
                Point3D pivotPosition = incomingBlockNodeGraphNode.getData()
                        .getBottomCenterPosition()
                        .add(0,blockNodeGraphNode.getData().getBlockInfo().getHeight()/2,0);
                blockNodeGraphNode.getData().setPosition(
                        pivotPosition.getX(),
                        pivotPosition.getY(),
                        pivotPosition.getZ());
                blockNodeGraphNode.getData().reshapeBlockModel();
            }
            else {
                // Middle node
            }
        });
    }
}