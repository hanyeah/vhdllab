package hr.fer.zemris.vhdllab.applets.schema2.interfaces;

import hr.fer.zemris.vhdllab.applets.schema2.exceptions.DuplicateKeyException;
import hr.fer.zemris.vhdllab.applets.schema2.exceptions.OverlapException;
import hr.fer.zemris.vhdllab.applets.schema2.exceptions.UnknownKeyException;
import hr.fer.zemris.vhdllab.applets.schema2.misc.Caseless;



/**
 * Sucelje za skup komponenti koje 
 * se nalaze u shemi.
 * 
 * @author Axel
 *
 */
public interface ISchemaComponentCollection extends ISerializable {
		
	/**
	 * Dohvaca komponentu zadanog imena.
	 * 
	 * @param componentName
	 * Jedinstveni String identifikator
	 * komponente.
	 * 
	 * @return
	 * Vraca komponentu ciji je jedinstveni
	 * identifikator zadano ime, ili null
	 * ako takva ne postoji.
	 * Pritom ime nije dio IParameterCollection,
	 * tj. sama komponenta ne zna svoje ime.
	 * Ime je jedinstveni identifikator po kojem
	 * je moguce dohvacati komponente.
	 * 
	 */
	ISchemaComponent fetchComponent(Caseless componentName);
	
	
	/**
	 * Odreduje da li postoji zadano ime.
	 * 
	 * @param componentName
	 * Ime komponente.
	 * 
	 * @return
	 * True ako komponenta zadanog imena
	 * postoji u kolekciji, false inace.
	 * 
	 */
	boolean containsName(Caseless componentName);
	
	
	/**
	 * Dohvaca komponentu na zadanim koordinatama.
	 * 
	 * @param x
	 * @param y
	 *  
	 * @return
	 * Vraca komponentu ako takva postoji na zadanim
	 * koordinatama. Komponenta postoji na nekoj
	 * koordinati ako bounding box komponente obuhvaca
	 * koordinatu.
	 * Ako ista ne postoji, vraca se null.
	 * 
	 */
	ISchemaComponent fetchComponent(int x, int y);
	
	
	/**
	 * Odreduje da li postoji komponenta
	 * na koordinatama.
	 * 
	 * @param x
	 * @param y
	 * @return
	 * Vraca true ako postoji, false inace.
	 * Komponenta postoji na koordinati ako
	 * bounding box komponente obuhvaca koordinatu.
	 * 
	 */
	boolean containsAt(int x, int y);
	
	
	/**
	 * Dodaje komponentu na shemu,
	 * na zadane koordinate.
	 * 
	 * @param x
	 * @param y
	 * @param component
	 * 
	 * @throws DuplicateKeyException
	 * Ako postoji komponenta tog imena.
	 * @throws OverlapException
	 * Ako dolazi do preklapanja.
	 * 
	 */
	void addComponent(int x, int y, ISchemaComponent component) throws DuplicateKeyException, OverlapException;
	
	/**
	 * Zadanu komponentu mice sa sheme.
	 * 
	 * @param name
	 * Jedinstveni identifikator komponente
	 * koja ce biti removeana sa sheme.
	 * @throws UnknownKeyException
	 * Ako ne postoji komponenta tog imena.
	 * 
	 */
	void removeComponent(Caseless name) throws UnknownKeyException;
	
}



















