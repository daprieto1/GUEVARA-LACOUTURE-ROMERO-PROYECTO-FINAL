package sample.logic.entities;

import java.util.List;

public abstract class Exportable
{
    public static final Character CSV = ',';
    public static final Character PCS = ';';
    public static final Character BSV = '|';

    public abstract List<String> toListString();

    public String toExportableValue(Character separateValue)
    {
        List<String> listString = this.toListString();
        StringBuilder sb = new StringBuilder();

        for (String s : listString)
        {
            sb.append(s).append(separateValue);
        }

        return sb.toString();
    }
}
