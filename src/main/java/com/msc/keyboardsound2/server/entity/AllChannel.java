package com.msc.keyboardsound2.server.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author micky
 */
public class AllChannel {

    public static List<Channel> channels = new LinkedList();

    private static List<String> channelsStr = null;
    
    public static List<String> getAllChannelName() {
	if (channels.isEmpty()) {
	    return new ArrayList<String>(0);
	}
        if (channelsStr != null) {
            return channelsStr;
        }
	List<String> res = new ArrayList(channels.size());
	for (Channel c : channels){
	    res.add(c.getName());
	}
        channelsStr = res;
	return res;
    }
    
}
