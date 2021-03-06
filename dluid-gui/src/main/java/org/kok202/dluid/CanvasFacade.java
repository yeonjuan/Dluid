package org.kok202.dluid;

import lombok.Data;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionInfoHolder;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
public class CanvasFacade {

    /*************************************************************************************************
     /* Singleton itself
     *************************************************************************************************/
    public static void initialize(){
        CanvasSingleton.getInstance().afterAllWidgetSet();
    }

    /*************************************************************************************************
    /* Canvas -> MainContent
    *************************************************************************************************/
    public static void hoveringListener(MaterialInsertionInfoHolder materialInsertionInfoHolder){
        CanvasSingleton.getInstance()
                .getMainCanvas()
                .getMainContent()
                .getBlockInsertionHandler()
                .hoveringListener(materialInsertionInfoHolder);
    }

    public static void insertListener(MaterialInsertionInfoHolder materialInsertionInfoHolder){
        CanvasSingleton.getInstance()
                .getMainCanvas()
                .getMainContent()
                .getBlockInsertionHandler()
                .insertListener(materialInsertionInfoHolder);
    }

    /*************************************************************************************************
    /* BlockNodeManager
    *************************************************************************************************/
    public static long getGraphManagerHashCode(){
        return CanvasSingleton.getInstance().getBlockNodeManager().getHashCode();
    }

    public static void notifyLayerDataChanged(String layerId){
        CanvasSingleton.getInstance().getBlockNodeManager().notifyLayerDataChanged(layerId);
    }

    public static List<GraphNode<BlockNode>> findAllReachableNode(String layerId){
        return CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findAllReachableNode(layerId);
    }

    public static void removeGraphNode(String layerId) {
        CanvasSingleton.getInstance().getBlockNodeManager().removeGraphNode(layerId);
    }

    public static void removeAllGraphNode() {
        while(!CanvasSingleton.getInstance().getBlockNodeManager().getGraphNodes().isEmpty()){
            GraphNode<BlockNode> blockNodeGraphNode = CanvasSingleton.getInstance().getBlockNodeManager().getGraphNodes().get(0);
            CanvasSingleton.getInstance().getBlockNodeManager().removeGraphNode(blockNodeGraphNode.getData().getBlockLayer().getId());
        }
    }

    public static List<Layer> findIncomingLayers(String layerId){
        return CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findGraphNodeByLayerId(layerId)
                .getIncomingNodes()
                .stream()
                .map(incomingGraphNode -> {
                    if(incomingGraphNode.getData().getBlockLayer().getType() == LayerType.PIPE_LAYER)
                        return incomingGraphNode.getIncomingNode().get().getData().getBlockLayer();
                    return incomingGraphNode.getData().getBlockLayer();
                })
                .collect(Collectors.toList());
    }

    public static String findStartLayerIdConnectedWithLayerId(String layerId){
        GraphNode<BlockNode> sourceLayerGraphNode = CanvasSingleton.getInstance().getBlockNodeManager().findStartBlockConnectedWithLayerId(layerId);
        return (sourceLayerGraphNode != null)? sourceLayerGraphNode.getData().getBlockLayer().getId() : "";
    }

    public static GraphNode<BlockNode> findGraphNodeByLayerId(String layerId){
        return CanvasSingleton.getInstance().getBlockNodeManager().findGraphNodeByLayerId(layerId);
    }

    public static List<GraphNode<BlockNode>> findAllInputLayer(){
        return findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType());
    }

    public static Optional<GraphNode<BlockNode>> findOutputLayer(){
        List<GraphNode<BlockNode>> outputNodes = findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isOutputLayerType());
        return outputNodes.isEmpty()? Optional.empty() : Optional.of(outputNodes.get(0));
    }

    public static List<GraphNode<BlockNode>> findAllGraphNode(Predicate<? super GraphNode<BlockNode>> predicate){
        return CanvasSingleton.getInstance().getBlockNodeManager().findAllGraphNode(predicate);
    }
}
