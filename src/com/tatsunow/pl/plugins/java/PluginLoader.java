package com.tatsunow.pl.plugins.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.jar.JarFile;

import com.tatsunow.pl.Main;

public class PluginLoader {
	

	
	public void scan(){
		for(File f : Main.getPluginsFolder().listFiles()){
			if(!f.isDirectory()){
				if(f.getAbsolutePath().endsWith(".jar")){
					dump(f);
				}
			}
		}
	}

	private HashMap<String, String> getDescriptionFile(File f){
		StringBuilder str = new StringBuilder();
		HashMap<String, String> hash = new HashMap<String, String>();
		try {
			JarFile jar = new JarFile(f);
			InputStream in = jar.getInputStream(jar.getJarEntry("plugin.yml"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while(reader.ready()){
				str.append(reader.readLine() + "\n");
			}
			for(String s : str.toString().split("\n")){
				if(s != null){
					String key = s.split(":")[0];
					String value = s.split(":")[1];
					if(key.equals("main")){
						value=value.replaceAll(" ", "");
					}
					hash.put(key, value);
					
				}
			}
			in.close();
			jar.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hash;
	}
	private void dump(File f) {
		try {
			@SuppressWarnings("deprecation")
						URL url =  f.toURL();
						@SuppressWarnings("resource")
						ClassLoader loader = new URLClassLoader(new URL[]{url});
						HashMap<String, String> description = getDescriptionFile(f);
						try {
							Class<?> c = loader.loadClass(description.get("main"));
							try {
								JavaPlugin jp = (JavaPlugin)c.newInstance();
								jp.onEnable();
							} catch (InstantiationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
		} catch(IOException e){
			
		}
	}
				
				
		
	

}
