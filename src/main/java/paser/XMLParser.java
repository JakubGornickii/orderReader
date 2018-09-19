package paser;

import dao.OrderDAO;
import dao.OrderDAOimpl;
import db.OrderDb;
import model.Order;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XMLParser {


    private int recordsBefore;
    private int recordsAfter;
    private String type = "XML";
    private OrderDAO orderDAO = new OrderDAOimpl();
    private DataValidation dataValidation = new DataValidation();

    public void parse(String url) {
        recordsBefore = OrderDb.orders.size();
        try {
            File xmlFile = new File(url);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList request = doc.getElementsByTagName("request");
            for (int i = 0; i < request.getLength(); i++) {
                Element element = (Element) request.item(i);
                Order order = checkAndGetOrder(element);
                if (order != null) {
                    orderDAO.addOrder(order);
                }
            }
            recordsAfter = OrderDb.orders.size();
            writeMessage();
        } catch (FileNotFoundException e) {
            System.err.println("Błędny plik");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private Order checkAndGetOrder(Element element) {
        try {
            String clientId = element.getElementsByTagName("clientId").item(0).getTextContent();
            String requestId = element.getElementsByTagName("requestId").item(0).getTextContent();
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String quantity = element.getElementsByTagName("quantity").item(0).getTextContent();
            String price = element.getElementsByTagName("price").item(0).getTextContent();

            if  (!(dataValidation.validateByClientId(clientId,type) &&
                 dataValidation.validateByRequestId(requestId,type) &&
                 dataValidation.validateByName(name,type) &&
                 dataValidation.validateByQuantity(quantity,type) &&
                 dataValidation.validateByPrice(price,type))){
                return null;
            }
            return new Order(clientId, Long.parseLong(requestId), name, Integer.parseInt(quantity), Double.parseDouble(price));
        }catch (NullPointerException e){
            dataValidation.printError("x", type);
            return null;
        }

    }

    private void writeMessage() {
        int recordsSaved = recordsAfter - recordsBefore;
        if (recordsSaved > 0)
            System.out.println("Wczytano i zapisano " + recordsSaved + " zamówień do bazy danych z pliku XML");
    }
}

