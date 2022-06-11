package com.example.centrosaludbd;

public class Events {
    String sintomas,medico,hora,paciente,fecha;

    public Events(String sintomas, String medico, String hora, String paciente,String fecha) {
        this.sintomas = sintomas;
        this.medico = medico;
        this.hora = hora;
        this.paciente = paciente;
        this.fecha=fecha;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
