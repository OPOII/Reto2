package com.example.pokemonentrega.classes;

public class TypeContainer {
    private int slot;
    private Type type;

    public TypeContainer() {
    }

    public TypeContainer(int slot, Type type) {
        this.slot = slot;
        this.type = type;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
