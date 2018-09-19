package paser;

import dao.OrderDAO;
import dao.OrderDAOimpl;
import db.OrderDb;
import model.Order;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class CSVParser {

    private int recordsBefore;
    private int recordsAfter;
    private DataValidation dataValidation = new DataValidation();
    private String type = "CSV";

    public void parse(String url) {
        String[] columnOrder = new String[5];
        String[] orders;
        boolean firstLine = true;
        String line = "";
        String cvsSplitBy = ",";

        recordsBefore = OrderDb.orders.size();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(url), "UTF-8"));
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    columnOrder = line.split(cvsSplitBy);
                    firstLine = false;
                    if (!checkFirstLine(columnOrder)) {
                        System.out.println("Błedne dane w pierwszej lini pliku CSV");
                        System.exit(1);
                    }
                    continue;
                }
                orders = line.split(cvsSplitBy);
                if (checkLine(columnOrder, orders))
                    generateAndSaveOrder(columnOrder, orders);
            }
            recordsAfter = OrderDb.orders.size();
            writeMessage();
        } catch (FileNotFoundException e) {
            System.err.println("System nie może odnaleźć określonej ścieżki");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void writeMessage() {
        int recordsSaved = recordsAfter - recordsBefore;
        if (recordsSaved > 0)
            System.out.println("Wczytano i zapisano " + recordsSaved + " zamówień do bazy danych z pliku CSV");
    }

    private boolean checkFirstLine(String[] columnOrder) {
        List<String> items = Arrays.asList(columnOrder);
        return items.contains("Client_Id") &&
                items.contains("Request_id") &&
                items.contains("Name") &&
                items.contains("Quantity") &&
                items.contains("Price") &&
                (items.size() == 5);
    }

    private boolean checkLine(String[] columnOrder, String[] orders) {

        for (int i = 0; i < columnOrder.length; i++) {
            if (orders.length != 5) {
                dataValidation.printError("x", type);
                return false;
            }
            if (columnOrder[i].equals("Client_Id")) {
                if (!dataValidation.validateByClientId(orders[i], type)) {
                    return false;
                }
            }
            if (columnOrder[i].equals("Request_id")) {
                if (!dataValidation.validateByRequestId(orders[i], type)) {
                    return false;
                }
            }
            if (columnOrder[i].equals("Name")) {
                if (!dataValidation.validateByName(orders[i], type)) {
                    return false;
                }
            }
            if (columnOrder[i].equals("Quantity")) {
                if (!dataValidation.validateByQuantity(orders[i], type)) {
                    return false;
                }
            }
            if (columnOrder[i].equals("Price")) {
                if (!dataValidation.validateByPrice(orders[i], type)) {
                    return false;
                }
            }
        }
        return true;
    }


    private void generateAndSaveOrder(String[] columnOrder, String[] orders) {
        Order order = new Order();
        OrderDAO orderDAO = new OrderDAOimpl();
        for (int i = 0; i < columnOrder.length; i++) {
            if (columnOrder[i].equals("Client_Id")) {
                order.setClientId(orders[i]);
            }
            if (columnOrder[i].equals("Request_id")) {
                order.setRequestId(Long.parseLong(orders[i]));
            }
            if (columnOrder[i].equals("Name")) {
                order.setName(orders[i]);
            }
            if (columnOrder[i].equals("Quantity")) {
                order.setQuantity(Integer.parseInt(orders[i]));
            }
            if (columnOrder[i].equals("Price")) {
                order.setPrice(Double.parseDouble(orders[i]));
            }

        }
        orderDAO.addOrder(order);
    }
}
