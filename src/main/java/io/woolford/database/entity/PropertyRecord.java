package io.woolford.database.entity;

public class PropertyRecord {

    private String address;
    private String cityStateZip;
    private Long price;
    private Long zpid;
    private Long zestimate;
    private Long rentZestimate;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityStateZip() {
        return cityStateZip;
    }

    public void setCityStateZip(String cityStateZip) {
        this.cityStateZip = cityStateZip;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getZpid() {
        return zpid;
    }

    public void setZpid(Long zpid) {
        this.zpid = zpid;
    }

    public Long getZestimate() {
        return zestimate;
    }

    public void setZestimate(Long zestimate) {
        this.zestimate = zestimate;
    }

    public Long getRentZestimate() {
        return rentZestimate;
    }

    public void setRentZestimate(Long rentZestimate) {
        this.rentZestimate = rentZestimate;
    }

    @Override
    public String toString() {
        return "PropertyRecord{" +
                "address='" + address + '\'' +
                ", cityStateZip='" + cityStateZip + '\'' +
                ", price=" + price +
                ", zpid=" + zpid +
                ", zestimate=" + zestimate +
                ", rentZestimate=" + rentZestimate +
                '}';
    }

}
