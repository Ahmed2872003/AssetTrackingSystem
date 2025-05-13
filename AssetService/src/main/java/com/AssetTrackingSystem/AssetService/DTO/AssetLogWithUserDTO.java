package com.AssetTrackingSystem.AssetService.DTO;

import com.AssetTrackingSystem.AssetService.Asset_log.Asset_log;

public class AssetLogWithUserDTO {
        public UserDTO staff;
        public Asset_log asset_log;

        public AssetLogWithUserDTO(Asset_log asset_log, UserDTO user) {
            this.asset_log = asset_log;
            this.staff = user;
        }

    public UserDTO getStaff() {
        return staff;
    }

    public Asset_log getAsset_log() {
        return asset_log;
    }

    public void setStaff(UserDTO user) {
        this.staff = user;
    }

    public void setAsset_log(Asset_log asset_log) {
        this.asset_log = asset_log;
    }
}
