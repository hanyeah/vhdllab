package hr.fer.zemris.vhdllab.api.results;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A test case for {@link Message}.
 *
 * @author Miro Bezjak
 */
public class MessageTest {

    private static final MessageType TYPE = MessageType.SUCCESSFUL;
    private static final String TEXT = "message text";
    private static final String ENTITY_NAME = "entity.name";

    private static Message message;

    @Before
    public void initEachTest() {
        message = new Message(TYPE, TEXT, ENTITY_NAME);
    }

    /**
     * Message is null
     */
    @Test(expected = NullPointerException.class)
    public void copyConstructor() throws Exception {
        new Message(null);
    }

    /**
     * everything ok
     */
    @Test
    public void copyConstructor2() throws Exception {
        Message newMessage = new Message(message);
        assertEquals("messages not equal.", newMessage, message);
    }

    /**
     * Message type is null
     */
    @Test(expected = NullPointerException.class)
    public void constructor() throws Exception {
        new Message(null, TEXT);
    }

    /**
     * Message text is null
     */
    @Test(expected = NullPointerException.class)
    public void constructor2() throws Exception {
        new Message(TYPE, null);
    }

    /**
     * Message type is null
     */
    @Test(expected = NullPointerException.class)
    public void constructor3() throws Exception {
        new Message(null, TEXT, ENTITY_NAME);
    }

    /**
     * Message text is null
     */
    @Test(expected = NullPointerException.class)
    public void constructor4() throws Exception {
        new Message(TYPE, null, ENTITY_NAME);
    }

    /**
     * Test getters and setters
     */
    @Test
    public void gettersAndSetters() throws Exception {
        assertEquals("getType.", TYPE, message.getType());
        assertEquals("getText.", TEXT, message.getMessageText());
        assertEquals("getEntityName.", ENTITY_NAME, message.getEntityName());
    }

    /**
     * Test equals with self, null, and non-message object
     */
    @Test
    public void equals() throws Exception {
        assertEquals("not equal.", message, message);
        assertFalse("message is equal to null.", message.equals(null));
        assertFalse("can compare with string object.", message
                .equals("a string object"));
    }

    /**
     * everything ok
     */
    @Test
    public void hashCodeAndEquals() throws Exception {
        Message newMessage = new Message(message);

        assertEquals("messages not equal.", message, newMessage);
        assertEquals("messages not equal.", message.hashCode(), newMessage
                .hashCode());
    }

    /**
     * Type is different
     */
    @Test
    public void hashCodeAndEquals2() throws Exception {
        Message newMessage = new Message(MessageType.ERROR, TEXT, ENTITY_NAME);

        assertFalse("messages are equal.", message.equals(newMessage));
        assertFalse("messages are equal.", message.hashCode() == newMessage
                .hashCode());
    }

    /**
     * Message text is different
     */
    @Test
    public void hashCodeAndEquals3() throws Exception {
        Message newMessage = new Message(TYPE, "different text", ENTITY_NAME);

        assertFalse("messages are equal.", message.equals(newMessage));
        assertFalse("messages are equal.", message.hashCode() == newMessage
                .hashCode());
    }

    /**
     * Entity name is different
     */
    @Test
    public void hashCodeAndEquals4() throws Exception {
        Message newMessage = new Message(TYPE, TEXT, "new.entity.name");

        assertFalse("messages are equal.", message.equals(newMessage));
        assertFalse("messages are equal.", message.hashCode() == newMessage.hashCode());
    }

    /**
     * Simulate serialization tempering
     */
    @Test(expected = NullPointerException.class)
    public void readResolve() throws Exception {
        Field field = message.getClass().getDeclaredField("type");
        field.setAccessible(true);
        field.set(message, null); // set illegal state of message object

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);

        ObjectInputStream bis = new ObjectInputStream(new ByteArrayInputStream(
                bos.toByteArray()));
        bis.readObject();
    }

    /**
     * Deserialization returns object of type Message.
     */
    @Test
    public void readResolve2() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);

        ObjectInputStream bis = new ObjectInputStream(new ByteArrayInputStream(
                bos.toByteArray()));
        Object readObject = bis.readObject();
        assertTrue("wrong object type.",
                readObject.getClass() == Message.class);
        assertEquals("message objects not same.", message, readObject);
    }

    /**
     * With entity name
     */
    @Ignore("must be tested by a user and this has already been tested")
    @Test
    public void asString() {
        System.out.println(message.toString());
    }

    /**
     * Without entity name
     */
    @Ignore("must be tested by a user and this has already been tested")
    @Test
    public void asString2() {
        message = new Message(TYPE, TEXT);
        System.out.println(message.toString());
    }

}