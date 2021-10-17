package com.msc.keyboardsound2.server.entity;

/**
 *
 * @author micky
 */
public class ListLine {

    private String channel;
    private String absoluteFileName;
    private String name;

    public ListLine(String line) {
        String[] split = line.split(";");
        channel = split[0];
        absoluteFileName = split[1];
        name = split[2];
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAbsoluteFileName() {
        return absoluteFileName;
    }

    public void setAbsoluteFileName(String absoluteFileName) {
        this.absoluteFileName = absoluteFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
