package com.tatsunow.pl;

import java.io.File;

import com.tatsunow.pl.plugins.java.PluginLoader;

public class Main {
	
	
	public static void main(String[] args) {
		if(getPluginsFolder().exists()){
			getPluginsFolder().mkdir();
			PluginLoader loader = new PluginLoader();
			loader.scan();
		}
	}

	public static File getPluginsFolder() {
		File folder = new File("").getAbsoluteFile();
		File file = new File(folder, "plugins");
		return file ;
	}
	
}
