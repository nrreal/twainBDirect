package extern.bosh.twain4j;

/**
 *
 * @author theboshy
 */
public class SystemProperties {
    
    public static String verifyJvmArch(){
        return System.getProperty("os.arch").equals("x86") ? "JVM_x32":"JVM_x64";
    }
}
