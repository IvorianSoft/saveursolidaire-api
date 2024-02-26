package com.ivoriandev.saveursolidaire.models;

import com.ivoriandev.saveursolidaire.models.base.BaseEntity;
import com.ivoriandev.saveursolidaire.utils.enums.file.FileTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "files")
@SQLDelete(sql = "UPDATE files SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class File extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    FileTypeEnum type;

    @Column(name = "url", nullable = false)
    private String url;
}