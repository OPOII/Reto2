package com.example.pokemonentrega.classes;

public class StatsContainer {
    private int base_stat;
    private int effort;
    private Stat stat;

    public StatsContainer() {
    }

    public StatsContainer(int base_stat, int effort, Stat stat) {
        this.base_stat = base_stat;
        this.effort = effort;
        this.stat = stat;
    }

    public int getBase_stat() {
        return base_stat;
    }

    public void setBase_stat(int base_stat) {
        this.base_stat = base_stat;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }
}
