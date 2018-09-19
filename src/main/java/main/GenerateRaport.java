package main;

import model.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GenerateRaport {


    void printOnScreen(String data) {
        print(generate(null, Collections.singletonList(data)));
    }

    void printOnScreen(String data, List<Order> orders) {
        List<String> orders1 = ordersToStringList(orders);
        print(generate(data, orders1));
    }

    void saveCsv(String data) {
        save(generate(null, Collections.singletonList(data)));
    }

    void saveCsv (String data, List < Order > orders){
        List<String> orders1 = ordersToStringList(orders);
        save(generate(data, orders1));
    }

    private void save(List<String> generate) {
        boolean fileExist = true;
        Scanner scanner = new Scanner(System.in);
        String fileName = "";
        FileWriter writer = null;
        try {

            while (fileExist) {
                System.out.println("Proszę wpisać nazwę pliku (bez rozszerzenia)");
                fileName = scanner.nextLine();
                File idea = new File("../"+fileName + ".txt");
                if (!idea.exists()) {
                    writer = new FileWriter("../"+fileName+".txt");
                    fileExist = false;
                } else
                    System.out.println("plik istnieje");
            }
            for (String str : generate) {
                writer.write(str);
                writer.write("\n");
            }
            writer.close();
            System.out.println("zapisano");
        }catch(IOException e){
                e.printStackTrace();
            }
        }



        private List<String> generate (String d, List < String > data){
            StringBuilder s = new StringBuilder();
            List<String> raport = new LinkedList<>();
            int sizeX;
            String firstLine = "Raport wygenerowano: " + getDate();
            sizeX = firstLine.length();
            for (String st : data) {
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
            if (d != null) {
                s.setLength(0);
                s.append("#");
                s.append(d);
                space = sizeX - d.length() - 2;
                s.append(addSpace(space));
                s.append("#");
                raport.add(s.toString());
                raport.add(generateLine(sizeX, true));
            }


            for (int i = 0; i < data.size(); i++) {
                s.setLength(0);
                s.append("#" + data.get(i));
                space = sizeX - data.get(i).length() - 2;
                s.append(addSpace(space));
                s.append("#");
                raport.add(s.toString());
            }
            raport.add(generateLine(sizeX, false));
            return raport;
        }

        private String generateLine ( int sizeX, boolean line){
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

        //do tąd
        private String getDate () {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            return dtf.format(now);
        }

        private List<String> ordersToStringList (List < Order > orders) {
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


        private String addSpace ( int howMany){
            StringBuilder s = new StringBuilder();
            if (howMany < 0)
                return "";
            for (int i = 0; i < howMany; i++)
                s.append(" ");
            return s.toString();
        }

        private String build (String clientId, String requestId, String name, String quantity,
                String price,int addClientId, int addRequestId, int addName, int addQuantity, int addPrice){
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

        private void print (List < String > orders) {
            orders.forEach(System.out::println);
        }
    }