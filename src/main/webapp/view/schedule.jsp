<%@page import="java.sql.*" %>
<%@page import="com.mysql.jdbc.Driver" %>
<%@page import ="controller.*"%>
<%@page import ="model.Consultas.*"%>
<%@page import ="model.Tables.*"%>
<%@page import ="java.util.*"%>
<%@page import="java.time.*" %>
<%@page import="java.time.format.TextStyle" %>
<%@page import = "jakarta.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calendario de Pagos </title>
    
    <link rel="stylesheet" href="../css/style_menuprincipal.css" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href = "../Images/uaem.png" rel = "icon"/>
    <!--Librerias para alertas emergentes-->
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.all.min.js"></script>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
</head>

<body>
    <%
        //Obtiene la sesion al usuario principal
        HttpSession sesion = request.getSession();
        String usuarioPrincipal = (String)sesion.getAttribute("sesionIniciada");
        
        if(usuarioPrincipal == null){
            response.sendRedirect("/tallerDeInglesUAEM/view/sesionExpirada.jsp");
            return;
        }
        
        String rangoPrincipal = String.valueOf(sesion.getAttribute("rango"));
        String id_userPrincipal = String.valueOf(sesion.getAttribute("userId"));
        //Accede a la base de datos y accede a los datos del usuario principal
        BaseDatos base = new BaseDatos();
    %>
    <aside id = "menu_lateral">
        <ul id="menu_opciones">
            <li>
                <img src="../Images/Logo_Taller2.png" alt=""> 
            </li>
            
            <%
                if (rangoPrincipal.equals("ESTUDIANTE")){
                    //Obtiene el atributo del Id de pagos y calificaciones para el resto de la sesion
                    sesion.getAttribute("pagos");
                    sesion.getAttribute("calif");
            %>
            <li>
                <a href="../view/principal_students.jsp">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>

            <li>
                <a href="../view/seguimientoPago.jsp" style = "background-color: rgba(44, 82, 52, 1)">
                    <i class="fa-solid fa-dollar-sign"></i> <br>
                    Seguimiento
                </a>
            </li>

            <li>
                <a href="../view/vistaCalificaciones.jsp">
                    <i class="fa-solid fa-school"></i> <br>
                    Calificaciones
                </a>
            </li>

            <li>
                <a href="../cerrarSesion">
                    <i class="fa-solid fa-right-from-bracket"></i> <br>
                    Cerrar Sesión
                </a>
            </li>
            <%}else {%>
            <li>
                <a href="../view/principal_admin.jsp">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>

            <li>
                <a href="../view/listaAlumnos.jsp">
                   <i class="fa-solid fa-users-line"></i><br>
                    Alumnos
                </a>
            </li>

            <li>
                <a href="../view/listaTeachers.jsp">
                    <i class="fa-solid fa-chalkboard-user"></i>  <br>
                    Maestros
                </a>
            </li>
            
            <li>
                <a href="../view/listaAdmin.jsp">
                    <i class="fa-brands fa-black-tie"></i><br>
                    Administradores
                </a>
            </li>
            
            <li>
                <a href="../view/listaGrupos.jsp">
                   <i class="fa-solid fa-school"></i><br>
                    Grupos
                </a>
            </li>

            <li>
                <a href="../view/listaDocumentos.jsp">
                    <i class="fa-solid fa-print"></i><br>
                    Documentos
                </a>
            </li>

            <li>
                <a href="../cerrarSesion">
                    <i class="fa-solid fa-right-from-bracket"></i> <br>
                    Cerrar Sesión
                </a>
            </li>
            <%}%>
        </ul>
    </aside>
    <article>
        <div id = "perfil_usuario">
            <div class="form-container2">
                <%
                String mensaje = (String) sesion.getAttribute("actualizacionCompleta");
                String mensajeUsuario = (String) sesion.getAttribute("userNameRegistrado");
                if (mensaje != null && !mensaje.isEmpty()){
            %>
                <script>
                    Swal.fire({
                        icon: "success",
                        title: "<%= mensaje %>",
                        text: "<%= mensajeUsuario %>",
                        draggable: true
                      });
                </script>
                <%}
                sesion.setAttribute("actualizacionCompleta", null);
                sesion.setAttribute("userNameRegistrado", null);
            %>
                    <h1> 
                        <i class="fa-solid fa-graduation-cap"></i><br> 
                        Calendario de Periodos
                    </h1>
                    <%
                        LocalDate hoy = LocalDate.now();
                        String mesActual = hoy.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                        mesActual = mesActual.substring(0, 1).toUpperCase() + mesActual.substring(1);
                    %>
                    <h4>Fecha actual: <%=hoy%> </h4>
                    <%
                        String id_month = request.getParameter("id_month");
                        ArrayList <Pay_simbology> calendario;
                        String url = "";
                        boolean condicion = id_month != null && !id_month.isEmpty();
                        if(condicion){
                           calendario = base.obtenerDatosDeMes(Integer.parseInt(id_month)); 
                           url = "/tallerDeInglesUAEM/view/schedule.jsp";
                        }
                        else{
                           calendario = base.obtenerCalendario();
                           url = "/tallerDeInglesUAEM/view/listaAdmin.jsp"; 
                        }
                        Iterator <Pay_simbology> iter = calendario.iterator();
                        Pay_simbology per = null;
                    %>
                    <%if(condicion){%>
                    <form class = "form" action = "../updateSchedule" method = "post">
                    <%}%>
                    <table id = "tablaAlumnos" class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Periodo</th>
                            <th scope="col">Concepto de mensualidad</th>
                            <th scope="col">Descripcion</th>
                            <th scope="col">Costo de mensualidad</th>
                            <th scope="col">Fecha de corte</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        while(iter.hasNext()){
                            per = iter.next();
                            int id = per.getId_pay();
                            String mes = per.getMonth();
                            String nombreSelect = "";
                            String descripcion = per.getDescription_pay();
                            double mensualidad = per.getCost_pay();
                            String periodo = per.getPeriod_pay();
                            Object deadline = per.getDeadline_pay();
                    %>
                        <tr>
                            <th scope = "col">
                                <%if(condicion){%>
                                   <input type = "hidden" name = "id_pay" id = "id_pay" value = "<%=id%>"/> 
                                   <%=id%> 
                                <%}else{%>
                                   <%=id%>      
                                <%}%>
                            </th>
                            <td>
                                <%if(condicion){%>
                                   <%if (!periodo.equals("Cualquiera")){%>
                                        <select name = "periodo" id = "periodo">
                                            <option value = "Periodo A" <%=periodo.equals("Periodo A")? "selected" : ""%>> A </option>
                                            <option value = "Periodo B" <%=periodo.equals("Periodo B")? "selected" : ""%>> B </option>
                                        </select>
                                    <%}
                                    else{%>
                                        <input type = "hidden" name = "periodo" id = "periodo" value = "<%=periodo%>"/>
                                        <%=periodo%>
                                    <%}%> 
                                <%}else{%>
                                   <%=periodo%>     
                                <%}%>
                            </td>
                            <td>
                                <%if(condicion){%>
                                    <input type = "hidden" name = "mes" id = "mes" value = "<%=mes%>"/> 
                                    <%=mes%> 
                                <%}
                                  else{%>
                                    <%=mes%>
                                <%}%>
                            </td>
                            <td>
                                <%if(condicion){%>
                                     <%if (!descripcion.equals("Pago de Inscripcion")){%>
                                        <select name = "descripcion" id = "descripcion">
                                            <option value = "Primer mes" <%=descripcion.equals("Primer mes")? "selected" : ""%>> Primer mes </option>
                                            <option value = "Segundo mes" <%=descripcion.equals("Segundo mes")? "selected" : ""%>> Segundo mes </option>
                                            <option value = "Tercer mes" <%=descripcion.equals("Tercer mes")? "selected" : ""%>> Tercer mes </option>
                                            <option value = "Cuarto mes" <%=descripcion.equals("Cuarto mes")? "selected" : ""%>> Cuarto mes </option>
                                            <option value = "Quinto mes" <%=descripcion.equals("Quinto mes")? "selected" : ""%>> Quinto mes </option>
                                            <option value = "Sexto mes" <%=descripcion.equals("Sexto mes")? "selected" : ""%>> Sexto mes </option>
                                            <option value = "Septimo mes" <%=descripcion.equals("Septimo mes")? "selected" : ""%>> Septimo mes </option>
                                        </select>
                                        <%}
                                        else{%>
                                            <input type = "hidden" name = "descripcion" id = "descripcion" value = "<%=descripcion%>"/>
                                            <%=descripcion%>
                                        <%}%>
                                <%}
                                  else{%>
                                    <%=descripcion%>
                                <%}%>
                            </td>
                            <td>
                                <%if(condicion){%>
                                    $<input type ="number" name = "mensualidad" id = "mensualidad" step = 50.0 value = "<%=mensualidad%>" />
                                <%}
                                  else{%>
                                    $ <%=mensualidad%> MXN
                                <%}%>
                            </td> 
                            
                            <td>
                                <%if(condicion){%>
                                    <%if(id != 13){%>
                                    <input required type = "date" name = "deadline" id = "deadline" value = "<%=deadline%>" >
                                    <%}
                                    else{%>
                                    <input type = "hidden" name = "deadline" id = "deadline" value = "<%=deadline%>" >
                                    <%}%>
                                <%}
                                  else{%>
                                    <%if(deadline !=null){%>
                                        <%=deadline%>
                                    <%}else {%>
                                        NO APLICA
                                    <%}%>
                                <%}%>
                                
                            </td>
                            <td>
                                 <%if(condicion){%>
                                    <button type =  "button" id ="upd" title="Actualizar mensualidad" onclick = "showAlertUpdate()">
                                        <i class="fa-solid fa-rotate"></i>
                                    </button>
                                <%}
                                  else{%>
                                    <a href = '../view/schedule.jsp?id_month=<%=id%>' title="Modificar Datos de Mes" > 
                                        <i class="fa-solid fa-pen-clip"></i>
                                    </a> 
                                <%}%>
                            </td>
                        </tr>
                    <%}%>
                    </tbody>
                    </table>
                    <%if(condicion){%>
                    </form>
                    <%}%>
                    <div id = "button">
                    <div id = "modificar"> 
                        <button type = "button" name = "back" id ="back" onclick = "location.href = '<%=url%>'">
                            <i class="fa-solid fa-arrow-left-long"></i><br>
                            Regresar
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </article>
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <script>
    $(document).ready(function() {
        $('#tablaAlumnos').DataTable({
            "pageLength": 7, 
            "responsive": true,
            "lengthChange": false,
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
            }
        });
    });
    </script>
    <script src = "/tallerDeInglesUAEM/js/prueba.js"></script>
    <script src = "/tallerDeInglesUAEM/js/mensajesEmergentes.js"></script>
</body>
</html>