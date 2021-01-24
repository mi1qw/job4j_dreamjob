package ru.job4j.dream.model;

import java.io.Serializable;

public class ImgFile implements Serializable {
    private static final long serialVersionUID = 1;
    private int id;
    private String name;

    public ImgFile(final int id, final String name) {
        this.id = id;
        this.name = name;
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
     * toString.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "ImgFile{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
