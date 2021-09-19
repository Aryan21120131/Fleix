package com.example.fleix.Activities;

public class ORDER {
    String reciever_name;
    String pick_location;
    String drop_location;
    String order_id;
    String type;
    String weight;
    String length;
    String width;
    String height;

    public ORDER(String reciever_name, String pick_location, String drop_location, String order_id, String type, String weight, String length, String width, String height, String total_cost, String status) {
        this.reciever_name = reciever_name;
        this.pick_location = pick_location;
        this.drop_location = drop_location;
        this.order_id = order_id;
        this.type = type;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.total_cost = total_cost;
        this.status = status;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    String total_cost;
    String status;

    public String getReciever_name() {
        return reciever_name;
    }

    public void setReciever_name(String reciever_name) {
        this.reciever_name = reciever_name;
    }

    public String getPick_location() {
        return pick_location;
    }

    public void setPick_location(String pick_location) {
        this.pick_location = pick_location;
    }

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
