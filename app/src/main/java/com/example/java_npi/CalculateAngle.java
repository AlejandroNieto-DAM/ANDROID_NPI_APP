package com.example.java_npi;

/**
 * Es una clase de apoyo para calcular el angulo entre dos vectores. Nos servirá
 * para guiarnos con la brujula en CompassActivity ya que debemos de calcular el angulo
 * entre dos vectores que son el punto destino y punto origen y punto norte y punto origen.
 */
public class CalculateAngle {
    public static double calculateAngle(Nodo puntoInicial, Nodo puntoFinal) {

        double[] vector1 = {puntoFinal.x - puntoInicial.x, puntoFinal.y - puntoInicial.y};
        double[] vector2 = {90 - puntoInicial.x  , 0 - puntoInicial.y};

        double dotProduct = vector1[0] * vector2[0] + vector1[1] * vector2[1];
        double magnitude1 = calculateMagnitude(vector1);
        double magnitude2 = calculateMagnitude(vector2);

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0; // Evitar la división por cero
        }

        double cosTheta = dotProduct / (magnitude1 * magnitude2);

        // Ángulo en radianes
        double thetaRad = Math.acos(cosTheta);

        // Convertir a grados
        double thetaDegrees = Math.toDegrees(thetaRad);

        return thetaDegrees;
    }

    public static double calculateMagnitude(double[] vector) {
        double primer_operando = Math.pow(vector[0], 2);
        double segundo_operando = Math.pow(vector[1], 2);

        return Math.sqrt(primer_operando + segundo_operando);
    }
}