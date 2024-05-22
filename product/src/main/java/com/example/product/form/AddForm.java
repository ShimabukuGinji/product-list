package com.example.product.form;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddForm {

    @NotEmpty
    private String addName;
    @NotEmpty
    //@Digits(integer = 5, fraction = 0)
    private int addPrice;

}
