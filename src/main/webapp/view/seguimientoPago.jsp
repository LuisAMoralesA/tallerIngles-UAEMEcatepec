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
    <title>Seguimiento de Pago </title>
    
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
                <a href="../view/menuAlumno.jsp">
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
                <a href="../view/menuAdministrador.jsp">
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
                <a href="../view/listaAdministradores.jsp">
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
                <form class = "form" action = "../updatePay" method = "post">
                    <h1> 
                    <i class="fa-solid fa-graduation-cap"></i><br> 
                    Seguimiento de Pago
                    </h1>
                <%
                    LocalDate hoy = LocalDate.now();
                    String mesActual = hoy.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                    mesActual = mesActual.substring(0, 1).toUpperCase() + mesActual.substring(1);
                %>

                
                <%
                    //Establece los datos del usuario a modificar
                    String idPayRequest = request.getParameter("idPay");
                    String idSesion = String.valueOf(sesion.getAttribute("pagos"));
                    
                    int usuarioModificar = 0;
                    if(idPayRequest != null)
                        usuarioModificar = Integer.parseInt(idPayRequest);
                    else
                        usuarioModificar = Integer.parseInt(idSesion);
                        
                    ArrayList <Payment> seguimiento = base.obtenerSeguimiento(usuarioModificar);
                    Iterator <Payment> iter1 = seguimiento.iterator();
                    Payment per1 = null;
                    
                    String nombreCompleto = base.concatenarDatosAlumno(usuarioModificar);
                    String periodoActual = base.obtenerPeriodo(mesActual);
                    
                    int idStatus = 0;
                    String status = "";
                    
                    if(iter1.hasNext()){
                        per1 = iter1.next();
                        idStatus = per1.getId_payment();
                        status = base.obtenerEstatus(per1.getPayment_status());
                    }
                    
                %>
                <h4> <%=nombreCompleto%> </h4>
                <table id = "tablaSeguimiento" class="table table-striped">
                    <input type = "hidden" id = "idPayment" name = "idPayment" value = "<%=usuarioModificar%>"/>
                    <thead>
                        <tr>       
                            <th scope="col">STATUS DEL ALUMNO </th>
                            <th scope="col">Mes Actual </th>
                            <th scope="col">Periodo Actual</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th scope="col">
                                <%if (!rangoPrincipal.equals("ADMINISTRADOR")){%>
                                    <%=status%> 
                                <%}
                                else {%> 
                                <select name="status" id="status">
                                    <%
                                        ArrayList <Payment_status> selectStatus = base.obtenerEstatus();
                                        Iterator <Payment_status> iterSelect = selectStatus.iterator();
                                        Payment_status perSelect = null;
                                        while(iterSelect.hasNext()){
                                            perSelect = iterSelect.next();
                                            int valor =perSelect.getId_status();
                                            String descriptionSelect = perSelect.getDescription_status();
                                    %>
                                    <option value = "<%=valor%>" <%=descriptionSelect.equals(status) ? "selected" : ""%>> <%=descriptionSelect%> </option>
                                    <%}%>
                                </select>
                                <%}%>
                            </th>
                            <th scope="col"><%=mesActual%></th>
                            <td scope="col"><%=periodoActual%></td>
                                                  
                        </tr>
                    </tbody>
                </table>     
                   
                <table id = "tablaAlumnos" class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Mes Correspondiente</th>
                            <th scope="col">Concepto de Pago</th>
                            <th scope="col">Costo ($)</th>
                            <th scope="col">Fecha de Corte. </th>
                            <th scope="col">Estatus de Referencia</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <%
                                ArrayList <Pay_simbology> listaPagos = base.obtenerCalendario(periodoActual);
                                Iterator <Pay_simbology> iter = listaPagos.iterator();
                                Pay_simbology per = null;

                                while(iter.hasNext()){
                                    per = iter.next();
                                    boolean mensualidadPagada = false;
                                    String nombreSelect = "";
                                    switch(per.getId_pay()){
                                        case 1: case 8: 
                                            mensualidadPagada = per1.isPay_1();
                                            nombreSelect = "mensualidad1";
                                            break;
                                        case 2: case 9: 
                                            mensualidadPagada = per1.isPay_2(); 
                                            nombreSelect = "mensualidad2";
                                            break;
                                        case 3: case 10: 
                                            mensualidadPagada = per1.isPay_3();
                                            nombreSelect = "mensualidad3";
                                            break;
                                        case 4: case 11: 
                                            mensualidadPagada = per1.isPay_4();
                                            nombreSelect = "mensualidad4";
                                            break;
                                        case 5: case 12: 
                                            mensualidadPagada = per1.isPay_5();
                                            nombreSelect = "mensualidad5";
                                            break;
                                        case 6: 
                                            mensualidadPagada = per1.isPay_6();
                                            nombreSelect = "mensualidad6";
                                            break;
                                        case 7: 
                                            mensualidadPagada = per1.isPay_7();
                                            nombreSelect = "mensualidad7";
                                            break;
                                        default: 
                                            mensualidadPagada = per1.isRegister_payment();
                                            nombreSelect = "mensualidadInscripcion";
                                            break;
                                    }
                            %>
                            <th scope="col"><%=per.getId_pay()%></th>
                            <th scope="col"><%=per.getMonth()%></th>
                            <td scope="col"><%=per.getDescription_pay()%> </td>
                            <td><%=per.getCost_pay()%></td>
                            <td><%=per.getDeadline_pay() != null ? per.getDeadline_pay() : "NO APLICA" %></td>
                            <%if (!rangoPrincipal.equals("ADMINISTRADOR")){%>
                            <td style = "background-color: rgba<%=!mensualidadPagada ? "(245, 39, 39, 0.1)" :"(39, 245, 135, 0.1)"%>">
                                <%=mensualidadPagada ? "PAGADO" : "PENDIENTE"%>
                            </td>
                            <%}
                            else{
                            %>
                            <td>
                                <select name="<%=nombreSelect%>" id="<%=nombreSelect%>" style = "background-color: rgba<%=!mensualidadPagada ? "(245, 39, 39, 1)" :"(39, 245, 135, 1)"%>">
                                    <option value = "1" <%=mensualidadPagada ? "selected" : ""%> style = "background-color: rgba<%=!mensualidadPagada ? "(245, 39, 39, 1)" :"(39, 245, 135, 1)"%>"> PAGADA </option>
                                    <option value = "0" <%=!mensualidadPagada ? "selected" : ""%> style = "background-color: rgba<%=!mensualidadPagada ? "(245, 39, 39, 1)" :"(39, 245, 135, 1)"%>"> PENDIENTE </option>
                                </select>
                            </td>
                            <%}%>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
                <%if(!rangoPrincipal.equals("ESTUDIANTE")){%>
                <%
                    String urlBack = "";
                    switch(rangoPrincipal){
                        case "ADMINISTRADOR":
                            urlBack ="../view/listaAlumnos.jsp";
                            break;
                        case "PROFESOR":
                            urlBack = "../view/actualizarCalificaciones.jsp";
                            break;
                    }
                %>
                <div id = "options">
                    <div id = "modificar">
                        <button type = "button" name = "update" id ="add" onclick="showAlertUpdate()">
                           <i class="fa-solid fa-arrow-rotate-right"></i><br>
                            Actualizar Pagos
                        </button>
                        <button type = "button"  name = "back" id ="back" onclick = "location.href = '<%=urlBack%>'">
                           <i class="fa-solid fa-arrow-left-long"></i><br>
                            Regresar
                        </button>
                    </div>
                </div>
                <%}%>
                </form> 
            </div>
        </div>
    </article>
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <script>
    $(document).ready(function() {
        $('#tablaAlumnos').DataTable({
            "pageLength": 5, 
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