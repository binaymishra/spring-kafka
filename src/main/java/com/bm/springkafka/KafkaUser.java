package com.bm.springkafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaUser {

  @JsonProperty(value = "name")
  private String name;

  public KafkaUser() {
  }

  public KafkaUser(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "KafkaUser{" +
        "name='" + name + '\'' +
        '}';
  }
}
