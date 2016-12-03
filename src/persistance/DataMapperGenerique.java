package persistance;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import settings.ConnectionInfo;

class DataMapperGenerique<T> {

	String table; // nom de la table en BDD
	Map<String, Class<?>> champs; // champs de la BDD
	Class<?> maClasse; // classe d'où vient les infos
	private Connection conn;

	public DataMapperGenerique(String table, Map<String, Class<?>> champs, Class<?> maClasse) {
		this.table = table;
		this.maClasse = maClasse;
		this.champs = champs;
		try {
			conn = DriverManager.getConnection(ConnectionInfo.DB_URL, ConnectionInfo.COMPTE, ConnectionInfo.MDP);
			conn.setAutoCommit(false);
		} catch (SQLException e) {

		}
	}

	void insert(T p) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			boolean first = true;

			String req = " VALUES(";
			String nomChamps = "";

			for (Map.Entry<String, Class<?>> e : champs.entrySet()) {
				if (!first) {
					req += ",";
					nomChamps += ",";
				}
				req += "?";
				nomChamps += e.getKey();
				first = false;
			}
			req = "INSERT INTO " + table + " (" + nomChamps + ") " + "(" + req + ")";
			PreparedStatement ps = conn.prepareStatement(req);
			int i = 0;
			for (Map.Entry<String, Class<?>> e : champs.entrySet()) {
				i++;
				String key = e.getKey(); // nom du champ
				String name = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
				Method m = maClasse.getMethod(name);
				Object r = m.invoke(p);
				if (e.getValue() != r.getClass()) {
					// BUG!
					System.out.println("Bug");
				}
				if (e.getValue() == String.class) {
					System.out.println(
							"On insere le champ " + key + " de type " + e.getValue() + " valeur: " + (String) r);
					ps.setString(i, (String) r);
				} else if (e.getValue() == Integer.class) { // integer
					ps.setInt(i, (Integer) r);
				}
			}
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
