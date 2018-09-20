package paser;

import main.Console;
/**
 * This class validate data from csv and xml file
 */
class DataValidation {

    /**
     * This method validate data with order model variable requestId
     *
     * @param line int with line number in csv or xml file
     * @param type String with file type xml or csv
     * @param data String with data to validate
     * @return true or false when data are correct or not
     */
    boolean validateByRequestId(String data, String type, int line) {

        try {
            Long check = Long.parseLong(data);
        } catch (NumberFormatException e) {
            printError("Request_Id", type, line);
            return false;
        }
        return true;
    }

    /**
     * This method validate data with order model variable clientId
     *
     * @param line int with line number in csv or xml file
     * @param type String with file type xml or csv
     * @param data String with data to validate
     * @return true or false when data are correct or not
     */
    boolean validateByClientId(String data, String type, int line) {
        if (data.length() > 6 || data.contains(" ")) {
            printError("Client_Id", type, line);
            return false;
        }
        return true;
    }
    /**
     * This method validate data with order model variable name
     *
     * @param line int with line number in csv or xml file
     * @param type String with file type xml or csv
     * @param data String with data to validate
     * @return true or false when data are correct or not
     */
    boolean validateByName(String data, String type, int line) {
        if (data.length() > 255) {
            printError("Name", type, line);
            return false;
        }
        return true;
    }
    /**
     * This method validate data with order model variable quantity
     *
     * @param line int with line number in csv or xml file
     * @param type String with file type xml or csv
     * @param data String with data to validate
     * @return true or false when data are correct or not
     */
    boolean validateByQuantity(String data, String type, int line) {
        try {
            int check = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            printError("Quantity", type, line);
            return false;
        }
        return true;
    }
    /**
     * This method validate data with order model variable price
     *
     * @param line int with line number in csv or xml file
     * @param type String with file type xml or csv
     * @param data String with data to validate
     * @return true or false when data are correct or not
     */
    boolean validateByPrice(String data, String type, int line) {
        try {
            double check = Double.parseDouble(data);
        } catch (NumberFormatException e) {
            printError("Price", type, line);
            return false;
        }
        return true;
    }

    /**
     * This method printing error on screen when validate
     * detect some errors
     * @param line int with line number in csv or xml file
     * @param type String with file type xml or csv
     * @param name order model variable name with validate detect errors
     */
    void printError(String name, String type, int line) {
        if (type.equals("XML")) {
            line++;
        }
        Console.clear();
        if (name.equals("x")) {
            System.out.println("Błąd zła ilość parametrów w " + line + " zamówieniu");
        } else {
            System.out.println("Błędne dane dla parametru " + name + " w " + line + " zamówieniu");
        }
        System.out.println("Pominieto " + line + " linie w zamówieniu wczytywanym z pliku " + type);
        Console.pressEnter();
    }
    /**
     * This method printing message on screen with information
     * how many records was added to database
     * @param type String with file type xml or csv
     * @param recordsSaved int with data how many records saved to database
     */
    public void writeMessage(String type, int recordsSaved) {
        Console.clear();
        if (recordsSaved > 0)
            System.out.println("Wczytano i zapisano " + recordsSaved + " zamówień do bazy danych z pliku " + type + "\n");
    }
}