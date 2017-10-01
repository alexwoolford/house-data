package io.woolford;

import com.github.jillow.core.ZillowApiService;
import io.woolford.database.entity.PropertyRecord;
import io.woolford.database.mapper.DbMapper;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Component
public class DataCrawl {

    static Logger logger = Logger.getLogger(DataCrawl.class.getName());

    @Value("${com.github.jillow.util.ApplicationProperties.zwsId}")
    private String zwsid;

    @Autowired
    DbMapper dbMapper;

    private ZillowApiService zillowApiService = new ZillowApiService();

    @PostConstruct
    private void dataCrawl() throws IOException {

        for (PropertyRecord propertyRecord : getRefinCardRecords()){

            try {
                JSONObject zpidJson = zillowApiService.getZillowZpidByAddress(zwsid,
                        propertyRecord.getAddress(),
                        propertyRecord.getCityStateZip());
                String zpid = String.valueOf(zpidJson.get("zpid"));
                propertyRecord.setZpid(Long.valueOf(zpid));

                JSONObject zestimateJson = zillowApiService.getZillowZestimateJsonResponse(zwsid, zpid);
                Long zestimate = ((JSONObject) ((JSONObject) zestimateJson.get("zestimate")).get("amount")).getLong("content");
                propertyRecord.setZestimate(zestimate);

                JSONObject rentZestimateJson = zillowApiService.getZillowRentZestimateJsonResponse(zwsid, zpid);
                Long rentZestimate = ((JSONObject) ((JSONObject) rentZestimateJson.get("rentzestimate")).get("amount")).getLong("content");
                propertyRecord.setRentZestimate(rentZestimate);

                dbMapper.insertPropertyRecord(propertyRecord);

                logger.info(propertyRecord.toString());

            } catch (Exception e) {
                logger.warning("unable to process record: " + propertyRecord + " due to error: " + e.getMessage());
            }

        }

    }


    List<PropertyRecord> getRefinCardRecords(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.redfin.com/zipcode/80026/filter/sort=lo-days");

        List<WebElement> basicCards = driver.findElements(By.className("basic-card"));

        List<PropertyRecord> propertyRecordList = new ArrayList<PropertyRecord>();
        for (WebElement basicCard : basicCards){
            Long price = Long.valueOf(basicCard.findElement(By.className("price")).getText().replace(",", "").replace("$", ""));
            String address = basicCard.findElement(By.className("adr")).getText().split("\n")[0];
            String cityStateZip = basicCard.findElement(By.className("adr")).getText().split("\n")[1];

            PropertyRecord propertyRecord = new PropertyRecord();
            propertyRecord.setPrice(price);
            propertyRecord.setAddress(address);
            propertyRecord.setCityStateZip(cityStateZip);

            propertyRecordList.add(propertyRecord);
        }

        driver.close();

        return propertyRecordList;

    }


}
