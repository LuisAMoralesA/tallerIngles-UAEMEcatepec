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
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=Constantes.TITULO_ACTUALIZAR_INFORMACION%> </title>
    
    <link rel="stylesheet" href="../css/style_menuprincipal.css" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href = "../Images/uaem.png" rel = "icon"/>
    <!--Librerias para alertas emergentes-->
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.all.min.js"></script>
        <!--Link para visualizar alertas
            https://sweetalert2.github.io/-->
</head>

<body>
    <%
        //Obtiene la sesion al usuario principal
        HttpSession sesion = request.getSession();
        String usuarioPrincipal = (String) sesion.getAttribute("sesionIniciada");
        
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
                <a href="../view/seguimientoPago.jsp">
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
            <%}
                else if (rangoPrincipal.equals("PROFESOR")){
                    sesion.getAttribute("gruposId");
            %>
            <li>
                <a href="../view/menuTeacher.jsp">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>


            <li>
                <a href="../view/actualizarCalificaciones.jsp">
                    <i class="fa-solid fa-school"></i> <br>
                    Información del grupo
                </a>
            </li>

            <li>
                <a href="../cerrarSesion">
                    <i class="fa-solid fa-right-from-bracket"></i> <br>
                    Cerrar Sesión
                </a>
            </li>
            <%}
            else {%>
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
            <div class="form-container1">
                <form action="../updateInfo" method="post">
                    <div class ="titulo-form">
                        <h1> 
                        <i class="fa-solid fa-arrows-rotate"></i><br> 
                        Actualizar Datos Personales 
                        </h1>
                    </div>
                <%
                    //Establece los datos del usuario a modificar
                    String idUserRequest = request.getParameter("idUser");
                    String usuario = String.valueOf(sesion.getAttribute("modificar"));
                    int usuarioModificar = 0;
                    String url = "";
                    if(idUserRequest != null)
                        usuarioModificar = Integer.parseInt(idUserRequest);
                    else
                        usuarioModificar = Integer.parseInt(usuario);

                    //Colocar todos los atributos en comun de las tablas
                    int idUser = 0;
                    String rangoModificar = "";                    
                    int idPrincipal = 0;
                    String aPaterno = "";
                    String aMaterno = "";
                    String nombre = "";
                    String birthdate = "";
                    String phone1 ="";
                    String email = "";
                    
                    //Inicializar todas las consultas que se generen
                    ArrayList <Students> datosAlumno = base.obtenerEstudiante(usuarioModificar);
                    String phone2 = "";
                    boolean sale_solo = false ;
                    int idprofesor = 0;
                    
                    ArrayList <Teachers> datosTeacher = base.obtenerTeacher(usuarioModificar);
                    String classroom = "";
                    int idgrupo = 0;
                    String status = "";
                    
                    ArrayList <Admin_school> datosAdministrador = base.obtenerAdministrador(usuarioModificar);
                    
                    if(datosAlumno != null && !datosAlumno.isEmpty()){
                        Iterator <Students> iter = datosAlumno.iterator();
                        Students per = null;
                        if(iter.hasNext()){
                            per = iter.next();
                            rangoModificar = "ESTUDIANTE";
                            idUser = per.getId_user_student();
                            idPrincipal = per.getId_student();
                            aPaterno = per.getApellido_paterno_student();
                            aMaterno = per.getApellido_materno_student();
                            nombre = per.getNombre_student();
                            birthdate = String.valueOf(per.getFecha_nacimiento_student());
                            phone1 = per.getTelefono1_student();
                            email = per.getEmail_student();
                            phone2 = per.getTelefono2_student();
                            sale_solo = per.isSale_solo();
                            idprofesor = per.getId_teacher_student();
                        }
                    }
                    
                    else if(datosTeacher != null && !datosTeacher.isEmpty()){
                        Iterator <Teachers> iter = datosTeacher.iterator();
                        Teachers per = null;
                        if(iter.hasNext()){
                            per = iter.next();
                            rangoModificar = "PROFESOR";
                            idUser = per.getId_user_teacher();
                            idPrincipal = per.getId_teacher();
                            aPaterno = per.getApellido_paterno_teacher();
                            aMaterno = per.getApellido_materno_teacher();
                            nombre = per.getNombre_teacher();
                            birthdate = String.valueOf(per.getFecha_nacimiento_teacher());
                            phone1 = per.getTelefono_teacher();
                            email = per.getEmail_teacher();
                            idgrupo = per.getId_group_teacher();
                            status = per.getStatus_teacher();
                            classroom = per.getClassroom_teacher();
                        }
                    }
                    
                    else {
                        Iterator <Admin_school> iter = datosAdministrador.iterator();
                        Admin_school per = null;
                        if(iter.hasNext()){
                            per = iter.next();
                            rangoModificar = "ADMINISTRADOR";
                            idUser = per.getId_user_admin();
                            idPrincipal = per.getId_admin();
                            aPaterno = per.getApellido_paterno_admin();
                            aMaterno = per.getApellido_materno_admin();
                            nombre = per.getNombre_admin();
                            birthdate = String.valueOf(per.getFecha_nacimiento_admin());
                            phone1 = per.getTelefono_admin();
                            email = per.getEmail_admin();
                            url = "../view/menuAdministrador.jsp";
                        }
                    }
                %>
                
                    <input type = "hidden" name = "iduser" id="iduser" value = "<%=idUser%>">
                    <input type = "hidden" name = "rango" id="rango" value = "<%=rangoModificar%>">
                
                    <div>
                        <input type = "hidden" name = "idprincipal" id="idprincipal" value = "<%=idPrincipal%>">
                        <label for="apaterno">
                            Apellido Paterno: 
                        </label>
                        <input type="text" name="apaterno" id="apaterno" required value ="<%=aPaterno%>">

                        <label for="amaterno">
                            Apellido Materno: 
                        </label>
                        <input type="text" name="amaterno" id="amaterno" required value ="<%=aMaterno%>">

                       <label for="nombre">
                            Nombre(s): 
                        </label>
                        <input type="text" name="nombre" id="nombre" required value ="<%=nombre%>">

                        <label for="birthdate">
                            Fecha de Nacimiento: 
                        </label>
                        <input type="date" name="birthdate" id="birthdate" required value ="<%=birthdate%>">

                        <label for="phone1">
                            Telefono: 
                        </label>
                        <input type="text" name="phone1" id="phone1" value ="<%=phone1%>" maxlength = 10>

                        <label for="email">
                            Correo Electronico: 
                        </label>

                        <input type="email" name="email" id="email" value ="<%=email%>">
                        <%
                            if (rangoModificar.equals("ESTUDIANTE")){
                                if(rangoPrincipal.equals("ADMINISTRADOR")){
                                    url = "../view/listaAlumnos.jsp";
                                }
                                else{
                                    url = "../view/menuAlumno.jsp"; 
                                }
                        %>
                    </div>
                    
                    <div>
                        <label for="phone2">
                            Telefono extra: 
                        </label>
                        <input type="text" name="phone2" id="phone2" value ="<%=phone2%>" maxlength = 10>
                        
                        <label for="sale_solo">
                            ¿Sale solo?: 
                        </label>
                        <select name="sale_solo" id="sale_solo">
                            <option value = "1" <%=sale_solo ? "selected" : ""%>> SI </option>
                            <option value = "0" <%=!sale_solo ? "selected" : ""%>> NO </option>
                        </select>
                    </div>

                    <%if (!rangoPrincipal.equals("ESTUDIANTE")){%>
                    <div>
                        <label for="idprofesor">
                            Grupo Asignado: 
                        </label>
                        <select name="idprofesor" id="idprofesor">
                            <option value = "0" <%=idprofesor == 0 ? "selected" : ""%>> Ningun Grupo Asignado </option>
                            <%
                                ArrayList <ConsultaTeacher> profesores = base.obtenerDatosProfesores();
                                Iterator <ConsultaTeacher> iter3 = profesores.iterator();
                                ConsultaTeacher per3 = null;
                                while(iter3.hasNext()){
                                    per3 = iter3.next();
                                    String datosProfesor = "";
                                    int idSelect = per3.getId_teacher();
                                    String grupoSelect = base.concatenarDatosGrupo(idSelect) ;
                                    String nombreProfesorSelect = base.concatenarDatosProfesor(idSelect);
                            %>
                                <option value = "<%=idSelect%>" <%=idSelect == idprofesor ? "selected" : ""%>> <%=grupoSelect + "    -->    " + nombreProfesorSelect%> </option>
                            <%}%>
                        </select>
                    </div>
                
                <%} else{%>
                    <input type = "hidden" name = "idprofesor" id="idprofesor" value = "<%=idprofesor%>">
                <%}
                }
                    else if(rangoModificar.equals("PROFESOR")){
                        if(rangoPrincipal.equals("ADMINISTRADOR")){
                            url = "../view/listaTeachers.jsp";
                %>
                    <div>
                        <label for="status">
                            Estatus del Profesor: 
                        </label>
                        <select name="status" id="status">
                            <option value = "VIGENTE" <%=status.equals("VIGENTE") ? "selected" : ""%>> VIGENTE </option>
                            <option value = "BAJA" <%=status.equals("BAJA") ? "selected" : ""%>> BAJA </option>
                        </select>
                        
                        <label for="grupo">
                            Grupo Asignado: 
                        </label>
                        
                        <select name="grupo" id="grupo">
                            <option value = "0" <%=idprofesor == 0 ? "selected" : ""%>> Ningun Grupo Asignado </option>
                            <%
                                ArrayList <ConsultaGrupos> grupos = base.obtenerDatosGrupos();
                                Iterator <ConsultaGrupos> iter2 = grupos.iterator();
                                ConsultaGrupos per2 = null;
                                while(iter2.hasNext()){
                                    per2 = iter2.next();
                                    String datosGrupo = per2.getDescription_grade() + " " + 
                                    per2.getLevel_group() + ": " + per2.getDescription_category();
                            %>
                                <option value = "<%=per2.getId_group()%>" <%=per2.getId_group() == idgrupo ? "selected" : ""%>> <%=datosGrupo%> </option>
                            <%}%>
                        </select>

                        <label for="status">
                            Salon de Clases: 
                        </label>
                        <input type="text" name="classroom" id="classroom" value ="<%=classroom%>" >
                        <%}
                        else {
                            url = "../view/menuTeacher.jsp";%>
                            <input type="hidden" name="status" id="status" value ="<%=status%>" >
                            <input type="hidden" name="grupo" id="grupo" value ="<%=idgrupo%>" >
                            <input type="hidden" name="classroom" id="classroom" value ="<%=classroom%>" >
                        <%}%>
                    </div>
                <%}%>
                
                <div id = "button">
                    <div id = "modificar"> 
                        <button type = "button" name = "update" id ="update" onclick = "showAlertUpdate()">
                        <i class="fa-solid fa-pen"></i><br>
                        Actualizar Datos
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