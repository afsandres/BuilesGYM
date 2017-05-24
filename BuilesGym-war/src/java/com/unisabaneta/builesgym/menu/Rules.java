/*
 * OpenFaces - JSF Component Library 3.0
 * Copyright (C) 2007-2010, TeamDev Ltd.
 * licensing@openfaces.org
 * Unless agreed in writing the contents of this file are subject to
 * the GNU Lesser General Public License Version 2.1 (the "LGPL" License).
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * Please visit http://openfaces.org/licensing/ for more details.
 */

package com.unisabaneta.builesgym.menu;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Rules {
    private static List<RuleItem> rules;

    public static void buildSiteRules(ServletContext context) {
        try {
            Digester digester = new Digester();            
            digester.setValidating(false);
            digester.addObjectCreate("menu", ArrayList.class);
            digester.addObjectCreate("menu/menuItem", RuleItem.class);

            digester.addSetNext("menu/menuItem", "add");

            digester.addCallMethod("*/menuItem/url", "setUrl", 0);
            digester.addCallMethod("*/menuItem/shortPath", "setShortPath", 0);

            InputStream is = context.getResource("/WEB-INF/menu.xml").openStream();
            rules = (List<RuleItem>) digester.parse(is);

        } catch (IOException | SAXException e) {
            Logger.getLogger(Rules.class.getName()).log(Level.WARNING, "Could not build Site Rules: {0}", e.getMessage());
        }
    }

    public static List<RuleItem> getRules() {
        return rules;
    }
}
