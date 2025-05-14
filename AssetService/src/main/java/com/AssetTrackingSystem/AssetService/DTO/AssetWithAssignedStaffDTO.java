package com.AssetTrackingSystem.AssetService.DTO;

import com.AssetTrackingSystem.AssetService.Asset.Asset;

public class AssetWithAssignedStaffDTO {
    public UserDTO staff;
    public Asset asset;

    public AssetWithAssignedStaffDTO(Asset asset, UserDTO staff) {
        this.asset = asset;
        this.staff = staff;
    }

    public UserDTO getStaff() {
        return staff;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setstaff(UserDTO user) {
        this.staff = user;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}
