package nrreal.projects.twain4j.test;

import org.omg.CORBA.SystemException;

/**
 *
 * @author theboshy
 */
public class LoadLibrary {

    private static final String LIB_X32 = "nrreal/projects/twain4j/lib/x32/";
    private static final String LIB_X64 = "nrreal/projects/twain4j/lib/x64/";
    private static final String ARCHITECTURE_X32 = "32";
    private static final String NAME = "jtwain.dll";

    public static void main(String[] args) {
        //--agregar dlls faltantes para utilizar jtwain
        String arch = System.getenv("PROCESSOR_ARCHITECTURE");
        String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
        String realArch = arch.endsWith(ARCHITECTURE_X32) || wow64Arch != null && wow64Arch.endsWith(ARCHITECTURE_X32) ? ARCHITECTURE_X32 : "64";
        if (System.getProperty("os.name").startsWith("Windows")) {
            try {
                if (realArch.equals(ARCHITECTURE_X32)) {
                    System.load(LIB_X32 + NAME);
                    System.out.println("cargada lib twain x32 ");
                } else {
                    System.load(LIB_X64 + NAME);
                    System.out.println("cargada lib twain x64 ");
                }
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
