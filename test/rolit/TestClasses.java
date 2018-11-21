package rolit;

public class TestClasses extends junit.framework.TestCase {
		
	/**
	 * This test will check if the model correctly calucaltes blocking moves on a vertical line upwards.
	 */
	public void testVerticalUP() {
		Board bord = new Board();
		bord.vakjes[0] = 1;
		bord.vakjes[7] = 1;
		bord.vakjes[11] = 1;
		bord.vakjes[12] = 2;
		bord.vakjes[44] = 1;
		bord.vakjes[46] = 1;
		bord.vakjes[37] = 1;
		bord.vakjes[63] = 1;
		bord.vakjes[56] = 1;
		bord.vakjes[8] = 1;
		bord.vakjes[24] = 1;
		bord.vakjes[59] = 1;
		bord.vakjes[31] = 2;
		bord.vakjes[48] = 2;
		bord.vakjes[16] = 2;
		bord.vakjes[54] = 2;
		bord.vakjes[55] = 2;
		bord.verticalUp(1);
		String check = "";
		String result = "";
		check =	bord.toString(result);
		assertEquals("Check the result!", "204047", check);
		
	}
	
	/**
	 * This test will check if the model correctly calucaltes blocking moves on a vertical line downwards.
	 */
	public void testVerticalDown() {
		Board bord = new Board();
		bord.vakjes[0] = 1;
		bord.vakjes[7] = 1;
		bord.vakjes[11] = 1;
		bord.vakjes[12] = 2;
		bord.vakjes[44] = 1;
		bord.vakjes[46] = 1;
		bord.vakjes[37] = 1;
		bord.vakjes[63] = 1;
		bord.vakjes[56] = 1;
		bord.vakjes[8] = 1;
		bord.vakjes[24] = 1;
		bord.vakjes[59] = 1;
		bord.vakjes[31] = 2;
		bord.vakjes[48] = 2;
		bord.vakjes[16] = 2;
		bord.vakjes[54] = 2;
		bord.vakjes[55] = 2;
		bord.verticalDown(1);
		String check = "";
		String result = "";
		check =	bord.toString(result);
		assertEquals("Check the result!", "4362", check);
		
	}
	
	/**
	 * This test will check if the model correctly calucaltes blocking moves on a vertical line downwards.
	 */
	public void testHorizontalright() {
		Board bord = new Board();
		bord.vakjes[0] = 1;
		bord.vakjes[7] = 1;
		bord.vakjes[11] = 1;
		bord.vakjes[12] = 2;
		bord.vakjes[44] = 1;
		bord.vakjes[46] = 1;
		bord.vakjes[37] = 1;
		bord.vakjes[63] = 1;
		bord.vakjes[56] = 1;
		bord.vakjes[8] = 1;
		bord.vakjes[24] = 1;
		bord.vakjes[59] = 1;
		bord.vakjes[31] = 2;
		bord.vakjes[48] = 2;
		bord.vakjes[16] = 2;
		bord.vakjes[54] = 2;
		bord.vakjes[55] = 2;
		bord.horizontalRight(1);
		String check = "";
		String result = "";
		check =	bord.toString(result);
		assertEquals("Check the result!", "1329", check);
		
	}
	
	/**
	 * This test will check if the model correctly calucaltes blocking moves on a horizontal line leftwards.
	 */
	public void testHorizontalleft() {
		Board bord = new Board();
		bord.vakjes[0] = 1;
		bord.vakjes[7] = 1;
		bord.vakjes[11] = 1;
		bord.vakjes[12] = 2;
		bord.vakjes[44] = 1;
		bord.vakjes[46] = 1;
		bord.vakjes[37] = 1;
		bord.vakjes[63] = 1;
		bord.vakjes[56] = 1;
		bord.vakjes[8] = 1;
		bord.vakjes[24] = 1;
		bord.vakjes[59] = 1;
		bord.vakjes[31] = 2;
		bord.vakjes[48] = 2;
		bord.vakjes[16] = 2;
		bord.vakjes[54] = 2;
		bord.vakjes[55] = 2;
		bord.horizontalLeft(1);
		String check = "";
		String result = "";
		check =	bord.toString(result);
		assertEquals("Check the result!", "34", check);
		
	}
	
	/**
	 * This test will check if the model correctly calucaltes blocking moves on a diagonal line left upwards.
	 */
	public void testDiagonalLeftUp() {
		Board bord = new Board();
		bord.vakjes[0] = 1;
		bord.vakjes[7] = 1;
		bord.vakjes[11] = 1;
		bord.vakjes[12] = 2;
		bord.vakjes[44] = 1;
		bord.vakjes[46] = 1;
		bord.vakjes[37] = 1;
		bord.vakjes[63] = 1;
		bord.vakjes[56] = 1;
		bord.vakjes[8] = 1;
		bord.vakjes[24] = 1;
		bord.vakjes[59] = 1;
		bord.vakjes[31] = 2;
		bord.vakjes[48] = 2;
		bord.vakjes[16] = 2;
		bord.vakjes[54] = 2;
		bord.vakjes[55] = 2;
		bord.hasLinksOmhoog(1);
		String check = "";
		String result = "";
		check =	bord.toString(result);
		assertEquals("Check the result!", "192645", check);
		
	}
	
	/**
	 * This test will check if the model correctly calucaltes blocking moves on a diagonal line right upwards.
	 */
	public void testDiagonalRightUp() {
		Board bord = new Board();
		bord.vakjes[0] = 1;
		bord.vakjes[14] = 2;
		bord.vakjes[11] = 1;
		bord.vakjes[12] = 2;
		bord.vakjes[44] = 1;
		bord.vakjes[46] = 1;
		bord.vakjes[37] = 1;
		bord.vakjes[63] = 1;
		bord.vakjes[56] = 1;
		bord.vakjes[8] = 1;
		bord.vakjes[24] = 1;
		bord.vakjes[59] = 1;
		bord.vakjes[31] = 2;
		bord.vakjes[48] = 2;
		bord.vakjes[16] = 2;
		bord.vakjes[54] = 2;
		bord.vakjes[55] = 2;
		bord.vakjes[19] = 1;
		bord.vakjes[21] = 1;
		bord.vakjes[42] = 1;
		bord.vakjes[61] = 1;
		bord.vakjes[20] = 2;
		bord.vakjes[51] = 2;
		bord.naarlinksomlaag(1);
		String check = "";
		String result = "";
		check =	bord.toString(result);
		assertEquals("Check the result!", "571347", check);
		
	}
	
	/**
	 * This test will check if the model correctly calucaltes blocking moves on a diagonal line right downwards.
	 */
	public void testDiagonalRightDown() {
		Board bord = new Board();
		bord.vakjes[0] = 1;
		bord.vakjes[2] = 1;
		bord.vakjes[5] = 1;
		bord.vakjes[7] = 1;
		bord.vakjes[9] = 2;
		bord.vakjes[14] = 2;
		bord.vakjes[29] = 1;
		bord.vakjes[31] = 1;
		bord.vakjes[38] = 2;
		bord.vakjes[40] = 1;
		bord.vakjes[42] = 1;
		bord.vakjes[45] = 1;
		bord.vakjes[47] = 1;
		bord.vakjes[49] = 2;
		bord.vakjes[54] = 2;
		bord.hasRechtsOmlaag(1);
		String check = "";
		String result = "";
		check =	bord.toString(result);
		assertEquals("Check the result!", "18235863", check);
	}
	
	/**
	 * This test will check if the model correctly calucaltes blocking moves on a diagonal line left downwards.
	 */
	public void testDiagonalLeftDown() {
		Board bord = new Board();
		bord.vakjes[2] = 1;
		bord.vakjes[7] = 1;
		bord.vakjes[9] = 2;
		bord.vakjes[14] = 2;
		bord.vakjes[31] = 1;
		bord.vakjes[38] = 2;
		bord.vakjes[42] = 1;
		bord.vakjes[45] = 3;
		bord.vakjes[49] = 2;
		bord.vakjes[63] = 1;
		bord.naarrechtsomhoog(1);
		String check = "";
		String result = "";
		check =	bord.toString(result);
		assertEquals("Check the result!", "16215256", check);
	}
	
	/**
	 * Deze test kijkt of the methode isFull wel goed werkt, maak gebruik van assertTrue.
	 */
	public void testifFull() {
		Board bord = new Board();
		for (int i= 0; i <= 63; i++) {
			bord.vakjes[i] = 1;
		}
		boolean check = bord.isFull();
		assertTrue(check);
	}
	
	/**
	 * Deze test controlleert of de methode NaarBeneden wel goed vakjes naar beneden verovert.
	 */
	public void testVeroverNaarBeneden() {
		Board bord = new Board();
		bord.vakjes[8] = 2;
		bord.vakjes[16] = 1;
		bord.NaarBeneden(0, 1);
		bord.vakjes[19] = 2;
		bord.NaarBeneden(11, 1);
		bord.vakjes[55] = 2;
		bord.vakjes[63] = 1;
		bord.NaarBeneden(47, 1);
		String check = "";
		check =	bord.toString();
		String result = "";
		result = result + check.charAt(0) + check.charAt(8) + check.charAt(16) + check.charAt(11) + check.charAt(19) + check.charAt(27) + check.charAt(47) + check.charAt(55) + check.charAt(63);
		assertEquals("Check the result!", "111111111", result);
	}
	
	/**
	 * Deze test controlleert of de methode NaarBoven wel goed vakjes naar boven verovert.
	 */
	public void testVeroverNaarBoven() {
		Board bord = new Board();
		bord.vakjes[0] = 1;
		bord.vakjes[8] = 2;
		bord.NaarBoven(16, 1);
		bord.NaarBoven(43, 1);
		bord.vakjes[47] = 1;
		bord.vakjes[55] = 2;
		bord.NaarBoven(63, 1);
		String check = "";
		check =	bord.toString();
		String result = "";
		result = result + check.charAt(0) + check.charAt(8) + check.charAt(16) + check.charAt(27) + check.charAt(35) + check.charAt(43) + check.charAt(47) + check.charAt(55) + check.charAt(63);
		assertEquals("Check the result!", "111111111", result);
	}
	
	/**
	 * Deze test controlleert of de methode NaarRechts wel goed vakjes naar rechts verovert.
	 */
	public void testVeroverNaarRechts() {
		Board bord = new Board();
		bord.vakjes[1] = 2;
		bord.vakjes[2] = 1;
		bord.NaarRechts(0, 1);
		bord.vakjes[62] = 2;
		bord.vakjes[63] = 1;
		bord.NaarRechts(61, 1);
		String check = "";
		check =	bord.toString();
		String result = "";
		result = result + check.charAt(0) + check.charAt(1) + check.charAt(2) + check.charAt(61) + check.charAt(62) + check.charAt(63);
		assertEquals("Check the result!", "111111", result);
	}
	
	/**
	 * Deze test controlleert of de methode NaarLinks wel goed vakjes naar links verovert.
	 */
	public void testVeroverNaarLinks() {
		Board bord = new Board();
		bord.vakjes[0] = 1;
		bord.vakjes[1] = 2;
		bord.NaarLinks(2, 1);
		bord.vakjes[61] = 1;
		bord.vakjes[62] = 2;
		bord.NaarLinks(63, 1);
		String check = "";
		check =	bord.toString();
		String result = "";
		result = result + check.charAt(0) + check.charAt(1) + check.charAt(2) + check.charAt(61) + check.charAt(62) + check.charAt(63);
		assertEquals("Check the result!", "111111", result);
	}
	
	/**
	 * Deze test controlleert of de methode LinksOmlaag wel goed vakjes naar links omlaag verovert.
	 */
	public void testVeroverDiagonaalLinksOmlaag() {
		Board bord = new Board();
		bord.vakjes[56] = 1;
		bord.vakjes[49] = 2;
		bord.LinksOmlaag(42, 1);
		bord.vakjes[61] = 1;
		bord.vakjes[54] = 2;
		bord.LinksOmlaag(47, 1);
		String check = "";
		check =	bord.toString();
		String result = "";
		result = result + check.charAt(42) + check.charAt(49) + check.charAt(56) + check.charAt(47) + check.charAt(54) + check.charAt(61);
		System.out.println(result);
		assertEquals("Check the result!", "111111", result);
	}
	
	/**
	 * Deze test controlleert of de methode RechtsOmlaag wel goed vakjes naar rechts omlaag verovert.
	 */
	public void testVeroverDiagonaalRechtsOmlaag() {
		Board bord = new Board();
		bord.vakjes[63] = 1;
		bord.vakjes[54] = 2;
		bord.RechtsOmlaag(45, 1);
		String check = "";
		check =	bord.toString();
		String result = "";
		result = result + check.charAt(45) + check.charAt(54) + check.charAt(63);
		assertEquals("Check the result!", "111", result);
	}
	
	/**
	 * Deze test controlleert of de methode LinksOmhoog wel goed vakjes naar links omhoog verovert.
	 */
	public void testVeroverDiagonaalLinksOmhoog() {
		Board bord = new Board();
		bord.vakjes[0] = 1;
		bord.vakjes[9] = 2;
		bord.LinksOmhoog(18, 1);
		String check = "";
		check =	bord.toString();
		String result = "";
		result = result + check.charAt(0) + check.charAt(9) + check.charAt(18);
		
		assertEquals("Check the result!", "111", result);
	}
	
	/**
	 * Deze test controlleert of de methode RechtsOmhoog wel goed vakjes naar rechts omhoog verovert.
	 */
	public void testVeroverDiagonaalRechtsOmhoog() {
		Board bord = new Board();
		bord.vakjes[42] = 1;
		bord.vakjes[49] = 2;
		bord.RechtsOmhoog(56, 1);
		String check = "";
		check =	bord.toString();
		String result = "";
		result = result + check.charAt(42) + check.charAt(49) + check.charAt(56);
		assertEquals("Check the result!", "111", result);
	}
}
