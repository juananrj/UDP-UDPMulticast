import java.net.*;
import java.nio.ByteBuffer;
import java.util.Random;

public class ServidorNumSecreto {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9876);
            Random random = new Random();
            int numeroSecreto = random.nextInt(100) + 1; // Genera un número secreto aleatorio entre 1 y 100

            while (true) {
                byte[] datosRecepcion = new byte[4];
                DatagramPacket paqueteRecepcion = new DatagramPacket(datosRecepcion, datosRecepcion.length);
                socket.receive(paqueteRecepcion);

                int intentoCliente = ByteBuffer.wrap(paqueteRecepcion.getData()).getInt();

                // Comprueba el intento del cliente y responde
                int codigoRespuesta;
                if (intentoCliente < numeroSecreto) {
                    codigoRespuesta = 1; // El número secreto es mayor
                } else if (intentoCliente > numeroSecreto) {
                    codigoRespuesta = 2; // El número secreto es menor
                } else {
                    codigoRespuesta = 0; // ¡Adivinado!
                }

                // Envía el código de respuesta al cliente
                byte[] datosEnvio = ByteBuffer.allocate(4).putInt(codigoRespuesta).array();
                DatagramPacket paqueteEnvio = new DatagramPacket(datosEnvio, datosEnvio.length,
                        paqueteRecepcion.getAddress(), paqueteRecepcion.getPort());
                socket.send(paqueteEnvio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
