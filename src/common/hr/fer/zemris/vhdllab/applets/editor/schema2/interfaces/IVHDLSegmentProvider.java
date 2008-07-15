package hr.fer.zemris.vhdllab.applets.editor.schema2.interfaces;

import hr.fer.zemris.vhdllab.applets.editor.schema2.misc.Caseless;

import java.util.Map;



/**
 * Sluzi za generiranje djelica VHDL koda
 * kad se gradi citav strukturni kod.
 * Npr. vraca dio vezan uz port map
 * koji ce biti integriran u kod.
 * Takoder, vraca djelice VHDL koda potrebne
 * za eventualnu definiciju signala koje potrebuje
 * komponenta.
 * 
 * Podrazumijeva se da je getSignalDefinitions()
 * UVIJEK pozvan prije getInstantiation() i da
 * getInstantion() nikad nije pozvan bez da je
 * prethodno pozvan getSignalDefinitions().
 * 
 * @author Axel
 *
 */
public interface IVHDLSegmentProvider {
	/**
	 * Vraca kod za signale koji su potrebni
	 * za prospajanje zica na komponentu.
	 * 
	 * @param info
	 * Info sluzi kako bi se u slucaju koristenja
	 * pomocnih signala razrijesili konflikti imena.
	 * @return
	 */
	String getSignalDefinitions(ISchemaInfo info);
	
	/**
	 * Vraca linije koda vezanu uz instanciranje
	 * komponente i prospajanje zica.
	 * 
	 * @param info
	 * Info sluzi kako bi se u slucaju koristenja
	 * pomocnih signala razrijesili konflikti imena.
	 * @param renamedSignals
	 * Ako implementacija namjerava koristiti ime signala
	 * koji se nalazi u schematicu i cije se ime takoder
	 * se nalazi u navedenoj mapi, onda ta implementacija
	 * mora zamijeniti to ime vrijednoscu iz mape pod
	 * tim kljucem. 
	 * @return
	 */
	String getInstantiation(ISchemaInfo info, Map<Caseless, Caseless> renamedSignals);
}








