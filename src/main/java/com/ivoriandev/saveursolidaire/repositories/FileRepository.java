package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}