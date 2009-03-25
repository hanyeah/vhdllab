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
