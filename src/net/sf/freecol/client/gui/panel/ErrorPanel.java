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

package net.sf.freecol.client.gui.panel;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.common.i18n.Messages;
import net.sf.freecol.common.io.FreeColDirectories;
import net.sf.freecol.common.model.StringTemplate;
import net.sf.freecol.common.util.Utils;


/**
 * This is the panel that pops up when an error needs to be reported.
 */
public final class ErrorPanel extends FreeColPanel {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(ErrorPanel.class.getName());

    private static final String SHOW = "show";


    /**
     * Creates a panel to display the given error message.
     *
     * @param freeColClient The <code>FreeColClient</code> for the game.
     * @param message The error message to display in this error panel.
     */
    public ErrorPanel(FreeColClient freeColClient, String message) {
        super(freeColClient, new MigLayout());

        JButton showButton = Utility.localizedButton(StringTemplate
            .template("errorPanel.showLogFile")
            .addName("%path%", FreeColDirectories.getLogFilePath()));
        showButton.setActionCommand(SHOW);
        showButton.addActionListener(this);

        add(Utility.getDefaultTextArea(message, 40), "wrap 20");
        add(okButton, "split 2, tag ok");
        add(showButton);
    }

    /**
     * Creates an error panel containing the log file.
     *
     * @param freeColClient The <code>FreeColClient</code> for the game.
     */
    public ErrorPanel(FreeColClient freeColClient) {
        super(freeColClient, new MigLayout());

        File logFile = new File(FreeColDirectories.getLogFilePath());
        String message = Utils.getUTF8Contents(logFile);
        if (message == null) message = Messages.message("errorPanel.loadError");

        JTextArea textArea = Utility.getDefaultTextArea(message, 40);
        textArea.setFocusable(true);
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, "height 200:200:, wrap 20");
        add(okButton, "tag ok");
    }


    // Interface ActionListener

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        final String command = ae.getActionCommand();
        if (SHOW.equals(command)) {
            getGUI().showLogFilePanel();
        } else {
            super.actionPerformed(ae);
        }
    }
}
