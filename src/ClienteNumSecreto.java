import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class ClienteNumSecreto {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress servidorDireccion = InetAddress.getByName("localhost");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Pide al usuario que ingrese un número
                System.out.print("Ingresa un número (o escribe 'salir' para salir): ");
                String userInput = scanner.nextLine();

                // Comprueba si el usuario quiere salir
                if (userInput.equalsIgnoreCase("salir")) {
                    System.out.println("Saliendo del cliente.");
                    break;
                }

                try {
                    // Convierte la entrada del usuario a un entero
                    int numero = Integer.parseInt(userInput);

                    // Envia el número al servidor
                    byte[] sendData = ByteBuffer.allocate(4).putInt(numero).array();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                            servidorDireccion, 9876);
                    socket.send(sendPacket);

                    // Recibe la respuesta del servidor
                    byte[] receiveData = new byte[4];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);

                    // Convierte los datos recibidos a un entero
                    int respuesta = ByteBuffer.wrap(receivePacket.getData()).getInt();

                    // Imprime la respuesta del servidor
                    switch (respuesta) {
                        case 0:
                            System.out.println("¡Adivinaste el número secreto!");
                            break;
                        case 1:
                            System.out.println("El número secreto es mayor.");
                            break;
                        case 2:
                            System.out.println("El número secreto es menor.");
                            break;
                        default:
                            System.out.println("Respuesta inesperada del servidor.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, ingresa un número válido.");
                }
            }

            socket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
