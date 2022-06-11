package com.example.centrosaludbd;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class PruebaDb {

    private Statement s;
    private ResultSet rs;
    private PreparedStatement ps;

    private Connection con;
    private Statement stat;



    public Connection getConexion() {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://daniel.almazena.net:3306/db_dani", "dani", "blazquez");
            stat = con.createStatement();
            System.out.println("Bien");
        } catch(Exception e){
            e.printStackTrace();
            System.err.println("Error conexion");
        }
        return con;

    }

    public boolean accesoLogin(String dni, String pass) {
        boolean exito =false;
        PruebaDb con = new PruebaDb();
        Connection conexion = con.getConexion();

        try {
            ps = conexion.prepareStatement("SELECT dni, pass FROM Paciente WHERE dni=? AND pass=?");
            ps.setString(1,dni);
            ps.setString(2,pass);
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("dni")+" "+rs.getString("pass"));
                if(dni.equalsIgnoreCase(rs.getString("dni"))&& pass.equals(rs.getString("pass"))){
                    exito=true;

                }else{
                    exito=false;
                }
            }
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error en el catch");
        }
        return exito;
    }


    public int registrarUser(Paciente p) {
        PruebaDb con = new PruebaDb();
        Connection conexion = con.getConexion();
        String sql="INSERT INTO Paciente(dni,nombre,apellidos,pass,ciudad,cp,sexo,fecha_nacimiento) VALUES (?,?,?,?,?,?,?,?)";
        int res=-1;
        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellidos());
            ps.setString(4, p.getPassword());
            ps.setString(5, p.getCiudad());
            ps.setString(6, p.getCp());
            ps.setString(7, p.getSexo());
            ps.setString(8, p.getFecha());

            res = ps.executeUpdate();



            getConexion().close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
        }
        return res;
    }


    public String seleccionarNombre(String dni) {
        boolean exito = false;
        PruebaDb con = new PruebaDb();
        Connection conexion = con.getConexion();
        String nombre,apellidos,nombre_completo="";
        try {
            ps = conexion.prepareStatement("SELECT nombre ,apellidos FROM Paciente WHERE dni=?");
            ps.setString(1, dni);
            rs = ps.executeQuery();

            while (rs.next()) {
               nombre=rs.getString(1);
               apellidos=rs.getString(2);
               nombre_completo=nombre+" "+apellidos;
            }
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error en el catch");
        }
        return nombre_completo;
    }

    public int getIdNombre(String nombre){
        int id_paciente=0;
        PruebaDb con = new PruebaDb();
        Connection conexion = con.getConexion();
        String[]nom=nombre.split(" ");
        try {
            ps = conexion.prepareStatement("SELECT idPaciente FROM Paciente WHERE nombre=? AND apellidos=?");
            ps.setString(1, nom[0]);
            ps.setString(2, nom[1]);
            rs = ps.executeQuery();

            while (rs.next()) {
                id_paciente=rs.getInt(1);
                System.out.println(id_paciente);
            }
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error en el catch");
        }
        return id_paciente;
    }

    public int getIdMedico(String nombre){
        int id_medico=0;
        PruebaDb con = new PruebaDb();
        Connection conexion = con.getConexion();
        String[]nom=nombre.split(" ");
        try {
            ps = conexion.prepareStatement("SELECT idMedico FROM Medico WHERE nombre=? AND apellidos=?");
            ps.setString(1, nom[0]);
            ps.setString(2, nom[1]);
            rs = ps.executeQuery();

            while (rs.next()) {
                id_medico=rs.getInt(1);
                System.out.println(id_medico);
            }
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error en el catch");
        }
        return id_medico;
    }

    public int getIdEspecialidad(String especialidad){
        int id_especialidad=0;
        PruebaDb con = new PruebaDb();
        Connection conexion = con.getConexion();

        try {
            ps = conexion.prepareStatement("SELECT idEspecialidad FROM Especialidad WHERE especialidad=?");
            ps.setString(1, especialidad);
            rs = ps.executeQuery();

            while (rs.next()) {
                id_especialidad=rs.getInt(1);
                System.out.println(id_especialidad);
            }
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error en el catch");
        }
        return id_especialidad;
    }


    public ArrayList<String> accederMedicos(String especialidad){
        ArrayList<String> medicos=new ArrayList<>();
        PruebaDb con = new PruebaDb();
        Connection conexion = con.getConexion();
        int id=con.getIdEspecialidad(especialidad);
        String nombre,apellidos;
        try {
            ps = conexion.prepareStatement("SELECT nombre,apellidos FROM Medico WHERE idEspecialidad=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                nombre=rs.getString(1);
                apellidos=rs.getString(2);
                medicos.add(nombre+" "+apellidos);
            }
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error en el catch");
        }
        return medicos;
    }



    public int registrarCita(Events e) {
        PruebaDb con = new PruebaDb();
        Connection conexion = con.getConexion();
        String sql="INSERT INTO Cita(idPaciente,idMedico,fecha,hora,sintomas) VALUES (?,?,?,?,?)";
        int idPaciente=con.getIdNombre(e.paciente);
        int idMedico=con.getIdMedico(e.getMedico());
        boolean existe=con.comprobarHora(e);
        int res=-1;
        if(existe==false) {
            try {
                ps = conexion.prepareStatement(sql);

                ps.setInt(1, idPaciente);
                ps.setInt(2, idMedico);
                ps.setString(3, e.getFecha());
                ps.setString(4, e.getHora());
                ps.setString(5, e.getSintomas());


                res = ps.executeUpdate();


                getConexion().close();

            } catch (Exception exception) {
                exception.printStackTrace();
                System.out.println(sql);
            }
        }
        return res;
    }

    public boolean comprobarHora(Events e) {
        boolean existe = false;
        PruebaDb con = new PruebaDb();
        Connection conexion = con.getConexion();

        try {
            ps = conexion.prepareStatement("SELECT hora FROM Cita WHERE fecha=? AND hora=?");
            ps.setString(1, e.getFecha());
            ps.setString(2,e.getHora());
            rs = ps.executeQuery();

            while (rs.next()) {
                if(e.getHora().equalsIgnoreCase(rs.getString(1))){
                    existe=true;

                }else{
                    existe=false;

                }
            }
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.err.println("Error en el catch");
        }
        return existe;
    }

}
