package com.example.backendlicenta.web.dto;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public class voteDto {

    private MultipartFile file;
    private Map<String, Double> voters;
    private String trustReevaluation;
    private String binarizationThreshold;
    private Double trustThreshold;

    public voteDto() {}

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Map<String, Double> getVoters() {
        return voters;
    }

    public void setVoters(Map<String, Double> voters) {
        this.voters = voters;
    }

    public String getTrustReevaluation() {
        return trustReevaluation;
    }

    public void setTrustReevaluation(String trustReevaluation) {
        this.trustReevaluation = trustReevaluation;
    }

    public String getBinarizationThreshold() {
        return binarizationThreshold;
    }

    public void setBinarizationThreshold(String binarizationThreshold) {
        this.binarizationThreshold = binarizationThreshold;
    }

    public Double getTrustThreshold() {
        return trustThreshold;
    }

    public void setTrustThreshold(Double trustThreshold) {
        this.trustThreshold = trustThreshold;
    }
}
