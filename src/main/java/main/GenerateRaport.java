package main;

import model.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class generate reports and print them on screen or save to csv,xml file
 */
public class GenerateRaport {
    /**
     * This method is used for generate and print reports on screen
     *
     * @param data String to print on screen
     */
    void printOnScreen(String data) {
        print(generateTxt(null, Collections.singletonList(data)));
    }

    /**
     * This method is used for generate and print reports on screen
     *
     * @param data   String to print on screen
     * @param orders list of orders from database
     */
    void printOnScreen(String data, List<Order> orders) {
        List<String> orders1 = ordersToStringList(orders);
        print(generateTxt(data, orders1));
    }

    /**
     * This method is used for generate and save reports to csv file
     *
     * @param data String with data to save
     */
    void saveCsv(String data) {
        save(generateCsv(data), "csv");
    }

    /**
     * This method is used for generate and save reports to csv file
     *
     * @param orders list of orders from database
     */
    void saveCsv(List<Order> orders) {
        save(generateCsv(orders), "csv");
    }

    /**
     * This method is used for generate and save reports to txt file
     *
     * @param data String with data to save
     */
    void saveTxt(String data) {
        save(generateTxt(null, Collections.singletonList(data)), "txt");
    }

    /**
     * This method is used for generate and save reports to txt file
     *
     * @param data   String with data to save
     * @param orders list of orders from database
     */
    void saveTxt(String data, List<Order> orders) {
        List<String> orders1 = ordersToStringList(orders);
        save(generateTxt(data, orders1), "txt");
    }

    /**
     * This method is generate list in csv format from String
     *
     * @param data String to print on screen
     * @return String LinkedList with data in csv format
     */
    private List<String> generateCsv(String data) {
        List<String> oList = new LinkedList<>();
        String dataEN = "";
        String replaceA1 = "Łączna kwota zamówień";
        String replaceA2 = "Total_value_of_orders";
        String replaceB1 = "łączna ilość zamówień";
        String replaceB2 = "Total_number_of_orders";
        String replaceC1 = "Średnia kwota zamówień";
        String replaceC2 = "Average_value_of_orders";
        if (data.contains(replaceA1)) {
            dataEN = data.replace(replaceA1, replaceA2);
        }
        if (data.contains(replaceB1)) {
            dataEN = data.replace(replaceB1, replaceB2);
        }
        if (data.contains(replaceC1)) {
            dataEN = data.replace(replaceC1, replaceC2);
        }

        if (dataEN.contains("dla klienta")) {
            String[] buffer = dataEN.split(" dla klienta ");
            oList.add("Client_Id," + buffer[0]);
            buffer = buffer[1].split(" wynosi: ");
            oList.add(buffer[0] + "," + buffer[1]);
        } else {

            oList = Arrays.asList(dataEN.split(": "));

        }
        return oList;
    }

    /**
     * This method is generate list in csv format from list of Orders
     *
     * @param orders LinkedList with raw data
     * @return String LinkedList with data in csv format
     */
    private List<String> generateCsv(List<Order> orders) {
        List<String> oList = new LinkedList<>();
        oList.add("Client_Id,Request_id,Name,Quantity,Price");
        for (Order o : orders) {
            oList.add(o.getClientId() + "," +
                    o.getRequestId() + "," +
                    o.getName() + "," +
                    o.getQuantity() + "," +
                    o.getPrice());

        }
        return oList;

    }

    /**
     * This method is used to save file on disk with xml or csv format
     * @param format String with extension of file xml or csv
     * @param generate list of String with formatted data to save
     */
    private void save(List<String> generate, String format) {
        boolean fileExist = true;
        Scanner scanner = new Scanner(System.in);
        String fileName = "";
        FileWriter writer = null;
        try {

            while (fileExist) {
                Console.clear();
                System.out.println("Proszę wpisać nazwę pliku (bez rozszerzenia)");
                fileName = scanner.next();
                File idea = new File("../" + fileName + "." + format);
                if (!idea.exists()) {
                    writer = new FileWriter("../" + fileName + "." + format);
                    fileExist = false;
                } else {
                    System.out.println("plik istnieje");
                    Console.pressEnter();
                }
            }
            for (String str : generate) {
                writer.write(str);
                writer.write("\n");
            }
            writer.close();
            System.out.println("zapisano plik " + fileName + "." + format);
            Console.pressEnter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used for generate LinkedList of data
     * with can by printed on screen on saved in txt format
     * @param data String with data to save
     * @param orders list of orders from database
     * @return LinkedList of formatted data
     */
    private List<String> generateTxt(String data, List<String> orders) {
        StringBuilder s = new StringBuilder();
        List<String> raport = new LinkedList<>();
        int sizeX;
        String firstLine = "Raport wygenerowano: " + getDate();
        sizeX = firstLine.length();
        for (String st : orders) {
            if (st.length() > sizeX) {
                sizeX = st.length();
            }
        }
        sizeX += 2;
        raport.add(generateLine(sizeX, false));
        s.append("#" + firstLine);
        int space = sizeX - firstLine.length() - 2;
        s.append(addSpace(space));
        s.append("#");
        raport.add(s.toString());
        raport.add(generateLine(sizeX, true));
        if (data != null) {
            s.setLength(0);
            s.append("#");
            s.append(data);
            space = sizeX - data.length() - 2;
            s.append(addSpace(space));
            s.append("#");
            raport.add(s.toString());
            raport.add(generateLine(sizeX, true));
        }


        for (int i = 0; i < orders.size(); i++) {
            s.setLength(0);
            s.append("#" + orders.get(i));
            space = sizeX - orders.get(i).length() - 2;
            s.append(addSpace(space));
            s.append("#");
            raport.add(s.toString());
        }
        raport.add(generateLine(sizeX, false));
        return raport;
    }
    /**
     * This method generated lines for reports
     * @param sizeX line width
     * @param line boolean to choose which line generate
     * @return String with generated line
     */
    private String generateLine(int sizeX, boolean line) {
        StringBuilder s = new StringBuilder();
        if (line) {
            s.append("#");
            for (int i = 0; i < sizeX - 2; i++) {
                s.append("_");
            }
            s.append("#");
            return s.toString();
        }
        for (int i = 0; i < sizeX; i++) {
            s.append("#");

        }
        return s.toString();
    }

    /**
     * This method get actual data and time from system
     * @return String with generated date and time
     */
    private String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    /**
     * This method generated LinkedList od String from LinkedList of Orders
     * @param orders LinkedList with Orders from database
     * @return LinkedList of String with raw data
     */
    private List<String> ordersToStringList(List<Order> orders) {
        int addClientId = 0;
        int addRequestId;
        int addName;
        int addQuantity;
        int addPrice;
        String clientId = "Client id";
        String requestId = "Request id";
        String name = "Name";
        String quantity = "Quantity";
        String price = "Price";
        int clientIdCellSize = clientId.length();
        int requestIdCellSize = requestId.length();
        int nameCellSize = name.length();
        int quantityCellSize = quantity.length();
        int priceCellSize = price.length();
        List<String> orders1 = new LinkedList<>();
        for (Order o : orders) {
            if (o.getRequestId().toString().length() > requestIdCellSize) {

                requestIdCellSize = o.getRequestId().toString().length();
            }
            if (o.getName().length() > nameCellSize) {
                nameCellSize = o.getName().length();
            }
            if (o.getQuantity().toString().length() > quantityCellSize) {
                quantityCellSize = o.getQuantity().toString().length();
            }
            if (o.getPrice().toString().length() > priceCellSize) {
                priceCellSize = o.getPrice().toString().length();
            }
        }
        addRequestId = requestIdCellSize - requestId.length();
        addName = nameCellSize - name.length();
        addQuantity = quantityCellSize - quantity.length();
        addPrice = priceCellSize - price.length();
        orders1.add(build(clientId, requestId, name, quantity, price, addClientId, addRequestId, addName, addQuantity, addPrice));

        for (Order o : orders) {
            addClientId = clientIdCellSize - o.getClientId().length();
            addRequestId = requestIdCellSize - o.getRequestId().toString().length();
            addName = nameCellSize - o.getName().length();
            addQuantity = quantityCellSize - o.getQuantity().toString().length();
            addPrice = priceCellSize - o.getPrice().toString().length();
            orders1.add(build(o.getClientId(), o.getRequestId().toString(), o.getName(), o.getQuantity().toString(),
                    o.getPrice().toString(), addClientId, addRequestId, addName, addQuantity, addPrice));

        }

        return orders1;
    }

    /**
     * This method returning string with spaces
     * @param howMany int with present how many spaces add to String
     * @return generated String with spaces
     */
    private String addSpace(int howMany) {
        StringBuilder s = new StringBuilder();
        if (howMany < 0)
            return "";
        for (int i = 0; i < howMany; i++)
            s.append(" ");
        return s.toString();
    }
    /**
     * This method building String with is one line from report
     * @param clientId raw data from database object Order
     * @param requestId raw data from database object Order
     * @param name raw data from database object Order
     * @param quantity raw data from database object Order
     * @param price raw data from database object Order
     * @param addClientId int with data how many spaces must by added
     * @param addRequestId int with data how many spaces must by added
     * @param addName int with data how many spaces must by added
     * @param addQuantity int with data how many spaces must by added
     * @param addPrice int with data how many spaces must by added
     * @return String with generated line
     */
    private String build(String clientId, String requestId, String name, String quantity,
                         String price, int addClientId, int addRequestId, int addName, int addQuantity, int addPrice) {
        char line = '\u2502';
        StringBuilder s = new StringBuilder();
        s.append(clientId);
        s.append(addSpace(addClientId));
        s.append(line);
        s.append(requestId);
        s.append(addSpace(addRequestId));
        s.append(line);
        s.append(name);
        s.append(addSpace(addName));
        s.append(line);
        s.append(quantity);
        s.append(addSpace(addQuantity));
        s.append(line);
        s.append(price);
        s.append(addSpace(addPrice));
        return s.toString();
    }
    /**
     * This method print rapport in console
     * @param orders formatted data ready to print on screen
     */
    private void print(List<String> orders) {
        Console.clear();
        orders.forEach(System.out::println);
        Console.pressEnter();
    }
}