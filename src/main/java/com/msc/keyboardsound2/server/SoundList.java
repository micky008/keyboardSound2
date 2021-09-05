package com.msc.keyboardsound2.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Michael
 */
public class SoundList {

    private Config config;
    private static Map<Integer, String> songsRaw;
    private static Map<Integer, String> songsVisu;

    public SoundList(Config config) {
        this.config = config;
        refresh();
    }
    
    private SoundList(){}

    public void refresh() {
        try {
            read();
        } catch (IOException ex) {
            Logger.getLogger(SoundList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void refreshStatic(){
        SoundList sl = new SoundList();
        sl.refresh();
    }
    
    public static Map<Integer, String> getSongsVisu() {
        return songsVisu;
    }
    
    public static String getSondFileName(int id) {
        return songsRaw.get(id);
    }
    
    private void read() throws IOException {
        File file = new File(config.getServer().liste);
        if (!file.exists()){
            throw new FileNotFoundException();
        }
        songsVisu = new TreeMap<>();
        songsRaw = new HashMap<>();
        List<String> sons = FileUtils.readLines(file, "UTF-8");
        Integer id = 1;
        for (String son : sons) {
            if (son.startsWith("#")){
                continue;
            }
            String[] line = son.split(";");
            songsRaw.put(id, line[0]);
            songsVisu.put(id, line[1]);
            id++;
        }
    }

}
