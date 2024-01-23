package spr2024.cs2340.group9.studysync.database;

public enum Order {
    DUE_DATE("dueDate"),
    NAME("name"),
    START_TIME("startTime"),
    END_TIME("endTime");

    public final String columnName;

    private Order(String col) {
        columnName = col;
    }
}
