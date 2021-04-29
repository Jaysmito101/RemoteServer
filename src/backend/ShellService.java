import java.io.*;
import java.util.*;
public class ShellService{
    private ShellService(){}

    public static String execute(String command){
        if(System.getProperty("os.name").startsWith("Linux")){
            return executeLinux(command);
        }else{
            return executeWindows(command);
        }
    }

    private static String executeLinux(String command){
        try {
            Process process = new ProcessBuilder(new String[]{"bash", "-c", command}).redirectErrorStream(true).start();
            String output = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ( (line = br.readLine()) != null )
                output += line  + " <br> ";
            if (0 != process.waitFor()){
                BufferedReader bre = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                line = null;
                while ( (line = br.readLine()) != null )
                    output += (line) + " <br> ";
            }
            System.out.println("shell >> " + command);
            System.out.println(output.replace(" <br> ", "\n"));
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    private static String executeWindows(String command){
        try {
            Process process = new ProcessBuilder(("cmd" + " " + command).strip().trim().split(" ")).redirectErrorStream(true).start();
            String output = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ( (line = br.readLine()) != null )
                output += (line) + " <br> ";
            if (0 != process.waitFor()){
                BufferedReader bre = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                line = null;
                while ( (line = br.readLine()) != null )
                    output += (line) + " <br> ";
            }
            System.out.println("shell >> " + command);
            System.out.println(output.replace(" <br> ", "\n"));
            return output;
        } catch (Exception e) {
            return null;
        }
    }
}