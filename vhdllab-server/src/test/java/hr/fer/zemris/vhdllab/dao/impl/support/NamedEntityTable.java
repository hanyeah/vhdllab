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
package hr.fer.zemris.vhdllab.dao.impl.support;

import hr.fer.zemris.vhdllab.entity.NamedEntity;

import javax.persistence.Entity;

/**
 * Used as an entity to test persistence of {@link NamedEntity}.
 * 
 * @author Miro Bezjak
 * @version 1.0
 * @since vhdllab2
 */
@Entity
public class NamedEntityTable extends NamedEntity {

    private static final long serialVersionUID = 1L;

    public NamedEntityTable() {
        super();
    }

}
