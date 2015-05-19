
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.RWLock;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Daian
 */
public class Reader implements Runnable {
    private boolean running = true;
    private RWLock db;

    public static void main(String[] args) {
        (new Thread(new Reader())).start();

    }

    private void connectServer() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            this.db = (RWLock) reg.lookup("server");
            System.out.println("Conected to server");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        while (running) {
            connectServer();
            try {
                db.acquireReadLock();
                String text = db.read("arquivo1.txt", 0, 2);
                db.releaseReadLock();
                System.out.println(text);
                running = false;
            } catch (RemoteException ex) {
            }

        }
    }

}
