package br.com.activeultra.core.entity;

import br.com.activeultra.core.enums.AssetEventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "asset_event",
        indexes = {
                @Index(name = "ix_asset_event_asset_id_created_at", columnList = "asset_id, created_at"),
                @Index(name = "ix_asset_event_type", columnList = "event_type"),
                @Index(name = "ix_asset_event_actor_user_id", columnList = "actor_user_id")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AssetEvent extends TenantEntity {

    /**
     * Asset that this event belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    /**
     * What happened.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 60)
    private AssetEventType eventType;

    /**
     * Who did it (from user-jwt service or your local user table).
     * Keep it as a scalar to avoid coupling across bounded contexts.
     */
    @Column(name = "actor_user_id", nullable = false)
    private UUID actorUserId;

    /**
     * Optional free text reason/notes shown in the timeline.
     */
    @Column(name = "notes", length = 2000)
    private String notes;

    /**
     * Optional "why" code (useful for analytics / filtering).
     * Example: "TRANSFER", "CORRECTION", "MAINTENANCE_OPEN".
     */
    @Column(name = "reason_code", length = 80)
    private String reasonCode;

    /**
     * Minimal, flexible diff storage.
     * Store a JSON map or a JSON Patch describing what changed.
     * Example (simple): {"status":{"from":"ACTIVE","to":"MAINTENANCE"}}
     */
    @Lob
    @Column(name = "payload_json")
    private String payloadJson;

}