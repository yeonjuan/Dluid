package org.kok202.deepblock.domain.structure;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class GraphNode<T> {
    @Getter
    private T data;
    private List<GraphEdge<T>> edges;

    public GraphNode(T data) {
        this.data = data;
        this.edges = new ArrayList<>();
    }

    public boolean isNoWay(){
        return getOutgoingEdges().isEmpty();
    }

    public boolean isSingleWay(){
        return getOutgoingEdges().size() == 1;
    }

    public List<GraphEdge<T>> getOutgoingEdges(){
        return edges.stream()
                .filter(graphEdge -> graphEdge.getSourceGraphNode() == this)
                .collect(toList());
    }

    public List<GraphEdge<T>> getIncomingEdges(){
        return edges.stream()
                .filter(graphEdge -> graphEdge.getDestinationGraphNode() == this)
                .collect(toList());
    }

    public List<GraphNode<T>> getAdjacentNodes(){
        return edges.stream()
                .map(GraphEdge::getDestinationGraphNode)
                .collect(toList());
    }

    public List<GraphNode<T>> getOutgoingNodes(){
        return edges.stream()
                .filter(graphEdge -> graphEdge.getSourceGraphNode() == this)
                .map(GraphEdge::getDestinationGraphNode)
                .collect(toList());
    }

    public List<GraphNode<T>> getIncomingNodes(){
        return edges.stream()
                .filter(graphEdge -> graphEdge.getSourceGraphNode() == this)
                .map(GraphEdge::getSourceGraphNode)
                .collect(toList());
    }

    public void createEdgeTo(GraphNode<T> to){
        // TODO : For safe it needs for cycle validation.
        GraphEdge<T> newEdge = new GraphEdge<>(this, to);
        this.edges.add(newEdge);
        to.edges.add(newEdge);
    }

    public void createEdgeFrom(GraphNode<T> from){
        // TODO : For safe it needs for cycle validation.
        GraphEdge<T> newEdge = new GraphEdge<>(from, this);
        this.edges.add(newEdge);
        from.edges.add(newEdge);
    }

    public void removeEdge(T data){
        edges.stream()
            .filter(graphEdge ->
                    graphEdge.getSourceGraphNode().getData() == data ||
                    graphEdge.getDestinationGraphNode().getData() == data)
            .forEach(this::removeEdge);
    }

    public void removeEdge(GraphEdge<T> graphEdge){
        graphEdge.getSourceGraphNode().edges.remove(graphEdge);
        graphEdge.getDestinationGraphNode().edges.remove(graphEdge);
        graphEdge.setSourceGraphNode(null);
        graphEdge.setDestinationGraphNode(null);
    }

    public void remove(){
        edges.stream().forEach(this::removeEdge);
        edges = null;
        data = null;
    }

    // TODO : IMPORTANT : if it possible, graph must not have cycle
    public void removeAllWithReachableNode(){
        getOutgoingNodes().forEach(GraphNode::remove);
        remove();
    }

    // TODO : IMPORTANT : if it possible, graph must not have cycle
    public List<GraphNode<T>> getAllReachableNodes(){
        List<GraphNode<T>> result = new ArrayList<>();
        getAllReachableNodes(result);
        return result;
    }

    // TODO : IMPORTANT : if it possible, graph must not have cycle
    public List<GraphNode<T>> getAllLinkedNodes(){
        List<GraphNode<T>> result = new ArrayList<>();
        getAllLinkedNodes(result);
        return result;
    }

    private void getAllReachableNodes(List<GraphNode<T>> result){
        for(GraphNode<T> outgoingNodes : getOutgoingNodes()){
            result.add(outgoingNodes);
            outgoingNodes.getAllReachableNodes(result);
        }
    }

    private void getAllLinkedNodes(List<GraphNode<T>> result){
        for(GraphNode<T> adjacentNode : getAdjacentNodes()){
            result.add(adjacentNode);
            adjacentNode.getAllLinkedNodes(result);
        }
    }
}