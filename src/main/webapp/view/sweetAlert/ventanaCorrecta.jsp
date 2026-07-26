<%
    //Establece los atributos de sesion relacionados al Inicio de sesion
    String contraseñaCorrecta = (String) sesion.getAttribute("contraseñaCorrecta");
    String mensajeUsuario = (String) sesion.getAttribute("userNameRegistrado");
    
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
    //Establece que no hay mensaje de error
    sesion.setAttribute("contraseñaCorrecta", null);
    sesion.setAttribute("userNameRegistrado", null);
%>