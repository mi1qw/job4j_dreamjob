package ru.job4j.dream.model;

enum Type {
    POST(Post::new, "post"),
    CANDIDATE(Candidate::new, "candidate");
    private final TabFactory<?> tab;
    private final String name;

    Type(final TabFactory<?> tab, final String name) {
        this.tab = tab;
        this.name = name;
    }

    public <T> TabFactory<T> getTab() {
        return (TabFactory<T>) this.tab;
    }

    public String getName() {
        return this.name;
    }
}
