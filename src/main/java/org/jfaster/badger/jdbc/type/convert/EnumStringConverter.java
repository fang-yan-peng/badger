package org.jfaster.badger.jdbc.type.convert;

/**
 * Enum <-> String
 * @author yanpengfang
 * create 2019-01-07 2:40 PM
 */
public class EnumStringConverter implements TypeConverter<Enum, String> {

    @Override
    public String convert(Enum input) {
        return input == null ? null : input.name();
    }

    @Override
    public Enum reverse(String o, Class clz) {
        if (o == null) {
            return null;
        }
        Enum r = Enum.valueOf(clz, o);
        return r;
    }
}
