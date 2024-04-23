package com.ivoriandev.saveursolidaire.models.listeners;

import com.ivoriandev.saveursolidaire.models.Store;
import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StoreEntityListener {

    @PrePersist
    public void prePersist(Store store) {
        if (store.getGeopoint() == null) {
            Point point = new GeometryFactory().createPoint(
                    new Coordinate(
                            store.getLocation().getLatitude(),
                            store.getLocation().getLongitude()
                    )
            );

            store.setGeopoint(point);
        }
    }
}
