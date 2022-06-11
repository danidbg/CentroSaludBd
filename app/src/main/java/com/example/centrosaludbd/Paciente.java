package com.example.centrosaludbd;

public class Paciente {
    private int idPaciente;
    private String dni,nombre,apellidos,password,ciudad,sexo,fecha,cp;

    public Paciente(String dni, String nombre, String apellidos, String password, String ciudad, String sexo, String cp, String fecha) {
        this.cp = cp;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.ciudad = ciudad;
        this.sexo = sexo;
        this.fecha=fecha;
    }

    public Paciente(int idPaciente, String cp, String dni, String nombre, String apellidos, String password, String ciudad, String sexo, String fecha) {
        this.idPaciente = idPaciente;
        this.cp = cp;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.ciudad = ciudad;
        this.sexo = sexo;
        this.fecha=fecha;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
