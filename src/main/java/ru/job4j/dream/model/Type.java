package ru.job4j.dream.model;

/**
 * The enum Type.
 * <p>
 * ссылка на конструктор, имя таблицы "Type", имя таблицы с фото
 */
public enum Type {
    POST(Post::new, "post", "photopost"),
    CANDIDATE(Candidate::new, "candidate", "photo");
    private final TabFactory<?> tab;
    private final String name;
    private final String imgname;

    Type(final TabFactory<?> tab, final String name, final String imgname) {
        this.tab = tab;
        this.name = name;
        this.imgname = imgname;
    }

    public <T> TabFactory<T> getTab() {
        return (TabFactory<T>) this.tab;
    }

    public String getName() {
        return this.name;
    }

    public String getImgname() {
        return imgname;
    }
}
