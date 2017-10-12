package nrreal.projects.bosh.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 *
 * @author theboshy
 */
public class FatherTree {

    private static FatherTree instance;

    public FatherTree() {

    }

    public static FatherTree getInstance() {
        if (instance == null) {
            instance = new FatherTree();
        }
        return instance;
    }

    public static void createNewLogFile(String paString, String fileLogContent) {
        java.util.Date date = GregorianCalendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormatDays = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormatHour = new SimpleDateFormat("HH:mm");
        //--
        String logName = "logError" + simpleDateFormatDays.format(date) + ".txt";
        try {
            overrideFileCustom(paString, logName, true, fileLogContent, simpleDateFormatHour.format(date) + "->");
        } catch (IOException e) {
            System.out.println("Intern log : " + e.getMessage());
        }
    }

    /**
     * Permite sobreescrbir un archivo
     *
     * @param url la ruta del archivo que se desea sobreescribir
     * @param name nombre del archivo que se desea sobreescribir
     * @param content contenido para reemplazar
     * @throws IOException
     */
    private static void overrideFile(String url, String name, String content) throws IOException {
        BufferedWriter bw = null;
        try {
            File archivo = new File(url + "/" + name);
            //--
            Path path = Paths.get(url);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            //--
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(content);
        } catch (IOException e) {
            System.out.println("Ocurrio un Exception al intentar sobreescribir el archivo : ");
            for (StackTraceElement runnerObject : e.getStackTrace()) {
                System.out.println(runnerObject);
            }
        } finally {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        }
    }

    /**
     * Anade texto a un archivo creado con propiedades externas
     *
     * @param url la ruta del archivo que se desea sobreescribir
     * @param name nombre del archivo que se desea sobreescribir
     * @param newLine permite insertar una neuva linea que sirve de separador
     * @param content contenido para reemplazar
     * @param separator un separador entre elementos
     * @throws IOException
     */
    private static void overrideFileCustom(String url, String name, boolean newLine, String content, String separator) throws IOException {
        BufferedWriter bw = null;
        try {
            File archivo = new File(url + "/" + name);
            //--
            Path path = Paths.get(url);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            //--
            bw = new BufferedWriter(new FileWriter(archivo, true));
            if (newLine) {
                bw.newLine();
            }
            if (!separator.isEmpty()) {
                bw.newLine();
                bw.write(separator);
            }
            bw.write(content);
        } catch (IOException e) {
            System.out.println("Ocurrio un Exception al intentar sobreescribir el archivo : ");
            for (StackTraceElement runnerObject : e.getStackTrace()) {
                System.out.println(runnerObject);
            }
        } finally {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        }
    }

    /**
     * Permite eliminar un archivo
     *
     * @param url la ruta del archivo que se desea eliminar
     * @param name nombre del archivo que se desea eliminar
     * @throws IOException
     */
    private static void deleteFile(String url, String name) throws IOException {
        File archivo = new File(url + "/" + name);
        if (archivo.exists()) {
            archivo.delete();
        } else {
            System.out.println("El archvivo o la ruta especificada no existe");
        }
    }

    public String getCurrentFolder() {
        String compac = "";
        try {
            String[] pathCompleJar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getCanonicalPath().split("\\\\");
            if (pathCompleJar != null && pathCompleJar.length >= 1) {
                pathCompleJar[pathCompleJar.length - 1] = "";
                for (int runner = 0; runner < pathCompleJar.length; runner++) {
                    compac += pathCompleJar[runner] + "/";
                }
            }
        } catch (IOException e) {
            System.out.println("Intern Log : " + e.getMessage());
        }
        return compac + "logs\\";
    }

    public void validateFolder(File file) {
        try {
            Path path;
            path = Paths.get(file.getCanonicalPath());
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.out.println("Intern Log : " + e.getMessage());
        }
    }
}
