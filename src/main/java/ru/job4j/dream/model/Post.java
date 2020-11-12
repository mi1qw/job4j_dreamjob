package ru.job4j.dream.model;

import java.util.Objects;

/**
 * The type Post.
 */
public class Post {
    private int id;
    private String name;
    private String description;
    private String created;

    public Post(final int id, final String name, final String description, final String created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
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
    public String getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(final String created) {
        this.created = created;
    }

    /**
     * getId.
     *
     * @return id
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
     * @return bool
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    /**
     * hashCode.
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
