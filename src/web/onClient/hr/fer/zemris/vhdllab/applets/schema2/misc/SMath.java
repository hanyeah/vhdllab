package hr.fer.zemris.vhdllab.applets.schema2.misc;

import java.util.List;




public class SMath {
	
	/**
	 * Vraca najblizi port od lokacije
	 * (x, y) u sustavu komponente
	 * ako je takav udaljen manje od
	 * dist. Ako je udaljen vise od dist
	 * ili ne postoji, vraca se -1.
	 * 
	 * @param x
	 * @param y
	 * @param dist
	 * @param ports
	 * Lista portova. Vraca se -1 za null.
	 * @return
	 */
	public static int calcClosestPort(int x, int y, int dist, List<SchemaPort> ports) {
		if (ports == null) return -1;
		
		int index = -1, i = 0;
		double mindist = dist;
		double ndist = 0.f;
		
		for (SchemaPort port : ports) {
			ndist = Math.sqrt((x - port.getOffset().x) * (x - port.getOffset().x) 
					+ (y - port.getOffset().y) * (y - port.getOffset().y));
			if (ndist < mindist) {
				mindist = ndist;
				index = i;
			}
			i++;
		}
		
		return index;
	}
	
	/**
	 * Vraca najblizi port od lokacije
	 * location u sustavu komponente
	 * ako je takav udaljen manje od
	 * dist. Ako je udaljen vise od dist
	 * ili ne postoji, vraca se -1.
	 * 
	 * @param x
	 * @param y
	 * @param dist
	 * @param ports
	 * Lista portova. Vraca se -1 za null.
	 * @return
	 */
	public static int calcClosestPort(XYLocation location, int dist, List<SchemaPort> ports) {
		return calcClosestPort(location.x, location.y, dist, ports);
	}
	
	/**
	 * Pretvara zadane (x, y) koordinate u sustavu
	 * sheme u koordinate u sustavu komponente cija
	 * je gornja lijeva koordinata odredena s
	 * componentCoord.
	 * 
	 * @param x
	 * @param y
	 * @param componentCoord
	 * @return
	 */
	public static XYLocation toCompCoord(int x, int y, XYLocation componentCoord) {
		return new XYLocation(x - componentCoord.x, y - componentCoord.y);
	}
	
	/**
	 * Pretvara zadane s location koordinate u sustavu
	 * sheme u koordinate u sustavu komponente cija
	 * je gornja lijeva koordinata odredena s
	 * componentCoord.
	 * 
	 * @param location
	 * @param componentCoord
	 * @return
	 */
	public static XYLocation toCompCoord(XYLocation location, XYLocation componentCoord) {
		return new XYLocation(location.x - componentCoord.x,
				location.y - componentCoord.y);
	}
	
	/**
	 * Vraca manji od dva broja.
	 * 
	 * @param a
	 * @param b
	 * @return
	 * Manji broj. Ako su jednaki, vraca b.
	 */
	public static int min(int a, int b) {
		if (a < b) return a;
		return b;
	}
	
	/**
	 * Vraca veci od dva broja.
	 * 
	 * @param a
	 * @param b
	 * @return
	 * Veci broj. Ako su jednaki, vraca b.
	 */
	public static int max(int a, int b) {
		if (a > b) return a;
		return b;
	}
	
	/**
	 * Vraca true ako je num u zatvorenom
	 * intervalu [a, b].
	 * 
	 */
	public static boolean within(int num, int a, int b) {
		return (num >= a && num <= b);
	}
	
	/**
	 * Vraca true ako je num u zatvorenom
	 * intervalu [a, b] ako je a <= b,
	 * ili u zatvorenom intervalu [b, a] ako
	 * je a > b.
	 * 
	 */
	public static boolean withinOrd(int num, int a, int b) {
		if (a > b) {
			int t = b;
			b = a;
			a = t;
		}
		return (num >= a && num <= b);
	}
	
	/**
	 * Vraca true ako je num u otvorenom
	 * intervalu (a, b).
	 * 
	 */
	public static boolean inside(int num, int a, int b) {
		return (num > a && num < b);
	}
	
	/**
	 * Vraca true ako je num u otvorenom
	 * intervalu (a, b) ako je a <= b,
	 * ili u otvorenom intervalu (b, a) ako
	 * je a > b.
	 * 
	 */
	public static boolean insideOrd(int num, int a, int b) {
		if (a > b) {
			int t = b;
			b = a;
			a = t;
		}
		return (num > a && num < b);
	}
}










