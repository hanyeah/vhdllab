package hr.fer.zemris.vhdllab.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * A test case for FileResource.
 * 
 * @author Miro Bezjak
 */
public class FileResourceTest {

    private FileResource file;

    @Before
    public void initEachTest() throws Exception {
        file = StubFactory.create(FileResource.class, 1);
    }

    /**
     * Type is null.
     */
    @Test(expected = NullPointerException.class)
    public void constructor() throws Exception {
        Caseless name = StubFactory.getStubValue("name", 1);
        String data = StubFactory.getStubValue("data", 1);
        new FileResource(null, name, data);
    }

    /**
     * FileResource is null.
     */
    @Test(expected = NullPointerException.class)
    public void copyConstructor() throws Exception {
        new FileResource(null);
    }

    /**
     * Test copy constructor.
     */
    @Test
    public void copyConstructor2() throws Exception {
        FileResource another = new FileResource(file);
        assertTrue("same reference.", file != another);
        assertEquals("not equal.", file, another);
        assertEquals("hashCode not same.", file.hashCode(), another.hashCode());
        assertEquals("types not same.", file.getType(), another.getType());
    }

    /**
     * Test equals to Resource since FileResource doesn't override equals and
     * hashCode.
     */
    @Test
    public void equalsAndHashCode() throws Exception {
        Resource entity = new Resource(file);
        assertTrue("not same.", entity.equals(file));
        assertTrue("not same.", file.equals(entity));
        assertEquals("hashcode not same.", entity.hashCode(), file.hashCode());
    }

    /**
     * Entities are same after deserialization.
     */
    @Test
    public void serialization() throws Exception {
        Object deserialized = SerializationUtils.clone(file);
        assertEquals("not same.", file, deserialized);
    }

    /**
     * Simulate data tempering - type is null.
     */
    @Test(expected = NullPointerException.class)
    public void serialization2() throws Exception {
        StubFactory.setProperty(file, "type", 300);
        SerializationUtils.clone(file);
    }

    @Test
    public void testToString() {
        System.out.println(file);
    }

}