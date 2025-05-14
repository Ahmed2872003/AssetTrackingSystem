package com.AssetTrackingSystem.AssetService.Asset;

import com.AssetTrackingSystem.AssetService.Asset_log.Asset_log;
import com.AssetTrackingSystem.AssetService.DTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    private String category;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer status_id;

    @Transient
    private String status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long staff_id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long asset_manager_id;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asset_log> logs = new ArrayList<>();

    public static enum Status{
        ASSIGNED(1),
        AVAILABLE(2),
        UNDER_MAINTAINANCE(3);

        final Integer id;

        Status(Integer id){ this.id = id; }

        public Integer getId(){ return this.id; }

        public static String getStatusFromId(Integer id) {
            if(id == null) return null;
            for (Status status : Status.values()) {
                if (status.getId().equals(id)) {
                    return status.toString();
                }
            }
            return null;
        }

        public static Integer getIdFromStatus(String statusName){
            if(statusName == null) return null;

            statusName = statusName.toUpperCase();

            for (Status status : Status.values()) {
                if (status.toString().equals(statusName)) {
                    return status.getId();
                }
            }
            return null;
        }

    };


//    public static void main(String[] main){
//        Asset asset = new Asset();
//
//        asset.setStatus_id(3);
//
//        System.out.println(asset);
//    }


    public Asset(Long id, String name, String category, Integer status_id, Long staff_id, Long asset_manager_id) {
        setId(id);
        setName(name);
        setCategory(category);
        setStatus_id(status_id);
        setStaff_id(staff_id);
        setAsset_manager_id(asset_manager_id);
    }
    public Asset(String name, String category, Integer status_id, Long staff_id, Long asset_manager_id) {
        setName(name);
        setCategory(category);
        setStatus_id(status_id);
        setStaff_id(staff_id);
        setAsset_manager_id(asset_manager_id);
    }
    public Asset(String name, String status, String category, Long asset_manager_id) {
        setName(name);
        setCategory(category);
        setAsset_manager_id(asset_manager_id);
        setStatus(status);
    }

    public Asset() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public Long getStaff_id() {
        return staff_id;
    }


    public Long getAsset_manager_id() {
        return asset_manager_id;
    }

    public String getStatus() {
        if(this.status_id != null && this.status == null) this.status = Status.getStatusFromId(this.status_id);
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus_id(Integer status_id) {
        this.status = Status.getStatusFromId(status_id);
        this.status_id = status_id;
    }

    public void setStaff_id(Long staff_id) {
        this.staff_id = staff_id;
    }

    public void setAsset_manager_id(Long asset_manager_id) {
        this.asset_manager_id = asset_manager_id;
    }


    public void setStatus(String status) {
        this.status_id = Asset.Status.getIdFromStatus(status);
        this.status = status;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", status_id=" + status_id +
                ", staff_id=" + staff_id +
                ", asset_manager_id=" + asset_manager_id +
                '}';
    }
}
