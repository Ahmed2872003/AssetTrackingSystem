package com.AssetTrackingSystem.AssetService.DTO.Request;


import jakarta.validation.constraints.NotBlank;

public class CreateAssetDTO {
    @NotBlank
    public String name;

    @NotBlank
    public String category;


    public final String status = "AVAILABLE";

    @Override
    public String toString() {
        return "CreateAssetDTO{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
