package org.session6;

public abstract class AbstractHuman implements iHuman{

    protected String name;

    public AbstractHuman(String name) {
        this.name = name;
    }

    @Override
    public void speak() {
        System.out.println("Any human can speak");
    }

    public abstract void eat();
}
