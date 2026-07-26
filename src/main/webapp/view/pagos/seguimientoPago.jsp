<%-- 
    Document   : seguimientoPago
    Author     : Luis Morales
--%>
<%@page import="java.sql.*" %>
<%@page import="com.mysql.cj.jdbc.Driver" %>
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
    <title><%=Constantes.Titulos.TITULO_SEGUIMIENTO_PAGO%> </title>
    <link href = "<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" rel = "icon"/>
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_MENU_OPCIONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_BOTONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_SEGUIMIENTO%>">
    <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_FONTAWESOME%>" 
          integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_BOOTSTRAP%>"  
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!--Librerias para alertas emergentes-->
    <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_SWEETALERT%>" >
    <script src="<%=Constantes.LinksExternos.URL_JS_SWEETALERT%>"></script>
    <link rel="stylesheet" href="<%=Constantes.LinksExternos.URL_CSS_DATATABLES%>">
</head>
<body>
    <%
        //Obtiene la sesion al usuario principal
        HttpSession sesion = request.getSession();
        String usuarioPrincipal = (String)sesion.getAttribute("sesionIniciada");
        
        if(usuarioPrincipal == null){
            response.sendRedirect(Constantes.VentanasJSP.URL_SESION_EXPIRADA);
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
                <img src="<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" alt=""> 
            </li>
            
            <%
                if (rangoPrincipal.equals("ESTUDIANTE")){
                    //Obtiene el atributo del Id de pagos y calificaciones para el resto de la sesion
                    sesion.getAttribute("pagos");
                    sesion.getAttribute("calif");
            %>
            <li>
                <a href="<%=Constantes.VentanasJSP.URL_MENU_ALUMNO%>">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>

            <li>
                <a href="<%=Constantes.VentanasJSP.URL_SEGUIMIENTO_PAGO%>" style = "background-color: rgba(44, 82, 52, 1)">
                    <i class="fa-solid fa-dollar-sign"></i> <br>
                    Seguimiento
                </a>
            </li>

            <li>
                <a href="<%=Constantes.VentanasJSP.URL_VISTA_CALIFICACIONES%>">
                    <i class="fa-solid fa-school"></i> <br>
                    Calificaciones
                </a>
            </li>

            <li>
                <a href="<%=Constantes.Servlets.SERVLET_CERRAR_SESION%>">
                    <i class="fa-solid fa-right-from-bracket"></i> <br>
                    Cerrar Sesión
                </a>
            </li>

            <%}else {%>
            <%@include file="../menuLateral/menuLateralAdministradores.jsp"%>
            <%}%>
        </ul>
    </aside>
    <article>
        <div id = "perfil_usuario">
            <div class="form-container2">
                <form class = "form" id = "formUpdatePayment" action = "<%=Constantes.Servlets.SERVLET_ACTUALIZAR_PAGOS%>" method = "post">
                    <!--Encabezado del formulario de pago-->
                    <h1> 
                        <i class="fa-solid fa-graduation-cap"></i><br> 
                        Seguimiento de Pago
                    </h1>
                <%
                    //Busca si existe una peticion de modificacion 
                    String idPayRequest = request.getParameter("idPay");
                    String idSesion = String.valueOf(sesion.getAttribute("pagos"));
                    String mesActual = Constantes.MES.substring(0, 1).toUpperCase() + Constantes.MES.substring(1);
                    
                    int usuarioModificar = 0;
                    if(idPayRequest != null)
                        usuarioModificar = Integer.parseInt(idPayRequest);
                    else
                        usuarioModificar = Integer.parseInt(idSesion);
                        
                    //Obtiene la lista de pagos del alumno seleccionado. 
                    ArrayList <Payment> seguimiento = base.obtenerSeguimiento(usuarioModificar);
                    Iterator <Payment> iter1 = seguimiento.iterator();
                    Payment per1 = null;
                    
                    //Junta el nombre del alumno que desea modificar el estatus de pago
                    String nombreCompleto = base.concatenarDatosAlumno(usuarioModificar);
                    String periodoActual = !base.obtenerPeriodo(mesActual).trim().isEmpty() ? base.obtenerPeriodo(mesActual) : base.obtenerPeriodo(1);
                    
                    int idStatus = 0;
                    String status = "";
                    
                    //Obtiene el estatus del alumno para el pago. 
                    if(iter1.hasNext()){
                        per1 = iter1.next();
                        idStatus = per1.getId_payment();
                        status = base.obtenerEstatus(per1.getPayment_status());
                    }
                %>
                <!--Encabezado del nombre del alumno.-->
                <h4>  
                    <%=nombreCompleto%> 
                </h4>
                
                <!--Tabla general del periodo Actual.-->
                <table id = "tablaSeguimiento" class="table table-striped">
                    <input type = "hidden" id = "idPayment" name = "idPayment" value = "<%=usuarioModificar%>"/>
                    <thead>
                        <tr>       
                            <th scope="col">Estatus del Alumno </th>
                            <th scope="col">Mes Actual </th>
                            <th scope="col">Periodo Actual</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <!--Estatus del Alumno. -->
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
                            
                            <!--Mes actual. -->
                            <th scope="col"><%=mesActual%></th>
                            
                            <!--Periodo actual. -->
                            <td scope="col"><%=periodoActual%></td>                   
                        </tr>
                    </tbody>
                </table>
                        
                <!--Tabla de seguimiento de pago del alumno-->   
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
                        <!--Logica de mensualidades-->
                            <%
                                //1. Obtiene todo el calendario filtrado con los meses correspondientes al periodo Actual
                                ArrayList <Pay_simbology> listaPagos = base.obtenerCalendarioFiltrado();
                                Iterator <Pay_simbology> iter = listaPagos.iterator();
                                Pay_simbology per = null;
                                //Obtiene una variable para el conteo de la tabla. 
                                int conteo = 0;
                                //Mientras tenga registros
                                while(iter.hasNext()){
                                    per = iter.next();
                                    boolean mensualidadPagada = false;
                                    String nombreSelect = "";
                                    switch(per.getId_pay()){
                                        case 1: 
                                            mensualidadPagada = per1.isPay_1();
                                            nombreSelect = "mensualidad1";
                                            break;
                                        case 2: 
                                            mensualidadPagada = per1.isPay_2(); 
                                            nombreSelect = "mensualidad2";
                                            break;
                                        case 3: 
                                            mensualidadPagada = per1.isPay_3();
                                            nombreSelect = "mensualidad3";
                                            break;
                                        case 4: 
                                            mensualidadPagada = per1.isPay_4();
                                            nombreSelect = "mensualidad4";
                                            break;
                                        case 5: 
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
                                    conteo++;
                            %>
                            <!--#-->
                            <th scope="col"><%=conteo%></th>
                            
                            <!--Mes correspondiente. -->
                            <th scope="col"><%=per.getMonth()%></th>
                            
                            <!--Concepto de pago. -->
                            <td scope="col"><%=per.getDescription_pay()%> </td>
                            
                            <!--Costo($). -->
                            <td><%=per.getCost_pay()%></td>
                            
                            <!--Fecha de corte-->
                            <td><%=per.getDeadline_pay() != null ? per.getDeadline_pay() : "NO APLICA" %></td>
                            
                            <!--Estatus de referencia. -->
                            <%if (!rangoPrincipal.equals("ADMINISTRADOR")){%>
                            <td style = "background-color: rgba<%=!mensualidadPagada ? "(245, 39, 39, 0.5)" :"(63, 166, 83, 0.5)"%>">
                                <%=mensualidadPagada ? "PAGADO" : "PENDIENTE"%>
                            </td>
                            <%}
                            else{
                            %>
                            <input type = "hidden" name = "nombreCompleto" id ="nombreCompleto" value = "<%=nombreCompleto%>">
                            <td>
                                <select name="<%=nombreSelect%>" id="<%=nombreSelect%>" class="select-pago" style = "background-color: rgba<%=!mensualidadPagada ? "(245, 39, 39, 1)" :"(63, 166, 83, 1)"%>">
                                    <option value = "1" <%=mensualidadPagada ? "selected" : ""%>> PAGADA </option>
                                    <option value = "0" <%=!mensualidadPagada ? "selected" : ""%>> PENDIENTE </option>
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
                            urlBack = Constantes.VentanasJSP.URL_LISTA_ALUMNOS;
                            break;
                        case "PROFESOR":
                            urlBack = Constantes.VentanasJSP.URL_ASIGNAR_CALIFICACIONES;
                            break;
                    }
                %>
                <div class = "options">
                    <div id = "modificar">
                        <button type = "button" name = "update" id ="update" onclick="showAlertUpdatePayment()">
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
    <script src ="<%=Constantes.LinksExternos.URL_JS_JQUERY%>"></script>
    <script src ="<%=Constantes.LinksExternos.URL_JS_JQUERY_DATATABLES%>"></script>
    <script src ="<%=Constantes.LinksExternos.URL_JS_DATATABLES%>"></script>
    <script src ="<%=Constantes.JavaScript.URL_JS_SEGUIMIENTO_PAGO%>"></script>
    <script src ="<%=Constantes.JavaScript.URL_JS_MENSAJES_EMERGENTES%>"></script>
</body>
</html>