package no.hvl.dat152.obl4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SearchItemDAO {

	public List<SearchItem> getSearchHistoryLastFive() {
		String sql = "SELECT * FROM SecOblig.History ORDER BY datetime DESC";
		// LIMIT 5
		// Derby lacks LIMIT
		return getSearchItemList(sql, 5, null);
	}

	public List<SearchItem> getSearchHistoryForUser(String username) {
//    String sql = "SELECT * FROM SecOblig.History " 
//        + "WHERE username = '" + username 
//        + "' ORDER BY datetime DESC";

		// LIMIT 50
		// Derby lacks LIMIT

		String sql = "SELECT * FROM SecOblig.History " + "WHERE username = ?" + " ORDER BY datetime DESC";

		return getSearchItemList(sql, 50, username);
	}

	private List<SearchItem> getSearchItemList(String sql, Integer limit, String username) {

		List<SearchItem> result = new ArrayList<SearchItem>();

		Connection c = null;
		PreparedStatement s = null;
		ResultSet r = null;

		try {
			c = DatabaseHelper.getConnection();
			s = c.prepareStatement(sql);
			if (username != null)
				s.setString(1, username);
			if (limit > 0)
				s.setMaxRows(limit);
			r = s.executeQuery();

			while (r.next()) {
				SearchItem item = new SearchItem(r.getTimestamp("datetime"), r.getString("username"),
						r.getString("searchkey"));
				result.add(item);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DatabaseHelper.closeConnection(r, s, c);
		}

		return result;
	}

	public void saveSearch(SearchItem search) {

		String sql = "INSERT INTO SecOblig.History VALUES (?,?,?)";

		Connection c = null;
		PreparedStatement s = null;
		ResultSet r = null;

		try {
			c = DatabaseHelper.getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, search.getDatetime().toString());
			s.setString(2, search.getUsername());
			s.setString(3, search.getSearchkey());
			s.executeUpdate(sql);

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DatabaseHelper.closeConnection(r, s, c);
		}
	}

}
