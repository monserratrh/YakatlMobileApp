/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipn.cic.ci.yakatl;

/**
 *
 * @author sergio
 */
public class Sensor {
    
    private String sensor;
    private String fecha;
    private String contaminante;
    private String valor;
    private String puntos_imeca;
    private String calidad;

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContaminante() {
        return contaminante;
    }

    public void setContaminante(String contaminante) {
        this.contaminante = contaminante;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getPuntos_imeca() {
        return puntos_imeca;
    }

    public void setPuntos_imeca(String puntos_imeca) {
        this.puntos_imeca = puntos_imeca;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }
}
