import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet("/controller")
public class MainServlet extends HttpServlet {

    private MyDriver db;
    private Controller controller;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db = new MyDriver();
        controller = new Controller();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAction(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAction(request, response);
    }

    private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("status", "404");
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }

        if (action.equals( "getRooms")) {
            request.setAttribute("rooms", db.getRooms());
            request.setAttribute("room", null);
        }
        else if (action.equals( "redirectToAdd")){
            request.setAttribute("rooms", null);
            request.setAttribute("room", null);
        }
        else if (action.equals("addRoom")) {
            String name = request.getParameter("name");
            String country = request.getParameter("country");
            if (name == null || country == null) {
                request.setAttribute("status", "500");
            }
            Room room = new Room(name, country);
            db.addRoom(room);
            request.setAttribute("rooms", null);
            request.setAttribute("room", null);
        }
        else if(action.equals("enterRoom")){
                String id = request.getParameter("id");
                String address=request.getHeader("X-FORWARDED-FOR");
                Room room=db.getRoom(Integer.parseInt(id));
                if (controller.checkIpCountry(room, address)) {
                    request.setAttribute("room", room);
                    response.addHeader("val", Boolean.toString(room.isLightSwitched()));
                }else{
                    request.setAttribute("status", "403");
                    request.setAttribute("room", null);
                }
        }
        else if (action.equals("changeLight")){
            String id=request.getParameter("id");
            Room room=db.changeLight(Integer.parseInt(id));
            request.setAttribute("room", room);
            response.sendRedirect("/controller?action=enterRoom&id="+id);
        }
        else {
            request.setAttribute("status", "404");
        }
        if (request.getAttribute("status") == null) {
            request.setAttribute("status", "200");
        }
        if (!action.equals("changeLight")) {
            request.getRequestDispatcher("/").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        db.close();
    }
}

