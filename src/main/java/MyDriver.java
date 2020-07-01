import java.util.*;
import java.sql.*;

public class MyDriver {
    private Connection connection = null;
    private static final String DB_URL  = "jdbc:mysql://localhost:3306/rooms?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    MyDriver() {
        connect();
    }

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (Exception se) {
            se.printStackTrace();
            System.exit(1);
        }
    }

    void addRoom(Room room){
        try {
            PreparedStatement pst = connection.prepareStatement("insert into rooms (name, country, light) values (?, ?, ?)");
            pst.setString(1, room.getName());
            pst.setString(2, room.getCountry());
            pst.setBoolean(3, room.isLightSwitched());
            pst.executeUpdate();
        }catch (SQLException ex){ex.printStackTrace();}
    }
    ArrayList<Room> getRooms(){
        try {
            Statement st = connection.createStatement();
            ResultSet rs = null;
            rs = st.executeQuery("select * from rooms");
            return read(rs);
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    ArrayList<Room> read(ResultSet rs) throws SQLException {
        ArrayList<Room> arr = new ArrayList<Room>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String country = rs.getString(3);
            boolean light =rs.getBoolean(4);
            arr.add(new Room(id, name, country, light));
        }
        return arr;
    }

    Room changeLight(int id){
        try {
            Statement st = connection.createStatement();
            ResultSet rs = null;
            rs = st.executeQuery("select * from rooms where rooms.rooms.id="+id);
            Room room=read(rs).get(0);
            st=connection.createStatement();
            if (room.isLightSwitched()) {
                st.executeUpdate("UPDATE rooms SET light = 0 WHERE id = " + id);
                room.setLightSwitched(false);
            }else{
                st.executeUpdate("UPDATE rooms SET light = 1 WHERE id = " + id);
                room.setLightSwitched(true);
            }
            return room;
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    Room getRoom(int id){
        try {
            Statement st = connection.createStatement();
            ResultSet rs = null;
            rs = st.executeQuery("select * from rooms where rooms.rooms.id="+id);
            ArrayList<Room> result=read(rs);
            if (result.size()>0) {
                return result.get(0);
            }
            else{
                return null;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    void close(){
        try {
            connection.close();
        }catch (SQLException ex){ex.printStackTrace();}
    }
}