package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //프록시와 같은 기술을 사용하기 위해서 허용을 준다.
    //JPA 스팩에서는Protected까지 허용 해준다. 함부로 new로 생성하면 안된다.(그래서 JPA에서는public보다 protected를 사용한다)
    protected Address(){

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
