package com.AssetTrackingSystem.AssetService.DTO.Request;

import jakarta.validation.constraints.NotBlank;

public class AssetRequestDTO {
    @NotBlank
    public String category;
    
    @NotBlank
    public String reason;
} 