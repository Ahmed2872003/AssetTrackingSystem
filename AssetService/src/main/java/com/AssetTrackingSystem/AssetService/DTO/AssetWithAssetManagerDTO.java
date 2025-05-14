package com.AssetTrackingSystem.AssetService.DTO;

import com.AssetTrackingSystem.AssetService.Asset.Asset;

public class AssetWithAssetManagerDTO {
        public UserDTO asset_manager;
        public Asset asset;

        public AssetWithAssetManagerDTO(Asset asset, UserDTO asset_manager) {
            this.asset = asset;
            this.asset_manager = asset_manager;
        }

        public UserDTO getAsset_manager() {
            return asset_manager;
        }

        public Asset getAsset() {
            return asset;
        }

        public void setAsset_manager(UserDTO user) {
            this.asset_manager = user;
        }

        public void setAsset(Asset asset) {
            this.asset = asset;
        }
}
