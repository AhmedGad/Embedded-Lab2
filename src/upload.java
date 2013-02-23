/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
public class upload extends MIDlet implements CommandListener, ItemStateListener {

    private String url = "http://embedded-lab.heroku.com/documents.xml";
    StringBuffer b = new StringBuffer();
    InputStream in = null;
    HttpConnection httpConn = null;
    InputStreamReader is;
    XmlParser parser = null;
    Document doc = new Document();

    public void startApp() {
        try {
            httpConn = (HttpConnection) Connector.open(url);

            httpConn.setRequestMethod(HttpConnection.POST);
            httpConn.setRequestProperty("User-Agent",
                    "Profile/MIDP-1.0 Confirguration/CLDC-1.0");
//            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = httpConn.openOutputStream();

            //document[title], document[author], document[content]
            String param = "document[title]=BestCase&document[author]=Gad&document[content]=Ay7aga";
            os.write(param.getBytes());

            
            in = httpConn.openInputStream();
            
            InputStreamReader is = new InputStreamReader(in);

            parser = new XmlParser(is);
            doc.parse(parser);
            
            System.out.println(doc.getRootElement());


        } catch (IOException ex) {
            ex.printStackTrace();
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
