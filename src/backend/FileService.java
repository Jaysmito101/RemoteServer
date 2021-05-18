import java.io.*;
import java.util.*;

public class FileService {
    public static HashMap<String, String> map;

    static{
        map = new HashMap();
    }

    private FileService(){}

    public static String getDetails(String directory){
        if(map.containsKey(directory) && Math.random() > 0.5){
            return map.get(directory);
        }
        File path = new File(directory);
        try{
            if(path.isDirectory()){
                String[] directories = path.list((FilenameFilter) new FilenameFilter() {
                    @Override
                    public boolean accept(File current, String name) {
                        return new File(current, name).isDirectory();
                    }
                });
                String[] files = path.list((FilenameFilter) new FilenameFilter() {
                    @Override
                    public boolean accept(File current, String name) {
                        return !(new File(current, name).isDirectory());
                    }
                });
                String resJSON ="{";
                if(files.length != 0)
                    resJSON += "\"files\":[";
                for(int i=0;i<files.length ;i++)
                    resJSON += "\"" + files[i] + "\""  + ", ";
                if(files.length != 0)
                    resJSON += "\"" + files[files.length - 1] + "\""  + "], ";
                else{
                }
                if(directories.length != 0)
                    resJSON += "\"dirs\":[";
                for(int i=0;i<directories.length -1 ;i++)
                    resJSON += "\"" +  directories[i] + "\""  + ", ";
                if(directories.length != 0)
                    resJSON += "\"" + directories[directories.length - 1] + "\""  + "]";
                resJSON +=", \"base\":\"" + path.getAbsolutePath() + "\"}";
                map.put(directory, resJSON);
                return resJSON.replace(", ,", ", ");
            }else{
                System.out.println("Error : File as Dir");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public static String getDefaultPath(){
        return javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + (new File("").separator) + "Desktop";
    }
    
}
