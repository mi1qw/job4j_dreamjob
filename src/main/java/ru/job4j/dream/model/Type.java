package ru.job4j.dream.model;

/**
 * The enum Type.
 * <p>
 * ссылка на конструктор, имя таблицы "Type", имя таблицы с фото
 */
public enum Type {
    POST(Post::new, "post", "photopost", "imagespost"),
    CANDIDATE(Candidate::new, "candidate", "photo", "images");
    private final TabFactory<?> tab;
    private final String name;
    private final String imgname;
    private final String foldImg;

    Type(final TabFactory<?> tab, final String name, final String imgname, final String foldImg) {
        this.tab = tab;
        this.name = name;
        this.imgname = imgname;
        this.foldImg = foldImg;
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

    public String getFoldImg() {
        return foldImg;
    }
}
