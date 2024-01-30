import java.net.*;

public class ServidorUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9876);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String nombreCliente = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Cliente conectado: " + nombreCliente);

                // Recibe y procesa mensajes del cliente
                while (true) {
                    socket.receive(receivePacket);
                    String mensaje = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Recibido de " + nombreCliente + ": " + mensaje);

                    // Comprueba si el cliente envía "adeu" para desconectarlo
                    if (mensaje.equalsIgnoreCase("adeu")) {
                        System.out.println("Cliente desconectado: " + nombreCliente);
                        break;
                    }

                    // Responde al cliente con el mensaje en mayúsculas
                    byte[] sendData = mensaje.toUpperCase().getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                            receivePacket.getAddress(), receivePacket.getPort());
                    socket.send(sendPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
