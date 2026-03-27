<%@page import="java.sql.*" %>
<%@page import="com.mysql.jdbc.Driver" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="controller.*"%>
<!doctype html>
<html lang = "es">
    <head>
        <meta charset = "UTF-8">
        <meta name = "viewport" content = "width-device-width, initial-scale = 1.0">
        <title> <%=Constantes.TITULO_LOGIN_TEACHER%> </title>
        <link rel = "stylesheet" href = "/tallerDeInglesUAEM/css/style_login.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link href = "/tallerDeInglesUAEM/Images/uaem.png" rel = "icon"/>
        <!--Librerias para alertas emergentes-->
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.all.min.js"></script>
        <!--Link para visualizar alertas
            https://sweetalert2.github.io/-->
    </head>

    <body class = "body-teacher">  
        <header class = "header">
            <div class="menu container">
                <a href="https://cuecatepec.uaemex.mx/" class="logo-navbar">
                    <img src="/tallerDeInglesUAEM/Images/uaem.png" class = "logo-navbar" alt="" >
                </a>
    
                <input type = "checkbox" id = "menu" />
                <label for="menu"> 
                    <img src="/tallerDeInglesUAEM/Images/Menu.png" class = "menu-icono" alt="">
                </label>
    
                <nav class = "navbar">
                    <ul>
                        <li>
                            <a href="../index.html"> Inicio </a>
                        </li>

                        <li>
                            <a href="/tallerDeInglesUAEM/view/loginAlumno.jsp"> Alumnos </a>  
                        </li>
                        <li> 
                            <a href="/tallerDeInglesUAEM/view/loginTeacher.jsp" style = "color: rgb(255, 213, 1)"> Profesores </a>  
                        </li>
                        <li> 
                            <a href="/tallerDeInglesUAEM/view/loginAdministrador.jsp"> Administrativos </a>  
                        </li>
                    </ul>
                </nav>
            </div>
        </header>
        
        <main>               
            <br><br>
            <div class="contenedor_login_register1">
                <%//Este apartado usa el servlet loginTeacher%>
                <form class = "form_login1" method = "POST" action = "../loginTeacher">
                    <h2> 
                        <i class="fa-solid fa-person-chalkboard"></i> <br>
                        Bienvenido, Profesor 
                    </h2>
                    <p>Ingrese sus datos para iniciar Sesión</p>
                    <input type = "text" id="user" name="user"placeholder="Usuario">
                    <input type = "password" id="pass" name="pass" placeholder="Contraseña">
                    <button type="submit" id = "submit" name="submit"> Ingresar </button>
                </form>
                        <%
                            //Obtiene la sesion que se encuentra en el navegador
                            HttpSession sesion = request.getSession();
                            //Busca si se trato de autenticarse pero no coincidian los datos
                            String mensaje = (String) sesion.getAttribute("errorMessage");
                            //Si esta accion es detectada, se abre el cuadro de texto diciendo el error de autenticación
                            if (mensaje != null && !mensaje.isEmpty()){
                        %>
                        <script>
                            Swal.fire({
                                icon: "error",
                                title: "Oops...",
                                text: "<%= mensaje %>",
                                confirmButtonColor: "#9C8412"
                              });
                        </script>
                        <%}
                        //Establece que no hay mensaje de error
                        sesion.setAttribute("errorMessage", null);%>
            </div> 
        </main>

        <footer class="footer" > 
        <div class="ubicacion">
            <p class ="p-mapa">
            <i class="fas fa-map-marker-alt"></i>
                <b> Calle:  </b> Jose Revueltas No. 17
                <br>
                <b>Colonia: </b> Tierra Blanca
                <br>
                Ecatepec de Morelos
                <br>
                <i>55020</i>
            </p>
        </div>
        

        <div class="links">
            <ul>
                <li>
                    <a href="https://www.facebook.com/TallerdeInglesEcatepec" target="_blank" rel="noopener noreferrer">
                        <i class="fa-brands fa-facebook"></i>&nbsp; 
                        Taller de Ingles para Niños y Adolescentes 
                    </a>
                </li>

                <li>
                    <a href="mailto:tallerdeinglesuaem@gmail.com">
                        <i class="fa-brands fa-square-instagram"></i>&nbsp; 
                        tallerdeinglesuaem@gmail.com
                    </a>         
                </li>
            </ul>
        </div>
    </footer>
    </body>
</html>