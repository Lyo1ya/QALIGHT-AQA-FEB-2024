package org.session6;

public class GrownUpHuman extends AbstractHuman{
    public static final String NAME = "John Doe";
    @Override
    public void walk() {
        System.out.println("A grown-up human can even run");
    }
    public GrownUpHuman() {
        super(NAME);
    }
    @Override
    public void speak() {
        System.out.println("My name is " + NAME);
    }
    @Override
    public void eat() {
        System.out.println("I can eat with a fork");
    }
}
