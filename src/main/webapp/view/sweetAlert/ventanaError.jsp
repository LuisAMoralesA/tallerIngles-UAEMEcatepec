<%
    //Busca si se trato de autenticarse pero no coincidian los datos
    String errorMensaje = (String) sesion.getAttribute("errorMessage");
    //Si esta accion es detectada, se abre el cuadro de texto diciendo el error de autenticación
    if (errorMensaje != null && !errorMensaje.isEmpty()){
%>
    <script>
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "<%= errorMensaje %>",
            confirmButtonColor: "#9C8412"
          });
    </script>
<%}
    //Establece que no hay mensaje de error
    sesion.setAttribute("errorMensaje", null);
%>