package com.msc.keyboardsound2.server.entity;

import com.msc.keyboardsound2.server.SoundList;

/**
 *
 * @author micky
 */
public class Channel {

    private String name;
    private SoundList list;

    public Channel(String name){
	this.name = name;
    }
    
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public SoundList getList() {
	return list;
    }

    public void setList(SoundList list) {
	this.list = list;
    }
    
    
    
}
