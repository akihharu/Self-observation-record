package com.example.selfobservationrecord.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class DiaryUpdateRequest {

    @NotNull
    private LocalDate recordDate;

    @NotBlank
    @Size(max = 300)
    private String event;

    @Size(max = 300)
    private String reflection;

    @NotNull
    private Integer status;

    private boolean overwrite;

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public String getEvent() {
        return event;
    }

    public String getReflection() {
        return reflection;
    }

    public Integer getStatus() {
        return status;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setReflection(String reflection) {
        this.reflection = reflection;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }
}