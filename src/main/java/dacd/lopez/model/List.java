package dacd.lopez.model;

import java.util.ArrayList;

public class List {
    private final ArrayList<Weather> list;

    public List(ArrayList<Weather> list) {
        this.list = list;
    }

    public ArrayList<Weather> getList() {
        return list;
    }

}

