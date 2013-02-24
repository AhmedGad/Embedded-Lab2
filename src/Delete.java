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
import org.kxml.kdom.*;
import org.kxml.parser.*;

/**
 * @author Ahmed
 */
public class Delete extends MIDlet implements CommandListener, ItemStateListener {

    private String url = "http://embedded-lab.heroku.com/documents/7170.xml";
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
            
            OutputStream os = httpConn.openOutputStream();

            //document[title], document[author], document[content]
            String param = "_method=DELETE";
            os.write(param.getBytes());

            
            httpConn.openInputStream();
            
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
