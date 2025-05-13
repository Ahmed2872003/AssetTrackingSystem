/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AssetTrackingSystem.AssetService.Asset;

import com.AssetTrackingSystem.AssetService.Client.UserServiceClient;
import com.AssetTrackingSystem.AssetService.DTO.AssetLogWithUserDTO;
import com.AssetTrackingSystem.AssetService.DTO.AssetWithAssetManagerDTO;
import com.AssetTrackingSystem.AssetService.DTO.AssetWithAssignedStaffDTO;
import com.AssetTrackingSystem.AssetService.DTO.Request.CreateAssetDTO;
import com.AssetTrackingSystem.AssetService.DTO.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/asset")
public class AssetController {

    @Autowired
    UserServiceClient userServiceClient;

    private static final Logger log = LoggerFactory.getLogger(AssetController.class);
    @Autowired
    AssetService assetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ASSET_MANAGER')")
    public void createAsset(@RequestBody CreateAssetDTO requestBody){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDTO assetManager = (UserDTO) auth.getPrincipal();

        log.info(assetManager.toString());

        Asset asset = new Asset(requestBody.name, requestBody.status, requestBody.category, assetManager.getId());

        assetService.createAsset(asset);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ASSET_MANAGER')")
    public void deleteAsset(@PathVariable(value = "id") Long assetId){
        Asset asset = new Asset();
        asset.setId(assetId);
        assetService.deleteAsset(asset);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ASSET_MANAGER')")
    public List<AssetWithAssignedStaffDTO> getCreatedAssets(@RequestBody Asset assetFilter){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDTO asset_manager = (UserDTO) auth.getPrincipal();


        return assetService.getCreatedAssets(asset_manager.getId(), assetFilter);

    }

    @GetMapping("/assigned")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('STAFF')")
    public List<AssetWithAssetManagerDTO> getAssignedAssets(@RequestBody Asset assetFilter){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDTO staff = (UserDTO) auth.getPrincipal();



        return assetService.getAssignedAssets(staff.getId(), assetFilter);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ASSET_MANAGER')")
    public void updateAssetById(@PathVariable Long id, @RequestBody Asset asset){

        assetService.updateAssetById(id, asset);
    }

    @PostMapping("/{id}/return")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('STAFF')")
    public void reutrnAsset(@PathVariable Long id){
        UserDTO staff = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assetService.returnAsset(id, staff.getId());
    }

    @PostMapping("/{id}/assign")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ASSET_MANAGER')")
    public void assignAssetToStaff(@PathVariable(value = "id") Long assetId, @RequestBody UserDTO staff){

        assetService.assignAssetToStaff(assetId, staff);
    }

    @GetMapping("/{id}/log")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ASSET_MANAGER') or hasRole('STAFF')")
    public List<AssetLogWithUserDTO> viewAssetLogs(@PathVariable Long id){

        return assetService.getLogs(id);

    }
}
