package com.AssetTrackingSystem.AssetService.Asset;

import com.AssetTrackingSystem.AssetService.Asset_log.AssetLogRepo;
import com.AssetTrackingSystem.AssetService.Asset_log.Asset_log;
import com.AssetTrackingSystem.AssetService.Client.UserServiceClient;
import com.AssetTrackingSystem.AssetService.DTO.AssetLogWithUserDTO;
import com.AssetTrackingSystem.AssetService.DTO.AssetWithAssetManagerDTO;
import com.AssetTrackingSystem.AssetService.DTO.AssetWithAssignedStaffDTO;
import com.AssetTrackingSystem.AssetService.DTO.UserDTO;
import com.AssetTrackingSystem.AssetService.Exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AssetService {

    @Autowired
    AssetRepo assetRepo;

    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    AssetLogRepo assetLogRepo;


    public void createAsset(Asset asset){
        assetRepo.save(asset);
    }

    public void deleteAsset(Asset asset){  assetRepo.deleteById(asset.getId()); }


    public List<Asset> getAllAssets(Asset asset){

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Asset> example = Example.of(asset, matcher);

        return assetRepo.findAll(example);
    }

    public List<AssetWithAssignedStaffDTO> getCreatedAssets(Long assetManagerId, Asset assetFilter){
        UserDTO assetManager = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assetFilter.setAsset_manager_id(assetManagerId);

        List<Asset> assets = getAllAssets(assetFilter);

        Set<Long> staffIds = assets.stream()
                .map(Asset::getStaff_id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, UserDTO> userMap = userServiceClient.getUsersByIds(staffIds, String.valueOf(assetManager.getId()), assetManager.getRole(), assetManager.getName());

        return assets.stream()
                .map(asset -> new AssetWithAssignedStaffDTO(asset, userMap.get(asset.getStaff_id())))
                .collect(Collectors.toList());
    }

    public List<AssetWithAssetManagerDTO> getAssignedAssets(Long staffId, Asset assetFilter){

        UserDTO staff = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assetFilter.setStaff_id(staffId);

        List<Asset> assets = getAllAssets(assetFilter);

        Set<Long> managerIds = assets.stream()
                .map(Asset::getAsset_manager_id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, UserDTO> userMap = userServiceClient.getUsersByIds(managerIds, String.valueOf(staff.getId()), staff.getRole(), staff.getName());

        return assets.stream()
                .map(asset -> new AssetWithAssetManagerDTO(asset, userMap.get(asset.getAsset_manager_id())))
                .collect(Collectors.toList());
    }


    public Asset updateAssetById(Long id, Asset newAssetData) {
        Optional<Asset> assetRecord = assetRepo.findById(id);

        if(assetRecord.isPresent()){
            Asset originalAsset = assetRecord.get();

            ModelMapper modelMapper = new ModelMapper();

            modelMapper.getConfiguration().setSkipNullEnabled(true);

            modelMapper.map(newAssetData, originalAsset);

            assetRepo.save(originalAsset);

            if(Asset.Status.UNDER_MAINTAINANCE.toString().equals(newAssetData.getStatus())){
                Asset_log assetLog = new Asset_log(originalAsset.getStatus_id(), originalAsset);

                assetLogRepo.save(assetLog);
            }

            return originalAsset;
        }
        throw new NotFoundException("No asset with that id(" + id + ")");
    }

    public void returnAsset(Long id, Long staffId) {
        Optional<Asset> assetRecord = assetRepo.findById(id);

        if (assetRecord.isPresent()) {
            Asset originalAsset = assetRecord.get();

            originalAsset.setStaff_id(null);
            originalAsset.setStatus(Asset.Status.AVAILABLE.toString());

            assetRepo.save(originalAsset);

            Asset_log asset_log = new Asset_log(originalAsset.getStatus_id(), staffId , originalAsset);

            assetLogRepo.save(asset_log);

            return;
        }
        throw new NotFoundException("No asset with that id(" + id + ")");
    }

    public void assignAssetToStaff(Long assetId, UserDTO staff){
        Asset newAsset = new Asset();

        newAsset.setStaff_id(staff.getId());
        newAsset.setStatus(Asset.Status.ASSIGNED.toString());

        Asset savedAsset = updateAssetById(assetId, newAsset);

        Asset_log assetLog = new Asset_log(newAsset.getStatus_id(), staff.getId(), savedAsset);

        assetLogRepo.save(assetLog);

    }

    public List<AssetLogWithUserDTO> getLogs(Long id){

        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Asset_log asset_log = new Asset_log();

        asset_log.setId(id);

        List<Asset_log> logs =  assetLogRepo.findAllById(Collections.singleton(id));

        Set<Long> staffIds = logs.stream()
                .map(Asset_log::getStaff_id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, UserDTO> userMap = userServiceClient.getUsersByIds(staffIds, String.valueOf(userDTO.getId()), userDTO.getRole(), userDTO.getName());

        return logs.stream()
                .map(log -> {
                    UserDTO user = userMap.get(log.getStaff_id());
                    return new AssetLogWithUserDTO(log, user);
                })
                .collect(Collectors.toList());

    }


}
