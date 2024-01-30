import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ClienteMulticast {
    public static void main(String[] args) {
        try {
            InetAddress direccionGrupo = InetAddress.getByName("224.0.0.1");
            MulticastSocket socketMulticast = new MulticastSocket(9876);
            socketMulticast.joinGroup(direccionGrupo);

            Map<String, Integer> contadorPalabras = new HashMap<>();

            System.out.println("Conectado al servicio multicast. Esperando palabras...");

            while (true) {
                byte[] datosRecepcion = new byte[1024];
                DatagramPacket paqueteRecepcion = new DatagramPacket(datosRecepcion, datosRecepcion.length);
                socketMulticast.receive(paqueteRecepcion);

                String palabra = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());

                // Imprime la palabra
                System.out.println("Palabra recibida: " + palabra);

                // Actualiza el contador de palabras
                contadorPalabras.put(palabra, contadorPalabras.getOrDefault(palabra, 0) + 1);

                // Imprime el número de veces que ha aparecido cada palabra
                System.out.println("Contador de palabras:");
                for (Map.Entry<String, Integer> entry : contadorPalabras.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue() + " veces");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
