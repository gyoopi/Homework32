import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Saver {
    public static void main(String[] args) {
        creatorDirectory("/Users/shamil/Games2");
        creatorDirectory("/Users/shamil/Games2/savegames");
        String zipPath = "/Users/shamil/Games2/savegames/savesZip.zip";

        GameProgress save1 = new GameProgress(100,3,6,160);
        GameProgress save2 = new GameProgress(50,7,40,11245);
        GameProgress save3 = new GameProgress(1,10,60,20000);
        List<GameProgress> saves = new ArrayList<>();
        saves.add(save1);
        saves.add(save2);
        saves.add(save3);

        List<String> dirPaths = new ArrayList<>();

        for(int i=1; i<saves.size()+1; i++) {
            String pathOfSave = "/Users/shamil/Games2/savegames/save"+i+".dat";
            dirPaths.add(pathOfSave);
            saveGame(pathOfSave, saves.get(i-1));
        }

        zipFiles(zipPath, dirPaths);

        for(int i=1; i<saves.size()+1; i++) {
            String pathOfSave = "/Users/shamil/Games2/savegames/save"+i+".dat";
            File toDelete = new File(pathOfSave);
            toDelete.delete();
        }

        System.out.println("YOUR GAME PROGRESS IS SAVED AND ZIPPED");
    }

    public static void saveGame(String pathname, GameProgress progress){
        File saveFile = new File(pathname);
        try{
            saveFile.createNewFile();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        try (FileOutputStream writer = new FileOutputStream(pathname, true)) {
            ObjectOutputStream fos = new ObjectOutputStream(writer);
            fos.writeObject(progress.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String pathname, List<String> pathsList){
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathname))) {
                for(int i=1 ; i <pathsList.size()+1;i++) {
                    FileInputStream fis = new FileInputStream(pathsList.get(i - 1));
                    ZipEntry entry = new ZipEntry(pathsList.get(i - 1));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);

                }
                zout.closeEntry();
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
    }

    public static void creatorDirectory(String nameDir){
        File installer = new File(nameDir);
        if(installer.mkdir()){
            System.out.println("saves files in the folder Savegames");
        }else{
            System.out.println("ERROR!!! folder Savegames wasn't created");
        }
    }
}
