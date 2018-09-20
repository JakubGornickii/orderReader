package paser;

import main.Console;

class DataValidation {
    boolean validateByRequestId(String data, String type, int line) {

        try {
            Long check = Long.parseLong(data);
        } catch (NumberFormatException e) {
            printError("Request_Id", type, line);
            return false;
        }
        return true;
    }


    boolean validateByClientId(String order, String type, int line) {
        if (order.length() > 6 || order.contains(" ")) {
            printError("Client_Id", type, line);
            return false;
        }
        return true;
    }

    boolean validateByName(String order, String type, int line) {
        if (order.length() > 255) {
            printError("Name", type, line);
            return false;
        }
        return true;
    }

    boolean validateByQuantity(String order, String type, int line) {
        try {
            int check = Integer.parseInt(order);
        } catch (NumberFormatException e) {
            printError("Quantity", type, line);
            return false;
        }
        return true;
    }

    boolean validateByPrice(String order, String type, int line) {
        try {
            double check = Double.parseDouble(order);
        } catch (NumberFormatException e) {
            printError("Price", type, line);
            return false;
        }
        return true;
    }


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

    public void writeMessage(String type, int recordsSaved) {
        Console.clear();
        if (recordsSaved > 0)
            System.out.println("Wczytano i zapisano " + recordsSaved + " zamówień do bazy danych z pliku " + type + "\n");
    }
}