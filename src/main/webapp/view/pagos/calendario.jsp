<%-- 
    Document   : calendario
    Author     : Luis Morales
--%>
<%@page import ="java.sql.*" %>
<%@page import ="com.mysql.cj.jdbc.Driver" %>
<%@page import ="controller.*"%>
<%@page import ="model.Consultas.*"%>
<%@page import ="model.Tables.*"%>
<%@page import ="java.util.*"%>
<%@page import ="java.time.*" %>
<%@page import ="java.time.format.TextStyle" %>
<%@page import ="jakarta.servlet.http.HttpSession"%>
<%@page contentType ="text/html" pageEncoding ="UTF-8"%>
<%@page session ="true"%>
<!DOCTYPE html>
<html lang = "es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=Constantes.Titulos.TITULO_CALENDARIO%> </title>
    <link href = "<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" rel = "icon"/>
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_MENU_OPCIONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_BOTONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_CALENDARIO%>">
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
            <%@include file="../menuLateral/menuLateralAdministradores.jsp"%>
        </ul>
    </aside>
    <article>
        <div id = "perfil_usuario">
            <div class="form-container2">
                <%@include file="../sweetAlert/ventanaExito.jsp" %>
                    <!-- Titulo principal de la ventana-->
                    <h1> 
                        <i class="fa-solid fa-money-bill"></i><br> 
                        Calendario de Mensualidades
                    </h1>
                    
                    <p> El objetivo de este calendario es definir los valores monetarios (MXN) a <br>
                        las mensualidades del TEI, asi como cambiar entre periodos (A/B) dependiendo del caso</p>
                    
                    <!-- Información de hoy-->
                    <h3>Fecha de hoy:  <%=Constantes.HOY%></h3>
                    
                    <!--Periodo del semestre actual
                        Si no encuentra el mes en el calendario, devuelve el periodo del primer registro del calendario en BD.-->
                    <%
                        String mesActual = Constantes.MES.substring(0, 1).toUpperCase() + Constantes.MES.substring(1);
                        String periodoActual = base.obtenerPeriodo(mesActual);
                        if(periodoActual == null || periodoActual.trim().isEmpty()){
                            periodoActual = base.obtenerPeriodo(1);
                        }
                    %>
                    <h4> <%=periodoActual%> </h4>
                    
                    <!-- Logica de la tabla de pagos. -->
                    <%
                        //Busca una parametro de solicitud, indicando que se desea modificar un mes
                        String id_month = request.getParameter("id_month");
                        //Declara un arreglo de datos para almacenar el calendario y una variable para redirigir.
                        ArrayList <Pay_simbology> calendario;
                        String url = "";
                        //Define que si el usuario desea modificar un mes del calendario.
                        boolean condicion = id_month != null && !id_month.isEmpty();
                        //Si se cumple esta condición, obtiene solo los datos del mes a modificar.
                        if(condicion){
                           calendario = base.obtenerDatosDeMes(Integer.parseInt(id_month)); 
                           url = Constantes.VentanasJSP.URL_CALENDARIO;
                        }
                        //De lo contrario, obtiene todos los registros del calendario para visualización
                        else{
                           calendario = base.obtenerCalendario();
                           url = Constantes.VentanasJSP.URL_LISTA_ADMIN;
                        }
                        //Define el iterador para transaladrse mes con mes. 
                        Iterator <Pay_simbology> iter = calendario.iterator();
                        Pay_simbology per = null;
                    %>
                    <%
                        //Si se desea modificar el mes, la tabla se convierte en un formulario. 
                        if(condicion){%>
                        <form class = "form" id = "formUpdateCalendar" action = "<%=Constantes.Servlets.SERVLET_ACTUALIZAR_CALENDARIO%>" method = "post">
                        <%}%>
                    <table id = "tablaAlumnos" class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Concepto de mensualidad</th>
                            <th scope="col">Descripcion</th>
                            <th scope="col">Importe ($MXN)</th>
                            <th scope="col">Fecha de corte</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        //Mientras la base de datos tenga registros, define una variable
                        //a cada campo para imprimir en la tabla. 
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
                        <tr style = "background-color: rgba<%=mes.equals("NA") ? "(255,0,0,0.5)" : "(0,255,0,0.5)"%>">
                            <!--#-->
                            <th scope = "col">
                                <%if(condicion){%>
                                   <input type = "hidden" name = "id_pay" id = "id_pay" value = "<%=id%>"/> 
                                <%}%>
                                <%=id%> 
                            </th>
                            
                            <!--Concepto de mensualidad-->
                            <td>
                                <%if(condicion){%>
                                    <%if (!mes.equals("Inscripcion / Reinscripcion")){%>
                                        <select name = "mes" id = "mes">
                                        <%for(Month mesSelect: Month.values()){
                                            Locale espanol = new Locale("es", "ES");
                                            String nombreMes = mesSelect.getDisplayName(TextStyle.FULL,espanol);
                                            nombreMes = nombreMes.substring(0, 1).toUpperCase() + nombreMes.substring(1);%>
                                            <option value = "<%=nombreMes%>" <%=mes.equals(nombreMes)? "selected" : ""%>> <%=nombreMes%> </option>
                                        <%}%>
                                            <option value = "NA" <%=mes.equals("") || mes.equals("NA")? "selected" : ""%>> No Aplica </option>
                                        </select>
                                    <%}
                                       else{%>
                                        <input type = "hidden" name = "mes" id = "mes" value = "<%=mes%>"/>
                                        <%=mes%>
                                        <%}%>
                                <%}
                                  else{%>
                                    <%=mes%>
                                <%}%>    
                            </td>
                            
                            <!--Descripcion-->
                            <td>
                                <%if(condicion){%>
                                     <input type = "hidden" name = "descripcion" id = "descripcion" value = "<%=descripcion%>"/> 
                                <%}%>
                                <%=descripcion%>
                            </td>
                            
                            <!--Importe (MXN)-->
                            <td>
                                <%if(condicion){%>
                                    $<input type ="number" name = "mensualidad" id = "mensualidad" step = 50.0 value = "<%=mensualidad%>" />
                                <%}
                                  else{%>
                                    $ <%=mensualidad%> MXN
                                <%}%>
                            </td> 
                            
                            <!--Fecha de corte-->
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
                            
                            <!--Periodo Actual-->
                            <%if (condicion){%>
                                <%if("Cualquiera".equals(periodo)){%>
                                <input type = "hidden" name = "periodo" id = "periodo" value = "<%=periodo%>">
                                <%}else{%>
                                <input type = "hidden" name = "periodo" id = "periodo" value = "<%=periodoActual%>">
                                <%}%>
                            <%}
                            else{%>
                                <input type = "hidden" name = "periodo" id = "periodo" value = "<%=periodo%>">
                            <%}%>
                            <td>
                                 <%if(condicion){%>
                                    <!--Boton de actualizar mensualidad. -->
                                    <button type =  "button" id ="upd" title="Actualizar mensualidad" onclick = "showAlertUpdateCalendar()">
                                        <i class="fa-solid fa-rotate"></i>
                                    </button>
                                <%}else{%>
                                    <!--Boton de modificar mes. -->
                                    <a href = '<%=Constantes.VentanasJSP.URL_CALENDARIO%>?id_month=<%=id%>' title="Modificar Datos de Mes" > 
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
                    
                    <!--Boton de regresar. -->
                    <div id = "button">
                        <div id = "modificar"> 
                            <button type = "button" name = "back" id ="back" onclick = "location.href = '<%=url%>'">
                                <i class="fa-solid fa-arrow-left-long"></i><br>
                                Regresar
                            </button>
                            <%if(!condicion){%>
                            <form class = "formPeriodo" id = "formUpdatePeriod" action = "<%=Constantes.Servlets.SERVLET_ACTUALIZAR_PERIODO%>" method = "post">
                                <input type = "hidden" name = "periodoActual" id = "periodoActual" value = "<%=periodoActual%>"/>
                                <!--Boton de actualizar periodo. -->
                                <button type =  "button" id ="updPeriodo" title="Actualizar periodo" onclick = "showAlertUpdatePeriodo()">
                                    <i class="fa-solid fa-rotate"></i><br>
                                    Actualizar periodo
                                </button>
                            </form>
                            <%}%>
                        </div>
                    </div>
            </div>
        </div>
    </article>
    <script src="<%=Constantes.LinksExternos.URL_JS_JQUERY%>"></script>
    <script src="<%=Constantes.LinksExternos.URL_JS_JQUERY_DATATABLES%>"></script>
    <script src="<%=Constantes.LinksExternos.URL_JS_DATATABLES%>"></script>
    <script>
    $(document).ready(function() {
        $('#tablaAlumnos').DataTable({
            "pageLength": 8, 
            "responsive": true,
            "lengthChange": false,
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
            }
        });
    });
    </script>
    <script src = "<%=Constantes.JavaScript.URL_JS_MENSAJES_EMERGENTES%>"></script>
</body>
</html>