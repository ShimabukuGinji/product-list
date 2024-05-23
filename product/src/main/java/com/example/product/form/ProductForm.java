package com.example.product.form;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class ProductForm {

    @NotBlank
    @Length(min=1, max=50)
    private String name;

    @NotNull
    @Positive
    @Range(max = 2147483647)
    private String price;

}
