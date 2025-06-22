package ru.todo.spring.javadaddy.todoApp.enums;

public enum TaskStatus {
    TODO(0),
    IN_PROGRESS(1),
    DONE(2);

    TaskStatus(int sortPriority) {
        this.sortPriority = sortPriority;
    }

    public int getSortPriority() {
        return sortPriority;
    }

    private final int sortPriority;
}
