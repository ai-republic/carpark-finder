package com.airepublic.wego.exercise.carparkinfo.util;

/**
 * 
 * @author cgcai
 * 
 *         The SVY21 class provides functionality to convert between the SVY21 and Lat/Lon
 *         coordinate systems.
 * 
 *         Internally, the class uses the equations specified in the following web page to perform
 *         the conversion.
 *         http://www.linz.govt.nz/geodetic/conversion-coordinates/projection-conversions/transverse-mercator-preliminary-computations/index.aspx
 */
public class SVY21 {
    private static final double radRatio = Math.PI / 180; // Ratio to convert degrees to radians.

    // Datum and Projection
    private static final double a = 6378137; // Semi-major axis of reference ellipsoid.
    private static final double f = 1 / 298.257223563; // Ellipsoidal flattening.
    private static final double oLat = 1.366666; // Origin latitude (degrees).
    private static final double oLon = 103.833333; // Origin longitude (degrees).
    private static final double No = 38744.572; // False Northing.
    private static final double Eo = 28001.642; // False Easting.
    private static final double k = 1.0; // Central meridian scale factor.

    // Computed Projection Constants
    // Naming convention: the trailing number is the power of the variable.
    private static final double b;
    private static final double e2, e4, e6;
    private static final double n, n2, n3, n4;
    private static final double G;

    // Naming convention: A0..6 are terms in an expression, not powers.
    private static final double A0, A2, A4, A6;

    // Initialize Projection Constants.
    static {
        b = a * (1 - f); // Semi-minor axis of reference ellipsoid.

        e2 = 2 * f - f * f; // Squared eccentricity of reference ellipsoid.
        e4 = e2 * e2;
        e6 = e4 * e2;

        A0 = 1 - e2 / 4 - 3 * e4 / 64 - 5 * e6 / 256;
        A2 = 3. / 8. * (e2 + e4 / 4 + 15 * e6 / 128);
        A4 = 15. / 256. * (e4 + 3 * e6 / 4);
        A6 = 35 * e6 / 3072;

        n = (a - b) / (a + b);
        n2 = n * n;
        n3 = n2 * n;
        n4 = n2 * n2;

        G = a * (1 - n) * (1 - n2) * (1 + 9 * n2 / 4 + 225 * n4 / 64) * radRatio;
    }

    private static double calcM(final double lat) { // M: meridian distance.
        final double latR = lat * radRatio;
        final double m = a * (A0 * latR - A2 * Math.sin(2 * latR) + A4 * Math.sin(4 * latR) - A6 * Math.sin(6 * latR));
        return m;
    }


    private static double calcRho(final double sin2Lat) { // Rho: radius of curvature of meridian.
        final double num = a * (1 - e2);
        final double denom = Math.pow(1 - e2 * sin2Lat, 3. / 2.);
        final double rho = num / denom;
        return rho;
    }


    private static double calcV(final double sin2Lat) { // v: radius of curvature in the prime
                                                        // vertical.
        final double poly = 1 - e2 * sin2Lat;
        final double v = a / Math.sqrt(poly);
        return v;
    }


    /**
     * Computes Latitude and Longitude based on an SVY21 coordinate.
     * 
     * This method returns an immutable LatLonCoordiante object that contains two fields, latitude,
     * accessible with .getLatitude(), and longitude, accessible with .getLongitude().
     * 
     * @param N Northing based on SVY21.
     * @param E Easting based on SVY21.
     * @return the conversion result a LatLonCoordinate.
     */
    public static LatLonCoordinate computeLatLon(final double N, final double E) {
        final double Nprime = N - No;
        final double Mo = calcM(oLat);
        final double Mprime = Mo + Nprime / k;
        final double sigma = Mprime / G * radRatio;

        // Naming convention: latPrimeT1..4 are terms in an expression, not powers.
        final double latPrimeT1 = (3 * n / 2 - 27 * n3 / 32) * Math.sin(2 * sigma);
        final double latPrimeT2 = (21 * n2 / 16 - 55 * n4 / 32) * Math.sin(4 * sigma);
        final double latPrimeT3 = 151 * n3 / 96 * Math.sin(6 * sigma);
        final double latPrimeT4 = 1097 * n4 / 512 * Math.sin(8 * sigma);
        final double latPrime = sigma + latPrimeT1 + latPrimeT2 + latPrimeT3 + latPrimeT4;

        // Naming convention: sin2LatPrime = "square of sin(latPrime)" = Math.pow(sin(latPrime),
        // 2.0)
        final double sinLatPrime = Math.sin(latPrime);
        final double sin2LatPrime = sinLatPrime * sinLatPrime;

        // Naming convention: the trailing number is the power of the variable.
        final double rhoPrime = calcRho(sin2LatPrime);
        final double vPrime = calcV(sin2LatPrime);
        final double psiPrime = vPrime / rhoPrime;
        final double psiPrime2 = psiPrime * psiPrime;
        final double psiPrime3 = psiPrime2 * psiPrime;
        final double psiPrime4 = psiPrime3 * psiPrime;
        final double tPrime = Math.tan(latPrime);
        final double tPrime2 = tPrime * tPrime;
        final double tPrime4 = tPrime2 * tPrime2;
        final double tPrime6 = tPrime4 * tPrime2;
        final double Eprime = E - Eo;
        final double x = Eprime / (k * vPrime);
        final double x2 = x * x;
        final double x3 = x2 * x;
        final double x5 = x3 * x2;
        final double x7 = x5 * x2;

        // Compute Latitude
        // Naming convention: latTerm1..4 are terms in an expression, not powers.
        final double latFactor = tPrime / (k * rhoPrime);
        final double latTerm1 = latFactor * (Eprime * x / 2);
        final double latTerm2 = latFactor * (Eprime * x3 / 24) * (-4 * psiPrime2 + 9 * psiPrime * (1 - tPrime2) + 12 * tPrime2);
        final double latTerm3 = latFactor * (Eprime * x5 / 720) * (8 * psiPrime4 * (11 - 24 * tPrime2) - 12 * psiPrime3 * (21 - 71 * tPrime2) + 15 * psiPrime2 * (15 - 98 * tPrime2 + 15 * tPrime4) + 180 * psiPrime * (5 * tPrime2 - 3 * tPrime4) + 360 * tPrime4);
        final double latTerm4 = latFactor * (Eprime * x7 / 40320) * (1385 - 3633 * tPrime2 + 4095 * tPrime4 + 1575 * tPrime6);
        final double lat = latPrime - latTerm1 + latTerm2 - latTerm3 + latTerm4;

        // Compute Longitude
        // Naming convention: lonTerm1..4 are terms in an expression, not powers.
        final double secLatPrime = 1. / Math.cos(lat);
        final double lonTerm1 = x * secLatPrime;
        final double lonTerm2 = x3 * secLatPrime / 6 * (psiPrime + 2 * tPrime2);
        final double lonTerm3 = x5 * secLatPrime / 120 * (-4 * psiPrime3 * (1 - 6 * tPrime2) + psiPrime2 * (9 - 68 * tPrime2) + 72 * psiPrime * tPrime2 + 24 * tPrime4);
        final double lonTerm4 = x7 * secLatPrime / 5040 * (61 + 662 * tPrime2 + 1320 * tPrime4 + 720 * tPrime6);
        final double lon = oLon * radRatio + lonTerm1 - lonTerm2 + lonTerm3 - lonTerm4;

        return new LatLonCoordinate(lat / radRatio, lon / radRatio);
    }

}