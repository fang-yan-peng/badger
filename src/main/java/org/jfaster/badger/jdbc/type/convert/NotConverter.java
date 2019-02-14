package org.jfaster.badger.jdbc.type.convert;

import org.jfaster.badger.exception.BadgerException;

/**
 *
 * @author yanpengfang
 * create 2019-01-07 4:47 PM
 */
public class NotConverter implements TypeConverter {
    @Override
    public Object convert(Object o) {
        throw new BadgerException("not run here");
    }

    @Override
    public Object reverse(Object o, Class clz) {
        throw new BadgerException("not run here");
    }
}
