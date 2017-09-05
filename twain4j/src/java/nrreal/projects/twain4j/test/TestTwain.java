package nrreal.projects.twain4j.test;

/**
 *
 * @author theboshy
 */
public class TestTwain {

    private static final String LIB_X32 = "nrreal/projects/twain4j/lib/x32";
    private static final String LIB_X64 = "nrreal/projects/twain4j/lib/x64";
    private static final String ARCHITECTURE_X32 = "32";

    public static void main(String[] args) {
        String arch = System.getenv("PROCESSOR_ARCHITECTURE");
        String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
        String realArch = arch.endsWith("32") || wow64Arch != null && wow64Arch.endsWith("32") ? "32" : "64";
        if (System.getProperty("os.name").startsWith("windows")) {
            if (realArch.equals(ARCHITECTURE_X32)) {
                System.load(LIB_X32);
            } else {
                System.load(LIB_X64);
            }
        }
    }
}
