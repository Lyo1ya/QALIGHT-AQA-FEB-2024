package org.example;

public class Main {

    public static void main(String[] args) {
        Phone[] phones = new Phone[4];
        phones[0] = new Phone();
        phones[1] = new Phone();
        phones[2] = new Phone();
        phones[3] = new Phone();

        phones[0].model = "Nokia";
        phones[1].model = "OnePlus";
        phones[2].model = "iPhone";
        phones[3].model = "Motorola";

        for (int i = 0; i < phones.length; i++) {
            while (phones[i].batteryLevel < 100) {
                phones[i].charge();
                System.out.println(phones[i].model + " battery level is " + phones[i].batteryLevel);
                if (phones[i].batteryLevel == 100) {
                    System.out.println(phones[i].model + " is fully charged");
                }
            }
        }
    }
}
