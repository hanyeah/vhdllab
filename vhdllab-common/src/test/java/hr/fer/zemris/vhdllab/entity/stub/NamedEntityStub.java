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
package hr.fer.zemris.vhdllab.entity.stub;

import hr.fer.zemris.vhdllab.entity.NamedEntity;

import org.apache.commons.beanutils.BeanUtils;

public class NamedEntityStub extends NamedEntity {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "entity name";
    public static final String NAME_UPPERCASE = NAME.toUpperCase();
    public static final String NAME_2 = "another entity name";

    public NamedEntityStub() throws Exception {
        BeanUtils.copyProperties(this, new BaseEntityStub());
        setName(NAME);
    }

}