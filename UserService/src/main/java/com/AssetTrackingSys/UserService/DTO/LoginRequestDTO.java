package com.AssetTrackingSys.UserService.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
    @NotBlank
    public String name;

    @NotBlank
    public String password;
}
