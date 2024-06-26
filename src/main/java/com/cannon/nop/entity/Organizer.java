package com.cannon.nop.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Organizer implements Serializable {
    private List<String> formData;
    private Date startDate;
    private String uuid;
    private String secretKey;
}
