package sample.logic.entities;

import java.util.ArrayList;
import java.util.List;

public class Company extends  Exportable {

    private String name;
    private String address;

    public Company(String name, String address)
    {
        this.name=name;
        this.address=address;
    }


    @Override
    public List<String> toListString() {
        List<String> result = new ArrayList<>();
        result.add(this.name);
        result.add(this.address);
        return result;
    }
}
