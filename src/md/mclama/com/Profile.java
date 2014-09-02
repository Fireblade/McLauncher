package md.mclama.com;

import java.util.ArrayList;
import java.util.List;

public class Profile {
	public String name;
	//public String[] mods = new String[50];
	public List<String> mods = new ArrayList<String>();
	
	public Profile(String name, String mods) {
		this.name = name;
		if(mods!=null){
			this.mods.add(mods);
		}
	}
	
	public boolean findMod(String mod){
		for(int i=mods.size()-1; i>=0; i--) {
			String p = mods.get(i);
			if(p.equals(mod)){return false;}
		}
		return true;
	}
	
	public void removeMod(String mod){
		for(int i=mods.size()-1; i>=0; i--) {
			String p = mods.get(i);
			if(p.equals(mod)){
				mods.remove(i);
			}
		}
	}

}
