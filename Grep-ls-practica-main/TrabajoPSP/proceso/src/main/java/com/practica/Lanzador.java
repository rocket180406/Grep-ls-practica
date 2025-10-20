package com.practica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lanzador {

    public static final String MSG_ERROR = "Se ha producido un error al ejecutar el comando";
    public static final String MSG_EXITO = "Comandos lanzados con éxito";
    public static final String[] COMANDO_LS = {"ls"};
    public static final String[] COMANDO_GREP = {"grep", "a"};

    public static void main(String[] args) {
        try {

            Process procesoLs = Runtime.getRuntime().exec(COMANDO_LS);

            Process procesoGrep = Runtime.getRuntime().exec(COMANDO_GREP);

            try (var outputLs = procesoLs.getInputStream(); var inputGrep = procesoGrep.getOutputStream()) {

                outputLs.transferTo(inputGrep);
            }

            StringBuilder output = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(procesoGrep.getInputStream()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    output.append(linea).append("\n");
                }
            }

            int exitLs = procesoLs.waitFor();
            int exitGrep = procesoGrep.waitFor();

            if (exitLs == 0 && exitGrep == 0) {
                System.out.println(MSG_EXITO);
                System.out.println(output);
                System.exit(0);
            } else {
                System.out.println(MSG_ERROR);
                System.exit(1);
            }

        } catch (IOException | InterruptedException e) {
            System.out.println(MSG_ERROR);
            System.exit(34);
        }
    }
}
