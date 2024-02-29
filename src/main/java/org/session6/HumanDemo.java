package org.session6;

public class HumanDemo {
    public static void main(String[] args) {
        GrownUpHuman grownUpHuman = new GrownUpHuman();
        BabyHuman babyHuman = new BabyHuman();

        grownUpHuman.speak();
        grownUpHuman.walk();
        babyHuman.eat();
        babyHuman.walk();
    }
}
