import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import org.kxml.Xml;
import org.kxml.kdom.*;
import org.kxml.parser.*;

public class ServerFunctions {

    static void Delete(int id) {


        try {
            url = "http://embedded-lab.heroku.com/documents/" + id + ".xml";

            httpConn = (HttpConnection) Connector.open(url);

            httpConn.setRequestMethod(HttpConnection.POST);

            os = httpConn.openOutputStream();

            //document[title], document[author], document[content]
            String param = "_method=DELETE";

            os.write(param.getBytes());

            httpConn.openInputStream();
            os.close();
            httpConn.close();
            System.out.println("DELETED");
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
    }

    private static void rec(Vector v, Element root) {

        if (root.getName().equals("document")) {
            int id = 0;
            String title = null, author = null, content = null;
            for (int i = 0; i < root.getChildCount(); i++) {
                if (root.getType(i) == Xml.ELEMENT) {
                    if (root.getElement(i).getName().equals("id")) {
                        id = Integer.parseInt(root.getElement(i).getText(0));
                    } else if (root.getElement(i).getName().equals("title")) {
                        title = root.getElement(i).getText(0);
                    } else if (root.getElement(i).getName().equals("author")) {
                        author = root.getElement(i).getText(0);
                    } else if (root.getElement(i).getName().equals("content")) {
                        content = root.getElement(i).getText(0);
                    }
                }
            }

            v.addElement(new Doc(title, author, content, id));

        } else {
            for (int i = 0; i < root.getChildCount(); i++) {
                if (root.getType(i) == Xml.ELEMENT) {
                    rec(v, root.getElement(i));
                }
            }
        }

    }

    static void search(String query,Vector v) {
        ListAll(v, "?query=" + query
                + "&commit=Search");
    }

    static synchronized void ListAll(Vector v) {
        ListAll(v, "");
    }
    static String url;
    static InputStream in = null;
    static HttpConnection httpConn = null;
    static XmlParser parser = null;
    static Document doc = new Document();
    static OutputStream os;

    private static synchronized void ListAll(Vector v, String param) {
        url = "http://embedded-lab.heroku.com/documents.xml" + param;
        doc = new Document();
        
        try {
            httpConn = (HttpConnection) Connector.open(url);

            httpConn.setRequestMethod(HttpConnection.GET);

            in = httpConn.openInputStream();
            InputStreamReader is;
            parser = new XmlParser(is = new InputStreamReader(in));
            
            doc.parse(parser);


            v.removeAllElements();
            if (doc.getRootElement() != null) {
                rec(v, doc.getRootElement());
            }
            is.close();
            in.close();
            httpConn.close();
        } catch (Exception ex) {
//            ex.printStackTrace();
        }


    }

    static void update(int id, String title, String author, String content) {
        url = "http://embedded-lab.heroku.com/documents/" + id + ".xml";

        try {
            httpConn = (HttpConnection) Connector.open(url);

            httpConn.setRequestMethod(HttpConnection.POST);

            os = httpConn.openOutputStream();

            String param = "document[title]=" + title + "&document[author]=" + author + "&document[content]=" + content + "&_method=PUT";
            os.write(param.getBytes());

            //you must get the returned value from the server
            httpConn.openInputStream();
            
            os.close();
            httpConn.close();
            
            System.out.println("UPDATED" + title + " " + id);
        } catch (Exception ex) {
//            ex.printStackTrace();
        }


    }

    static Doc show(int id) {

        url = "http://embedded-lab.heroku.com/documents/" + id + ".xml";

        doc = new Document();

        try {
            httpConn = (HttpConnection) Connector.open(url);

            httpConn.setRequestMethod(HttpConnection.GET);

            in = httpConn.openInputStream();
            InputStreamReader is;
            parser = new XmlParser(is = new InputStreamReader(in));
            System.out.println("test");
            doc.parse(parser);
            Vector v = new Vector();

            rec(v, doc.getRootElement());

            is.close();
            in.close();
            httpConn.close();
            return (Doc) v.elementAt(0);
        } catch (Exception ex) {
//            ex.printStackTrace();
        }

        return null;
    }

    static void upload(String title, String author, String content) {

        url = "http://embedded-lab.heroku.com/documents.xml";
        
        doc = new Document();

        try {
            httpConn = (HttpConnection) Connector.open(url);

            httpConn.setRequestMethod(HttpConnection.POST);

            OutputStream os = httpConn.openOutputStream();

            //document[title], document[author], document[content]
            String param = "document[title]=" + title + "&document[author]=" + author + "&document[content]=" + content;
            os.write(param.getBytes());


            in = httpConn.openInputStream();
            InputStreamReader is;
            parser = new XmlParser(is = new InputStreamReader(in));
            doc.parse(parser);


            System.out.println(doc.getRootElement());
            doc = null;
            is.close();
            in.close();
            httpConn.close();

        } catch (Exception ex) {
//            ex.printStackTrace();
        }
    }
}
