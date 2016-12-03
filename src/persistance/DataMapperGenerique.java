package persistance;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import domaine.Personne;
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

	public Integer getId(T p) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method m = maClasse.getMethod("getId");
		Object r = m.invoke(p);
		return (Integer) r;
	}

	public void insert(T p) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
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
			req = "INSERT INTO " + table + " (" + nomChamps + ") " + "(" + req + ");";
			PreparedStatement ps = conn.prepareStatement(req);
			int i = 0;
			for (Map.Entry<String, Class<?>> e : champs.entrySet()) {
				i++;
				String key = e.getKey(); // nom du champ
				String name = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
				Method m = maClasse.getMethod(name);
				Object r = m.invoke(p);
				if (e.getValue() != r.getClass()) {
					System.out.println("Bug");
				}
				if (e.getValue() == String.class) {
					System.out.println(
							"On insere le champ " + key + " de type " + e.getValue() + " valeur: " + (String) r);
					ps.setString(i, (String) r);
				} else if (e.getValue() == Integer.class) {
					ps.setInt(i, (Integer) r);
				}
			}
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(T p) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		try {
			String req = "delete from " + table + " where id=?";
			PreparedStatement ps = conn.prepareStatement(req);
			int id = getId(p);
			ps.setInt(1, id);
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(T p) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		try {
			boolean first = true;
			String req = " set ";
			for (Map.Entry<String, Class<?>> e : champs.entrySet()) {
				if (!first) {
					req += ",";
				}
				req += e.getKey() + "=?";
				first = false;
			}
			req = "update " + table + req + " where id=?";
			PreparedStatement ps = conn.prepareStatement(req);
			int i = 0;
			for (Map.Entry<String, Class<?>> e : champs.entrySet()) {
				i++;
				String key = e.getKey(); // nom du champ
				String name = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
				Method m = maClasse.getMethod(name);
				Object r = m.invoke(p);
				if (e.getValue() != r.getClass()) {
					System.out.println("Bug");
				}
				if (e.getValue() == String.class) {
					ps.setString(i, (String) r);
				} else if (e.getValue() == Integer.class) {
					ps.setInt(i, (Integer) r);
				}
			}
			i++;
			int id = getId(p);
			ps.setInt(i, id);
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public T findById(int id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			boolean first = true;
			String nomChamps = "";
			for (Map.Entry<String, Class<?>> e : champs.entrySet()) {
				if (!first) {
					nomChamps += ",";
				}
				nomChamps += e.getKey();
				first = false;
			}
			String req = "Select " + nomChamps + " From " + table + " where " + "id=?;";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int i = 0;
			T p = (T) getClass().getConstructors();
			for (Map.Entry<String, Class<?>> e : champs.entrySet()) {
				i++;
				String key = e.getKey(); // nom du champ
				String name = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);	
				if (e.getValue() == String.class) {
					Method m = maClasse.getMethod(name,rs.getString(e.getKey()).getClass());
					m.invoke(p);
				} else if (e.getValue() == Integer.class) {
					// Il faudrait pouvoir faire un getInt sauf qu'on ne peut plus faire un getClass() ce qui
					// pose problème pour le getMethode()
					Method m = maClasse.getMethod(name,rs.getString(e.getKey()).getClass());
					m.invoke(p);
				}		
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
