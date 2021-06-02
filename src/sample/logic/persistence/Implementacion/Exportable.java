package sample.logic.persistence.Implementacion;

import sample.logic.persistence.IExport;

import java.util.ArrayList;
import java.util.List;

public class Exportable extends sample.logic.entities.Exportable implements IExport {
    @Override
    public void export(List<sample.logic.entities.Exportable> exportable, Character separateValue) {

        List<String> values = new ArrayList<>();
        for (sample.logic.entities.Exportable e : exportable)
        {
            values.add(e.toExportableValue(separateValue));
            System.out.println(e.toExportableValue(separateValue));
        }

    }

    @Override
    public List<String> toListString() {
        return null;
    }
}
