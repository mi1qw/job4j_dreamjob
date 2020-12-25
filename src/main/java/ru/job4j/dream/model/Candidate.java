package ru.job4j.dream.model;

import java.util.Date;
import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String description;
    private Date created;
    private int photoId;

    public Candidate(final int id, final String name, final String description,
                     final Date created, final int photoId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.photoId = photoId;
    }

    public Candidate(final int id, final String name, final String description,
                     final Date created) {
        this(id, name, description, created, 0);
    }

    /**
     * Gets photo.
     *
     * @return the photo
     */
    public int getPhotoId() {
        return photoId;
    }

    /**
     * Sets photo id.
     *
     * @param photoId the photo id
     */
    public void setPhotoId(final int photoId) {
        this.photoId = photoId;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(final Date created) {
        this.created = created;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * equals.
     *
     * @param o o
     * @return boolean
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    /**
     * toString.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Candidate{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + ", photo=" + photoId + '}';
    }

    /**
     * hashCode.
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
