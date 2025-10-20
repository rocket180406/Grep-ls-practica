import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.practica.Lanzador;

public class LanzadorTest {

    @Test
    void testEjecutaLsYGrepCorrectamente() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "com.practica.Lanzador");
        pb.redirectErrorStream(true);
        Process proceso = pb.start();

        StringBuilder salida = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                salida.append(line).append("\n");
            }
        }

        int exitCode = proceso.waitFor();

        assertEquals(0, exitCode, "El proceso debería finalizar con código 0");
        assertTrue(salida.toString().contains(Lanzador.MSG_EXITO),
                "Debe contener el mensaje de éxito");
    }

    @Test
    void testErrorAlEjecutarComandoInvalido() throws IOException, InterruptedException {

        String[] comandoMalo = {"no_existe_comando"};
        ProcessBuilder pb = new ProcessBuilder(comandoMalo);
        pb.redirectErrorStream(true);

        Process proceso = pb.start();

        StringBuilder salida = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                salida.append(line).append("\n");
            }
        }

        int exitCode = proceso.waitFor();

        assertNotEquals(0, exitCode, "El proceso debería fallar con código distinto de 0");
    }
}