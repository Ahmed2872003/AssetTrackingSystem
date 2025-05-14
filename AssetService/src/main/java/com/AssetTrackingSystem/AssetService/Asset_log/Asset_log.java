package com.AssetTrackingSystem.AssetService.Asset_log;

import com.AssetTrackingSystem.AssetService.Asset.Asset;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
public class Asset_log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
    public Integer status_id;

    @Transient
    public String status;

    @CreationTimestamp
    @Column(updatable = false)
    public Date createdAt;

    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
    public Long staff_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    @JsonBackReference
    private Asset asset;

    public Asset_log() {
    }

    public Asset_log(Integer status_id, Long staff_id, Asset asset) {
        this.status_id = status_id;
        this.staff_id = staff_id;
        this.asset = asset;
    }

    public Asset_log(Integer statusId, Asset originalAsset) {
        this.status_id = statusId;
        this.asset = originalAsset;
    }

    public Long getId() {
        return id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public String getStatus() {
        if(status_id != null && status == null) this.status = Asset.Status.getStatusFromId(status_id);
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Asset getAsset() {
        return asset;
    }

    public Long getStaff_id() {
        return staff_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus_id(Integer status_id) {
        if(status_id != null && status == null) status = Asset.Status.getStatusFromId(status_id);


        this.status_id = status_id;
    }

    public void setStatus(String status) {
        if(status != null && status_id == null) status_id = Asset.Status.getIdFromStatus(status);


        this.status = status;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setStaff_id(Long staff_id) {
        this.staff_id = staff_id;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}
