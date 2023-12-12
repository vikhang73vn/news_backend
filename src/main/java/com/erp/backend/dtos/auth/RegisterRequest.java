package com.erp.backend.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RegisterRequest {

  @Email(message = "invalid email address")
  @NotNull(message = "Email is required")

  private String email;
  @Size(min = 8)
  @NotNull(message = "Password is required")
  private String password;

  private String name;
  private String phone;

}
