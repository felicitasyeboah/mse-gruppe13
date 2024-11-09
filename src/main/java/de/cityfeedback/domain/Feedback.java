package de.cityfeedback.domain;

import java.time.Instant;

public class Feedback {

    private Long id;
    private Category category;
    private String title;
    private String content;
    private User citizen;
    private User employee;
    private String comment;
    private Status status;
    private Instant createdAt;
    private Instant updatedAt;

    public Feedback(Long id, Category category, String title, String content, User citizen, User employee,
                    String comment, Status status, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.content = content;
        this.citizen = citizen;
        this.employee = employee;
        this.comment = comment;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public User getCitizen() {
        return citizen;
    }

    public void setCitizen(User citizen) {
        this.citizen = citizen;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
