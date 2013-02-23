/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.midlet.*;
import org.kxml.Xml;
import org.kxml.kdom.*;
import org.kxml.parser.*;

/**
 * @author Ahmed
 */
public class Lab2 extends MIDlet implements CommandListener, ItemStateListener {

    private String url = "http://embedded-lab.heroku.com/documents.xml";
    StringBuffer b = new StringBuffer();
    InputStream in = null;
    HttpConnection c = null;
    InputStreamReader is;
    XmlParser parser = null;
    Document doc = new Document();

    void printAttribute(Element elem) {
        System.out.println("=======================");
        System.out.println(elem);
        System.out.println("========================");
        int n = elem.getChildCount();
        System.out.println(n);
        for (int i = 0; i < n; i++) {
            System.out.println(elem.getChild(i));
        }
    }

    public void startApp() {
        try {
            c = (HttpConnection) Connector.open(url);
            in = c.openInputStream();
            InputStreamReader is = new InputStreamReader(in);

            parser = new XmlParser(is);
            doc.parse(parser);
            parser = null;


        } catch (IOException ex) {
            ex.printStackTrace();
        }


        Element root = doc.getRootElement();

//        printAttribute(root);
        int n = root.getChildCount(), tmp = 0;
//        System.out.println(root);
        for (int i = 0; i < n; i++) {
            if (root.getType(i) == Xml.ELEMENT) {
//                System.out.println(root.getElement(i));
                printAttribute(root.getElement(i));
            }
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
    }

    public void itemStateChanged(Item item) {
    }
}
