package com.org.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="websites")
public class Websites implements Serializable{

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "name", length = 255)
  private String name;

  @Column(name = "url", length = 255)
  private String url;

  @Column(name = "alexa", length = 255)
  private Long alexa;

  @Column(name = "country", length = 255)
  private String country;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getAlexa() {
    return alexa;
  }

  public void setAlexa(Long alexa) {
    this.alexa = alexa;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "Websites{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", url='" + url + '\'' +
            ", alexa=" + alexa +
            ", country='" + country + '\'' +
            '}';
  }
}
