package rolit;

import java.util.ArrayList;

public interface Strategie {
	
	public int doeZet(int spelerkleurint, int[] speelbord);

	public ArrayList<Integer> getHint(int spelerkleurint, int[] speelbord);
	
}
