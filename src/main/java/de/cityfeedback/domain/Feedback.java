package de.cityfeedback.domain;

import java.time.Instant;

public class Feedback {

    private Long id;
    private Long categoryId;
    private String title;
    private String content;
    private Long citizenId;
    private Long employeeId;
    private String comment;
    private Long statusId;
    private Instant createdAt;
    private Instant updatedAt;

    public Feedback() {
    }

    public Feedback(Long id, Long categoryId, String title, String content, Long citizenId, Long employeeId,
                    String comment, Long statusId, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.citizenId = citizenId;
        this.employeeId = employeeId;
        this.comment = comment;
        this.statusId = statusId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
