package spr2024.cs2340.group9.studysync.notifications;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class NotificationRequest {
    @NonNull
    @PrimaryKey
    public UUID id;

    public NotificationRequest(@NonNull UUID id) {
        this.id = id;
    }
}
