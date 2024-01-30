import java.net.*;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress direccionServidor = InetAddress.getByName("localhost");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingresa tu nombre: ");
            String nombreCliente = scanner.nextLine();

            // Envia el nombre del cliente al servidor
            byte[] datosNombre = nombreCliente.getBytes();
            DatagramPacket paqueteNombre = new DatagramPacket(datosNombre, datosNombre.length, direccionServidor, 9876);
            socket.send(paqueteNombre);

            while (true) {
                System.out.print("Ingresa un mensaje (escribe 'adeu' para desconectar): ");
                String mensaje = scanner.nextLine();

                // Envia el mensaje al servidor
                byte[] datosEnvio = mensaje.getBytes();
                DatagramPacket paqueteEnvio = new DatagramPacket(datosEnvio, datosEnvio.length, direccionServidor, 9876);
                socket.send(paqueteEnvio);

                // Comprueba si el mensaje es "adeu" para desconectarse
                if (mensaje.equalsIgnoreCase("adeu")) {
                    System.out.println("Desconectado del servidor.");
                    break;
                }

                // Recibe e imprime la respuesta del servidor
                byte[] datosRecepcion = new byte[1024];
                DatagramPacket paqueteRecepcion = new DatagramPacket(datosRecepcion, datosRecepcion.length);
                socket.receive(paqueteRecepcion);
                String respuesta = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());
                System.out.println("Respuesta del servidor: " + respuesta);
            }

            socket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
