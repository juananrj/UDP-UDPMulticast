import java.io.BufferedReader;
import java.io.FileReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServidorMulticast {
    public static void main(String[] args) {
        try {
            MulticastSocket socketMulticast = new MulticastSocket();
            InetAddress direccionGrupo = InetAddress.getByName("224.0.0.1");

            List<String> palabras = leerPalabrasDesdeArchivo("src/palabras.txt");

            while (true) {
                // Escoge una palabra aleatoria de la lista
                String palabraAleatoria = palabras.get(new Random().nextInt(palabras.size()));

                // Envía la palabra al grupo multicast
                byte[] datosEnvio = palabraAleatoria.getBytes();
                DatagramPacket paqueteEnvio = new DatagramPacket(datosEnvio, datosEnvio.length, direccionGrupo, 9876);
                socketMulticast.send(paqueteEnvio);

                // Duerme para simular intervalos entre palabras
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> leerPalabrasDesdeArchivo(String nombreArchivo) {
        List<String> palabras = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                palabras.add(linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return palabras;
    }
}
