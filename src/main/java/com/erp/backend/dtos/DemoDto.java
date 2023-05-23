package com.erp.backend.dtos;

import lombok.Data;
import javax.validation.constraints.NotBlank;
@Data
public class DemoDto {
    @NotBlank(message = "vui long dien thong tin")
    String name;
}
