package practicaSAX;

import org.xml.sax.helpers.DefaultHandler;

public class GestionSax extends DefaultHandler {

    private String texto = "";

    private String nombreAutor;
    private String tituloLibro;
    private double precio;
    private double totalPedido;

    public GestionSax() {
        super();
    }

    @Override
    public void startDocument() {
        System.out.println("======================================");
        System.out.println("           FACTURA DEL PEDIDO         ");
        System.out.println("======================================");
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        texto = new String(ch, start, length).trim();
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "nombre":
                tituloLibro = texto;
                break;
            case "autor":
                nombreAutor = texto;
                break;
            case "precio":
                if (!texto.isEmpty()) {
                    texto = texto.replace(",", ".");
                    try {
                        precio = Double.parseDouble(texto);
                    } catch (NumberFormatException e) {
                        precio = 0.0;
                    }
                }
                break;
            case "libro":
                double precioConIva = precio * 1.04;
                System.out.println("-------------------------");
                System.out.println("Titulo: " + tituloLibro);
                System.out.println("Autor : " + nombreAutor);
                System.out.printf("Precio: %.2f €\n", precio);
                System.out.printf("Precio total (4%%): %.2f €\n", precioConIva);
                totalPedido += precioConIva;
                break;
        }
    }

    @Override
    public void endDocument() {

        System.out.println("--------------------------------------");
        System.out.println("TOTAL PEDIDO: " + String.format("%.2f", totalPedido) + " €");
        System.out.println("======================================");
    }
}
