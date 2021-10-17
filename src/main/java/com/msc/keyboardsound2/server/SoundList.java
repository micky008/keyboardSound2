package com.msc.keyboardsound2.server;

import com.msc.keyboardsound2.server.entity.Config;
import com.msc.keyboardsound2.server.entity.ListLine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Michael
 */
public class SoundList {

    private Map<Integer, String> songsRaw;
    private Map<Integer, String> songsVisu;

    public Map<Integer, String> getSongsVisu() {
        return songsVisu;
    }

    public String getSondFileName(int id) {
        return songsRaw.get(id);
    }

    public static Map<String, SoundList> read(Config config) throws IOException {
        File file = new File(config.getServer().liste);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        Map<String, SoundList> res = new HashMap<>();
        Integer id = 1;
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        for (String line : lines) {
            if (line.startsWith("#")) {
                continue;
            }
            if (line.isEmpty()) {
                continue;
            }
            ListLine lline = new ListLine(line);
            if (!res.containsKey(lline.getChannel())) {
                SoundList sl = new SoundList();
                sl.songsRaw = new HashMap<>();
                sl.songsVisu = new TreeMap<>();
                res.put(lline.getChannel(), sl);
            }
            res.get(lline.getChannel()).songsRaw.put(id, lline.getAbsoluteFileName());
            res.get(lline.getChannel()).songsVisu.put(id, lline.getName());
            id++;
        }
        return res;
    }

}
