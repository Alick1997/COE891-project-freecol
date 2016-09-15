/**
 *  Copyright (C) 2002-2016   The FreeCol Team
 *
 *  This file is part of FreeCol.
 *
 *  FreeCol is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  FreeCol is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FreeCol.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.freecol.server.ai;

import javax.xml.stream.XMLStreamException;

import java.util.Comparator;

import net.sf.freecol.common.io.FreeColXMLReader;
import net.sf.freecol.common.io.FreeColXMLWriter;
import net.sf.freecol.common.model.FreeColObject;


/**
 * Abstract class of AI object with a simple enclosed comparable
 * integer value.
 */
public abstract class ValuedAIObject extends AIObject {

    /** A comparator by ascending AI object value. */
    public static final Comparator<ValuedAIObject> ascendingValueComparator
        = Comparator.comparingInt(ValuedAIObject::getValue);
    
    /** A comparator by descending AI object value. */
    public static final Comparator<ValuedAIObject> descendingValueComparator
        = ascendingValueComparator.reversed();

    /** The value of this AIObject. */
    private int value;


    /**
     * Creates a new {@code ValuedAIObject} instance.
     *
     * @param aiMain an {@code AIMain} value
     */
    public ValuedAIObject(AIMain aiMain) {
        super(aiMain);
    }

    /**
     * Creates a new uninitialized {@code ValuedAIObject} instance.
     *
     * @param aiMain an {@code AIMain} value
     * @param id The object identifier.
     */
    public ValuedAIObject(AIMain aiMain, String id) {
        super(aiMain, id);

        this.value = 0;
    }

    /**
     * Creates a new {@code ValuedAIObject} from the given
     * XML-representation.
     *
     * @param aiMain The main AI-object.
     * @param xr The input stream containing the XML.
     * @throws XMLStreamException if a problem was encountered
     *      during parsing.
     */
    public ValuedAIObject(AIMain aiMain,
                          FreeColXMLReader xr) throws XMLStreamException {
        super(aiMain, xr);
    }


    /**
     * Get the {@code Value} value.
     *
     * @return an {@code int} value
     */
    public final int getValue() {
        return value;
    }

    /**
     * Set the {@code Value} value.
     *
     * @param newValue The new Value value.
     */
    public final void setValue(final int newValue) {
        this.value = newValue;
    }


    // Override FreeColObject

    @Override
    public int compareTo(FreeColObject other) {
        int cmp = 0;
        if (other instanceof ValuedAIObject) {
            ValuedAIObject vao = (ValuedAIObject)other;
            cmp = vao.value - this.value;
        }
        if (cmp == 0) cmp = super.compareTo(other);
        return cmp;
    }


    // Serialization

    @Override
    protected void writeAttributes(FreeColXMLWriter xw) throws XMLStreamException {
        super.writeAttributes(xw);

        xw.writeAttribute(VALUE_TAG, value);
    }

    @Override
    protected void readAttributes(FreeColXMLReader xr) throws XMLStreamException {
        super.readAttributes(xr);

        setValue(xr.getAttribute(VALUE_TAG, -1));
    }
}
