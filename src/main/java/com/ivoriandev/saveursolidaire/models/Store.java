package com.ivoriandev.saveursolidaire.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivoriandev.saveursolidaire.models.base.BaseEntity;
import com.ivoriandev.saveursolidaire.models.embedded.Location;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stores")
@SQLDelete(sql = "UPDATE stores SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Store extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact", nullable = false, unique = true)
    private String contact;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File logo;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Embedded
    private Location location;
}