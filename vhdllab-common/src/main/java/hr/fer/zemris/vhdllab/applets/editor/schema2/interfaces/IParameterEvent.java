/*******************************************************************************
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package hr.fer.zemris.vhdllab.applets.editor.schema2.interfaces;

import hr.fer.zemris.vhdllab.applets.editor.schema2.misc.ChangeTuple;

import java.util.List;









/**
 * Svaki IParameter moze sadrzavati ovakav objekt.
 * Nakon promjene vrijednosti parametra ovaj objekt radi
 * promjene na citavoj shemi.
 * BITNO: <b>NAKON promjene vrijednosti parametra.</b>
 * 
 * @author brijest
 *
 */
public interface IParameterEvent {
	
	/**
	 * Vraca deep copy ovog objekta.
	 * 
	 * @return
	 * Deep copy ovog objekta.
	 */
	IParameterEvent copyCtor();

	/**
	 * Lista promjena koje obavlja ovaj event.
	 * Lista koja se vraca ne smije biti mijenjana,
	 * a implementirana je kao staticki read-only
	 * objekt.
	 * 
	 */
	List<ChangeTuple> getChanges();
	
	/**
	 * Svaki IParameterEvent putem ove metode obavlja
	 * promjene na shemi. Promjene se obavljaju na info
	 * objektu u skladu s navedenim parametrom. Treci
	 * argument je ovisan o samom tipu eventa.
	 * 
	 * @param oldvalue
	 * Vrijednost parametra, prije nego sto je promijenjena.
	 * @param parameter
	 * @param info
	 * @param wire
	 * Ako je parametar dio zice, inace null.
	 * @param component
	 * Ako je parametar dio komponente, inace null.
	 * @return
	 * Ako je promjena uspjesna, vratit ce true. Ako nije,
	 * vratit ce false i PRITOM NECE napraviti nikakve promjene.
	 */
	boolean performChange(Object oldvalue, IParameter parameter, ISchemaInfo info,
			ISchemaWire wire, ISchemaComponent component);
	
	
	/**
	 * Odgovara da li su promjene koje izvrsi ovaj event
	 * funkcija ISKLJUCIVO vrijednosti parametra koji je specificiran.
	 * 
	 */
	boolean isUndoable();
}













