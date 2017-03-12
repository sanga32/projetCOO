package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Interface.InfoInterface;
import persistance.PersonneMapper;

public class Server {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		//Ici je pense qu'il faut gérer tous les mappers, on devra appeler le serveur via les controllers en utilisant les interfaces. 
		Registry registry = LocateRegistry.createRegistry(10000);
		InfoInterface info = new Info();
		registry.bind("info", info);
	}

}
