package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public void listAllPlayers(Map<Integer, Player> idMap){
		String sql = "SELECT * FROM Players";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				if(!idMap.containsKey(res.getInt("PlayerID"))) {
					Player p = new Player(res.getInt("PlayerID"), res.getString("Name"));
					idMap.put(p.getPlayerID(), p);
				}

				
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Player> getAllVertici(double media, Map<Integer, Player> idMap) {
		String sql = "SELECT PlayerID "
				+ "FROM actions "
				+ "GROUP BY PlayerID "
				+ "HAVING AVG(Goals) > ?";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, media);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				result.add(idMap.get(res.getInt("PlayerID")));
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	}

	public void getAllArchi(List<Arco> archi, Player p1, Player p2) {
		String sql = "SELECT a1.PlayerID AS id1, a2.PlayerID AS id2, SUM(a1.TimePlayed) AS t1, SUM(a2.TimePlayed) AS t2 "
				+ "FROM actions a1, actions a2 "
				+ "WHERE a1.PlayerID = ? AND a2.PlayerID = ? AND "
				+ "a1.TeamID <> a2.TeamID AND a1.`Starts` = 1 AND a2.`Starts` = 1 "
				+ "AND a1.MatchID = a2.MatchID "
				+ "GROUP BY id1, id2";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, p1.getPlayerID());
			st.setInt(2, p2.getPlayerID());
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				archi.add(new Arco(p1,p2,res.getInt("t1"), res.getInt("t2")));
				
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();

		}	
	}
	
}
