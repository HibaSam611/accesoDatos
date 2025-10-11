package practicaSAX;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;

public class Principal {
    public static void main(String[] args) throws SAXException, IOException {

        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        GestionSax gestionSax = new GestionSax();
        xmlReader.setContentHandler(gestionSax);
        InputSource fichero = new InputSource("documentopedidos.xml");
        xmlReader.parse(fichero);
    }
}
