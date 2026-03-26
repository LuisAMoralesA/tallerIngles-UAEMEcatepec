<%-- 
    Document   : principal_students
    Created on : 22 abr. 2025, 21:05:47
    Author     : Luis Morales
--%>

<%@page import="java.sql.*" %>
<%@page import="com.mysql.jdbc.Driver" %>
<%@page import ="controller.*"%>
<%@page import ="model.Consultas.*"%>
<%@page import ="model.Tables.*"%>
<%@page import ="java.util.*"%>
<%@page import = "jakarta.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel = "stylesheet" href = "../css/style_menuprincipal.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href = "../Images/uaem.png" rel = "icon"></link>
    <link href = "../Images/uaem.png" rel = "icon"/>
    <!--Librerias para alertas emergentes-->
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.all.min.js"></script>
    <!--Link para visualizar alertas
            https://sweetalert2.github.io/-->
    <title>Control Escolar de Administradores </title>
</head>
<body>
    <%
       //Obtiene la sesion al usuario
        HttpSession sesion = request.getSession();
        String usuario = (String) sesion.getAttribute("sesionIniciada");
        if(usuario == null){
            response.sendRedirect("/tallerDeInglesUAEM/view/sesionExpirada.jsp");
            return;
        }
        //Accede a la base de datos y accede a los datos del usuario
        BaseDatos base = new BaseDatos();
        ArrayList <ConsultasAdmin> lista = base.obtenerDatosAdministrador(usuario);
        Iterator<ConsultasAdmin> iter = lista.iterator();
        ConsultasAdmin per = null;
        //Inicializa cadenas de texto para guardar datos concatenados
        String nombreCompleto = "";
    %>
    <aside id = "menu_lateral">
        <% 
        if(iter.hasNext()){
            per = iter.next();
            //FUNCIONES DE CONCATENAR STRINGS
            nombreCompleto = base.concatenarDatosAdministrador(per.getId_admin());
            
            //** --> ATRIBUTOS DEL USUARIO PRINCIPAL
            //Obtiene el atributo del Rango para el resto de la sesion
            sesion.setAttribute("rango", per.getRango());
            //Obtiene el atributo del Id_User para el resto de la sesion
            sesion.setAttribute("userId", per.getId_user());
            //Obtiene el atributo del modificar datos personales para el resto de la sesion
            sesion.setAttribute("modificar", per.getId_user());
            //Obtiene el atributo del Rango para el resto de la sesion
            sesion.setAttribute("rangoModificar", per.getRango());
        }
        %>
        <ul id="menu_opciones">
            <li>
                <img src="../Images/Logo_Taller2.png" alt=""> 
            </li>

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
        </ul>
    </aside>

    <article >
        <%
                String opcion = request.getParameter("add");
                String encabezado = "Agregar ";
                String accion = "";
                String url = "";
                String rango = "";
                if(opcion != null){
                    switch(opcion){
                        case "1":
                            encabezado += "Estudiante";
                            accion = "../addStudent";
                            url = "../view/listaAlumnos.jsp";
                            rango = "ESTUDIANTE";
                            break;
                        case "2":
                            encabezado += "Profesor";
                            accion = "../addTeacher";
                            url = "../view/listaTeachers.jsp";
                            rango = "PROFESOR";
                            break;
                        case "3":
                            encabezado += "Administrador";
                            accion = "../addAdmin";
                            url = "../view/listaAdmin.jsp";
                            rango = "ADMINISTRADOR";
                            break;
                        case "4":
                            encabezado += "Grupo";
                            accion = "../addGroup";
                            url = "../view/listaGrupos.jsp";
                            break;
                    }
                }
                
            %>
        <div id = "perfil_usuario">
            <%
                String mensaje = (String) sesion.getAttribute("actualizacionCompleta");
                if (mensaje != null && !mensaje.isEmpty()){
            %>
                <script>
                    Swal.fire({
                        icon: "success",
                        title: "<%= mensaje %>",
                        draggable: true
                      });
                </script>
                <%}
                sesion.setAttribute("actualizacionCompleta", null);
            %>
            <div class="form-container1">
                <form class = "form_register" action="<%=accion%>" method="post">
                    <div class ="titulo-form">
                        <h1> 
                        <i class="fa-solid fa-person-circle-plus"></i><br> 
                        <%=encabezado%>
                        </h1>
                        <b>Favor de llenar los campos para agregar terminar el proceso. </b>
                    </div>
                    <input type = "hidden" name = "rango" id="rango" value = "<%=rango%>">
                    <div>
                        <%if (!opcion.equals("4")){%>
                        <label for = "apaterno">
                        Apellido Paterno:
                        </label>
                        <input type = "text" name = "apaterno" id = "apaterno" placeholder="Apellido Paterno" required>

                        <label for = "amaterno">
                            Apellido Materno:
                        </label>
                        <input type = "text" name = "amaterno" id = "amaterno" placeholder="Apellido Materno" required>

                        <label for = "name">
                            Nombre (s):
                        </label>
                        <input type = "text" name = "name" id = "name" placeholder="Nombre" required>
                        
                        <label for = "birthdate">
                            Fecha de Nacimiento:
                        </label>
                        <input type = "date" name = "birthdate" id = "birthdate" title="Fecha de nacimiento" class = "date" required>
                        
                        <label for = "phone">
                            Numero de Telefono:
                        </label>
                        <input type = "text" name = "phone" id = "phone" placeholder="Numero de Telefono" maxlength = 10 required>
                        
                        <label for = "email">
                            Correo Electronico:
                        </label>
                        <input type = "text" name = "email" id = "email" placeholder="Correo Electronico" required>
                        <%}
                            else {
                                //Accede a la base de datos y accede a los datos del usuario principal
                                ArrayList <Category> categorias = base.obtenerCategorias();
                                Iterator <Category> iteradorCat = categorias.iterator();
                                Category perCat = null;

                                ArrayList <Grade> nivel = base.obtenerNivel();
                                Iterator <Grade> iteradorNivel = nivel.iterator();
                                Grade perNivel = null;
                        %>
                        <label for = "grade">
                            Categoria a elegir:
                        </label>
                        <select name="grade" id="grade">
                        <%
                                while(iteradorNivel.hasNext()){
                                    perNivel = iteradorNivel.next();                           
                                    int idNivel = perNivel.getId_grade();
                                    String descNivel = perNivel.getDescription_grade();
                        %>
                                <option value= <%=idNivel%>> 
                                    <%=descNivel%> 
                                </option>
                                <%}%>
                         </select>
                        
                        <label for = "level">
                            Nivel del 1 al 3:
                        </label>
                        <input type = "number" name = "level" id = "level" min = 1 max = 3/>
                        <label for = "category">
                            Grupos a elegir:
                        </label>
                        <select name="category" id="category">
                        <%
                                while(iteradorCat.hasNext()){
                                    perCat = iteradorCat.next();                           
                                    int id = perCat.getId_category();
                                    String desc = perCat.getDescription_category();
                        %>
                                <option value= <%=id%>> 
                                    <%=desc%> 
                                </option>
                                <%}%>
                         </select>
                        <%}%>
                    </div> 
                    <div>
                        <%if (!opcion.equals("4")){%>
                            <%if (opcion.equals("1")){%>
                            <label for="sale_solo">
                                ¿Sale solo?: 
                            </label>
                            <select name="sale_solo" id="sale_solo">
                                <option value = "1" > SI </option>
                                <option value = "0" > NO </option>
                            </select>
                            <%}else if(opcion.equals("2")){%>
                            <label for="status">
                                Estatus del Profesor: 
                            </label>
                            <select name="status" id="status">
                                <option value = "VIGENTE" > VIGENTE </option>
                                <option value = "BAJA" > BAJA </option>
                            </select>
                            <label for="grupo">
                                Grupo Asignado: 
                            </label>
                            <select name="grupo" id="grupo">
                                <option value = "0"> Ningun Grupo Asignado </option>
                                <%
                                    ArrayList <ConsultaGrupos> grupos = base.obtenerDatosGrupos();
                                    Iterator <ConsultaGrupos> iterGrupos = grupos.iterator();
                                    ConsultaGrupos perGrupos = null;
                                    while(iterGrupos.hasNext()){
                                        perGrupos = iterGrupos.next();
                                        int idGrupo = perGrupos.getId_group();
                                        String datosGrupo = perGrupos.getDescription_grade() + " " + 
                                        perGrupos.getLevel_group() + ": " + perGrupos.getDescription_category();
                                %>
                                    <option value = "<%=idGrupo%>"> <%=datosGrupo%> </option>
                                <%}%>
                            </select>
                            <label for="classroom">
                                Salon de Clases: 
                            </label>
                            <input type="text" name="classroom" id="classroom" placeholder ="Salon de Clases" >
                        <%}%>
                    </div>
                    <br>    
                    <div id = "passSquare">
                        <label for = "password1">
                            <b>Creación de Contraseña:</b>
                        </label>
                        <input type = "password" name = "password1" id = "password1" placeholder="Contraseña" required >
                        <input type = "password" name = "password2" id = "password2" placeholder="Confirmar Contraseña" required> 
                        <%}%>
                    </div>
                
                <div id = "button">
                    <div id = "modificar"> 
                        <button type = "button" name = "add" id ="add" onclick = "showAlertAdd()">
                            <i class="fa-solid fa-pen"></i><br>
                            Agregar Datos
                        </button>
                        <button type = "button" name = "back" id ="back" onclick = "location.href = '<%=url%>'">
                            <i class="fa-solid fa-arrow-left-long"></i><br>
                            Regresar
                        </button>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </article>
    <script src = "/tallerDeInglesUAEM/js/mensajesEmergentes.js"></script>
</body>
</html>
