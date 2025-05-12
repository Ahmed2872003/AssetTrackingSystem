package com.AssetTrackingSystem.AssetService.Asset;

import com.AssetTrackingSystem.AssetService.Exceptions.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AssetService {

    @Autowired
    AssetRepo assetRepo;

    public void createAsset(Asset asset){
        assetRepo.save(asset);
    }

    public List<Asset> getAllAssets(Asset asset){

        asset.setStatus(asset.getStatus());


        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Asset> example = Example.of(asset, matcher);

        return assetRepo.findAll(example);
    }


    public void updateAssetById(Long id, Asset newAssetData) {
        Optional<Asset> assetRecord = assetRepo.findById(id);

        if(assetRecord.isPresent()){
            Asset originalAsset = assetRecord.get();

            BeanUtils.copyProperties(newAssetData, originalAsset);

            originalAsset.setStatus_id(Asset.Status.getIdFromStatus(originalAsset.getStatus()));

            assetRepo.save(originalAsset);

            return;
        }
        throw new NotFoundException("No asset with that id(" + id + ")");
    }
}
