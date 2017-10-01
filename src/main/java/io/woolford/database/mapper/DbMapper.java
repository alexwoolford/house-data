package io.woolford.database.mapper;

import io.woolford.database.entity.PropertyRecord;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;


@Component
public interface DbMapper {


    //TODO: upsert to avoid duplicates (see example below)
    @Insert("INSERT INTO house_data.house_data (" +
            "   zpid,                           " +
            "   address,                        " +
            "   cityStateZip,                   " +
            "   price,                          " +
            "   zestimate,                      " +
            "   rentZestimate                   " +
            ")                                  " +
            "VALUES                             " +
            "    (#{zpid},                      " +
            "     #{address},                   " +
            "     #{cityStateZip},              " +
            "     #{price},                     " +
            "     #{zestimate},                 " +
            "     #{rentZestimate})             " +
            "ON DUPLICATE KEY UPDATE            " +
            "     address=#{address},           " +
            "     cityStateZip=#{cityStateZip}, " +
            "     price=#{price},               " +
            "     zestimate=#{zestimate},       " +
            "     rentZestimate=#{rentZestimate}")
    public void insertPropertyRecord(PropertyRecord propertyRecord);

}