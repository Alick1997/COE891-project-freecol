/**
 *  Copyright (C) 2002-2007  The FreeCol Team
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

package net.sf.freecol.client.gui.action;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.client.gui.panel.MainPanel;

/**
 * Returns to the <code>MainPanel</code>.
 * All in-game components are removed.
 * 
 * @see MainPanel
 */
public class ShowMainAction extends FreeColAction {
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(ShowMainAction.class.getName());




    public static final String id = "showMainAction";


    /**
     * Creates this action.
     * 
     * @param freeColClient The main controller object for the client.
     */
    ShowMainAction(FreeColClient freeColClient) {
        super(freeColClient, "menuBar.game.returnToMain", null, null);
    }

    /**
     * Checks if this action should be enabled.
     * @return <code>true</code>
     */
    protected boolean shouldBeEnabled() {
        return true;
    }

    /**
     * Returns the id of this <code>Option</code>.
     * @return The <code>String</code>: "showMainAction"
     */
    public String getId() {
        return id;
    }

    /**
     * Applies this action.
     * @param e The <code>ActionEvent</code>.
     */
    public void actionPerformed(ActionEvent e) {
        if (!getFreeColClient().getCanvas().showConfirmDialog("stopCurrentGame.text", "stopCurrentGame.yes", "stopCurrentGame.no")) {
            return;
        }
        
        getFreeColClient().getCanvas().removeInGameComponents();
        getFreeColClient().setMapEditor(false);
        getFreeColClient().setGame(null);
        getFreeColClient().getCanvas().returnToTitle();
    }
}
