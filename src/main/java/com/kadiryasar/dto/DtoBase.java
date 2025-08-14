package com.kadiryasar.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class DtoBase {

    private Long id;

    private Date createTime;

}
