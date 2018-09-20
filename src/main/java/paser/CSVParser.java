package paser;

import dao.OrderDAO;
import dao.OrderDAOimpl;
import db.OrderDb;
import main.Console;
import model.Order;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * This class read csv orders files, validate data
 * and save them to database
 */
public class CSVParser {

    private int recordsBefore;
    private int recordsAfter;
    private DataValidation dataValidation = new DataValidation();
    private String type = "CSV";
    private int lineCounter;

    /**
     * Main method in this class use for read file,
     * swap for Order model and save
     *
     * @param url directory to file
     * @return true when successfully parse file and false when
     * are some errors with file
     */
    public boolean parse(String url) {
        String[] columnOrder = new String[5];
        String[] orders;
        boolean firstLine = true;
        String line = "";
        String cvsSplitBy = ",";
        lineCounter = 0;
        recordsBefore = OrderDb.orders.size();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(url), "UTF-8"));
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    columnOrder = line.split(cvsSplitBy);
                    firstLine = false;
                    if (!checkFirstLine(columnOrder)) {
                        System.out.println("Błedne dane w pierwszej lini pliku CSV");
                        Console.pressEnter();
                    }

                    continue;
                }
                lineCounter++;
                orders = line.split(cvsSplitBy);
                if (checkLine(columnOrder, orders))
                    generateAndSaveOrder(columnOrder, orders);
            }
            recordsAfter = OrderDb.orders.size();
            dataValidation.writeMessage(type, recordsAfter - recordsBefore);
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("System nie może odnaleźć określonej ścieżki");
            Console.pressEnter();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method check if headline csv file is correct
     *
     * @param columnOrder raw data flom file
     * @return true of false when headline are correct
     */
    private boolean checkFirstLine(String[] columnOrder) {
        List<String> items = Arrays.asList(columnOrder);
        return items.contains("Client_Id") &&
                items.contains("Request_id") &&
                items.contains("Name") &&
                items.contains("Quantity") &&
                items.contains("Price") &&
                (items.size() == 5);
    }

    /**
     * This method validate data from file read first line sequence
     * and set correct data to correct heading
     *
     * @param columnOrder first line of csv file already checked with
     *                    checkFirstLine method.
     * @param orders      raw line from csv file.
     * @return true or false if input data are correct or not
     */
    private boolean checkLine(String[] columnOrder, String[] orders) {

        for (int i = 0; i < columnOrder.length; i++) {
            if (orders.length != 5) {
                dataValidation.printError("x", type, lineCounter);
                return false;
            }
            if (columnOrder[i].equals("Client_Id")) {
                if (!dataValidation.validateByClientId(orders[i], type, lineCounter)) {
                    return false;
                }
            }
            if (columnOrder[i].equals("Request_id")) {
                if (!dataValidation.validateByRequestId(orders[i], type, lineCounter)) {
                    return false;
                }
            }
            if (columnOrder[i].equals("Name")) {
                if (!dataValidation.validateByName(orders[i], type, lineCounter)) {
                    return false;
                }
            }
            if (columnOrder[i].equals("Quantity")) {
                if (!dataValidation.validateByQuantity(orders[i], type, lineCounter)) {
                    return false;
                }
            }
            if (columnOrder[i].equals("Price")) {
                if (!dataValidation.validateByPrice(orders[i], type, lineCounter)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method read first line sequence and set correct data
     * to correct heading, create Order and save to database using
     * OrderDAO
     *
     * @param columnOrder first line of csv file already checked with
     *                    checkFirstLine method.
     * @param orders      line of data alredy checked with checkLine method
     */
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
