package com.skyforce.goal.model;

import lombok.*;

import javax.persistence.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "storage")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_directory")
    private String fileDirectory;

    @Column(name = "file_name")
    private String fileName;

    @Column(name="file_extension")
    private String fileExtension;

    @Transient
    private String fileBaseName;

    public Path getFilePath() {
        if (fileName == null || fileDirectory == null) {
            return null;
        }

        return Paths.get(fileDirectory, fileName);
    }

    public Image(String fileDirectory, String fileName, String fileExtension, String fileBaseName) {
        this.fileDirectory = fileDirectory;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileBaseName = fileBaseName;
    }
}
