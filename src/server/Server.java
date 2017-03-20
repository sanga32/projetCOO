package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;

import Interface.InfoInterface;
import Interface.SalonInterface;
import domaine.Salon;
import persistance.PersonneMapper;
import persistance.SalonMapper;

public class Server {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException, SQLException {
		//Ici je pense qu'il faut gérer tous les mappers, on devra appeler le serveur via les controllers en utilisant les interfaces. 
		Registry registry = LocateRegistry.createRegistry(10000);
		InfoInterface info = new Info();
		registry.bind("info", info);
		List<SalonInterface> salons = SalonMapper.getInstance().getSalons();
		for(SalonInterface s : salons){
			registry.bind(s.getNom(), s);
		}
	}

}
