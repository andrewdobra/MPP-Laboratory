package socket;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private BufferedReader input = null;
    private PrintWriter output = null;

    // constructor to set IP address and port
    public Client(String address, int port) {
        // establish a connection
        try {
            this.socket = new Socket(address, port);
            System.out.println("Connected to server!");

            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(socket.getOutputStream(), true);
        } catch(IOException ex) {
            System.out.println("Error while establishing connection: " + ex.getMessage());
        }

        String answer = "";

        Scanner keyboard = new Scanner(System.in);

        while (!answer.equals("0") && !answer.equals("42")) {
            try {
                String response;

                while ((response = input.readLine()) != null) {
                    if (response.equals("endRead"))
                        break;

                    System.out.println(response);
                }

                answer = keyboard.nextLine();

                output.println(answer);
            } catch(IOException ex) {
                System.out.println("IOException: " + ex.getMessage());
            }
        }

        try {
            input.close();
            output.close();
            socket.close();
        } catch(IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        }
    }
}
