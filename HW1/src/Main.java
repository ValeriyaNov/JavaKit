import server.Server;
import client.Client;
public class Main {
    public static void main(String[] args) {

        Server serverWindow = new Server();
        new Client(serverWindow);
        new Client(serverWindow);
    }
}