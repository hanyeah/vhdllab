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
package hr.fer.zemris.vhdllab.service.ci;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import hr.fer.zemris.vhdllab.test.ValueObjectTestSupport;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;

public class CircuitInterfaceTest extends ValueObjectTestSupport {

    private CircuitInterface ci;

    @Before
    public void initObject() {
        ci = new CircuitInterface();
    }

    @Test
    public void basics() {
        ci = new CircuitInterface();
        assertNull(ci.getName());
        assertNotNull(ci.getPorts());
        assertTrue(ci.getPorts().isEmpty());

        ci.setName("ci_name");
        assertEquals("ci_name", ci.getName());
        ci.setName(null);
        assertNull(ci.getName());
    }

    @Test
    public void constructorString() {
        ci = new CircuitInterface("ci_name");
        assertEquals("ci_name", ci.getName());
        assertNotNull(ci.getPorts());
        assertTrue(ci.getPorts().isEmpty());
    }

    @Test
    public void constructorStringPort() {
        ci = new CircuitInterface("ci_name", new Port());
        assertEquals("ci_name", ci.getName());
        assertNotNull(ci.getPorts());
        assertEquals(1, ci.getPorts().size());
    }

    @Test
    public void isName() {
        assertTrue(ci.isName(null));
        ci.setName("name");
        assertFalse(ci.isName(null));
        assertTrue(ci.isName("name"));
        assertTrue(ci.isName("NAME"));
    }

    @Test
    public void getPorts() {
        List<Port> ports = ci.getPorts();
        ports.add(new Port());
        assertEquals(1, ci.getPorts().size());
    }

    @Test
    public void addAll() {
        List<Port> ports = new ArrayList<Port>(2);
        ports.add(new Port());
        ports.add(new Port());
        ci.addAll(ports);
        assertEquals(2, ci.getPorts().size());
    }

    @Test
    public void add() {
        ci.add(new Port());
        assertEquals(1, ci.getPorts().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPortNullArgument() {
        ci.getPort(null);
    }

    @Test
    public void getPort() {
        String portName = "port_name";
        assertNull(ci.getPort(portName));

        Port port = new Port(portName, PortDirection.IN);
        ci.add(port);
        assertEquals(port, ci.getPort(portName));
        assertEquals(port, ci.getPort(portName.toUpperCase()));
    }

    @Test
    public void hashCodeAndEquals() throws Exception {
        ci.setName("ci_name");
        basicEqualsTest(ci);

        CircuitInterface another = (CircuitInterface) BeanUtils.cloneBean(ci);
        assertEqualsAndHashCode(ci, another);

        another.setName("another_name");
        assertNotEqualsAndHashCode(ci, another);

        another.setName("CI_name");
        assertEqualsAndHashCode(ci, another);

        another = (CircuitInterface) BeanUtils.cloneBean(ci);
        another.add(new Port());
        assertNotEqualsAndHashCode(ci, another);
    }

    @Test
    public void testToString() {
        ci.setName("ci_name");
        toStringPrint(ci);
        assertEquals("ENTITY ci_name IS\nEND ci_name;", ci.toString());

        Port port = new Port("a", PortDirection.IN);
        ci.add(port);
        toStringPrint(ci);
        assertEquals(
                "ENTITY ci_name IS PORT(\n\ta: IN STD_LOGIC\n);\nEND ci_name;",
                ci.toString());

        port = new Port("f", PortDirection.OUT);
        ci.add(port);
        toStringPrint(ci);
        assertEquals(
                "ENTITY ci_name IS PORT(\n\ta: IN STD_LOGIC;\n\tf: OUT STD_LOGIC\n);\nEND ci_name;",
                ci.toString());
    }

}
