package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
public List<String> listRole() {
		
	String sql ="SELECT DISTINCT a.role as r "
			+ "FROM authorship a";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				
				result.add(res.getString("r"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

   
   public void vertici(Map<Integer,Artist>map,String r) {
		
	   String sql ="SELECT a.artist_id as id,a.name as n "
		   		+ "FROM artists a,authorship au "
		   		+ "WHERE a.artist_id=au.artist_id AND au.role=?";
			
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1,r);
				ResultSet res = st.executeQuery();
				while (res.next()) {

					
					
					map.put(res.getInt("id"), new Artist(res.getInt("id"),res.getString("n")));
				}
				conn.close();
				return ;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return ;
			}
		}
   
   String sql ="SELECT a1.artist_id AS id1,a2.artist_id AS id2, COUNT(e1.exhibition_id) AS peso\n"
   		+ "FROM authorship a1,authorship a2,exhibition_objects e1,exhibition_objects e2\n"
   		+ "WHERE a1.artist_id> a2.artist_id AND a1.object_id= e1.object_id AND a2.object_id = e2.object_id\n"
   		+ " AND e1.exhibition_id=e2.exhibition_id\n"
   		+ " and a1.role=? AND a2.role=a1.role\n"
   		+ " GROUP BY id1,id2";
   public List<Adiacenza> archi(Map<Integer,Artist>map,String r) {
		
	   String sql = "SELECT a1.artist_id AS id1,a2.artist_id AS id2, COUNT(e1.exhibition_id) AS peso "
		   		+ "FROM authorship a1,authorship a2,exhibition_objects e1,exhibition_objects e2 "
		   		+ "WHERE a1.artist_id> a2.artist_id AND a1.object_id= e1.object_id AND a2.object_id = e2.object_id "
		   		+ " AND e1.exhibition_id=e2.exhibition_id "
		   		+ " and a1.role=? AND a2.role=a1.role "
		   		+ " GROUP BY id1,id2";
			
			Connection conn = DBConnect.getConnection();
			List<Adiacenza> result= new ArrayList<>();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1,r);
				ResultSet res = st.executeQuery();
				while (res.next()) {
					Artist a1 = map.get(res.getInt("id1"));
					Artist a2 = map.get(res.getInt("id2"));
					if(a1!=null&&a2!=null) {
						Adiacenza a = new Adiacenza(a1,a2,res.getInt("peso"));
						result.add(a);
					}

					
					
					
				}
				conn.close();
				return result ;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
}
