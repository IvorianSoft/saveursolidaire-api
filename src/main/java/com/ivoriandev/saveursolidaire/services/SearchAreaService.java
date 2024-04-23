package com.ivoriandev.saveursolidaire.services;

import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchAreaService {

    public static Geometry getSearchedArea(Double latitude, Double longitude, Double _radius) {
        int POINTS = 32;
        double DEFAULT_RADIUS = 5; // 5 km
        double radius = _radius != null && _radius > 0.0 ? _radius : DEFAULT_RADIUS;
        double radiusInDegrees = kilometersToDegrees(radius);

        GeometricShapeFactory geometricShapeFactory = new GeometricShapeFactory();
        geometricShapeFactory.setNumPoints(POINTS);
        geometricShapeFactory.setCentre(new Coordinate(latitude, longitude));
        geometricShapeFactory.setSize(radiusInDegrees * 2);

        return geometricShapeFactory.createCircle();
    }

    private static double kilometersToDegrees(double kilometers) {
        return kilometers / 111; // Approximation générale : 1 degré de latitude ~ 111 km
    }
}
