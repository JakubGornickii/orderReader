package paser;

class DataValidation {
    boolean validateByRequestId(String data, String type) {

        try {
            Long check = Long.parseLong(data);
        } catch (NumberFormatException e) {
            printError("Request_Id", type);
            return false;
        }
        return true;
    }


    boolean validateByClientId(String order, String type) {
        if (order.length() > 6 || order.contains(" ")) {
            printError("Client_Id", type);
            return false;
        }
        return true;
    }

    boolean validateByName(String order, String type) {
        if (order.length() > 255) {
            printError("Name",type);
            return false;
        }
        return true;
    }

    boolean validateByQuantity(String order, String type){
        try {
            int check = Integer.parseInt(order);
        } catch (NumberFormatException e) {
            printError("Quantity",type);
            return false;
        }
        return true;
    }

    boolean validateByPrice(String order, String type){
        try {
            double check = Double.parseDouble(order);
        } catch (NumberFormatException e) {
            printError("Price",type);
            return false;
        }
        return true;
    }


    void printError(String name, String type) {
        if (name.equals("x")) {
            System.out.println("Błąd zła ilość parametrów");
        } else {
            System.out.println("Błędne dane dla parametru " + name);
        }
        System.out.println("Pominieto linie w zamówieniu wczytywanym z pliku " + type);
    }
}