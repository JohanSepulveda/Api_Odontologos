package com.Backend.apiOdontologos.Repository;

import com.Backend.apiOdontologos.Model.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OdontologoRepository extends JpaRepository<Odontologo,Long> {
}
