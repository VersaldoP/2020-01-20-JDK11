package it.polito.tdp.artsmia.model;

import java.util.List;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	
	private ArtsmiaDAO dao;
	
	
	public Model() {
		dao= new ArtsmiaDAO();
	}
	
	public List<String> getRole(){
		return dao.listRole();
	}

}
