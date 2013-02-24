/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Stack;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;
import org.kxml.kdom.*;
import org.kxml.parser.*;

/**
 * @author Ahmed
 */
public class Base extends MIDlet implements CommandListener {

    Vector docs;
    List list;
    Command show = new Command("Show", Command.OK, 0);
    Command upload = new Command("Upload", Command.OK, 0);
    Command delete = new Command("Delete", Command.OK, 0);
    Command search = new Command("Search", Command.OK, 0);
    Command back = new Command("Back", Command.BACK, 0);
    Command OK = new Command("OK", Command.OK, 0);
    Form doc = new Form("Document");
    TextField title = new TextField("Title", "", 200, TextField.ANY);
    TextField author = new TextField("Author", "", 200, TextField.ANY);
    TextField content = new TextField("Content", "", 200, TextField.ANY);
    Form s = new Form("Document");
    TextField query = new TextField("Search for Content with", "", 200, TextField.ANY);
    int current = 0;
    int listAll = 0;
    int document = 1;
    int srch = 2;
    int prev = 0;

    public Base() {
        s.addCommand(back);
        s.addCommand(OK);
        s.append(query);
        s.setCommandListener(this);

        docs = new Vector();

        list = new List("Docs:", List.IMPLICIT);
        list.addCommand(show);
        list.addCommand(upload);
        list.addCommand(delete);
        list.addCommand(search);
        list.setCommandListener(this);

        updateList();

        l.addCommand(show);
        l.addCommand(back);
        l.setCommandListener(this);

        doc.append(title);
        doc.append(author);
        doc.append(content);
        doc.addCommand(back);
        doc.addCommand(OK);
        doc.setCommandListener(this);
    }

    public void updateList() {
        ServerFunctions.ListAll(docs);

        int s = docs.size();
        list.deleteAll();

        for (int i = 0; i < s; i++) {
            list.append(((Doc) docs.elementAt(i)).getTitle(), null);
        }

    }
    Display disp;

    public void startApp() {
        try {
            disp = Display.getDisplay(this);
            disp.setCurrent(list);
            refresh();
        } catch (Exception e) {
            disp = Display.getDisplay(this);
            disp.setCurrent(list);
            refresh();
        }

    }

    void refresh() {
        while (true) {
            try {
                System.out.println("============REFRESHED=============");

                if (disp.getCurrent() == list) {
                    updateList();
                    disp.setCurrent(list);
                } else if (disp.getCurrent() == l) {
                    updateSearchedList();
                    disp.setCurrent(l);
                }
                Thread.currentThread().sleep(5000);
            } catch (Exception ex) {
//                ex.printStackTrace();
            }
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
    Vector v = new Vector();
    List l = new List("Docs Matched:", List.IMPLICIT);

    public void updateSearchedList() {
        ServerFunctions.search(query.getString(), v);

        int s = v.size();
        l.deleteAll();
        for (int i = 0; i < s; i++) {
            l.append(((Doc) v.elementAt(i)).getTitle(), null);
        }
    }
    int prevId;

    public void commandAction(Command c, Displayable d) {
        if (c == show) {
            if (current == srch) {
                Doc cur = ServerFunctions.show(prevId = ((Doc) v.elementAt(prevId = l.getSelectedIndex())).getId());
                title.setString(cur.getTitle());
                author.setString(cur.getAuthor());
                content.setString(cur.getContent());
                prev = 1;
                current++;
                disp.setCurrent(doc);
            } else {
                System.out.println(docs.size());
                Doc cur = ServerFunctions.show(prevId = ((Doc) docs.elementAt(list.getSelectedIndex())).getId());
                title.setString(cur.getTitle());
                author.setString(cur.getAuthor());
                content.setString(cur.getContent());
                prev = 1;
                current++;
                disp.setCurrent(doc);
            }
        } else if (c == upload) {
            prev = 2;
            current++;
            disp.setCurrent(doc);
        } else if (c == delete) {
            ServerFunctions.Delete(((Doc) docs.elementAt(list.getSelectedIndex())).getId());
            updateList();
            disp.setCurrent(list);
        } else if (c == search) {
            current = srch;
            prev = 3;
            disp.setCurrent(s);
        } else if (c == back) {
            updateList();
            disp.setCurrent(list);
            current = 0;
        } else if (c == OK) {
            if (prev == 1) {
                ServerFunctions.update(prevId, title.getString(),
                        author.getString(), content.getString());
                current = 0;
                updateList();
                disp.setCurrent(list);
            } else if (prev == 2) {
                ServerFunctions.upload(title.getString(), author.getString(),
                        content.getString());
                current = 0;
                updateList();
                disp.setCurrent(list);
            } else if (prev == 3) {
                updateSearchedList();
                disp.setCurrent(l);
            }
        }
    }
}
