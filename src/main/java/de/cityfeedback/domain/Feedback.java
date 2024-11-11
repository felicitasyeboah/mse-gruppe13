package de.cityfeedback.domain;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

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

    public Feedback(Long categoryId, String title, String content, Long citizenId) {
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.citizenId = citizenId;
        this.statusId = 1L;
        this.createdAt = new Date().toInstant();
        this.updatedAt = new Date().toInstant();
    }

    public Feedback(Long categoryId, String title, String content, Long citizenId, Long employeeId) {
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.citizenId = citizenId;
        this.employeeId = employeeId;
        this.statusId = 1L;
        this.createdAt = new Date().toInstant();
        this.updatedAt = new Date().toInstant();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) && Objects.equals(categoryId, feedback.categoryId) && Objects.equals(title, feedback.title) && Objects.equals(content, feedback.content) && Objects.equals(citizenId, feedback.citizenId) && Objects.equals(employeeId, feedback.employeeId) && Objects.equals(comment, feedback.comment) && Objects.equals(statusId, feedback.statusId) && Objects.equals(createdAt, feedback.createdAt) && Objects.equals(updatedAt, feedback.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, title, content, citizenId, employeeId, comment, statusId, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", citizenId=" + citizenId +
                ", employeeId=" + employeeId +
                ", comment='" + comment + '\'' +
                ", statusId=" + statusId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
