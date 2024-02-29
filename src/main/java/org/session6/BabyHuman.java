package org.session6;

public class BabyHuman extends AbstractHuman {

    public static final String NAME = "Pumpkin";
    public BabyHuman() {
        super(NAME);
    }
    @Override
    public void walk() {
        System.out.println("Chop-chop");
    }

    @Override
    public void eat() {
        System.out.println("Mommy feeds me");
    }
}
