package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	
	private ArtsmiaDAO dao;
	private SimpleWeightedGraph grafo;
	private Map<Integer,Artist> idMap;
	
	
	public Model() {
		dao= new ArtsmiaDAO();
	}
	
	public List<String> getRole(){
		return dao.listRole();
	}
	
	
	public void creaGrafo(String r) {
		grafo = new SimpleWeightedGraph(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		dao.vertici(idMap, r);
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		
		
		
		
	}
	
	public String getNvertici() {
		return this.grafo.vertexSet().size()+"";
	}

}
