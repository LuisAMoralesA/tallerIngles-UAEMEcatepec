<%@page import="java.sql.*" %>
<%@page import="com.mysql.jdbc.Driver" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="controller.*"%>
<!doctype html>
<html lang = "es">
    
    <head>
        <meta charset = "UTF-8">
        <meta name = "viewport" content = "width-device-width, initial-scale = 1.0">
        <title> Iniciar Sesion </title>
        <link rel = "stylesheet" href = "/tallerDeInglesUAEM/css/style_login.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  
        <link href = "/tallerDeInglesUAEM/Images/uaem.png" rel = "icon"/>
        <!--Librerias para alertas emergentes-->
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.all.min.js"></script>
        <!--Link para visualizar alertas
            https://sweetalert2.github.io/-->
    </head>

    <body class = "body-student">
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
                            <a href="/tallerDeInglesUAEM/view/loginAlumno.jsp" style = "color: rgb(255, 213, 1)"> Alumnos </a>  
                        </li>
                        <li> 
                            <a href="/tallerDeInglesUAEM/view/loginTeacher.jsp"> Profesores </a>  
                        </li>
                        <li> 
                            <a href="/tallerDeInglesUAEM/view/loginAdministrador.jsp"> Administrativos </a>  
                        </li>
                    </ul>
                </nav>
            </div>
        </header>

        <main>
            <div class="contenedor_form">
                <div class="caja_trasera">
                    <div class="caja_trasera_login">
                        <h1> ¿Estas registrado en el sistema? </h1>
                        <p> Inicia sesion para ingresar </p>
                        <button id = "btn_ingresar" type="submit" name="submit"> Ingresar </button>
                    </div>
    
                    <div class="caja_trasera_register">
                        <h1> ¿Aún no tienes una cuenta? </h1>
                        <p> Crea una cuenta para iniciar sesión </p>
                        <button id = "btn_registro"> Registrarse </button>
                    </div>
                </div>
    
                <div class="contenedor_login_register">
                    <%//Este apartado usa el servlet loginStudent%>
                    <form class = "form_login" method = "POST" action = "../loginStudent" >
                        <h2> 
                            <i class="fa-solid fa-user-graduate"></i> <br>
                            Bienvenido, Alumno
                        </h2>
                        <p>Ingrese sus datos para iniciar Sesión</p>
                        <input type = "text" id="user" name="user" placeholder="Usuario" required = "required">
                        <input type = "password" id="pass" name="pass" placeholder="Contraseña" required = "required">
                        <button type="submit" id = "submit" name="submit"> Ingresar </button>
                        
                    </form>
                        <%
                            //Obtiene la sesion que se encuentra en el navegador
                            HttpSession sesion = request.getSession();
                            //Busca si se trato de autenticarse pero no coincidian los datos
                            String errorMessage = (String) sesion.getAttribute("errorMessage");
                            //Si esta accion es detectada, se abre el cuadro de texto diciendo el error de autenticación
                            if (errorMessage != null && !errorMessage.isEmpty()){
                        %>
                        <script>
                            Swal.fire({
                                icon: "error",
                                title: "Error de Autenticación",
                                text: "<%= errorMessage %>",
                                confirmButtonColor: "#9C8412"
                              });
                        </script>
                        <%}
                        //Establece que no hay mensaje de error
                        sesion.setAttribute("errorMessage", null);
                        sesion.setAttribute("sesionIniciada", null);
                        %>
                        
                    <%//Este apartado usa el servlet registerStudent%>
                    <form class = "form_register" method = "POST" action = "../registerStudent">
                        <h2> Registrarse </h2>
                        <p> Por favor, rellene los siguientes campos para registrarse al taller. </p>
                        <input type = "text" name = "apaterno" id = "apaterno" placeholder="Apellido Paterno" required>
                        <input type = "text" name = "amaterno" id = "amaterno" placeholder="Apellido Materno" required>
                        <input type = "text" name = "name" id = "name" placeholder="Nombre" required>
                        <input type = "text" name = "phone" id = "phone" placeholder="Numero de Telefono" maxlength = 10 required>
                        <input type = "date" name = "birthdate" id = "birthdate" title="Fecha de nacimiento" class = "date" required>
                        <input type = "text" name = "email" id = "email" placeholder="Correo Electronico" required>
                        <input type = "password" name = "password1" id = "password1" placeholder="Contraseña" required>
                        <input type = "password" name = "password2" id = "password2" placeholder="Confirmar Contraseña" required>
                        <button type="submit" name="add" id = "add" onclick = "showAlertAdd()"> Concluir registro </button>
                    </form>
                        <%
                            //Establece los atributos de sesion relacionados al Inicio de sesion
                            String contraseñaCorrecta = (String) sesion.getAttribute("contraseñaCorrecta");
                            String mensajeUsuario = (String) sesion.getAttribute("userNameRegistrado");
                            String contraseñaIncorrecta = (String) sesion.getAttribute("contraseñaIncorrecta");
                            //Si la contraseña es correcta, entonces se dirige a la siguiente pantalla
                            if (contraseñaCorrecta != null && !contraseñaCorrecta.isEmpty()){
                        %>
                        <script>
                            Swal.fire({
                                icon: "success",
                                title: "<%= contraseñaCorrecta %>",
                                text: "<%= mensajeUsuario %>"
                                //confirmButtonColor: "#2C5243"
                              });
                        </script>
                        <%}
                        //De lo contrario, si es incorrecta manda el mensaje en pantalla
                        else if (contraseñaIncorrecta!=null && !contraseñaIncorrecta.isEmpty()){%>
                        <script>
                            Swal.fire({
                                icon: "error",
                                title: "Contraseñas incorrectas.",
                                text: "<%= contraseñaIncorrecta %>"
                                //confirmButtonColor: "#2C5243"
                              });
                        </script>
                        <%}
                        sesion.setAttribute("contraseñaCorrecta", null);
                        sesion.setAttribute("userNameRegistrado", null);
                        sesion.setAttribute("contraseñaIncorrecta", null);%>
                </div> 
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
    <script src = "/tallerDeInglesUAEM/js/prueba.js"></script>
    <script src = "/tallerDeInglesUAEM/js/mensajesEmergentes.js"></script>
    </body>
    
</html>