package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {

	private Graph<Player, DefaultWeightedEdge> grafo;
	
	private Map<Integer, Player> idMap;
	
	private PremierLeagueDAO dao;
	
	private List<Arco> archi;
	private List<Player> vertici;
	
	private List<Player> best;
	private int maxGrado;
	
	public Model() {
		
		this.dao = new PremierLeagueDAO();
		
		this.idMap = new HashMap<>();
		
		this.dao.listAllPlayers(idMap);
		

		
	}
	
	public String creaGrafo(double media) {
		
		this.grafo = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		this.vertici = this.dao.getAllVertici(media, idMap);
		
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		this.archi = new ArrayList<Arco>();
		
		for(Player p1 : this.vertici) {
			for(Player p2 : this.vertici) {
				if(!p1.equals(p2)) {
					this.dao.getAllArchi(this.archi, p1,p2);
				}
			}
		}
		
		for(Arco a : this.archi) {
			int peso = a.getT1() - a.getT2();
			if(peso < 0) {
				peso = peso * -1;
			}
			
			if(peso!=0) {
				if(a.getT1()>a.getT2()) {
					Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), peso);
				}else {
					Graphs.addEdgeWithVertices(this.grafo, a.getP2(), a.getP1(), peso);

				}
			}

		}
		
		String result = "GRAFO CREATO!\n" + "#VERTICI: " + this.grafo.vertexSet().size() + "\n" + "#ARCHI: " + this.grafo.edgeSet().size()+"\n";
		return result;
	}
	
	public List<PlayerDelta> getGiocatoreMigliore() {
		//devo vedere chi ha la somma dei pesi degli archi uscenti maggiore
		List<PlayerDelta> result = new ArrayList<PlayerDelta>();
		for(Player p : this.vertici) {
			int somma = 0;
			somma = this.grafo.outDegreeOf(p);
			result.add(new PlayerDelta(p, somma));
		}
		Collections.sort(result);
		return result;
	}
	
	public List<PlayerDelta> getAvversariBattuti(Player p){
		List<PlayerDelta> result = new ArrayList<PlayerDelta>();
		for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(p)) {
			result.add(new PlayerDelta(this.grafo.getEdgeTarget(e), (int) this.grafo.getEdgeWeight(e)));
		}
		Collections.sort(result);
		return result;
	}
	
	public List<Player> cercaDreamTeam(int k){
		
		this.best = new ArrayList<Player>();
		this.maxGrado = 0;
		
		List<Player> parziale = new ArrayList<Player>();
		
		cerca(parziale, k);
		
		return this.best;
		
	}
	
	private void cerca(List<Player> parziale, int k) {
		if(parziale.size() == k) {
			int grado = calcolaGrado(parziale);
			if(grado > this.getMaxGrado()) {
				this.maxGrado = grado;
				this.best = new ArrayList<Player>(parziale);
			}
			return;
		}
		
		for(Player p : this.vertici) {
			if(!parziale.contains(p)) {
				if(controllaGiocatore(parziale, p)) {
					parziale.add(p);
					cerca(parziale, k);
					parziale.remove(parziale.size()-1);
					
				}
			}
		}
	}

	private boolean controllaGiocatore(List<Player> parziale, Player pp) {
		boolean ok = false;
		if(parziale.size()==0) {
			return true;
		}else {
			for(Player p : parziale) {
				for(PlayerDelta avversario : this.getAvversariBattuti(p)) {
					if(avversario.getP1().equals(pp))
						ok = true;
				}
				
			}
			return ok;
		}
		
	}

	private int calcolaGrado(List<Player> parziale) {
		int grado = 0;
		for(Player p : parziale) {
			int sommaEntranti = 0;
			for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(p))
				sommaEntranti+=this.grafo.getEdgeWeight(e);
			int sommaUscenti = 0;
			for(DefaultWeightedEdge u :  this.grafo.outgoingEdgesOf(p))
				sommaUscenti+=this.grafo.getEdgeWeight(u);
			grado+=(sommaUscenti - sommaEntranti);
		}
		return grado;
	}

	public boolean isCreato() {
		if(this.grafo == null)
			return false;
		else
			return true;
	}

	public int getMaxGrado() {
		return maxGrado;
	}
}
