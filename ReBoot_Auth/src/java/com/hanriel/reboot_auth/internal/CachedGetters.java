package com.hanriel.reboot_auth.internal;

import com.hanriel.reboot_auth.util.VersionUtils;

public class CachedGetters {
	
	private static long lastOnlinePlayersRefresh;
	private static int onlinePlayers;
	

	public static int getOnlinePlayers() {
		long now = System.currentTimeMillis();
		if (lastOnlinePlayersRefresh == 0 || now - lastOnlinePlayersRefresh > 1000) {
			// getOnlinePlayers() could be expensive if called frequently
			lastOnlinePlayersRefresh = now;
			onlinePlayers = VersionUtils.getOnlinePlayers().size();
		}
		
		return onlinePlayers;
	}
	
}
