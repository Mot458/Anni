package cz.mot.anni.maps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

public class MapLoader {
    private final Logger log;
    private final File dataFolder;

    public MapLoader(Logger log, File dataFolder) {
        this.dataFolder = dataFolder;
        this.log = log;
    }

    public boolean loadMap(String name) {
        File mapsFolder = new File(dataFolder, "maps");
        if (!mapsFolder.exists())
            return false;
        File source = new File(mapsFolder, name);
        if (!source.exists())
            return false;
        
        Bukkit.unloadWorld(name, false);

        File destination = new File(dataFolder.getParentFile().getParentFile(),
                name + File.separator + "region");
        try {
            copyFolder(source, destination);
            return true;
        } catch (IOException e) {
            log.severe("Could not load map " + name);
            return false;
        }
    }
    
    public boolean saveMap(String name) {
        File mapsFolder = new File(dataFolder, "maps");
        if (!mapsFolder.exists())
            mapsFolder.mkdir();
        File source = new File(dataFolder.getParentFile().getParentFile(), name + File.separator + "region");
        if (!source.exists())
            return false;
        
        File destination = new File(mapsFolder, name);
        try {
            copyFolder(source, destination);
            return true;
        } catch (IOException e) {
            log.severe("Could not save map " + name);
            return false;
        }
    }

    private void copyFolder(File src, File dest) throws IOException {
        if (!src.exists()) {
            log.severe("File " + src.toString()
                    + " does not exist, cannot copy");
            return;
        }

        if (src.isDirectory()) {
            boolean existed = dest.exists();
            if (!existed)
                dest.mkdir();

            String[] srcFiles = src.list();

            if (existed)
                log.info("Copying folder " + src.getAbsolutePath()
                        + " and overwriting " + dest.getAbsolutePath());
            else
                log.info("Copying folder " + src.getAbsolutePath() + " to "
                        + dest.getAbsolutePath());

            for (String file : srcFiles) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                copyFolder(srcFile, destFile);
            }

            if (existed)
                log.info("Overwrote folder " + dest.getAbsolutePath());
            else
                log.info("Copied folder " + dest.getAbsolutePath());

        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            boolean existed = dest.exists();

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();

            if (existed)
                log.info("Overwrote file " + dest.getName());
            else
                log.info("Copied file " + dest.getName());
        }
    }
}
