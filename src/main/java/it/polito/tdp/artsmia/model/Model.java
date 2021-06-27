package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
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
	private List<Adiacenza> archi;
	private List<Artist> migliore;
	private int pesoA;
	
	
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
		
		archi = new ArrayList<>(dao.archi(idMap, r));
		for (Adiacenza a :archi) {
			Graphs.addEdge(this.grafo, a.getA1(), a.getA2(), a.getPeso());
		}
		
		
			
		}
		
		
	public String artistiConnessi() {
		
		Collections.sort(archi);
		
		String result = "";
		
		for(Adiacenza a :archi) {
			result+=a.toString()+"\n";
		}
		
		return result;
	}
	


	public String getNvertici() {
		return this.grafo.vertexSet().size()+"";
	}
	public String getNarchi() {
		return this.grafo.edgeSet().size()+"";
	}
	
	public String cerca (Integer id) {
		String result= null;
		Artist artist = idMap.get(id);
				
				if (artist==null) {
					 return result="Errore l'id inserito, non è presente nella mappa";
				}
				else {
					List<Artist> parziale = new ArrayList<>();
					migliore= new ArrayList<>();
					parziale.add(artist);
					
					ricorsivo(parziale,0);
					
					result="\n Cammino Trovato \n";
					for(Artist a: migliore) {
						result+=a.toString();
					}
					return result;
				}
				
				
		
	}

	private void ricorsivo(List<Artist> parziale, int liv) {
		//caso terminale 
		if(parziale.size()>migliore.size()) {
			this.migliore =new ArrayList<>(parziale);
		}
		List<DefaultWeightedEdge> edgelist= new ArrayList<>(this.grafo.edgesOf(parziale.get(liv)));
		for(DefaultWeightedEdge e :edgelist) {
			
			if(liv==0) {
				Artist vicino = Graphs.getOppositeVertex(this.grafo, e, parziale.get(liv));
				parziale.add(vicino);
				 pesoA = (int) this.grafo.getEdgeWeight(e);
				ricorsivo(parziale,liv++);
				parziale.remove(liv);
				liv--;	
			}
			else if((int) this.grafo.getEdgeWeight(e) == pesoA) {
				Artist vicino = Graphs.getOppositeVertex(this.grafo, e, parziale.get(liv));
				parziale.add(vicino);
				ricorsivo(parziale,liv++);
				parziale.remove(liv);
				liv--;
			}
			
			
			
		}
		
	}
	

}
