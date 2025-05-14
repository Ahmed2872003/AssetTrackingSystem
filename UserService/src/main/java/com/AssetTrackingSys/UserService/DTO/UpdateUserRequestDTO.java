package com.AssetTrackingSys.UserService.DTO;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequestDTO {
    @NotBlank
    public String name;

    public String password;
}
