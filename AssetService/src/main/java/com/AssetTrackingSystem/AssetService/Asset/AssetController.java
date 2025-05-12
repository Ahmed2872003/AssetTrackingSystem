/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AssetTrackingSystem.AssetService.Asset;

import com.AssetTrackingSystem.AssetService.DTO.Request.CreateAssetDTO;
import com.AssetTrackingSystem.AssetService.DTO.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/asset")
public class AssetController {

    @Autowired
    AssetService assetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ASSET_MANAGER')")
    public void createAsset(@RequestBody CreateAssetDTO requestBody){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDTO assetManager = (UserDTO) auth.getPrincipal();

        Asset asset = new Asset(requestBody.name, requestBody.category, Asset.Status.getIdFromStatus(requestBody.status), assetManager.getId());

        assetService.createAsset(asset);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ASSET_MANAGER', 'STAFF')")
    public List<Asset> getAllAssets(@RequestBody Asset asset){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDTO assetManager = (UserDTO) auth.getPrincipal();

        asset.setAsset_manager_id(assetManager.getId());

        List<Asset> assets = assetService.getAllAssets(asset);

        return assets;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ASSET_MANAGER')")
    public void updateAssetById(@PathVariable Long id, @RequestBody Asset asset){

        assetService.updateAssetById(id, asset);
    }



}
